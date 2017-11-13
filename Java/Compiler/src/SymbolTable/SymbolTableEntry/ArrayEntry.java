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
public class ArrayEntry extends SymbolTableEntry {
    private TokenType type;
    private int address, upperBound, lowerBound;
    
    public ArrayEntry(String name, int address, TokenType type, int upperBound, int lowerBound) {
        super(name);
        this.address = address;
        this.type = type;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
    
    public boolean isArray() { return true; }
    public void setType(TokenType t) { type = t; }
    public void setUpperBound(int ub) { upperBound = ub; }
    public void setLowerBound(int lb) { lowerBound = lb; }
    public void setAddress(int ads) { address = ads; }
    
    public int getAddress() { return address; }
    public int getUpperBound() { return upperBound; }
    public int getLowerBound() { return lowerBound; }
    public TokenType getType() { return type; }
}
