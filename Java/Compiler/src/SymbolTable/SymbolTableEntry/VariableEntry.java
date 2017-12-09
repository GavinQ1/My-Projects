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
public class VariableEntry extends SymbolTableEntry {
    
    public VariableEntry(String name, int address, TokenType type) {
        super(name);
        this.address = address;
        this.type = type;
    }
    
    public VariableEntry(String name) {
        this(name, 0, null);
    }
    
    public void setType(TokenType t) { type = t; }
    public void setAddress(int n) { address = n; }
    
    public TokenType getType() { return this.type; }
    public int getAddress() { return address; }
    
    public boolean isVariable() { return true; }
}
