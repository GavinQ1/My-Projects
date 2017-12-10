/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import constants.*;
import Lexer.*;
import Lexer.LexicalExceptions.LexicalException;
import Parser.ParserExceptions.ParserException;
import SemanticActions.SemanticActions;
import SemanticActions.SemanticActionsExceptions.SemanticActionsException;
import SymbolTable.SymbolTableExceptions.SymbolTableException;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class Parser {

    private boolean __DEBUG__ = false;
    private PrintWriter logger = null;

    private final Lexer lexer;
    private final Stack<GrammarSymbol> stack = new Stack<>();
    private final RHSTable RHSTable = new RHSTable();
    private final ParserTable ParserTable = new ParserTable();
    private final SemanticActions semact;

    public Parser(String filename) throws IOException, SymbolTableException {
        this(filename, false);
    }

    public Parser(String filename, boolean debug) throws IOException, SymbolTableException {
        lexer = new Lexer(filename);
        __DEBUG__ = debug;
        semact = new SemanticActions();
        if (__DEBUG__) {
            logger = new PrintWriter("logs.txt", "UTF-8");
        }
    }

    // Wrapper, currentInputly we only need token type
    public TokenType getNextToken() throws LexicalException, IOException {
        return lexer.getNextToken().getType();
    }

    public void parse() throws LexicalException, IOException, ParserException, SymbolTableException, SemanticActionsException {
        Token currentInput = lexer.getNextToken(), lastInput = null;
        stack.push(TokenType.ENDOFFILE);
        stack.push(NonTerminal.Goal);

        GrammarSymbol predicted;
        while (!currentInput.getType().equals(TokenType.ENDOFFILE)) {
            dumpStack();
            predicted = stack.peek();
            if (predicted.isToken()) {
                // matched
                if (currentInput.getType().equals(predicted)) {
                    trackStack(predicted, currentInput.getType(), "* MATCH *");
                    
                    // consume token
                    stack.pop();
                    lastInput = currentInput;
                    currentInput = lexer.getNextToken();
                } else {
                    trackStack(predicted, currentInput.getType(), "ERROR: MISMATCH");
                    throw new ParserException(
                            lexer.getLine(),
                            lexer.getFilename(),
                            String.format("Expecting %s, \"%s\" (Type: %s) found.",
                                    (predicted.isToken()) ? ((TokenType) predicted).toInputString() : predicted,
                                    lexer.getCurrentLexeme(),
                                    currentInput.getType())
                    );
                }
            } else if (predicted.isNonTerminal()) {
                int code = ParserTable.getCode(currentInput.getType().getIndex(), predicted.getIndex());
                // error case
                if (code == 999) {
                    trackStack(predicted, currentInput.getType(), "ERROR: MISMATCH");
                    throw new ParserException(
                            lexer.getLine(),
                            lexer.getFilename(),
                            String.format("Expecting %s, \"%s\" (Type: %s) found.", 
                                    (predicted.isToken()) ? ((TokenType) predicted).toInputString() : predicted,
                                    lexer.getCurrentLexeme(), 
                                    currentInput.getType()));
                    // empty string case
                } else if (code < 0) {
                    trackStack(predicted, currentInput.getType(), "# EPSILON #");
                    
                    // do nothing
                    stack.pop();
                    
                    // matched rule
                } else {
                    // add rule from right to left
                    stack.pop();
                    GrammarSymbol[] rule = RHSTable.getRule(code);
                    for (int i = rule.length - 1; i >= 0; i--) {
                        stack.push(rule[i]);
                    }
                    
                    trackStack(predicted, currentInput.getType(), "$ PUSH $  [" + code + "] ::= " + (rule.length == 0 ? "# EPSILON #" : Arrays.toString(rule)));
                }
            } else if (predicted.isAction()) {
                stack.pop();
                int line = lexer.getLine();
                if (currentInput.isTypeOf(TokenType.END) || currentInput.isTypeOf(TokenType.THEN)) line--;
                semact.setLine(line);
                semact.execute((SemanticAction) predicted, lastInput);
            }
        }

        if (__DEBUG__ && logger != null) {
            logger.print("\r\nENDOFFILE ---> ! ACCEPT !");
            logger.close();
        }
    }

    private void trackStack(GrammarSymbol predicted, GrammarSymbol currentInput, String comment) {
        if (__DEBUG__) {
            String log = String.format("Popped %s with token %s (input string: \"%s\"). %s <File: %s, line %d>",
                    predicted,
                    currentInput,
                    lexer.getCurrentLexeme(),
                    comment,
                    lexer.getFilename(),
                    lexer.getLine());
            System.out.println(log);
            logger.println(log);
        }
    }

    private void dumpStack() {
        if (__DEBUG__) {
            GrammarSymbol[] a = new GrammarSymbol[stack.size()];
            a = stack.toArray(a);
            Collections.reverse(Arrays.asList(a));
            String log = "Stack :==> " + Arrays.toString(a);
            System.out.println(log);
            logger.println(log);
        }
    }
    
    public void output() {
        semact.dumpCode();
    }
    
    public void output(PrintWriter o) {
        semact.dumpCode(o);
        o.close();
    }
    
    public boolean isDebugging() {
        return __DEBUG__;
    }
    
    public static void main(String[] args) {
        String filename = "phase3-1.pas";
        boolean debug = false;
        if (args.length > 0) {
            filename = args[0];
            if (args.length > 1) {
                String flag = args[1].toLowerCase();
                if ("true".equals(flag)) {
                    debug = true;
                }
            }
        }
        try {
            Parser parser = new Parser(filename, debug);
            parser.parse();
            // System.out.println("\n*****************\nEnd state:\n");
            parser.semact.dumpCode();
            // parser.semact.dump();
            System.out.println("\nACCEPT! (Degbug mode is: " + (parser.__DEBUG__ ? "on" : "off") + ")\n");
        } catch (LexicalException | ParserException | IOException | SymbolTableException | SemanticActionsException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
