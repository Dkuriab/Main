import math.LexicalAnalyzer;
import math.Parser;
import org.junit.jupiter.api.Assertions;

public class AbstractParserTest {
    public void test(String expression, int expectedResult) {
        Assertions.assertEquals(expectedResult, new Parser(new LexicalAnalyzer(expression)).expr().val);
    }
}
