// Generated from /Users/danila/Documents/GitHub/ParserGenerator/src/grammarParser/GrammarOfGrammar.g4 by ANTLR 4.9.2
package grammarParser;

import java.util.*;
import parserGenerator.Pair;
import parserGenerator.data.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarOfGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GRAMMAR_WORD=1, TOKEN_NAME=2, NAME=3, EPS=4, OR=5, COLON=6, SEMICOLON=7, 
		FIGURE_OPEN=8, FIGURE_CLOSE=9, SQUARE_OPEN=10, SQUARE_CLOSE=11, BODY=12, 
		ARROW=13, SIMPLE_OPEN=14, SIMPLE_CLOSE=15, CODE=16, RETURN_ATTR=17, GET_ATTR=18, 
		REGEXP=19, WHITESPACE=20;
	public static final int
		RULE_grammarOfGrammar = 0, RULE_header = 1, RULE_parts = 2, RULE_grammarRule = 3, 
		RULE_terminalRule = 4, RULE_nonTerminalRule = 5, RULE_part = 6, RULE_product = 7, 
		RULE_name = 8, RULE_regexp = 9, RULE_eps = 10, RULE_code = 11, RULE_returnAttributes = 12, 
		RULE_getAttributes = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"grammarOfGrammar", "header", "parts", "grammarRule", "terminalRule", 
			"nonTerminalRule", "part", "product", "name", "regexp", "eps", "code", 
			"returnAttributes", "getAttributes"
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

	@Override
	public String getGrammarFileName() { return "GrammarOfGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarOfGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class GrammarOfGrammarContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public HeaderContext header;
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public PartsContext parts() {
			return getRuleContext(PartsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GrammarOfGrammarParser.EOF, 0); }
		public GrammarOfGrammarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarOfGrammar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterGrammarOfGrammar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitGrammarOfGrammar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitGrammarOfGrammar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarOfGrammarContext grammarOfGrammar() throws RecognitionException {
		GrammarOfGrammarContext _localctx = new GrammarOfGrammarContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_grammarOfGrammar);
		 LanguageGrammar grammar = new LanguageGrammar(); ((GrammarOfGrammarContext)_localctx).grammar =  grammar; 
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			((GrammarOfGrammarContext)_localctx).header = header();
			setState(29);
			parts(grammar);
			 _localctx.grammar.setName(((GrammarOfGrammarContext)_localctx).header.gramarName); 
			setState(31);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeaderContext extends ParserRuleContext {
		public String gramarName;
		public NameContext name;
		public TerminalNode GRAMMAR_WORD() { return getToken(GrammarOfGrammarParser.GRAMMAR_WORD, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GrammarOfGrammarParser.SEMICOLON, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(GRAMMAR_WORD);
			setState(34);
			((HeaderContext)_localctx).name = name();
			setState(35);
			match(SEMICOLON);
			 ((HeaderContext)_localctx).gramarName =  (((HeaderContext)_localctx).name!=null?_input.getText(((HeaderContext)_localctx).name.start,((HeaderContext)_localctx).name.stop):null); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PartsContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public List<GrammarRuleContext> grammarRule() {
			return getRuleContexts(GrammarRuleContext.class);
		}
		public GrammarRuleContext grammarRule(int i) {
			return getRuleContext(GrammarRuleContext.class,i);
		}
		public PartsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PartsContext(ParserRuleContext parent, int invokingState, LanguageGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_parts; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterParts(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitParts(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitParts(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartsContext parts(LanguageGrammar grammar) throws RecognitionException {
		PartsContext _localctx = new PartsContext(_ctx, getState(), grammar);
		enterRule(_localctx, 4, RULE_parts);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TOKEN_NAME || _la==NAME) {
				{
				{
				setState(38);
				grammarRule(grammar);
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GrammarRuleContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public TerminalRuleContext terminalRule() {
			return getRuleContext(TerminalRuleContext.class,0);
		}
		public NonTerminalRuleContext nonTerminalRule() {
			return getRuleContext(NonTerminalRuleContext.class,0);
		}
		public GrammarRuleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public GrammarRuleContext(ParserRuleContext parent, int invokingState, LanguageGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_grammarRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterGrammarRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitGrammarRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitGrammarRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarRuleContext grammarRule(LanguageGrammar grammar) throws RecognitionException {
		GrammarRuleContext _localctx = new GrammarRuleContext(_ctx, getState(), grammar);
		enterRule(_localctx, 6, RULE_grammarRule);
		try {
			setState(46);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				terminalRule(grammar);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				nonTerminalRule(grammar);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminalRuleContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public NameContext name;
		public RegexpContext regexp;
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GrammarOfGrammarParser.COLON, 0); }
		public RegexpContext regexp() {
			return getRuleContext(RegexpContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GrammarOfGrammarParser.SEMICOLON, 0); }
		public TerminalRuleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TerminalRuleContext(ParserRuleContext parent, int invokingState, LanguageGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_terminalRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterTerminalRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitTerminalRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitTerminalRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalRuleContext terminalRule(LanguageGrammar grammar) throws RecognitionException {
		TerminalRuleContext _localctx = new TerminalRuleContext(_ctx, getState(), grammar);
		enterRule(_localctx, 8, RULE_terminalRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			((TerminalRuleContext)_localctx).name = name();
			setState(49);
			match(COLON);
			setState(50);
			((TerminalRuleContext)_localctx).regexp = regexp();
			setState(51);
			match(SEMICOLON);
			 _localctx.grammar.addToken((((TerminalRuleContext)_localctx).name!=null?_input.getText(((TerminalRuleContext)_localctx).name.start,((TerminalRuleContext)_localctx).name.stop):null), (((TerminalRuleContext)_localctx).regexp!=null?_input.getText(((TerminalRuleContext)_localctx).regexp.start,((TerminalRuleContext)_localctx).regexp.stop):null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonTerminalRuleContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public NameContext name;
		public GetAttributesContext getAttributes;
		public ReturnAttributesContext returnAttributes;
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public GetAttributesContext getAttributes() {
			return getRuleContext(GetAttributesContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(GrammarOfGrammarParser.ARROW, 0); }
		public ReturnAttributesContext returnAttributes() {
			return getRuleContext(ReturnAttributesContext.class,0);
		}
		public TerminalNode BODY() { return getToken(GrammarOfGrammarParser.BODY, 0); }
		public List<PartContext> part() {
			return getRuleContexts(PartContext.class);
		}
		public PartContext part(int i) {
			return getRuleContext(PartContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(GrammarOfGrammarParser.SEMICOLON, 0); }
		public List<TerminalNode> OR() { return getTokens(GrammarOfGrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(GrammarOfGrammarParser.OR, i);
		}
		public NonTerminalRuleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public NonTerminalRuleContext(ParserRuleContext parent, int invokingState, LanguageGrammar grammar) {
			super(parent, invokingState);
			this.grammar = grammar;
		}
		@Override public int getRuleIndex() { return RULE_nonTerminalRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterNonTerminalRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitNonTerminalRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitNonTerminalRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTerminalRuleContext nonTerminalRule(LanguageGrammar grammar) throws RecognitionException {
		NonTerminalRuleContext _localctx = new NonTerminalRuleContext(_ctx, getState(), grammar);
		enterRule(_localctx, 10, RULE_nonTerminalRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			((NonTerminalRuleContext)_localctx).name = name();
			setState(55);
			((NonTerminalRuleContext)_localctx).getAttributes = getAttributes();
			setState(56);
			match(ARROW);
			setState(57);
			((NonTerminalRuleContext)_localctx).returnAttributes = returnAttributes();
			setState(58);
			match(BODY);
			setState(59);
			part(grammar, new NonTerminal((((NonTerminalRuleContext)_localctx).name!=null?_input.getText(((NonTerminalRuleContext)_localctx).name.start,((NonTerminalRuleContext)_localctx).name.stop):null), (((NonTerminalRuleContext)_localctx).returnAttributes!=null?_input.getText(((NonTerminalRuleContext)_localctx).returnAttributes.start,((NonTerminalRuleContext)_localctx).returnAttributes.stop):null), (((NonTerminalRuleContext)_localctx).getAttributes!=null?_input.getText(((NonTerminalRuleContext)_localctx).getAttributes.start,((NonTerminalRuleContext)_localctx).getAttributes.stop):null)));
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(60);
				match(OR);
				setState(61);
				part(grammar, new NonTerminal((((NonTerminalRuleContext)_localctx).name!=null?_input.getText(((NonTerminalRuleContext)_localctx).name.start,((NonTerminalRuleContext)_localctx).name.stop):null), (((NonTerminalRuleContext)_localctx).returnAttributes!=null?_input.getText(((NonTerminalRuleContext)_localctx).returnAttributes.start,((NonTerminalRuleContext)_localctx).returnAttributes.stop):null), (((NonTerminalRuleContext)_localctx).getAttributes!=null?_input.getText(((NonTerminalRuleContext)_localctx).getAttributes.start,((NonTerminalRuleContext)_localctx).getAttributes.stop):null)));
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PartContext extends ParserRuleContext {
		public LanguageGrammar grammar;
		public NonTerminal n;
		public ProductContext product;
		public CodeContext code;
		public List<ProductContext> product() {
			return getRuleContexts(ProductContext.class);
		}
		public ProductContext product(int i) {
			return getRuleContext(ProductContext.class,i);
		}
		public EpsContext eps() {
			return getRuleContext(EpsContext.class,0);
		}
		public CodeContext code() {
			return getRuleContext(CodeContext.class,0);
		}
		public PartContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PartContext(ParserRuleContext parent, int invokingState, LanguageGrammar grammar, NonTerminal n) {
			super(parent, invokingState);
			this.grammar = grammar;
			this.n = n;
		}
		@Override public int getRuleIndex() { return RULE_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartContext part(LanguageGrammar grammar,NonTerminal n) throws RecognitionException {
		PartContext _localctx = new PartContext(_ctx, getState(), grammar, n);
		enterRule(_localctx, 12, RULE_part);
		 List<Product> productions = new ArrayList<>(); 
		int _la;
		try {
			setState(84);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_NAME:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(72); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(69);
					((PartContext)_localctx).product = product();
					 productions.add(((PartContext)_localctx).product.production); 
					}
					}
					setState(74); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==TOKEN_NAME || _la==NAME );
				 _localctx.grammar.putRule(_localctx.n, new Rule(productions)); 
				}
				break;
			case EPS:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				eps();
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CODE) {
					{
					setState(79);
					((PartContext)_localctx).code = code();
					}
				}

				 _localctx.grammar.putRule(_localctx.n, new Rule(List.of(new Product(List.of(), (((PartContext)_localctx).code!=null?_input.getText(((PartContext)_localctx).code.start,((PartContext)_localctx).code.stop):null))))); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProductContext extends ParserRuleContext {
		public Product production;
		public NameContext name;
		public GetAttributesContext getAttributes;
		public CodeContext code;
		public CodeContext code() {
			return getRuleContext(CodeContext.class,0);
		}
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<GetAttributesContext> getAttributes() {
			return getRuleContexts(GetAttributesContext.class);
		}
		public GetAttributesContext getAttributes(int i) {
			return getRuleContext(GetAttributesContext.class,i);
		}
		public ProductContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_product; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterProduct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitProduct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitProduct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProductContext product() throws RecognitionException {
		ProductContext _localctx = new ProductContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_product);
		 List<Pair<String, String>> entryList = new ArrayList<>();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(86);
				((ProductContext)_localctx).name = name();
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==GET_ATTR) {
					{
					setState(87);
					((ProductContext)_localctx).getAttributes = getAttributes();
					}
				}

				 entryList.add(new Pair((((ProductContext)_localctx).name!=null?_input.getText(((ProductContext)_localctx).name.start,((ProductContext)_localctx).name.stop):null), (((ProductContext)_localctx).getAttributes!=null?_input.getText(((ProductContext)_localctx).getAttributes.start,((ProductContext)_localctx).getAttributes.stop):null))); 
				}
				}
				setState(94); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TOKEN_NAME || _la==NAME );
			setState(96);
			((ProductContext)_localctx).code = code();
			 ((ProductContext)_localctx).production =  new Product(entryList, (((ProductContext)_localctx).code!=null?_input.getText(((ProductContext)_localctx).code.start,((ProductContext)_localctx).code.stop):null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(GrammarOfGrammarParser.NAME, 0); }
		public TerminalNode TOKEN_NAME() { return getToken(GrammarOfGrammarParser.TOKEN_NAME, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_la = _input.LA(1);
			if ( !(_la==TOKEN_NAME || _la==NAME) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegexpContext extends ParserRuleContext {
		public TerminalNode REGEXP() { return getToken(GrammarOfGrammarParser.REGEXP, 0); }
		public RegexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterRegexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitRegexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitRegexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexpContext regexp() throws RecognitionException {
		RegexpContext _localctx = new RegexpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_regexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(REGEXP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EpsContext extends ParserRuleContext {
		public TerminalNode EPS() { return getToken(GrammarOfGrammarParser.EPS, 0); }
		public EpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterEps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitEps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitEps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EpsContext eps() throws RecognitionException {
		EpsContext _localctx = new EpsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_eps);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(EPS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CodeContext extends ParserRuleContext {
		public TerminalNode CODE() { return getToken(GrammarOfGrammarParser.CODE, 0); }
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_code);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(CODE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnAttributesContext extends ParserRuleContext {
		public TerminalNode RETURN_ATTR() { return getToken(GrammarOfGrammarParser.RETURN_ATTR, 0); }
		public ReturnAttributesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnAttributes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterReturnAttributes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitReturnAttributes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitReturnAttributes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnAttributesContext returnAttributes() throws RecognitionException {
		ReturnAttributesContext _localctx = new ReturnAttributesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnAttributes);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(RETURN_ATTR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetAttributesContext extends ParserRuleContext {
		public TerminalNode GET_ATTR() { return getToken(GrammarOfGrammarParser.GET_ATTR, 0); }
		public GetAttributesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getAttributes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).enterGetAttributes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarOfGrammarListener ) ((GrammarOfGrammarListener)listener).exitGetAttributes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarOfGrammarVisitor ) return ((GrammarOfGrammarVisitor<? extends T>)visitor).visitGetAttributes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetAttributesContext getAttributes() throws RecognitionException {
		GetAttributesContext _localctx = new GetAttributesContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_getAttributes);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(GET_ATTR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\26r\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\7\4*\n\4\f\4\16\4-\13\4\3\5\3\5\5\5\61\n\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7A\n\7\f\7\16\7D\13\7\3\7\3\7"+
		"\3\b\3\b\3\b\6\bK\n\b\r\b\16\bL\3\b\3\b\3\b\3\b\5\bS\n\b\3\b\3\b\5\bW"+
		"\n\b\3\t\3\t\5\t[\n\t\3\t\3\t\6\t_\n\t\r\t\16\t`\3\t\3\t\3\t\3\n\3\n\3"+
		"\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\2\2\20\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\2\3\3\2\4\5\2k\2\36\3\2\2\2\4#\3\2\2\2\6+\3\2\2"+
		"\2\b\60\3\2\2\2\n\62\3\2\2\2\f8\3\2\2\2\16V\3\2\2\2\20^\3\2\2\2\22e\3"+
		"\2\2\2\24g\3\2\2\2\26i\3\2\2\2\30k\3\2\2\2\32m\3\2\2\2\34o\3\2\2\2\36"+
		"\37\5\4\3\2\37 \5\6\4\2 !\b\2\1\2!\"\7\2\2\3\"\3\3\2\2\2#$\7\3\2\2$%\5"+
		"\22\n\2%&\7\t\2\2&\'\b\3\1\2\'\5\3\2\2\2(*\5\b\5\2)(\3\2\2\2*-\3\2\2\2"+
		"+)\3\2\2\2+,\3\2\2\2,\7\3\2\2\2-+\3\2\2\2.\61\5\n\6\2/\61\5\f\7\2\60."+
		"\3\2\2\2\60/\3\2\2\2\61\t\3\2\2\2\62\63\5\22\n\2\63\64\7\b\2\2\64\65\5"+
		"\24\13\2\65\66\7\t\2\2\66\67\b\6\1\2\67\13\3\2\2\289\5\22\n\29:\5\34\17"+
		"\2:;\7\17\2\2;<\5\32\16\2<=\7\16\2\2=B\5\16\b\2>?\7\7\2\2?A\5\16\b\2@"+
		">\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DB\3\2\2\2EF\7\t\2\2"+
		"F\r\3\2\2\2GH\5\20\t\2HI\b\b\1\2IK\3\2\2\2JG\3\2\2\2KL\3\2\2\2LJ\3\2\2"+
		"\2LM\3\2\2\2MN\3\2\2\2NO\b\b\1\2OW\3\2\2\2PR\5\26\f\2QS\5\30\r\2RQ\3\2"+
		"\2\2RS\3\2\2\2ST\3\2\2\2TU\b\b\1\2UW\3\2\2\2VJ\3\2\2\2VP\3\2\2\2W\17\3"+
		"\2\2\2XZ\5\22\n\2Y[\5\34\17\2ZY\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\b\t\1"+
		"\2]_\3\2\2\2^X\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2\2ab\3\2\2\2bc\5\30"+
		"\r\2cd\b\t\1\2d\21\3\2\2\2ef\t\2\2\2f\23\3\2\2\2gh\7\25\2\2h\25\3\2\2"+
		"\2ij\7\6\2\2j\27\3\2\2\2kl\7\22\2\2l\31\3\2\2\2mn\7\23\2\2n\33\3\2\2\2"+
		"op\7\24\2\2p\35\3\2\2\2\n+\60BLRVZ`";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}