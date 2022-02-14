package parserGenerator;

import main.CodePartsReader;
import parserGenerator.data.LanguageGrammar;
import parserGenerator.data.ParserGenerationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class LexerGenerator {
    private static CodePartsReader codePartsReader = new CodePartsReader();
    private final LanguageGrammar grammar;
    private final Path path;

    public LexerGenerator(LanguageGrammar grammar, String path) {
        this.grammar = grammar;
        this.path = Path.of(path, grammar.getName());
    }

    public void generate() {
        try {
            Files.createDirectories(path);
        } catch (IOException ignored) {}

        generateTokenClass();
        generateTypeToken();
        generateLexicalAnalyzer();
    }

    private void generateTokenClass() {
        String tokenBody = codePartsReader.getCodeFrom("src/codeParts/token.txt");
        final String sourceCodeToken = String.format("package %s;\n\n" + tokenBody, grammar.getName());

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(path + "/Token.java"))) {
            bufferedWriter.write(sourceCodeToken);
        } catch (IOException e) {
            throw new ParserGenerationException("Can't create Token.java.");
        }
    }

    private void generateTypeToken() {
        String startClass = String.format("package %s;\n\n" + codePartsReader.getCodeFrom("src/codeParts/typeTokenHeader.txt"), grammar.getName());
        String endClass = codePartsReader.getCodeFrom("src/codeParts/typeTokenEnder.txt");

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path.resolve(Path.of("TypeToken.java")))) {
            bufferedWriter.write(startClass + grammar.getTokens().stream().map(pair -> "\t" + pair.getFirst() + "(" + pair.getSecond() + ")").collect(Collectors.joining(",\n", "\n", ";\n")) + endClass);
        } catch (IOException e) {
            throw new ParserGenerationException("Can't create TypeToken.java");
        }
    }

    private void generateLexicalAnalyzer() {
        String startClass = String.format("package %s;\n\n"
                        + codePartsReader.getCodeFrom("src/codeParts/analyzerHeader.txt"),
                grammar.getName());

        String endClass = codePartsReader.getCodeFrom("src/codeParts/analyzerEnder.txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path.resolve(Path.of("LexicalAnalyzer.java")))) {
            bufferedWriter.write(startClass + grammar.getTokens().stream().map(Pair::getSecond).map(s -> s.substring(1, s.length() - 1)).collect(Collectors.joining("|", "\"", "|.\"")) + endClass);
        } catch (IOException e) {
            throw new ParserGenerationException("Can't create TypeToken.java");
        }
    }
}
