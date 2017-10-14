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
public class InvalidInputException extends LexicalException {
    public InvalidInputException() { super(); }
    public InvalidInputException(int line, String filename, char input) { 
        super(line, filename, "Invalid input \"" + input + "\" found."); 
    }
    public InvalidInputException(String message) { super(message); }
    public InvalidInputException(String message, Throwable cause) { super(message, cause); }
    public InvalidInputException(Throwable cause) { super(cause); }
}
