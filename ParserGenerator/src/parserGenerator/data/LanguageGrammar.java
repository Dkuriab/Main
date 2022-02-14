package parserGenerator.data;

import parserGenerator.Pair;
import parserGenerator.data.NonTerminal;
import parserGenerator.data.Rule;

import java.util.ArrayList;
import java.util.List;

public class LanguageGrammar {
    private String name;

    private final List<Pair<String, String>> tokens;
    private final List<Pair<NonTerminal, Rule>> rules;

    public LanguageGrammar() {
        this.tokens = new ArrayList<>();
        this.rules = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pair<String, String>> getTokens() {
        return tokens;
    }

    public List<Pair<NonTerminal, Rule>> getRules() {
        return rules;
    }

    public void addToken(String name, String regexp) {
        tokens.add(new Pair<>(name, regexp));
    }

    public void putRule(NonTerminal nonTerminal, Rule rule) {
        rules.add(new Pair<>(nonTerminal, rule));
    }
}