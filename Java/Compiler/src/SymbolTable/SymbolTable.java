/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable;
import SymbolTable.SymbolTableEntry.IODeviceEntry;
import SymbolTable.SymbolTableEntry.SymbolTableEntry;
import SymbolTable.SymbolTableEntry.ProcedureEntry;
import java.util.Hashtable;
import java.util.Map;
import java.util.ArrayList;
import SymbolTable.SymbolTableExceptions.SymbolTableException;
/**
 *
 * @author Gavin
 */
public class SymbolTable {
    private Hashtable<String, SymbolTableEntry> table;
    
    public SymbolTable() {
        this(11);
    }
    
    public SymbolTable(int capacity) {
        this.table = new Hashtable<>(capacity);
    }
    
    public void installBuiltins() throws SymbolTableException {
        insert("main", new ProcedureEntry("main", 0, new ArrayList()));
        insert("read", new ProcedureEntry("read", 0, new ArrayList()));
        insert("write", new ProcedureEntry("write", 0, new ArrayList()));
        insert("input", new IODeviceEntry("input"));
        insert("output", new IODeviceEntry("output"));
    }
    
    private String standardize(String key) {
        return key.toLowerCase();
    }
    
    public SymbolTableEntry get(String name) {
        name = standardize(name);
        if (lookup(name)) {
            return table.get(name);
        }
        return null;
    }
    
    public boolean lookup(String name) {
        name = standardize(name);
        return table.contains(name);
    }
    
    public int size() { return table.size(); }
    
    public void insert(String name, SymbolTableEntry entry) throws SymbolTableException {
        name = standardize(name);
        if (lookup(name)) {
            throw new SymbolTableException(name + " already exists in the scope.");
        }
        table.put(name, entry);
    }
    
    public void dumpTable() {
        for (Map.Entry<String, SymbolTableEntry> entry : table.entrySet()) {
            String key = entry.getKey();
            SymbolTableEntry value = entry.getValue();
            System.out.println(key + " <==> " + value);
        }
    }
    
}
