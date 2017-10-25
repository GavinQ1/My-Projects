/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import constants.NonTerminal;
import constants.TokenType;
import constants.GrammarSymbol;
import Lexer.*;
import Lexer.LexicalExceptions.LexicalException;
import Parser.ParserExceptions.ParserException;
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

    public Parser(String filename) throws IOException {
        lexer = new Lexer(filename);
    }

    public Parser(String filename, boolean debug) throws IOException {
        this(filename);
        __DEBUG__ = debug;
        if (__DEBUG__) {
            logger = new PrintWriter("logs.txt", "UTF-8");
        }
    }

    // Wrapper, currently we only need token type
    public TokenType getNextToken() throws LexicalException, IOException {
        return lexer.getNextToken().getType();
    }

    public void parse() throws LexicalException, IOException, ParserException {
        Token current = lexer.getNextToken();
        stack.push(TokenType.ENDOFFILE);
        stack.push(NonTerminal.Goal);

        GrammarSymbol predicted;
        while (!current.getType().eqauls(TokenType.ENDOFFILE)) {
            dumpStack();
            predicted = stack.peek();
            if (predicted.isToken()) {
                // matched
                if (current.getType().equals(predicted)) {
                    trackStack(predicted, current.getType(), "* MATCH *");
                    
                    // consume token
                    stack.pop();
                    current = lexer.getNextToken();
                } else {
                    trackStack(predicted, current.getType(), "ERROR: MISMATCH");
                    throw new ParserException(
                            lexer.getLine(),
                            lexer.getFilename(),
                            String.format("Expecting %s, \"%s\" (Type: %s) found.",
                                    (predicted.isToken()) ? ((TokenType) predicted).toInputString() : predicted,
                                    lexer.getCurrentLexeme(),
                                    current.getType())
                    );
                }
            } else if (predicted.isNonTerminal()) {
                int code = ParserTable.getCode(current.getType().getIndex(), predicted.getIndex());
                // error case
                if (code == 999) {
                    trackStack(predicted, current.getType(), "ERROR: MISMATCH");
                    throw new ParserException(
                            lexer.getLine(),
                            lexer.getFilename(),
                            String.format("Expecting %s, \"%s\" (Type: %s) found.", 
                                    (predicted.isToken()) ? ((TokenType) predicted).toInputString() : predicted,
                                    lexer.getCurrentLexeme(), 
                                    current.getType()));
                    // empty string case
                } else if (code < 0) {
                    trackStack(predicted, current.getType(), "# EPSILON #");
                    
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
                    
                    trackStack(predicted, current.getType(), "$ PUSH $  [" + code + "] ::= " + (rule.length == 0 ? "# EPSILON #" : Arrays.toString(rule)));
                }
                // omit actions currently
            } else if (predicted.isAction()) {
                stack.pop();
                if (__DEBUG__) {
                    System.out.println("*** IGNORE ACTIONS CURRENTLY ***");
                }
            }
        }

        if (__DEBUG__ && logger != null) {
            logger.print("\r\nENDOFFILE ---> ! ACCEPT !");
            logger.close();
        }
    }

    private void trackStack(GrammarSymbol predicted, GrammarSymbol current, String comment) {
        if (__DEBUG__) {
            String log = String.format("Popped %s with token %s (input string: \"%s\"). %s <File: %s, line %d>",
                    predicted,
                    current,
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

    public static void main(String[] args) throws IOException {
        String filename = "ult.pas";
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
        Parser parser = new Parser(filename, debug);
        try {
            parser.parse();
            System.out.println("\nACCEPT! (Degbug mode is: " + (parser.__DEBUG__ ? "on" : "off") + ")\n");
        } catch (LexicalException | ParserException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
