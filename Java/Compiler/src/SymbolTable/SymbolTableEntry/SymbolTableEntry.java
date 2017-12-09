/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;

import constants.TokenType;
import java.util.List;

/**
 *
 * @author Gavin
 */
public abstract class SymbolTableEntry {
    protected String name;
    protected boolean isReserved, isParam, isFuncRes, isKeyword;
    protected TokenType type = null;
    protected int address, upperBound, lowerBound, numberOfParameters;
    protected List<SymbolTableEntry> parameterInfo;
    protected VariableEntry result;
    
    public SymbolTableEntry(String name) {
        this.name = name;
        isReserved = isParam = isFuncRes = isKeyword = false;
    }
    
    
    public String getName() { return this.name; }
    
    public void setType(TokenType t) { type = t; }
    public void setUpperBound(int ub) { upperBound = ub; }
    public void setLowerBound(int lb) { lowerBound = lb; }
    public void setAddress(int n) { address = n; }
    public String toString() { return this.name; }
    
    // var, array, const
    public int getAddress() { throw new UnsupportedOperationException("This method is not supported."); }
    public TokenType getType() { throw new UnsupportedOperationException("This method is not supported."); }
    public int getUpperBound() { throw new UnsupportedOperationException("This method is not supported."); }
    public int getLowerBound() { throw new UnsupportedOperationException("This method is not supported."); }
    
    // func
    public void setResult(VariableEntry result) { this.result = result; }
    public void setNumberOfParameters(int n) { this.numberOfParameters = n; }
    public void setParameterInfo(List<SymbolTableEntry> info) { this.parameterInfo = info; }
    public void addParam(SymbolTableEntry entry) { this.parameterInfo.add(entry); }
    public List getParameterInfo() { throw new UnsupportedOperationException("This method is not supported."); }
    public int getNumberOfParameters() { throw new UnsupportedOperationException("This method is not supported."); }
    public VariableEntry getResult() { throw new UnsupportedOperationException("This method is not supported."); }
    
    public void setIsReserved(boolean flag) { isReserved = flag; }
    public void setIsParam(boolean flag) { isParam = flag; }
    public void setIsFuncRes(boolean flag) { isFuncRes = flag;}
    
    public boolean isVariable() { return false; }
    public boolean isProcedure() { return false; }
    public boolean isFunction() { return false; }
    public boolean isFunctionResult() { return  isFuncRes; }
    public boolean isParameter() { return isParam; }
    public boolean isArray() { return false; }
    public boolean isReserved() { return isReserved; }
    public boolean isKeyword() { return isKeyword; }
    public boolean isConstant() { return false; }
}
