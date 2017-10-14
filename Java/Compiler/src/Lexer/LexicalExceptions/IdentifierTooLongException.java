/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer.LexicalExceptions;

/**
 *
 * @author Gavin
 */
public class IdentifierTooLongException extends LexicalException {
    public IdentifierTooLongException() { super(); }
    public IdentifierTooLongException(int line, String filename, String lexeme, int limit) { 
        super(line, filename, "\"" + lexeme + "\" exceeds the maximum length of identifier: " + limit); 
    }
    public IdentifierTooLongException(String message) { super(message); }
    public IdentifierTooLongException(String message, Throwable cause) { super(message, cause); }
    public IdentifierTooLongException(Throwable cause) { super(cause); }
}
