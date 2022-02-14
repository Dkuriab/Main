// Generated from /Users/danila/Documents/GitHub/ParserGenerator/src/grammarParser/GrammarOfGrammar.g4 by ANTLR 4.9.2
package grammarParser;

import java.util.*;
import parserGenerator.Pair;
import parserGenerator.data.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarOfGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GRAMMAR_WORD=1, TOKEN_NAME=2, NAME=3, EPS=4, OR=5, COLON=6, SEMICOLON=7, 
		FIGURE_OPEN=8, FIGURE_CLOSE=9, SQUARE_OPEN=10, SQUARE_CLOSE=11, BODY=12, 
		ARROW=13, SIMPLE_OPEN=14, SIMPLE_CLOSE=15, CODE=16, RETURN_ATTR=17, GET_ATTR=18, 
		REGEXP=19, WHITESPACE=20;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"GRAMMAR_WORD", "TOKEN_NAME", "NAME", "EPS", "OR", "COLON", "SEMICOLON", 
			"FIGURE_OPEN", "FIGURE_CLOSE", "SQUARE_OPEN", "SQUARE_CLOSE", "BODY", 
			"ARROW", "SIMPLE_OPEN", "SIMPLE_CLOSE", "CODE", "RETURN_ATTR", "GET_ATTR", 
			"REGEXP", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'grammar'", null, null, "'\u03B5'", "'|'", "':'", "';'", "'{'", 
			"'}'", "'['", "']'", "'::'", "'->'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "GRAMMAR_WORD", "TOKEN_NAME", "NAME", "EPS", "OR", "COLON", "SEMICOLON", 
			"FIGURE_OPEN", "FIGURE_CLOSE", "SQUARE_OPEN", "SQUARE_CLOSE", "BODY", 
			"ARROW", "SIMPLE_OPEN", "SIMPLE_CLOSE", "CODE", "RETURN_ATTR", "GET_ATTR", 
			"REGEXP", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammarOfGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GrammarOfGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\26\u008f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\6\3\65\n\3\r\3\16\3\66\3\4\6\4:\n\4\r\4\16\4;\3\4\7\4?\n\4\f\4\16\4"+
		"B\13\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\6\21`\n\21"+
		"\r\21\16\21a\3\21\5\21e\n\21\7\21g\n\21\f\21\16\21j\13\21\3\21\3\21\3"+
		"\22\3\22\7\22p\n\22\f\22\16\22s\13\22\3\22\3\22\3\23\3\23\7\23y\n\23\f"+
		"\23\16\23|\13\23\3\23\3\23\3\24\3\24\7\24\u0082\n\24\f\24\16\24\u0085"+
		"\13\24\3\24\3\24\3\25\6\25\u008a\n\25\r\25\16\25\u008b\3\25\3\25\5qz\u0083"+
		"\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26\3\2\6\3\2C\\\3\2c|\4\2}}\177\177\5\2\13"+
		"\f\17\17\"\"\2\u0098\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2"+
		"\5\64\3\2\2\2\79\3\2\2\2\tC\3\2\2\2\13E\3\2\2\2\rG\3\2\2\2\17I\3\2\2\2"+
		"\21K\3\2\2\2\23M\3\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31S\3\2\2\2\33V\3\2\2"+
		"\2\35Y\3\2\2\2\37[\3\2\2\2!]\3\2\2\2#m\3\2\2\2%v\3\2\2\2\'\177\3\2\2\2"+
		")\u0089\3\2\2\2+,\7i\2\2,-\7t\2\2-.\7c\2\2./\7o\2\2/\60\7o\2\2\60\61\7"+
		"c\2\2\61\62\7t\2\2\62\4\3\2\2\2\63\65\t\2\2\2\64\63\3\2\2\2\65\66\3\2"+
		"\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67\6\3\2\2\28:\t\3\2\298\3\2\2\2:;\3"+
		"\2\2\2;9\3\2\2\2;<\3\2\2\2<@\3\2\2\2=?\t\2\2\2>=\3\2\2\2?B\3\2\2\2@>\3"+
		"\2\2\2@A\3\2\2\2A\b\3\2\2\2B@\3\2\2\2CD\7\u03b7\2\2D\n\3\2\2\2EF\7~\2"+
		"\2F\f\3\2\2\2GH\7<\2\2H\16\3\2\2\2IJ\7=\2\2J\20\3\2\2\2KL\7}\2\2L\22\3"+
		"\2\2\2MN\7\177\2\2N\24\3\2\2\2OP\7]\2\2P\26\3\2\2\2QR\7_\2\2R\30\3\2\2"+
		"\2ST\7<\2\2TU\7<\2\2U\32\3\2\2\2VW\7/\2\2WX\7@\2\2X\34\3\2\2\2YZ\7*\2"+
		"\2Z\36\3\2\2\2[\\\7+\2\2\\ \3\2\2\2]h\5\21\t\2^`\n\4\2\2_^\3\2\2\2`a\3"+
		"\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2ce\5!\21\2dc\3\2\2\2de\3\2\2\2eg\3"+
		"\2\2\2f_\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\5"+
		"\23\n\2l\"\3\2\2\2mq\5\25\13\2np\13\2\2\2on\3\2\2\2ps\3\2\2\2qr\3\2\2"+
		"\2qo\3\2\2\2rt\3\2\2\2sq\3\2\2\2tu\5\27\f\2u$\3\2\2\2vz\5\35\17\2wy\13"+
		"\2\2\2xw\3\2\2\2y|\3\2\2\2z{\3\2\2\2zx\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\5"+
		"\37\20\2~&\3\2\2\2\177\u0083\7$\2\2\u0080\u0082\13\2\2\2\u0081\u0080\3"+
		"\2\2\2\u0082\u0085\3\2\2\2\u0083\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084"+
		"\u0086\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\7$\2\2\u0087(\3\2\2\2\u0088"+
		"\u008a\t\5\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2"+
		"\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\25\2\2\u008e"+
		"*\3\2\2\2\r\2\66;@adhqz\u0083\u008b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}