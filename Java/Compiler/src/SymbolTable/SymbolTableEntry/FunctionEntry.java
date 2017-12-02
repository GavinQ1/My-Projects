/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;
import java.util.List;
import constants.TokenType;
/**
 *
 * @author Gavin
 */
public class FunctionEntry extends SymbolTableEntry {
    private int numberOfParameters;
    private List parameterInfo;
    private VariableEntry result;
    
    public FunctionEntry(String name, int numberOfParameters, List parameterInfo, VariableEntry result) {
        super(name);
        this.numberOfParameters = numberOfParameters;
        this.parameterInfo = parameterInfo;
        this.result = result;
    }
    
    public boolean isFunction() { return true; }
    public List getParameterInfo() { return parameterInfo; }
    public int getNumberOfParameters() { return numberOfParameters; }
    public VariableEntry getResult() { return result; }
    public TokenType getType() { return result.getType(); }
}
