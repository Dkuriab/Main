import math.LexicalAnalyzer;
import math.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest extends AbstractParserTest {
    @Test
    public void numberTest() {
        test("12345", 12345);
    }

    @Test
    public void numberInParensTest() {
        test("(((123)))", 123);
    }

    @Test
    public void okString_3() {
        test("-33 * (7 - 4) + 2", -33 * (7 - 4) + 2);
    }

    @Test
    public void okString_5() {
        test("1 * 2 *3 *4 *5 / 2/3", 20);
    }

    @Test
    public void okString_6() {
        test("-33 * 2", -66);
    }


    @Test
    public void power() {
        test("2 ** 2 ** 2", 16);
    }

    @Test
    public void sqr() {
        test("1 + sqr(12 / 2 * 2 / 2 + 6)", 145);
    }
}
