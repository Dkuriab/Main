package main;

import grammarParser.GrammarOfGrammarLexer;
import grammarParser.GrammarOfGrammarParser;
import math.LexicalAnalyzer;
import math.ParseException;
import math.Parser;
import math.Tree;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parserGenerator.data.LanguageGrammar;
import parserGenerator.LexerGenerator;
import parserGenerator.ParserGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static BufferedWriter out;
    private static int count = 0;

    public static void main(String[] args) throws IOException {
        String path = "generated";
        String expressions = Files.readString(Path.of("src", "main", "expressionsGrammar.txt"));

        GrammarOfGrammarLexer lexer = new GrammarOfGrammarLexer(CharStreams.fromString(expressions));
        GrammarOfGrammarParser parser = new GrammarOfGrammarParser(new CommonTokenStream(lexer));
        LanguageGrammar grammar = parser.grammarOfGrammar().grammar;

//        printGrammarInfo(grammar);

        LexerGenerator lexerGenerator = new LexerGenerator(grammar, path);
        lexerGenerator.generate();
        ParserGenerator generatorParser = new ParserGenerator(grammar, path);
        generatorParser.generate();

        generateGraph();
    }

    private static void generateGraph() throws IOException {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Graph1.txt")));

//        String test = "fun 10";
//        String test = " fun 3 - 2 / ( -4) * ( - 3  * (7-4)+2) + 8 * (--2 + 3)";
        String test = "(2 **  sqr(2 + sqr(1)))";
        try {
            LexicalAnalyzer analyzer = new LexicalAnalyzer(test);
            Parser.Expr result = new Parser(analyzer).expr();

            System.out.println(result.val);

            out.write("digraph regexp {");
            out.newLine();
            dfs(result);
            out.write("}");

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } finally {
            out.close();
        }
    }

    public static void dfs(Tree x) throws IOException {
        int parentNumber = count;
        out.write("n" + parentNumber + " [label=\"" + x.getNode() + "\"]");
        out.newLine();

        if (x.getChildren() == null) {
            return;
        }

        for (Tree children : x.getChildren()) {
            count++;
            out.write("n" + parentNumber + " -> " + "n" + count);
            out.newLine();

            dfs(children);
        }
    }

//    private static void printGrammarInfo(LanguageGrammar grammar) {
//        System.out.println("Tokes: " + grammar.getTokens().size());
//        for (var e : grammar.getTokens()) {
//            System.out.println(e.getFirst() + " " + e.getSecond());
//        }
//        System.out.println("Rules: " + grammar.getRules().size());
//        for (var e : grammar.getRules()) {
//            System.out.println(e.getFirst() + " " + e.getSecond());
//        }
//    }
}
