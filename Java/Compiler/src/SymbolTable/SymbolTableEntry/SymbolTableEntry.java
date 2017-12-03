/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;

import constants.TokenType;

/**
 *
 * @author Gavin
 */
public abstract class SymbolTableEntry {
    protected String name;
    protected boolean isReserved;
    protected TokenType type = null;
    
    public SymbolTableEntry(String name) {
        this(name, false);
    }
    
    public SymbolTableEntry(String name, boolean isReserved) {
        this.name = name;
        this.isReserved = isReserved;
    }
    
    public String getName() { return this.name; }
    public TokenType getType() { return this.type; }
    public String toString() { return this.name; }
    public Integer getAddress() { return null; }
    
    public void setIsReserved(boolean flag) { isReserved = flag; }
    
    public boolean isVariable() { return false; }
    public boolean isProcedure() { return false; }
    public boolean isFunction() { return false; }
    public boolean isFunctionResult() { return false; }
    public boolean isParameter() { return false; }
    public boolean isArray() { return false; }
    public boolean isReserved() { return isReserved; }
    public boolean isKeyword() { return false; }
    public boolean isConstant() { return false; }
}