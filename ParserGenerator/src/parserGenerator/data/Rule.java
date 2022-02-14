package parserGenerator.data;

import parserGenerator.Pair;
import parserGenerator.data.Product;

import java.util.List;
import java.util.stream.Collectors;

public record Rule(List<Product> production) {
    public List<String> getTokens() {
        return production.stream().flatMap(p -> p.pairList().stream().map(Pair::getFirst)).collect(Collectors.toList());
    }
}
