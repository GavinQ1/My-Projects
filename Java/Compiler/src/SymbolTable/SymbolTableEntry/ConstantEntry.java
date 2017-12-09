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
public class ConstantEntry extends SymbolTableEntry {
    public ConstantEntry(String name, TokenType type) {
        super(name);
        this.type = type;
    }
    
    public boolean isConstant() { return true; }
    public TokenType getType() { return this.type; }
}
