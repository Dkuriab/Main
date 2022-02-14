package parserGenerator.data;

public class ParserGenerationException extends RuntimeException {
    public ParserGenerationException(String message) {
            super("Generation error: " + message);
        }
}
