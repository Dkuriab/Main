import math.LexicalAnalyzer;
import math.Token;
import math.TypeToken;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class AbstractLexerTest {

    protected static Token LEFT_BRACE = new Token(TypeToken.OPEN, "(");
    protected static Token RIGHT_BRACE = new Token(TypeToken.CLOSE, ")");
    protected static Token ADD = new Token(TypeToken.PLUS, "+");
    protected static Token SUB = new Token(TypeToken.MINUS, "-");
    protected static Token MUL = new Token(TypeToken.MUL, "*");
    protected static Token DIVISION = new Token(TypeToken.DIV, "/");
    protected static Token FUN = new Token(TypeToken.SQR, "sqr");
    protected static Token POW = new Token(TypeToken.POW, "**");

    protected static Token num(int number) {
        return new Token(TypeToken.NUM, String.valueOf(number));
    }

    protected void test(String input, List<Token> expectedTokens) {
        LexicalAnalyzer lexer = new LexicalAnalyzer(input);
        lexer.nextToken();
        for (var token : expectedTokens) {
            assertEquals(lexer.getToken(), token);
            lexer.nextToken();
        }
    }
}
