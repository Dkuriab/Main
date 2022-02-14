module HW3.Pretty where

import Data.Ratio (denominator, numerator)
import Data.Text hiding (foldl, map, intercalate)
import Prettyprinter (Doc, pretty)
import Prettyprinter.Render.Terminal (AnsiStyle)
import HW3.Base
import Prettyprinter.Internal (viaShow)
import Numeric (showHex)
import Data.Scientific (fromRationalRepetendUnlimited, formatScientific, FPFormat(Fixed))
import Data.Sequence (Seq)
import Data.Foldable (toList)
import Data.List
import qualified Data.ByteString as BS
import Data.Word (Word8)

prettyValue :: HiValue -> Doc AnsiStyle
prettyValue hiValue = case hiValue of
  (HiValueBool bool)      -> pretty $ toLower $ pack (show bool)
  (HiValueString str)     -> viaShow str
  (HiValueNumber number)  -> rationalPrettyPrinter number
  HiValueNull             -> viaShow "null"
  (HiValueFunction fun)   -> pretty (functionPrettyPrinter fun)
  (HiValueList list)      -> pretty (listPrettyPrinter list)
  (HiValueBytes bs)       -> pretty (bytesPrettyPrinter bs)
  (HiValueAction action)  -> pretty (actionsPrettyPrinter action)
  (HiValueTime time)      -> pretty ("parse-time" ++ "(\"" ++ show time ++ "\")")


rationalPrettyPrinter :: Rational -> Doc ann
rationalPrettyPrinter number
  | isInteger number = pretty (numerator number)
  | isDouble number = pretty $ formatScientific Fixed Nothing val
  | otherwise = pretty res
  where
    val = fst (fromRationalRepetendUnlimited number)

    zPart = truncate number
    fractionalPart = number - toRational zPart
    sign = if fractionalPart < 0 then "-" else "+"

    showFractional = show (numerator $ abs fractionalPart) ++ "/" ++ show (denominator fractionalPart)
    res =
      if zPart == 0
      then  if fractionalPart > 0
            then showFractional
            else sign ++ showFractional
      else show zPart ++ " " ++ sign ++ " " ++ showFractional

isDouble :: Rational -> Bool
isDouble rational = tenDividing (denominator rational)
  where
    tenDividing :: Integer -> Bool
    tenDividing 1 = True
    tenDividing x = if mod x 5 == 0 then tenDividing (div x 5)
                    else even x && tenDividing (div x 2)

isInteger :: Rational -> Bool
isInteger = (== 1) . denominator

actionsPrettyPrinter :: HiAction -> String
actionsPrettyPrinter (HiActionRead path) = "read(" ++ show path ++ ")"
actionsPrettyPrinter (HiActionWrite path bs) = "write(" ++ show path ++ bytesPrettyPrinter bs ++ ")"
actionsPrettyPrinter (HiActionMkDir path) = "mkdir(" ++ show path ++ ")"
actionsPrettyPrinter (HiActionChDir path) = "cd(" ++ show path ++ ")"
actionsPrettyPrinter HiActionCwd = show HiActionCwd
actionsPrettyPrinter HiActionNow = show HiActionNow

listPrettyPrinter :: Seq HiValue -> String
listPrettyPrinter list = "[" ++ intercalate ", " (map (show . prettyValue) (toList list)) ++ "]"

bytesPrettyPrinter :: BS.ByteString -> String
bytesPrettyPrinter bs   = "[# " ++ Data.List.unwords (map funMap (BS.unpack bs)) ++ " #]" where
  funMap :: Word8 -> String
  funMap x = if x < 16
             then "0" ++ showHex x ""
             else showHex x ""

functionPrettyPrinter :: HiFun -> String
functionPrettyPrinter fun = case fun of
      HiFunAdd              -> "add"                 -- T1
      HiFunMul              -> "mul"
      HiFunDiv              -> "div"
      HiFunSub              -> "sub"
      HiFunNot              -> "not"                 -- T2
      HiFunAnd              -> "and"
      HiFunOr               -> "or"
      HiFunLessThan         -> "less-than"
      HiFunGreaterThan      -> "greater-than"
      HiFunEquals           -> "equals"
      HiFunNotLessThan      -> "not-less-than"
      HiFunNotGreaterThan   -> "not-greater-than"
      HiFunNotEquals        -> "not-equals"
      HiFunIf               -> "if"
      HiFunLength           -> "length"              -- T4
      HiFunToUpper          -> "to-upper"
      HiFunToLower          -> "to-lower"
      HiFunReverse          -> "reverse"
      HiFunTrim             -> "trim"
      HiFunList             -> "list"                -- T5
      HiFunRange            -> "range"
      HiFunFold             -> "fold"
      HiFunPackBytes        -> "pack-bytes"          -- T6
      HiFunUnpackBytes      -> "unpack-bytes"
      HiFunEncodeUtf8       -> "encode-utf8"
      HiFunDecodeUtf8       -> "decode-utf8"
      HiFunZip              -> "zip"
      HiFunUnzip            -> "unzip"
      HiFunSerialise        -> "serialise"
      HiFunDeserialise      -> "deserialise"
      HiFunRead             -> "read"                -- T7
      HiFunWrite            -> "write"
      HiFunMkDir            -> "mkdir"
      HiFunChDir            -> "cd"
