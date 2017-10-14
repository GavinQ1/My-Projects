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
public class IllFormatedCommentException extends LexicalException {
    public IllFormatedCommentException() { super(); }
    public IllFormatedCommentException(int line, String filename) { 
        super(line, filename, "Mismatched comment found.");
    }
    public IllFormatedCommentException(String message) { super(message); }
    public IllFormatedCommentException(String message, Throwable cause) { super(message, cause); }
    public IllFormatedCommentException(Throwable cause) { super(cause); }
}
