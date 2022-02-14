package parserGenerator.data;

public record NonTerminal(String name, String attr, String args) {
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}