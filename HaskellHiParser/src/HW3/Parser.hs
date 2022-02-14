module HW3.Parser where

import Control.Monad.Combinators.Expr (Operator(..), makeExprParser)
import Data.Void (Void)
import HW3.Base
import Text.Megaparsec (ParseErrorBundle, Parsec, between, choice, runParser, sepBy, some, try, (<|>), notFollowedBy, manyTill, many, count)
import Text.Megaparsec.Char (char, space1, string)
import Text.Megaparsec.Char.Lexer (symbol)
import qualified Text.Megaparsec.Char.Lexer as L
import Data.Text (pack)
import Data.Sequence (fromList)
import qualified Data.ByteString as BS

type Parser = Parsec Void String

parse :: String -> Either (ParseErrorBundle String Void) HiExpr
parse = runParser builtHiExprParser ""

skipSpace :: Parser ()
skipSpace =
  L.space
    space1
    (L.skipLineComment ";;")
    (L.skipBlockCommentNested "/*" "*/")

lexeme :: Parser a -> Parser a
lexeme x = skipSpace *> x <* skipSpace

parens :: Parser a -> Parser a
parens = between (L.symbol skipSpace "(") (L.symbol skipSpace ")")

squareParens :: Parser a -> Parser a
squareParens = between (L.symbol skipSpace "[") (L.symbol skipSpace "]")

bytesParens :: Parser a -> Parser a
bytesParens = between (string "[#" <* skipSpace) (skipSpace *> string "#]")

op :: String -> Parser String
op n = (lexeme . try) (string n <* notFollowedBy (char '='))

integerParser :: Parser HiValue
integerParser = HiValueNumber . toRational <$> lexeme (L.signed skipSpace L.scientific)

boolParser :: Parser HiValue
boolParser = HiValueBool <$> lexeme (False <$ string "false" <|> True <$ string "true")

nullParser :: Parser HiValue
nullParser = HiValueNull <$ string "null"

stringParser :: Parser HiValue
stringParser = HiValueString . pack <$> lexeme (char '"' *> manyTill L.charLiteral (symbol skipSpace "\""))

listParser :: Parser HiValue
listParser = HiValueList . fromList <$> squareParens (hiValueParser `sepBy` symbol skipSpace ",")

hexCharParser :: Parser Char
hexCharParser = choice (map char "0123456789abcdef")

bytesParser :: Parser HiValue
bytesParser = HiValueBytes . BS.pack . map (read . ("0x" <>)) <$> bytesParens (many (count 2 hexCharParser <* notFollowedBy hexCharParser <* skipSpace))

cwdParser :: Parser HiValue
cwdParser = HiValueAction HiActionCwd <$ lexeme (string "cwd")

nowParser :: Parser HiValue
nowParser = HiValueAction HiActionNow <$ lexeme (string "now")


hiFunParser :: Parser HiValue
hiFunParser =
  HiValueFunction <$> choice
    [ HiFunAdd              <$ lexeme (string "add"),                 -- T1
      HiFunMul              <$ lexeme (string "mul"),
      HiFunDiv              <$ lexeme (string "div"),
      HiFunSub              <$ lexeme (string "sub"),
      HiFunNot              <$ lexeme (string "not"),                 -- T2
      HiFunAnd              <$ lexeme (string "and"),
      HiFunOr               <$ lexeme (string "or"),
      HiFunLessThan         <$ lexeme (string "less-than"),
      HiFunGreaterThan      <$ lexeme (string "greater-than"),
      HiFunEquals           <$ lexeme (string "equals"),
      HiFunNotLessThan      <$ lexeme (string "not-less-than"),
      HiFunNotGreaterThan   <$ lexeme (string "not-greater-than"),
      HiFunNotEquals        <$ lexeme (string "not-equals"),
      HiFunIf               <$ lexeme (string "if"),
      HiFunLength           <$ lexeme (string "length"),              -- T4
      HiFunToUpper          <$ lexeme (string "to-upper"),
      HiFunToLower          <$ lexeme (string "to-lower"),
      HiFunReverse          <$ lexeme (string "reverse"),
      HiFunTrim             <$ lexeme (string "trim"),
      HiFunList             <$ lexeme (string "list"),                -- T5
      HiFunRange            <$ lexeme (string "range"),
      HiFunFold             <$ lexeme (string "fold"),
      HiFunPackBytes        <$ lexeme (string "pack-bytes"),          -- T6
      HiFunUnpackBytes      <$ lexeme (string "unpack-bytes"),
      HiFunEncodeUtf8       <$ lexeme (string "encode-utf8"),
      HiFunDecodeUtf8       <$ lexeme (string "decode-utf8"),
      HiFunZip              <$ lexeme (string "zip"),
      HiFunUnzip            <$ lexeme (string "unzip"),
      HiFunSerialise        <$ lexeme (string "serialise"),
      HiFunDeserialise      <$ lexeme (string "deserialise"),
      HiFunRead             <$ lexeme (string "read"),                -- T7
      HiFunWrite            <$ lexeme (string "write"),
      HiFunMkDir            <$ lexeme (string "mkdir"),
      HiFunChDir            <$ lexeme (string "cd"),
      HiFunParseTime        <$ lexeme (string "parse-time")
    ]

table :: [[Operator Parser HiExpr]]
table =
  [ [ binary "*"  HiFunMul,
      binary "/"  HiFunDiv
    ],
    [ binary "+"  HiFunAdd,
      binary "-"  HiFunSub
    ],
    [ binary "<"  HiFunLessThan,
      binary ">"  HiFunGreaterThan,
      binary ">=" HiFunNotLessThan,
      binary "<=" HiFunNotGreaterThan,
      binary "==" HiFunEquals,
      binary "/=" HiFunNotEquals ],
    [ binary "&&" HiFunAnd
    ],
    [ binary "||" HiFunOr
    ]
  ]

binary :: String -> HiFun -> Operator Parser HiExpr
binary name f = InfixL (generateBinaryOperator f <$ op name)

hiValueParser :: Parser HiValue
hiValueParser =
  choice
    [ integerParser,
      hiFunParser,
      boolParser,
      nullParser,
      stringParser,
      try listParser,
      bytesParser,
      cwdParser,
      nowParser
    ]

data Args
  = ArgsList [HiExpr]
  | ExclamationSign

argumentsParser :: Parser [Args]
argumentsParser = some
   (
       ArgsList <$> parens (builtHiExprParser `sepBy` symbol skipSpace ",")
   <|> ExclamationSign <$ lexeme (char '!')
   )

hiExprParser :: Parser HiExpr
hiExprParser =
  choice
    [ try $ parens builtHiExprParser,
      try $ foldl verySmartLambda <$> (HiExprValue <$> hiValueParser) <*> argumentsParser,
      try $ HiExprValue <$> hiValueParser
    ] where

  verySmartLambda :: HiExpr -> Args -> HiExpr
  verySmartLambda expr ExclamationSign = HiExprRun expr
  verySmartLambda expr (ArgsList args) = HiExprApply expr args

generateBinaryOperator :: HiFun -> HiExpr -> HiExpr -> HiExpr
generateBinaryOperator fun left right = HiExprApply (HiExprValue (HiValueFunction fun)) [left, right]

builtHiExprParser :: Parser HiExpr
builtHiExprParser = makeExprParser (skipSpace *> hiExprParser) table