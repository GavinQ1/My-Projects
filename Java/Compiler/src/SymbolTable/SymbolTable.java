/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolTable;
import SymbolTable.SymbolTableEntry.*;
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
        // insert("input", new IODeviceEntry("input"));
        // insert("output", new IODeviceEntry("output"));
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
        return table.containsKey(name);
    }
    
    public int size() { return table.size(); }
    
    public void insertAnyway(String name, SymbolTableEntry entry) {
        table.put(name, entry);
    }
    
    public void insert(String name, SymbolTableEntry entry) throws SymbolTableException {
        name = standardize(name);
        if (lookup(name)) {
            throw new SymbolTableException(name + " already exists in the scope.");
        } else {
            table.put(name, entry);
        }
    }
    
    public void dumpTable() {
        for (Map.Entry<String, SymbolTableEntry> entry : table.entrySet()) {
            String key = entry.getKey();
            SymbolTableEntry value = entry.getValue();
            // String addr = "";
            // if (value.isVariable()) addr += ((VariableEntry) value).getAddress();
            System.out.println(key + " <==> " + value + " ");
        }
    }
    
    // unit test
    public static void main(String[] args) {
        try {
            SymbolTable st = new SymbolTable();
            
            System.out.println("Testing installBuiltins: ");
            st.installBuiltins();
            System.out.println("Dumping table: ");
            System.out.println(st.size());
            st.dumpTable();
            System.out.println("*** ================= ***\n");
            
            // insertion test
            System.out.println("Testing insertion and lookup: ");
            System.out.println("Is 'test' in the table? " + st.lookup("test"));
            System.out.println("Inserting 'test' and 'test1' ... 'test6'\n");
            st.insert("test", new VariableEntry("test", 1, null));
            st.insert("test1", new VariableEntry("test1", 1, null));
            st.insert("test2", new VariableEntry("test2", 1, null));
            st.insert("test3", new VariableEntry("test3", 1, null));
            st.insert("test4", new VariableEntry("test4", 1, null));
            st.insert("test5", new VariableEntry("test5", 1, null));
            st.insert("test6", new VariableEntry("test6", 1, null));
            System.out.println("Is 'test' in the table? " + st.lookup("test"));
            
            System.out.println("Dumping table: ");
            System.out.println(st.size());
            st.dumpTable();
            System.out.println("*** ================= ***\n");
            
            // test for error
            System.out.println("Testing insertion error by inserting 'test' again:");
            st.insert("test", new VariableEntry("test", 1, null));
            
        } catch(SymbolTableException e) {
            System.out.println("*** =======ERROR======= ***");
            System.out.println(e);
        }
    }
}
