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
    
    public ArrayEntry(String name) {
        this(name, 0, null, 0, 0);
    }
    
    public ArrayEntry(String name, int address, TokenType type, int upperBound, int lowerBound) {
        super(name);
        this.address = address;
        this.type = type;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
    
    public boolean isArray() { return true; }
    
    
    public int getUpperBound() { return upperBound; }
    public int getLowerBound() { return lowerBound; }
    public int getAddress() { return address; }
    public TokenType getType() { return this.type; }
}
