package main;

import parserGenerator.data.ParserGenerationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CodePartsReader {

    public String getCodeFrom(String fileName) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(fileName))) {
            StringBuilder answer = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                answer.append(line).append("\n");
            }

            return answer.toString();
        } catch (IOException e) {
            throw new ParserGenerationException("Can't find " + fileName);
        }
    }
}
