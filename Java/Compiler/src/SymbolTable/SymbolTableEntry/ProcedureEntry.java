/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;
import java.util.List;
/**
 *
 * @author Gavin
 */
public class ProcedureEntry extends SymbolTableEntry {
    private int numberOfParameters;
    private List parameterInfo;
    
    public ProcedureEntry(String name, int numberOfParameters, List parameterInfo) {
        super(name);
        this.numberOfParameters = numberOfParameters;
        this.parameterInfo = parameterInfo;
    }
    
    public boolean isProcedure() { return true; }
    public boolean isReserved() { return true; }
    public List getParameterInfo() { return parameterInfo; }
    public int getNumberOfParameters() { return numberOfParameters; }
}
