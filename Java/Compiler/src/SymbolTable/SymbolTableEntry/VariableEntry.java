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
    private TokenType type;
    private int address;
    
    public VariableEntry(String name, int address, TokenType type) {
        super(name);
        this.address = address;
        this.type = type;
    }
    
    public void setType(TokenType t) { type = t; }
    public void setAddress(int ads) { address = ads; }
    
    public boolean isVariable() { return true; }
    public TokenType getType() { return type; }
    public int getAddress() { return address; }
}
