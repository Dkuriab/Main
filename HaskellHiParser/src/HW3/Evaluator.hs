module HW3.Evaluator where

import Control.Monad.Trans.Except
import HW3.Base
import Data.Text (index, singleton, length, singleton, pack, take, drop, toUpper, toLower, reverse, strip, unpack)
import Data.Semigroup (stimes)
import Data.Sequence (fromList, Seq ((:<|)), length, reverse, index, drop, take)
import Data.Foldable (foldlM, toList)
import qualified Data.ByteString as BS
import Data.Word (Word8)
import Data.Ratio (denominator)
import Data.Text.Encoding (encodeUtf8, decodeUtf8')
import Codec.Compression.Zlib.Internal (defaultCompressParams, compressLevel, bestCompression, defaultDecompressParams)
import Codec.Compression.Zlib (CompressionLevel(BestCompression), compressWith, decompressWith)
import Codec.Serialise (deserialiseOrFail, serialise)
import Data.ByteString.Lazy (fromStrict, toStrict)
import Control.Monad.Trans.Class (lift)
import Text.Read (readMaybe)
import Data.Time.Clock (addUTCTime, diffUTCTime)


type EvaluationMonad m = ExceptT HiError m HiValue

eval :: HiMonad m => HiExpr -> m (Either HiError HiValue)
eval x = runExceptT (evalHiExpr x)

evalHiExpr :: HiMonad m => HiExpr -> EvaluationMonad m
evalHiExpr (HiExprValue value) = pure value
evalHiExpr (HiExprApply fun args) = evalFun fun args
evalHiExpr (HiExprRun (HiExprValue (HiValueAction action))) = lift (runAction action)
evalHiExpr (HiExprRun expr) = do
  evaluatedExpr <- evalHiExpr expr
  case evaluatedExpr of
    (HiValueAction action) -> lift (runAction action)
    _ -> throwE HiErrorInvalidArgument

evalFun :: HiMonad m => HiExpr -> [HiExpr] -> EvaluationMonad m
evalFun fun args = do
  parsedFun <- evalHiExpr fun
  arguments <- mapM evalHiExpr args
  evalHiFun parsedFun arguments
  

evalHiFun :: HiMonad m => HiValue -> [HiValue] -> EvaluationMonad m

evalHiFun (HiValueNumber _) _ = throwE HiErrorInvalidFunction

evalHiFun HiValueNull _ = throwE HiErrorInvalidFunction

evalHiFun (HiValueBool _) _ = throwE HiErrorInvalidFunction

--------------------------------
--           Task 1           --
--------------------------------

evalHiFun (HiValueFunction HiFunAdd) arguments = case arguments of
  [HiValueNumber f, HiValueNumber s] -> return (HiValueNumber (f + s))
  [HiValueString a, HiValueString b] -> return (HiValueString (a <> b))
  [HiValueList a, HiValueList b] -> return (HiValueList (a <> b))
  [HiValueTime time, HiValueNumber x] -> return (HiValueTime ( addUTCTime (fromRational x) time))
  [HiValueNumber x, HiValueTime time] -> return (HiValueTime (addUTCTime (fromRational x) time))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunSub) arguments = case arguments of
  [HiValueNumber f, HiValueNumber s] -> return (HiValueNumber (f - s))
  [HiValueTime time, HiValueTime x] -> return (HiValueNumber(toRational $ diffUTCTime time x))

  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunMul) arguments = case arguments of
  [HiValueNumber f, HiValueNumber s] -> return (HiValueNumber (f * s))
  [HiValueString a, HiValueNumber b] -> return (HiValueString (stimes (round b) a))
  [HiValueList a, HiValueNumber b] -> return (HiValueList (stimes (round b) a))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunDiv) arguments = case arguments of
  [HiValueNumber _, HiValueNumber 0] -> throwE HiErrorDivideByZero
  [HiValueNumber f, HiValueNumber s] -> return (HiValueNumber (f / s))
  [HiValueString a, HiValueString b] -> return (HiValueString (a <> pack "/" <> b))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

--------------------------------
--           Task 2           --
--------------------------------

