/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser.ParserExceptions;

/**
 *
 * @author Gavin
 */
public class ParserException extends Exception {
    public ParserException() { super(); }
    public ParserException(int line, String filename, String detail) {
        this("At file: \"" + filename + "\", line " + line + ": " + detail);
    }
    public ParserException(String message) { super(message); }
    public ParserException(String message, Throwable cause) { super(message, cause); }
    public ParserException(Throwable cause) { super(cause); }
}
