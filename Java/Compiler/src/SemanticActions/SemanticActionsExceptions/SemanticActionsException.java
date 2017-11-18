/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions.SemanticActionsExceptions;

/**
 *
 * @author Gavin
 */
public class SemanticActionsException extends Exception {
    public SemanticActionsException() { super(); }
    public SemanticActionsException(String message, int line) { super("Error on line " + line + " : " + message); }
    public SemanticActionsException(String message, Throwable cause) { super(message, cause); }
    public SemanticActionsException(Throwable cause) { super(cause); }
}
