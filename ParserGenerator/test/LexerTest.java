import math.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerTest extends AbstractLexerTest {
    @Test
    public void number() {
        test("1", List.of(num(1)));
        test("2102301203", List.of(num(2102301203)));
    }


    @Test
    public void oneOperation() {
        test("1 + 2", List.of(num(1), ADD, num(2)));
        test("1 / 2", List.of(num(1), DIVISION, num(2)));
        test("1 * 2", List.of(num(1), MUL, num(2)));
        test("1 - 2", List.of(num(1), SUB, num(2)));
    }

    @Test
    public void function() {
        test("fun(  10 ) ",
            List.of(
                    FUN,
                    LEFT_BRACE,
                    num(10),
                    RIGHT_BRACE
            )
        );
    }

    @Test
    public void expressionWithDifferentOperations() {
        List<Token> tokens = List.of(
            FUN, LEFT_BRACE, num(100), ADD, num(101), RIGHT_BRACE,
            MUL,
            FUN,
            LEFT_BRACE,
            SUB, num(33), MUL,
            LEFT_BRACE, num(21), SUB, num(21), RIGHT_BRACE, ADD, num(2),
            RIGHT_BRACE
        );
        test(" fun (100 + 101)  *   fun(-33 * (21 - 21)  +     2)  ", tokens);
    }
}
