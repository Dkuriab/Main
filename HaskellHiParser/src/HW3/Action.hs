module HW3.Action where

import Control.Exception (throw)
import Control.Monad (ap, join, liftM)
import qualified Data.ByteString as BS
import Data.Sequence (fromList)
import Data.Set (Set, member)
import Data.Text (pack)
import Data.Text.Encoding (decodeUtf8')
import GHC.Exception (Exception)
import HW3.Base (HiAction (..), HiMonad (..), HiValue (..))
import System.Directory (createDirectory, doesFileExist, getCurrentDirectory, listDirectory, setCurrentDirectory)
import Data.Time.Clock (getCurrentTime)

data HiPermission
  = AllowRead
  | AllowWrite
  | AllowTime
  deriving (Bounded, Enum, Show, Eq, Ord)

newtype PermissionException
  = PermissionRequired HiPermission
  deriving (Show)

instance Exception PermissionException

newtype HIO a = HIO {runHIO :: Set HiPermission -> IO a}

instance Monad HIO where
  return a = HIO $ \_ -> return a
  m >>= k = HIO $ \env -> do
    v <- runHIO m env
    runHIO (k v) env

instance Functor HIO where
  fmap = liftM

instance Applicative HIO where
  pure = return
  (<*>) = ap

instance HiMonad HIO where
  runAction (HiActionRead path) = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permission =
        if member AllowRead permission
          then do
            isExists <- doesFileExist path
            if isExists
              then do
                bs <- BS.readFile path
                case decodeUtf8' bs of
                  (Left _) -> return (HiValueBytes bs)
                  (Right inheritance) -> return (HiValueString inheritance)
              else do
                files <- listDirectory path
                return (HiValueList (fromList (map (HiValueString . pack) files)))
          else throw (PermissionRequired AllowRead)
  runAction (HiActionWrite path bs) = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permissions =
        if member AllowWrite permissions
          then do
            BS.writeFile path bs
            return HiValueNull
          else throw (PermissionRequired AllowWrite)
  runAction (HiActionMkDir path) = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permissions =
        if member AllowWrite permissions
          then do
            createDirectory path
            return HiValueNull
          else throw (PermissionRequired AllowWrite)
  runAction (HiActionChDir path) = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permissions =
        if member AllowRead permissions
          then do
            setCurrentDirectory path
            return HiValueNull
          else throw (PermissionRequired AllowRead)
  runAction HiActionCwd = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permissions =
        if member AllowWrite permissions
          then do
            x <- getCurrentDirectory
            return (HiValueString (pack x))
          else throw (PermissionRequired AllowWrite)
          
  runAction HiActionNow = HIO go
    where
      go :: Set HiPermission -> IO HiValue
      go permissions =
        if member AllowTime permissions
          then do
            x <- getCurrentTime
            return (HiValueTime x)
          else throw (PermissionRequired AllowTime)