evalHiFun (HiValueFunction HiFunNot) arguments = case arguments of
  [HiValueBool x] -> return (HiValueBool (not x))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunAnd) arguments = case arguments of
  [HiValueBool x, HiValueBool y] -> return (HiValueBool (x && y))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunOr) arguments = case arguments of
  [HiValueBool x, HiValueBool y] -> return (HiValueBool (x || y))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunLessThan) arguments = case arguments of
  [x, y] -> return (HiValueBool (x < y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunGreaterThan) arguments = case arguments of
  [x, y] -> return (HiValueBool (x > y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunEquals) arguments = case arguments of
  [x, y] -> return (HiValueBool (x == y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunNotLessThan) arguments = case arguments of
  [x, y] -> return (HiValueBool (x >= y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunNotGreaterThan) arguments = case arguments of
  [x, y] -> return (HiValueBool (x <= y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunNotEquals) arguments = case arguments of
  [x, y] -> return (HiValueBool (x /= y))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunIf) arguments = case arguments of
  [HiValueBool condition, first, second] -> if condition
                                  then return first
                                  else return second
  [_, _, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch
  
--------------------------------
--           Task 4           --
--------------------------------

evalHiFun (HiValueString text) arguments = case arguments of
  [HiValueNumber pos] -> if pos < 0 || pos > toRational len
     then return HiValueNull
     else return $ HiValueString $ singleton (Data.Text.index text position) where
        position = mod (round pos) len
        len = Data.Text.length text

  [HiValueNumber start, HiValueNumber end] -> if start < 0 || start > toRational len || end < 0 || end > toRational len || end < start
                                              then return HiValueNull
                                              else return (HiValueString substring) where
                                                substring = Data.Text.drop (round start) (Data.Text.take (round end) text)
                                                len = Data.Text.length text

  [_] -> throwE HiErrorInvalidArgument
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunLength) arguments = case arguments of
  [HiValueList list] -> return (HiValueNumber (toRational (Data.Sequence.length list)))
  [HiValueString text] -> return (HiValueNumber (toRational (Data.Text.length text)))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunToUpper) arguments = case arguments of
  [HiValueString text] -> return (HiValueString (Data.Text.toUpper text))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunToLower) arguments = case arguments of
  [HiValueString text] -> return (HiValueString (Data.Text.toLower text))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunReverse) arguments = case arguments of
  [HiValueList list] -> return (HiValueList (Data.Sequence.reverse list))
  [HiValueString text] -> return (HiValueString (Data.Text.reverse text))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunTrim) arguments = case arguments of
  [HiValueString text] -> return (HiValueString (Data.Text.strip text))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

--------------------------------
--           Task 5           --
--------------------------------

evalHiFun (HiValueFunction HiFunList) arguments = return (HiValueList (fromList arguments))

evalHiFun (HiValueFunction HiFunRange) arguments = case arguments of
  [HiValueNumber start, HiValueNumber end] -> return (HiValueList (fromList (map HiValueNumber [start..end])))
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunFold) arguments = case arguments of
  [HiValueFunction fun, HiValueList (x :<| xs)] ->  foldlM (hiFunApply fun) x xs
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch
  
evalHiFun (HiValueList list) arguments = case arguments of
  [HiValueNumber pos] -> if pos < 0 || pos > toRational len
                         then throwE HiErrorInvalidArgument
                         else return (Data.Sequence.index list position) where
                            position = mod (round pos) len
                            len = Data.Sequence.length list

  [HiValueNumber start, HiValueNumber end] ->   if start < 0 || start > toRational len || end < 0 || end > toRational len || end < start
                                                then return HiValueNull
                                                else return (HiValueList sublist) where
                                                  sublist = Data.Sequence.drop (round start) (Data.Sequence.take (round end) list)
                                                  len = Data.Sequence.length list

  [_] -> throwE HiErrorInvalidArgument
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch
  
  
--------------------------------
--           Task 6           --
--------------------------------

