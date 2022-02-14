grammar GrammarOfGrammar;

@header {
import java.util.*;
import parserGenerator.Pair;
import parserGenerator.data.*;
}

grammarOfGrammar returns[LanguageGrammar grammar] @init { LanguageGrammar grammar = new LanguageGrammar(); $grammar = grammar; }:
	header parts[grammar] { $grammar.setName($header.gramarName); } EOF
    ;

header returns[String gramarName]:
	//grammar math;
	GRAMMAR_WORD name SEMICOLON { $gramarName = $name.text; }
	;

parts[LanguageGrammar grammar]:
	grammarRule[grammar]*
	;

grammarRule[LanguageGrammar grammar]:
	terminalRule[grammar]
	| nonTerminalRule[grammar]
	;

terminalRule[LanguageGrammar grammar]:
	name COLON regexp SEMICOLON { $grammar.addToken($name.text, $regexp.text); }
	;



nonTerminalRule[LanguageGrammar grammar]:
	//expr() -> [int val] ::
	name getAttributes ARROW returnAttributes BODY

	//   part
	// | part
    part[grammar, new NonTerminal($name.text, $returnAttributes.text, $getAttributes.text)]
    (OR part[grammar, new NonTerminal($name.text, $returnAttributes.text, $getAttributes.text)])* SEMICOLON
    ;

part[LanguageGrammar grammar, NonTerminal n] @init { List<Product> productions = new ArrayList<>(); }:
	// (product)+
	// ε code
    (product { productions.add($product.production); })+ { $grammar.putRule($n, new Rule(productions)); }
    | eps code? { $grammar.putRule($n, new Rule(List.of(new Product(List.of(), $code.text)))); }
    ;

product returns [Product production] @init { List<Pair<String, String>> entryList = new ArrayList<>();}:
	// name ()?  name (term.val) ... {code}
    (name getAttributes? { entryList.add(new Pair($name.text, $getAttributes.text)); })+ code { $production = new Product(entryList, $code.text); }
    ;


name: NAME | TOKEN_NAME;
regexp: REGEXP;
eps: EPS;
code: CODE;
returnAttributes: RETURN_ATTR;
getAttributes: GET_ATTR;

GRAMMAR_WORD: 'grammar';

TOKEN_NAME: [A-Z]+;
NAME: [a-z]+[A-Z]*;

EPS: 'ε';
OR: '|';
COLON: ':';
SEMICOLON: ';' ;
FIGURE_OPEN: '{';
FIGURE_CLOSE: '}';
SQUARE_OPEN: '[';
SQUARE_CLOSE: ']';
BODY: '::';
ARROW: '->';
SIMPLE_OPEN: '(';
SIMPLE_CLOSE: ')';

CODE: FIGURE_OPEN (~('{'|'}')+ CODE?)* FIGURE_CLOSE;
RETURN_ATTR: SQUARE_OPEN .*? SQUARE_CLOSE;
GET_ATTR: SIMPLE_OPEN .*? SIMPLE_CLOSE;
REGEXP: '"'.*?'"';

WHITESPACE: [ \t\r\n]+ -> skip;