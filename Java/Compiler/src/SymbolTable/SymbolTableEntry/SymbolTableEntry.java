/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;

/**
 *
 * @author Gavin
 */
public abstract class SymbolTableEntry {
    protected String name;
    
    public SymbolTableEntry(String name) {
        this.name = name;
    }
    
    public String getName() { return this.name; }
    public String toString() { return this.name; }
    
    public boolean isVariable() { return false; }
    public boolean isProcedure() { return false; }
    public boolean isFunction() { return false; }
    public boolean isFunctionResult() { return false; }
    public boolean isParameter() { return false; }
    public boolean isArray() { return false; }
    public boolean isReserved() { return false; }
    public boolean isKeyword() { return false; }
    public boolean isConstant() { return false; }
}
