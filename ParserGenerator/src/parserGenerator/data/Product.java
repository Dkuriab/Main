package parserGenerator.data;

import parserGenerator.Pair;

import java.util.List;

public record Product(List<Pair<String, String>> pairList, String code) {
}