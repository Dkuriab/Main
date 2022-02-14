package parserGenerator;

import parserGenerator.data.LanguageGrammar;
import parserGenerator.data.NonTerminal;
import parserGenerator.data.Rule;

import java.util.*;

public class FirstAndFollowGenerator {
    private static final String EPSILON = "ε";

    private final List<Pair<NonTerminal, Rule>> rules;
    private final Map<String, Set<String>> first = new HashMap<>();
    private final Map<String, Set<String>> follow = new HashMap<>();


    public FirstAndFollowGenerator(LanguageGrammar grammar) {
        this.rules = grammar.getRules();

        if (rules.isEmpty()) {
            return;
        }

        constructFirst();
        constructFollow();
    }


    public Map<String, Set<String>> getFirst() {
        return first;
    }

    public Map<String, Set<String>> getFollow() {
        return follow;
    }

    public Set<String> getFirst(List<String> products) {
        if (products.isEmpty()) {
            return new HashSet<>(Set.of(EPSILON));
        }

        HashSet<String> res = new HashSet<>();
        for (var c : products) {
            if (c.matches("[A-Z]+")) {
                res.add(c);
            } else if (first.containsKey(c)) {
                res.addAll(first.get(c));
                if (first.get(c).contains(EPSILON)) {
                    continue;
                }
            }
            break;
        }
        return res;
    }

    private void constructFirst() {
        boolean changed = true;

        while (changed) {
            changed = false;

            for (var rule : rules) {
                final String name = rule.getFirst().name();
                int curSize = first.computeIfAbsent(name, k -> new HashSet<>()).size();
                first.get(name).addAll(getFirst(rule.getSecond().getTokens()));

                changed |= curSize < first.get(name).size();
            }
        }
    }

    private void constructFollow() {
        boolean change = true;

        follow.put(rules.get(0).getFirst().name(), new HashSet<>(Set.of("END")));

        while (change) {
            change = false;

            for (var rule : rules) {
                List<String> products = rule.getSecond().getTokens();
                String A = rule.getFirst().name();
                for (int i = 0; i < products.size(); i++) {
                    final String B = products.get(i);
                    if (!B.matches("[A-Z]+") && !B.equals(EPSILON)) {
                        int curSize = follow.computeIfAbsent(B, k -> new HashSet<>()).size();
                        Set<String> firstGamma = getFirst(products.subList(i + 1, products.size()));
                        if (firstGamma.contains(EPSILON)) {
                            follow.get(B).addAll(follow.computeIfAbsent(A, k -> new HashSet<>()));
                        }
                        firstGamma.remove(EPSILON);
                        follow.get(B).addAll(firstGamma);
                        change = change || curSize < follow.get(B).size();
                    }
                }
            }
        }
    }
}
