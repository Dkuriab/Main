import grammarParser.GrammarOfGrammarLexer;
import grammarParser.GrammarOfGrammarParser;
import main.CodePartsReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parserGenerator.data.LanguageGrammar;
import parserGenerator.data.NonTerminal;
import parserGenerator.data.Rule;
import parserGenerator.FirstAndFollowGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirstAndFollowTest {
    private static final String MATH_GRAMMAR = new CodePartsReader().getCodeFrom("src/main/expressionsGrammar.txt");
    private static FirstAndFollowGenerator firstAndFollowMath;
    private static LanguageGrammar mathGrammar;

    @BeforeAll
    public static void init() {
        mathGrammar = getGrammar();
        firstAndFollowMath = new FirstAndFollowGenerator(mathGrammar);
    }

    @Test
    public void checkFirstEntity() {
        // first(a) x first(b)
        Map<NonTerminal, List<Rule>> mapRules = getRules(mathGrammar);

        for (Map.Entry<NonTerminal, List<Rule>> nonTerminalRules : mapRules.entrySet()) {
            var outputs = nonTerminalRules.getValue();

            for (int i = 0; i < outputs.size(); i++) {
                for (int j = i + 1; j < outputs.size(); j++) {
                    var firstA = getFirst(outputs.get(i), firstAndFollowMath);
                    var firstB = getFirst(outputs.get(j), firstAndFollowMath);
                    Assertions.assertTrue(intersect(firstA, firstB).isEmpty());
                }
            }
        }
    }

    @Test
    public void checkFollowEntity() {
        // Follow(N) x first(b)
        Map<NonTerminal, List<Rule>> mapRules = getRules(mathGrammar);

        for (Map.Entry<NonTerminal, List<Rule>> nonTerminalRules : mapRules.entrySet()) {
            var outputs = nonTerminalRules.getValue();

            for (int i = 0; i < outputs.size(); i++) {
                for (int j = i + 1; j < outputs.size(); j++) {
                    var firstA = getFirst(outputs.get(i), firstAndFollowMath);
                    var firstB = getFirst(outputs.get(j), firstAndFollowMath);
                    if (!firstA.contains("ε")) continue;

                    var followN = firstAndFollowMath.getFollow().get(nonTerminalRules.getKey().name());
                    Assertions.assertTrue(intersect(followN, firstB).isEmpty());
                }
            }
        }
    }

    private Set<String> getFirst(Rule output, FirstAndFollowGenerator table) {
        return table.getFirst(output.getTokens());
    }

    private Map<NonTerminal, List<Rule>> getRules(LanguageGrammar grammar) {
        Map<NonTerminal, List<Rule>> mapRules = new HashMap<>();
        grammar.getRules()
            .forEach(rule -> mapRules.merge(rule.getFirst(), List.of(rule.getSecond()), (acc, val) -> {
                var res = new ArrayList<>(acc);
                res.addAll(val);
                return res;
            }));
        return mapRules;
    }

    private <T> Set<T> intersect(Set<T> a, Set<T> b) {
        Set<T> intersection = new HashSet<>(b);
        intersection.retainAll(a);
        return intersection;
    }

    private static LanguageGrammar getGrammar() {
        CharStream charStreamMath = CharStreams.fromString(FirstAndFollowTest.MATH_GRAMMAR);
        GrammarOfGrammarLexer lexerMath = new GrammarOfGrammarLexer(charStreamMath);
        GrammarOfGrammarParser parserMath = new GrammarOfGrammarParser(new CommonTokenStream(lexerMath));
        return parserMath.grammarOfGrammar().grammar;
    }
}
