/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions;

import SemanticActions.SemanticActionsExceptions.*;
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

    private int tempId = 0;
    private boolean __DEBUG__;

    private Stack<Object> stack;
    private boolean insertF, globalF, arrayF;
    public SymbolTable globalTable, localTable, constantTable;
    private int globalMemory, localMemory, globalStore, localStore;
    private Quadruples quads;
    private SymbolTableEntry currentFunction;
    
    private int line;

    enum ETYPE {
        ARITHMETIC(0),
        RELATIONAL(1);

        private int n;

        private ETYPE(int n) {
            this.n = n;
        }

        public int getIndex() {
            return n;
        }

        public boolean eqauls(ETYPE other) {
            return this.n == other.getIndex();
        }
    }

    public SemanticActions() throws SymbolTableException {
        this(false);
    }

    public SemanticActions(boolean debug) throws SymbolTableException {
        stack = new Stack<>();
        insertF = globalF = true;
        arrayF = false;
        quads = new Quadruples();
        globalTable = new SymbolTable();
        localTable = new SymbolTable();
        constantTable = new SymbolTable();
        globalMemory = localMemory = globalStore = localStore = 0;
        currentFunction = null;
        globalTable.installBuiltins();
        __DEBUG__ = debug;
    }
    
    private int getMemory() {
        return (globalF) ? globalMemory++ : localMemory++;
    }

    public VariableEntry create(String name, TokenType type) throws SymbolTableException {
        VariableEntry entry = new VariableEntry("$$" + name, -getMemory(), type);
        if (globalF) {
            globalTable.insert(name, entry);
        } else {
            localTable.insert(name, entry);
        }
        return entry;
    }
    
    public void setLine(int line) {
        this.line = line;
    }
    
    private String getAddr(SymbolTableEntry op) throws SymbolTableException {
        int res;
        if (op.isArray()) {
            res = ((ArrayEntry) op).getAddress();
        } else if (op.isVariable()) {
            res = ((VariableEntry) op).getAddress();
        } else {
            VariableEntry temp = create(getTempName(), ((ConstantEntry) op).getType());
            generate("move", op.getName(), temp);
            res = temp.getAddress();
        }
        return "_" + Math.abs(res);
    }

    private String getTempName() {
        return "TEMP" + tempId++;
    }

    private void generate(String tviCode) {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quads.addQuad(quad);
    }

    private void generate(String tviCode, SymbolTableEntry op1, SymbolTableEntry op2) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = getAddr(op1);
        quad[2] = getAddr(op2);
        quads.addQuad(quad);
    }
    
    private void generate(String tviCode, SymbolTableEntry op1, String op2, SymbolTableEntry op3) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = getAddr(op1);
        quad[2] = op2;
        quad[3] = getAddr(op3);
        quads.addQuad(quad);
    }
    
    private void generate(String tviCode, SymbolTableEntry op1, int op2, SymbolTableEntry op3) throws SymbolTableException {
        generate(tviCode, op1, String.valueOf(op2), op3);
    }

    private void generate(String tviCode, String op1, SymbolTableEntry op2) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = op1;
        quad[2] = getAddr(op2);
        quads.addQuad(quad);
    }

    private void generate(String tviCode, SymbolTableEntry op1, SymbolTableEntry op2, SymbolTableEntry op3) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = getAddr(op1);
        quad[2] = getAddr(op2);
        quad[3] = getAddr(op3);
        quads.addQuad(quad);
    }

    private void generate(String tviCode, SymbolTableEntry op1, SymbolTableEntry op2, int label) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = getAddr(op1);
        quad[2] = getAddr(op2);
        quad[3] = Integer.toString(label);
        quads.addQuad(quad);
    }

    private void generate(String tviCode, String op) {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = op;
        quads.addQuad(quad);
    }

    private void generate(String tviCode, String op1, int op2) {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = op1;
        quad[2] = Integer.toString(op2);
        quads.addQuad(quad);
    }
    
    private void generate(String tviCode, SymbolTableEntry op1, int op2) throws SymbolTableException {
        generate(tviCode, getAddr(op1), op2);
    }

    private void generate(String tviCode, int op) {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = Integer.toString(op);
        quads.addQuad(quad);
    }

    private void backpath(int p, int i) {
        quads.setField(p, 1, Integer.toString(i));
    }
    
    private int typeCheck(SymbolTableEntry id1, SymbolTableEntry id2) {
        TokenType t1 = id1.getType(), t2 = id2.getType();
        // both int
        if (t1.equals(TokenType.INTEGER) && t2.equals(TokenType.INTEGER)) {
            return 0;
            // both real
        } else if (t1.eqauls(TokenType.REAL) && t2.equals(TokenType.REAL)) {
            return 1;
            // id1 real, id2 int
        } else if (t1.eqauls(TokenType.REAL) && t2.equals(TokenType.INTEGER)) {
            return 2;
            // id1 int, id2 real
        } else {
            return 3;
        }
    }
    
    private void action1() {
        insertF = true;
    }
    
    private void action2() {
        insertF = false;
    }
    
    private void action3() throws SymbolTableException {
        Token type = (Token) stack.pop();
        if (arrayF) {
            Token ub = (Token) stack.pop(), lb = (Token) stack.pop();
            int ubi = Integer.parseInt(ub.getValue().toString()), lbi = Integer.parseInt(lb.getValue().toString());
            int msize = ubi - lbi + 1;
            while (!stack.isEmpty()) {
                Token id = (Token) stack.pop();
                String idName = id.getValue().toString();
                ArrayEntry entry = new ArrayEntry(idName, 0, type.getType(), ubi, lbi);
                if (globalF) {
                    entry.setAddress(globalMemory);
                    globalTable.insert(idName, entry);
                    globalMemory += msize;
                } else {
                    entry.setAddress(localMemory);
                    localMemory += msize;
                    localTable.insert(idName, entry);
                }
            }
        } else {
            while (!stack.isEmpty()) {
                Token id = (Token) stack.pop();
                String idName = id.getValue().toString();
                VariableEntry entry = new VariableEntry(idName, 0, type.getType());
                if (globalF) {
                    entry.setAddress(globalMemory);
                    globalMemory += 1;
                    globalTable.insert(idName, entry);
                } else {
                    entry.setAddress(localMemory);
                    localMemory += 1;
                    localTable.insert(idName, entry);
                }
            }
        }
        arrayF = false;
    }
    
    private void action4(Token token) {
        stack.push(token);
    }
    
    private void action6() {
        arrayF = true;
    }
    
    private void action7(Token token) {
        stack.push(token);
    }
    
    private void action9() throws SymbolTableException {
        Token t1 = (Token) stack.pop(),
                t2 = (Token) stack.pop(),
                t3 = (Token) stack.get(0);
        String n1 = t1.getValue().toString(),
                n2 = t2.getValue().toString(),
                n3 = t3.getValue().toString();
        stack.remove(0);
        SymbolTableEntry e1 = new IODeviceEntry(n1),
                e2 = new IODeviceEntry(n2),
                e3 = new ProcedureEntry(n3, 0, null);
        if (globalF) {
            globalTable.insert(n1, e1);
            globalTable.insert(n2, e2);
            globalTable.insert(n3, e3);
            // globalMemory += 3;
        } else {
            localTable.insert(n1, e1);
            localTable.insert(n2, e2);
            localTable.insert(n3, e3);
        }
        insertF = false;

        generate("call", "main", 0);
        generate("exit");
    }
    
    private void action13(Token token) {
        stack.push(token);
    }
    
    private void action30(Token token) throws UndeclaredVariableException {
        String name = token.getValue().toString();
        SymbolTableEntry entry = globalTable.get(name);
        if (entry == null) {
            throw new UndeclaredVariableException(name, line);
        }
        stack.push(entry);
        // stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action31() throws SemanticActionsException, SymbolTableException {
        SymbolTableEntry id2 = (SymbolTableEntry) stack.pop();
        // ************* note to change in phase 3 ************** 
        SymbolTableEntry offset = null;
        // ************* note to change in phase 3 ************** 
        SymbolTableEntry id1 = (SymbolTableEntry) stack.pop();
        int check = typeCheck(id1, id2);
        VariableEntry temp;
        switch (check) {
            case 3:
                throw new SemanticActionsException("Cannot assign real value to integer variable", line);
            case 2:
                temp = create(getTempName(), TokenType.REAL);
                generate("ltof", id2, temp);
                if (offset == null) {
                    generate("move", temp, id1);
                } else {
                    generate("store", id2, offset, id1);
                }
                break;
            default:
                if (offset == null) {
                    generate("move", id2, id1);
                } else {
                    generate("store", id2, offset, id1);
                }
                break;
        }
    }
    
    private void action32() throws SemanticActionsException {
        ETYPE eType = (ETYPE) stack.pop();
        if (!eType.eqauls(ETYPE.ARITHMETIC)) {
            throw new SemanticActionsException("Should be arithmetic operation", line);
        }
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (!id.isArray()) {
            throw new SemanticActionsException(id.getName() + " should be an array", line);
        }
    }
    
    private void action33() throws SemanticActionsException, SymbolTableException {
        ETYPE eType = (ETYPE) stack.pop();
        SymbolTableEntry id = (SymbolTableEntry) stack.pop();
        if (id.getType() != TokenType.INTEGER) {
            throw new SemanticActionsException(id.getName() + " should be an integer", line);
        }
        VariableEntry temp = create(getTempName(), TokenType.INTEGER);
        ArrayEntry array = (ArrayEntry) stack.get(stack.size() - 1);
        generate("sub", id, array.getLowerBound(), temp);
        stack.push(temp);
    }
    
    private void action34() throws SemanticActionsException, SymbolTableException {
        // pop ETYPE
        stack.pop();
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (id.isFunction()) {
            action52();
        } else {
            stack.push(null);
        }
    }
    
    private void action40(Token token) {
        stack.push(token);
    }
    
    private void action41() throws SymbolTableException {
        Token sign = (Token) stack.pop();
        if (sign.isTypeOf(TokenType.UNARYMINUS)) {
            SymbolTableEntry id = (SymbolTableEntry) stack.pop();
            VariableEntry temp = create(getTempName(), id.getType());
            generate("uminus", id, temp);
            stack.push(temp);
        } else {
            stack.push((SymbolTableEntry) stack.pop());
        }
    }
    
    private void action42(Token token) {
        stack.push(token);
    }
    
    private void action43() throws SymbolTableException {
        SymbolTableEntry result, id1, id2;
        VariableEntry temp;
        id2 = (SymbolTableEntry) stack.pop();
        Token op = (Token) stack.pop();
        id1 = (SymbolTableEntry) stack.pop();
        int check = typeCheck(id1, id2);
        switch (check) {
            case 0:
                temp = create(getTempName(), TokenType.INTEGER);
                String ops = (Integer) op.getValue() == 1 ? "add" : "sub";
                generate(ops, id1, id2, temp);
                result = temp;
                break;
            case 1:
                temp = create(getTempName(), TokenType.REAL);
                ops = (Integer) op.getValue() == 1 ? "fadd" : "fsub";
                generate(ops, id1, id2, temp);
                result = temp;
                break;
            case 2:
                VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                generate("ltof", id2, temp1);
                VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                ops = (Integer) op.getValue() == 1 ? "fadd" : "fsub";
                generate(ops, id1, temp1, temp2);
                result = temp2;
                break;
            default:
                temp1 = create(getTempName(), TokenType.REAL);
                generate("ltof", id1, temp1);
                temp2 = create(getTempName(), TokenType.REAL);
                ops = (Integer) op.getValue() == 1 ? "fadd" : "fsub";
                generate(ops, temp1, id2, temp2);
                result = temp2;
                break;
        }
        stack.push(result);
    }
    
    private void action44(Token token) {
        stack.push(token);
    }
    
    private void action45() throws SemanticActionsException, SymbolTableException {
        SymbolTableEntry result, id1, id2;
        VariableEntry temp;
        Token op;
        int check;
        id2 = (SymbolTableEntry) stack.pop();
        op = (Token) stack.pop();
        id1 = (SymbolTableEntry) stack.pop();
        int opv = (Integer) op.getValue();
        check = typeCheck(id1, id2);
        if (check != 0) {
            // mod needs both to be integer
            if (opv == 4) {
                throw new SemanticActionsException("Operands of the MOD operator must both be of type integer", line);
                // div needs both to be integer
            } else if (opv == 3) {
                throw new SemanticActionsException("Operands of the DIV operator must both be of type integer", line);
            }
        }
        if (check == 0) {
            switch (opv) {
                // if op is "mod"
                case 4:
                    {
                        VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                        generate("move", id1, temp1);
                        VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                        generate("move", temp1, temp2);
                        generate("sub", temp2, id2, temp1);
                        generate("bge", temp1, id2, quads.getNextQuad() - 2);
                        result = temp1;
                        break;
                    }
            // if op is "/"
                case 2:
                    {
                        VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                        generate("ltof", id1, temp1);
                        VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                        generate("ltof", id2, temp2);
                        VariableEntry temp3 = create(getTempName(), TokenType.REAL);
                        generate("fdiv", temp1, temp2, temp3);
                        result = temp3;
                        break;
                    }
                default:
                    temp = create(getTempName(), TokenType.INTEGER);
                    generate((opv == 1) ? "mul" : "div", id1, id2, temp);
                    result = temp;
                    break;
            }
        } else if (check == 1) {
            // if op is "div"
            if (opv == 3) {
                VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                generate("ftol", id1, temp1);
                VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                generate("ftol", id2, temp2);
                VariableEntry temp3 = create(getTempName(), TokenType.INTEGER);
                generate("div", temp1, temp2, temp3);
                result = temp3;
            } else {
                temp = create(getTempName(), TokenType.REAL);
                generate((opv == 1) ? "fmul" : "fdiv", id1, id2, temp);
                result = temp;
            }
        } else if (check == 2) {
            // if op is "div"
            if (opv == 3) {
                VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                generate("ftol", id1, temp1);
                VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                generate("ftol", id2, temp2);
                VariableEntry temp3 = create(getTempName(), TokenType.INTEGER);
                generate("div", temp1, temp2, temp3);
                result = temp3;
            } else {
                VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                generate("ltof", id1, temp1);
                VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                generate((opv == 1) ? "fmul" : "fdiv", temp1, id2, temp2);
                result = temp2;
            }
        } else // if op is "div"
        if (opv == 3) {
            VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
            generate("ftol", id1, temp1);
            VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
            generate("ftol", id2, temp2);
            VariableEntry temp3 = create(getTempName(), TokenType.INTEGER);
            generate("div", temp1, temp2, temp3);
            result = temp3;
        } else {
            VariableEntry temp1 = create(getTempName(), TokenType.REAL);
            generate("ltof", id2, temp1);
            VariableEntry temp2 = create(getTempName(), TokenType.REAL);
            generate((opv == 1) ? "fmul" : "fdiv", id1, temp1, temp2);
            result = temp2;
        }
        stack.push(result);
    }
    
    private void action46(Token token) throws UndeclaredVariableException, SymbolTableException {
        // if token is an identifier
        String name = token.getValue().toString();
        SymbolTable table = (globalF) ? globalTable : localTable;
        if (token.isTypeOf(TokenType.IDENTIFIER)) {
            if (!table.lookup(name)) {
                throw new UndeclaredVariableException(name, line);
            }
            stack.push(table.get(name));
            // if token is a constant
        } else {
            ConstantEntry entry = (ConstantEntry) constantTable.get(name);
            if (entry == null) {
                entry = new ConstantEntry(name, (token.isTypeOf(TokenType.INTCONSTANT) ? TokenType.INTEGER : TokenType.REAL));
                constantTable.insert(name, entry);
            }
            stack.push(entry);
        }
    }
    
    private void action48() {
        // ************* note to change in phase 3 ************** 
        SymbolTableEntry offset = null;
        // ************* note to change in phase 3 ************** 
        if (offset != null) {
        }
    }

    private void action52() throws SemanticActionsException, SymbolTableException {
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (!id.isFunction()) {
            throw new SemanticActionsException(id.getName() + " should be function", line);
        }
        FunctionEntry fe = (FunctionEntry) id;
        if (fe.getNumberOfParameters() > 0) {
            throw new SemanticActionsException(id.getName() + " should have 0 parameters. It has " + fe.getNumberOfParameters(), line);
        }
        generate("call", id, 0);
        // ????? fe has no type
        VariableEntry temp = create(getTempName(), fe.getType());
        generate("move", fe.getResult(), temp);
        stack.pop(); // pop id
        stack.pop(); // pop ETYPE
        stack.push(temp);
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action53() throws SemanticActionsException {
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (id.isFunction()) {
            // is this enough??
            if (id == currentFunction) {
                throw new SemanticActionsException(id.getName() + " is not the current function: " + currentFunction.getName(), line);
            }
            stack.pop(); // pop id
            stack.pop(); // pop ETYPE
            stack.push(((FunctionEntry) id).getResult());
            stack.push(ETYPE.ARITHMETIC);
        }
    }
    
    private void action54() throws SemanticActionsException {
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (!id.isProcedure()) {
            throw new SemanticActionsException(id.getName() + " should be procedure", line);
        }
    }
    
    private void action55() {
        backpath(globalStore, globalMemory);
        generate("free", globalMemory);
        generate("PROCEND");
    }
    
    private void action56() {
        generate("PROCBEGIN", "main");
        globalStore = quads.getNextQuad();
        generate("alloc", globalMemory);
    }

    public void execute(SemanticAction action, Token token) throws SymbolTableException, SemanticActionsException {
        int actionIndex = action.getIndex();
        boolean flag = true;
        
        switch (actionIndex) {
            case 1:
                action1();
                break;
            case 2:
                action2();
                break;
            case 3:
                action3();
                break;
            case 4:
                action4(token);
                break;
            case 6:
                action6();
                break;
            case 7:
                action7(token);
                break;
            case 9:
                action9();
                break;
            case 13:
                action13(token);
                break;
            case 30:
                action30(token);
                break;
            case 31:
                action31();
                break;
            case 32:
                action32();
                break;
            case 33:
                action33();
                break;
            case 34:
                action34();
                break;
            case 40:
                action40(token);
                break;
            case 41:
                action41();
                break;
            case 42:
                action42(token);
                break;
            case 43:
                action43();
                break;
            case 44:
                action44(token);
                break;
            case 45:
                action45();
                break;
            case 46:
                action46(token);
                break;
            case 48:
                action48();
                break;
            case 52:
                action52();
                break;
            case 53:
                action53();
                break;
            case 54:
                action54();
                break;
            case 55:
                action55();
                break;
            case 56:
                action56();
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
    
    public void dumpCode() {
        quads.print();
    }

    private void dumpStack() {
        if (__DEBUG__ || true) {
            Token[] a = new Token[stack.size()];
            a = stack.toArray(a);
            Collections.reverse(Arrays.asList(a));
            String log = "Stack :==> " + Arrays.toString(a);
            System.out.println(log);
        }
    }
}
