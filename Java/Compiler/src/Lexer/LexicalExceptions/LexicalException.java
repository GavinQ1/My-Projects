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
public class LexicalException extends Exception {
    public LexicalException() { super(); }
    public LexicalException(int line, String filename, String detail) {
        this("At file: \"" + filename + "\", line " + line + ": " + detail);
    }
    public LexicalException(String message) { super(message); }
    public LexicalException(String message, Throwable cause) { super(message, cause); }
    public LexicalException(Throwable cause) { super(cause); }
}
