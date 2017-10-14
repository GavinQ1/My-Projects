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
public class IllFormatedConstantException extends LexicalException {
    public IllFormatedConstantException() { super(); }
    public IllFormatedConstantException(int line, String filename, String lexeme) {
        super(line, filename, "Ill-formated constant \"" + lexeme + "\" found.");
    }
    public IllFormatedConstantException(String message) { super(message); }
    public IllFormatedConstantException(String message, Throwable cause) { super(message, cause); }
    public IllFormatedConstantException(Throwable cause) { super(cause); }
}
