/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable.SymbolTableEntry;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gavin
 */
public class ProcedureEntry extends SymbolTableEntry {
    
    public ProcedureEntry(String name) {
        this(name, 0, new ArrayList<>());
    }
    
    public ProcedureEntry(String name, int numberOfParameters, List<SymbolTableEntry> parameterInfo) {
        super(name);
        this.numberOfParameters = numberOfParameters;
        this.parameterInfo = parameterInfo;
        this.isReserved = true;
    }
        
    public boolean isProcedure() { return true; }
    public List<SymbolTableEntry> getParameterInfo() { return parameterInfo; }
    public int getNumberOfParameters() { return numberOfParameters; }
}