evalHiFun (HiValueFunction HiFunPackBytes) arguments = case arguments of
  [HiValueList list] -> (HiValueBytes . BS.pack) . toList <$> mapM unpackHiNumberToWord8 list
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunUnpackBytes) arguments = case arguments of
  [HiValueBytes bs] -> return $ HiValueList $ fromList $ map (HiValueNumber . toRational) (BS.unpack bs)
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunEncodeUtf8) arguments = case arguments of
  [HiValueString text] -> return (HiValueBytes (encodeUtf8 text))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunDecodeUtf8) arguments = case arguments of -- decode-utf8([# c3 28 #]) evaluates to null (invalid UTF-8 byte sequence)
  [HiValueBytes bs] -> return result where
      result = case decodeUtf8' bs of
        (Left _) -> HiValueNull
        (Right str) -> HiValueString str
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunZip) arguments = case arguments of
  [HiValueBytes bs] -> return (HiValueBytes $ toStrict (compressWith (defaultCompressParams {compressLevel = bestCompression}) (fromStrict bs)))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunUnzip) arguments = case arguments of
  [HiValueBytes bs] -> return (HiValueBytes $ toStrict (decompressWith defaultDecompressParams (fromStrict bs)))
  [_] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunSerialise) arguments = case arguments of
  [x] -> return (HiValueBytes $ toStrict (serialise x))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunDeserialise) arguments = case arguments of
  [HiValueBytes x] -> result where
    result = case deserialiseOrFail (fromStrict x) of
      (Left _) -> throwE HiErrorInvalidArgument
      (Right val) -> return val
  _ -> throwE HiErrorArityMismatch
  
evalHiFun (HiValueBytes bs) arguments = case arguments of
  [HiValueNumber pos] -> if pos < 0 || pos > toRational len
                         then throwE HiErrorInvalidArgument
                         else return (HiValueNumber (fromIntegral (BS.unpack bs!!position))) where
                            position = mod (round pos) len
                            len = BS.length bs

  [HiValueNumber start, HiValueNumber end] ->   if start < 0 || start > toRational len || end < 0 || end > toRational len || end < start
                                                then return HiValueNull
                                                else return (HiValueBytes sublist) where
                                                  sublist = BS.drop (round start) (BS.take (round end) bs)
                                                  len = BS.length bs

  [_] -> throwE HiErrorInvalidArgument
  [_, _] -> throwE HiErrorInvalidArgument
  _ -> throwE HiErrorArityMismatch 
  
  
--------------------------------
--           Task 7           --
--------------------------------
  
evalHiFun (HiValueFunction HiFunRead) arguments = case arguments of
  [HiValueString text] -> return (HiValueAction (HiActionRead (unpack text)))
  _ -> throwE HiErrorArityMismatch
  
evalHiFun (HiValueFunction HiFunWrite) arguments = case arguments of
  [HiValueString text, HiValueBytes bs] -> return (HiValueAction (HiActionWrite (unpack text) bs))
  [HiValueString text, HiValueString bs] -> return (HiValueAction (HiActionWrite (unpack text) (encodeUtf8 bs)))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunMkDir) arguments = case arguments of
  [HiValueString text] -> return (HiValueAction (HiActionMkDir (unpack text)))
  _ -> throwE HiErrorArityMismatch
  
evalHiFun (HiValueFunction HiFunChDir) arguments = case arguments of
  [HiValueString text] -> return (HiValueAction (HiActionChDir (unpack text)))
  _ -> throwE HiErrorArityMismatch

evalHiFun (HiValueFunction HiFunParseTime) arguments = case arguments of
  [HiValueString text] -> return time where
    time = case readMaybe (unpack text) of
      Nothing -> HiValueNull
      (Just s) -> HiValueTime s
  _ -> throwE HiErrorArityMismatch
  
  
unpackHiNumberToWord8 :: HiMonad m => HiValue -> ExceptT HiError m Word8
unpackHiNumberToWord8 (HiValueNumber x) = if denominator x == 1 && x >= 0 && x <= 255
                                          then return (fromInteger (round x))
                                          else throwE HiErrorInvalidArgument
unpackHiNumberToWord8 _ = throwE HiErrorInvalidArgument

hiFunApply :: HiMonad m => HiFun -> HiValue -> HiValue -> EvaluationMonad m
hiFunApply fun a b = evalHiFun (HiValueFunction fun) [a, b]
