// Generated from /Users/danila/Documents/GitHub/ParserGenerator/src/grammarParser/GrammarOfGrammar.g4 by ANTLR 4.9.2
package grammarParser;

import java.util.*;
import parserGenerator.Pair;
import parserGenerator.data.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarOfGrammarParser}.
 */
public interface GrammarOfGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#grammarOfGrammar}.
	 * @param ctx the parse tree
	 */
	void enterGrammarOfGrammar(GrammarOfGrammarParser.GrammarOfGrammarContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#grammarOfGrammar}.
	 * @param ctx the parse tree
	 */
	void exitGrammarOfGrammar(GrammarOfGrammarParser.GrammarOfGrammarContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(GrammarOfGrammarParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(GrammarOfGrammarParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#parts}.
	 * @param ctx the parse tree
	 */
	void enterParts(GrammarOfGrammarParser.PartsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#parts}.
	 * @param ctx the parse tree
	 */
	void exitParts(GrammarOfGrammarParser.PartsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 */
	void enterGrammarRule(GrammarOfGrammarParser.GrammarRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 */
	void exitGrammarRule(GrammarOfGrammarParser.GrammarRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#terminalRule}.
	 * @param ctx the parse tree
	 */
	void enterTerminalRule(GrammarOfGrammarParser.TerminalRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#terminalRule}.
	 * @param ctx the parse tree
	 */
	void exitTerminalRule(GrammarOfGrammarParser.TerminalRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void enterNonTerminalRule(GrammarOfGrammarParser.NonTerminalRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void exitNonTerminalRule(GrammarOfGrammarParser.NonTerminalRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#part}.
	 * @param ctx the parse tree
	 */
	void enterPart(GrammarOfGrammarParser.PartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#part}.
	 * @param ctx the parse tree
	 */
	void exitPart(GrammarOfGrammarParser.PartContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#product}.
	 * @param ctx the parse tree
	 */
	void enterProduct(GrammarOfGrammarParser.ProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#product}.
	 * @param ctx the parse tree
	 */
	void exitProduct(GrammarOfGrammarParser.ProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(GrammarOfGrammarParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(GrammarOfGrammarParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#regexp}.
	 * @param ctx the parse tree
	 */
	void enterRegexp(GrammarOfGrammarParser.RegexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#regexp}.
	 * @param ctx the parse tree
	 */
	void exitRegexp(GrammarOfGrammarParser.RegexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#eps}.
	 * @param ctx the parse tree
	 */
	void enterEps(GrammarOfGrammarParser.EpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#eps}.
	 * @param ctx the parse tree
	 */
	void exitEps(GrammarOfGrammarParser.EpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(GrammarOfGrammarParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(GrammarOfGrammarParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#returnAttributes}.
	 * @param ctx the parse tree
	 */
	void enterReturnAttributes(GrammarOfGrammarParser.ReturnAttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#returnAttributes}.
	 * @param ctx the parse tree
	 */
	void exitReturnAttributes(GrammarOfGrammarParser.ReturnAttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarOfGrammarParser#getAttributes}.
	 * @param ctx the parse tree
	 */
	void enterGetAttributes(GrammarOfGrammarParser.GetAttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarOfGrammarParser#getAttributes}.
	 * @param ctx the parse tree
	 */
	void exitGetAttributes(GrammarOfGrammarParser.GetAttributesContext ctx);
}