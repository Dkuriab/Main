module Main where

import Control.Monad.IO.Class (liftIO)
import Data.Set (fromList)
import HW3.Action
import HW3.Base
import HW3.Evaluator
import HW3.Parser
import HW3.Pretty
import System.Console.Haskeline (InputT, defaultSettings, getInputLine, outputStrLn, runInputT)

main :: IO ()
main = runInputT defaultSettings loop
  where
    loop :: InputT IO ()
    loop = do
      minput <- getInputLine "hi> "
      case minput of
        Nothing -> return ()
        Just "quit" -> return ()
        Just input -> do
          x <- getAnswer input
          outputStrLn x
          loop

getAnswer :: String -> InputT IO String
getAnswer input = case parse input of
  (Left err) -> return (show err)
  (Right expr) -> do
    let monad = (eval expr :: HIO (Either HiError HiValue))
    ei <- liftIO $ runHIO monad (fromList [AllowRead, AllowWrite, AllowTime])
    return
      ( case ei of
          (Left err) -> show err
          (Right value) -> show (prettyValue value)
      )
