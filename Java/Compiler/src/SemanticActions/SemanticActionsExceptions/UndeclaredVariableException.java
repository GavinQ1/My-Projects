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
public class UndeclaredVariableException extends SemanticActionsException {
    public UndeclaredVariableException() { super(); }
    public UndeclaredVariableException(String id, int line) { super(id + " is not declared.", line); }
    public UndeclaredVariableException(String message, Throwable cause) { super(message, cause); }
    public UndeclaredVariableException(Throwable cause) { super(cause); }
}
