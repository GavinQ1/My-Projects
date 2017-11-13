/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions;

import constants.Token;
import SymbolTable.*;
import SymbolTable.SymbolTableEntry.*;
import SymbolTable.SymbolTableExceptions.SymbolTableException;
import constants.*;
import java.util.*;

/**
 *
 * @author Gavin
 */
public class SemanticActions {
    private Stack<Token> stack;
    private boolean insertF, globalF, arrayF;
    private boolean __DEBUG__;
    public SymbolTable globalTable, localTable;
    private int globalMemory,localMemory ;
    
    public SemanticActions() throws SymbolTableException {
        this(true);
    }
    
    public SemanticActions(boolean debug) throws SymbolTableException {
        stack = new Stack<>();
        insertF = globalF = true;
        arrayF = false;
        globalTable = new SymbolTable();
        // globalTable.installBuiltins();
        localTable = new SymbolTable();
        globalMemory = localMemory = 0;
        __DEBUG__ = debug;
    }
    
    public void execute(SemanticAction action, Token token) throws SymbolTableException {
        int actionIndex = action.getIndex();
        boolean flag = true;
        
        switch(actionIndex) {
            // #1   : INSERT/SEARCH = INSERT
            case 1:
                insertF = true;
                break;
            // #2   : INSERT/SEARCH = SEARCH
            case 2:
                insertF = false;
                break;
            case 3:
                Token type = stack.pop();
                if (arrayF) {
                    Token ub = stack.pop(), lb = stack.pop();
                    int ubi = Integer.parseInt(ub.getValue().toString()), lbi = Integer.parseInt(lb.getValue().toString());
                    int msize = ubi - lbi  + 1;
                    while (!stack.isEmpty()) {
                        Token id = stack.pop();
                        String idName = id.getValue().toString();
                        ArrayEntry entry = new ArrayEntry(idName, 0, type.getType(), ubi, lbi);
                        if (globalF) {
                            entry.setAddress(globalMemory);
                            globalTable.insert(idName, entry);
                            globalMemory += msize;
                        } else {
                            // entry.setAddress(localMemory);
                            // localMemory += msize;
                            // localTable.insert(idName, entry);
                        }
                    }
                } else {
                    while (!stack.isEmpty()) {
                        Token id = stack.pop();
                        String idName = id.getValue().toString();
                        VariableEntry entry = new VariableEntry(idName, 0,  type.getType());
                        if (globalF) {
                            entry.setAddress(globalMemory);
                            globalMemory += 1;
                            globalTable.insert(idName, entry);
                        } else {
                            entry.setAddress(localMemory);
                            localMemory += 1;
                            // localTable.insert(idName, entry);
                        }
                    }
                }
                arrayF = false;
                break;
            // #4   : push TYPE
            case 4:
            // #7   : push CONSTANT
            case 7:
            // #13  : push id
            case 13:
                stack.push(token);
                break;
            // #6   : ARRAY/SIMPLE = ARRAY
            case 6:
                arrayF = true;
                break;
            case 9:
                Token t1 = stack.pop(), t2 = stack.pop(), t3 = stack.get(0);
                String n1 = t1.getValue().toString(), n2 = t2.getValue().toString(), n3 = t3.getValue().toString();
                stack.remove(0);
                SymbolTableEntry e1 = new IODeviceEntry(n1), e2 = new IODeviceEntry(n2), e3 = new ProcedureEntry(n3, 0, null);
                if (globalF) {
                    globalTable.insert(n1, e1);
                    globalTable.insert(n2, e2);
                    globalTable.insert(n3, e3);
                } else {
                    localTable.insert(n1, e1);
                    localTable.insert(n2, e2);
                    localTable.insert(n3, e3);
                }
                insertF = false;
                break;
            default:
                flag = false;
                break;
        }
        
        if (__DEBUG__ && flag) {
            System.out.println("calling action : " + actionIndex + " with token " + token);
            dumpStack();
            System.out.printf("insertF: %s, globalF: %s, arrayF: %s, Global Memory: %s\n\n",
                insertF, globalF, arrayF, globalMemory);
        }
    }
    
    public void dump() {
        System.out.println("Global Table:");
        globalTable.dumpTable();
        System.out.println("\n*********************");
        System.out.printf("insertF: %s, globalF: %s, arrayF: %s, Global Memory: %d\n",
                insertF, globalF, arrayF, globalMemory);
    }
    
    private void dumpStack() {
        if (__DEBUG__) {
            Token[] a = new Token[stack.size()];
            a = stack.toArray(a);
            Collections.reverse(Arrays.asList(a));
            String log = "Stack :==> " + Arrays.toString(a);
            System.out.println(log);
        }
    }
}
