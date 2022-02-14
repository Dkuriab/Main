package parserGenerator;

import main.CodePartsReader;
import parserGenerator.data.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ParserGenerator {
    private static final CodePartsReader codePartsReader = new CodePartsReader();
    private static final String EXCEPTION_CODE = codePartsReader.getCodeFrom("src/codeParts/exception.txt");
    private static final String TREE_CODE = codePartsReader.getCodeFrom("src/codeParts/tree.txt");

    private final LanguageGrammar grammar;
    private final FirstAndFollowGenerator firstAndFollowGenerator;
    private final Path path;

    public ParserGenerator(LanguageGrammar grammar, String path) {
        this.grammar = grammar;
        this.firstAndFollowGenerator = new FirstAndFollowGenerator(grammar);
        this.path = Path.of(path, grammar.getName());
    }

    public void generate() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new ParserGenerationException("Can't create directories");
        }
        writeClassCode(EXCEPTION_CODE, "ParseException.java");
        writeClassCode(TREE_CODE, "Tree.java");
        generateParser();
    }

    private void writeClassCode(String builder, String nameClass) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path.resolve(nameClass))) {
            bufferedWriter.write(String.format("""
                package %s;
                                    
                """, grammar.getName()));
            bufferedWriter.write(builder);
        } catch (IOException e) {
            throw new ParserGenerationException("Can't create " + nameClass + ".");
        }
    }

    private void generateParser() {
        Map<NonTerminal, List<Rule>> map = grammar
            .getRules()
            .stream()
            .collect(Collectors.groupingBy(Pair::getFirst,
                Collectors.mapping(Pair::getSecond,
                    Collectors.toList()))
            );

        StringBuilder builder = new StringBuilder();
        String builderHeadParser = codePartsReader.getCodeFrom("src/codeParts/parserHeader.txt");

        builder.append(builderHeadParser);

        generateFun(map, builder);
        generateClasses(map.keySet(), builder);

        writeClassCode(builder + "\n }", "Parser.java");
    }

    private String nameClass(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private String getCode(String code) {
        return code == null ? "" : code.substring(1, code.length() - 1).trim();
    }

    private void caseHeader(StringBuilder builder, Set<String> tokens) {
        builder.append(String.format("\t\tcase %s -> {\n", String.join(", ", tokens)));
    }

    private void generateFun(Map<NonTerminal, List<Rule>> map, StringBuilder builder) {
        for (var entry : map.entrySet()) {
            String nameToken = entry.getKey().name();
            String nameClass = nameClass(nameToken);
            String getArgs = entry.getKey().args();
            builder.append(String.format("""
                        public %s %s%s {
                            %s answer = new %s("%s");
                            switch(token.typeToken()) {
                    """,
                nameClass,
                nameToken,
                getArgs == null ? "()" : getArgs,
                nameClass,
                nameClass,
                nameToken)
            );

            boolean isEps = false;
            String codeForFollow = "";
            Set<String> notEpsilonTokens = new HashSet<>();
            for (var rule : entry.getValue()) {
                List<Product> production = rule.production();

                Set<String> firsts = firstAndFollowGenerator.getFirst(rule.getTokens());
                notEpsilonTokens.addAll(firsts);

                String EPSILON = "ε";
                if (firsts.contains(EPSILON)) {
                    isEps = true;
                    codeForFollow = rule.production().get(0).code();
                }
                firsts.remove(EPSILON);

                if (firsts.isEmpty()) {
                    continue;
                }

                caseHeader(builder, firsts);
                for (var product : production) {
                    for (var token : product.pairList()) {
                        if (token.getFirst().matches("[A-Z]+")) {
                            builder.append(String.format("""   
                                                String %s = token.text();
                                                answer.addChild(token.text());
                                                nextToken();
                                """, token.getFirst()));
                        } else {
                            String args = token.getSecond();
                            builder.append(String.format("""
                                                %s %s = %s%s;
                                                answer.addChild(%s);
                                """,
                                nameClass(token.getFirst()),
                                token.getFirst(),
                                token.getFirst(),
                                args,
                                token.getFirst()
                            ).replaceAll("\\$", "answer."));
                        }
                    }

                    String code = getCode(product.code());
                    if (!code.isEmpty())
                        builder.append(String.format("""
                                            %s
                            """, code).replaceAll("\\$", "answer."));
                }
                builder.append("""
                                }
                    """);
            }

            if (isEps) {
                Set<String> follow = new HashSet<>(firstAndFollowGenerator.getFollow().get(nameToken));
                follow.removeAll(notEpsilonTokens);
                caseHeader(builder, follow);
                builder.append(String.format("""
                                    answer.addChild("eps");
                                    %s
                                }
                    """, getCode(codeForFollow)).replaceAll("\\$", "answer."));
            }

            builder.append(codePartsReader.getCodeFrom("src/codeParts/parserFunEnder.txt"));
        }
    }


    private void generateClasses(Set<NonTerminal> nonTerminals, StringBuilder builder) {
        builder.append(String.format("%n"));

        for (var nonTerm : nonTerminals) {
            String nameClass = nameClass(nonTerm.name());

            builder.append(String.format("""
                    public static class %s extends Tree {
                """, nameClass));

            builder.append(
                Arrays.stream(getCode(nonTerm.attr()).split(","))
                    .map(attr -> "\t\t" + "public " + attr + ";" + String.format("%n"))
                    .collect(Collectors.joining())
            );

            builder.append(String.format("""
                        public %s(String node) {
                            super(node);
                        }
                    }
                    
                """, nameClass));
        }
    }
}
