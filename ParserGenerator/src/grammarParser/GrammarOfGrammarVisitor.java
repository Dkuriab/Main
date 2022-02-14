// Generated from /Users/danila/Documents/GitHub/ParserGenerator/src/grammarParser/GrammarOfGrammar.g4 by ANTLR 4.9.2
package grammarParser;

import java.util.*;
import parserGenerator.Pair;
import parserGenerator.data.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarOfGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarOfGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#grammarOfGrammar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarOfGrammar(GrammarOfGrammarParser.GrammarOfGrammarContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(GrammarOfGrammarParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#parts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParts(GrammarOfGrammarParser.PartsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarRule(GrammarOfGrammarParser.GrammarRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#terminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminalRule(GrammarOfGrammarParser.TerminalRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTerminalRule(GrammarOfGrammarParser.NonTerminalRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPart(GrammarOfGrammarParser.PartContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#product}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduct(GrammarOfGrammarParser.ProductContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(GrammarOfGrammarParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#regexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexp(GrammarOfGrammarParser.RegexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#eps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEps(GrammarOfGrammarParser.EpsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(GrammarOfGrammarParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#returnAttributes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnAttributes(GrammarOfGrammarParser.ReturnAttributesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarOfGrammarParser#getAttributes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetAttributes(GrammarOfGrammarParser.GetAttributesContext ctx);
}