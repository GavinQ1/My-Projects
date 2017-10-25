/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableExceptions;

/**
 *
 * @author Gavin
 */
public class SymbolTableException extends Exception {
    public SymbolTableException() { super(); }
    public SymbolTableException(String message) { super(message); }
    public SymbolTableException(String message, Throwable cause) { super(message, cause); }
    public SymbolTableException(Throwable cause) { super(cause); }
}
