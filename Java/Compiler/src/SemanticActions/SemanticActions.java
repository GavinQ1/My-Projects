/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions;

import Lexer.Lexer;
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

    private Stack<Object> stack;
    private boolean insertF, globalF, arrayF;
    private boolean __DEBUG__;
    public SymbolTable globalTable, localTable, constantTable;
    private int globalMemory, localMemory, globalStore;
    private Quadruples quads;
    private Lexer lexer;

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
        globalMemory = localMemory = globalStore = 0;
        globalTable.installBuiltins();
        __DEBUG__ = debug;
    }

    public VariableEntry create(String name, TokenType type) throws SymbolTableException {
        VariableEntry entry = new VariableEntry("$$" + name, -globalMemory++, type);
        globalTable.insert(name, entry);
        return entry;
    }
    
    public void setLexer(Lexer l) { 
        lexer = l;
    }
    
    private String getAddr(SymbolTableEntry op) throws SymbolTableException {
        int res;
        if (op.isArray()) {
            res = ((ArrayEntry) op).getAddress();
        } else if (op.isVariable()) {
            res = ((VariableEntry) op).getAddress();
        } else {
            TokenType t = ((ConstantEntry) op).getType();
            VariableEntry temp = create(getTempName(), t);
            generate("move", op.getName(), temp);
            res = temp.getAddress();
        }
        return "_" + res;
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

    public void execute(SemanticAction action, Token token) throws SymbolTableException, SemanticActionsException {
        int actionIndex = action.getIndex();
        boolean flag = true;
        System.out.println("calling action : " + actionIndex + " with token " + token);
        switch (actionIndex) {
            // #1   : INSERT/SEARCH = INSERT
            case 1:
                insertF = true;
                break;
            // #2   : INSERT/SEARCH = SEARCH
            case 2:
                insertF = false;
                break;
            case 3:
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
                            // entry.setAddress(localMemory);
                            // localMemory += msize;
                            // localTable.insert(idName, entry);
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
                            // localTable.insert(idName, entry);
                        }
                    }
                }
                arrayF = false;
                break;
            // #4   : push TYPE
            case 4:
                stack.push(token);
                break;
            // #6   : ARRAY/SIMPLE = ARRAY
            case 6:
                arrayF = true;
                break;
            // #7   : push CONSTANT
            case 7:
                stack.push(token);
                break;
            case 9:
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
                } else {
                    localTable.insert(n1, e1);
                    localTable.insert(n2, e2);
                    localTable.insert(n3, e3);
                }
                insertF = false;

                generate("call", "main", 0);
                generate("exit");
                break;
            // #13  : push id
            case 13:
                stack.push(token);
                break;
            case 30:
                String name = token.getValue().toString();
                SymbolTableEntry entry = globalTable.get(name);
                if (entry == null) {
                    throw new UndeclaredVariableException(name, lexer.getLine());
                }
                stack.push(entry);
                // stack.push(ETYPE.ARITHMETIC);
                break;
            case 31:
                SymbolTableEntry id2 = (SymbolTableEntry) stack.pop();
                // ************* note to change in phase 3 ************** 
                SymbolTableEntry offset = null;
                // ************* note to change in phase 3 ************** 
                SymbolTableEntry id1 = (SymbolTableEntry) stack.pop();
                int check = typeCheck(id1, id2);
                switch (check) {
                    case 3:
                        throw new SemanticActionsException("Cannot assign real value to integer variable", lexer.getLine());
                    case 2:
                        VariableEntry temp = create(getTempName(), TokenType.REAL);
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
                break;
            // #40  : push sign
            case 40:
                stack.push(token);
                break;
            case 41:
                Token sign = (Token) stack.pop();
                if (sign.isTypeOf(TokenType.UNARYMINUS)) {
                    SymbolTableEntry id = (SymbolTableEntry) stack.pop();
                    VariableEntry temp = create(getTempName(), id.getType());
                    generate("uminus", id, temp);
                    stack.push(temp);
                } else {
                    stack.push((SymbolTableEntry) stack.pop());
                }
                break;
            // #42  : push operator
            case 42:
                stack.push(token);
                break;
            case 43:
                SymbolTableEntry result;
                id2 = (SymbolTableEntry) stack.pop();
                Token op = (Token) stack.pop();
                id1 = (SymbolTableEntry) stack.pop();
                check = typeCheck(id1, id2);
                switch (check) {
                    case 0:
                        VariableEntry temp = create(getTempName(), TokenType.INTEGER);
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
                break;
            // #44  : push operator
            case 44:
                stack.push(token);
                break;
            case 45:
                id2 = (SymbolTableEntry) stack.pop();
                op = (Token) stack.pop();
                id1 = (SymbolTableEntry) stack.pop();
                int opv = (Integer) op.getValue();
                check = typeCheck(id1, id2);
                if (check != 0 && opv == 4) {
                    // mod needs both to be integer
                    throw new SemanticActionsException("MOD requires integer operands", lexer.getLine());
                }
                System.out.println(id1.getType() + " " + id2.getType());
                if (check == 0) {
                    // if op is "mod"
                    if (opv == 4) {
                        VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                        generate("move", id1, temp1);
                        VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                        generate("move", temp1, temp2);
                        generate("sub", temp2, id2, temp1);
                        generate("bge", temp1, id2, quads.getNextQuad() - 2);
                        result = temp1;
                    } else // if op is "/"
                    if (opv == 2) {
                        VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                        generate("ltof", id1, temp1);
                        VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                        generate("ltof", id2, temp2);
                        VariableEntry temp3 = create(getTempName(), TokenType.REAL);
                        generate("fdiv", temp1, temp2, temp3);
                        result = temp3;
                    } else {
                        VariableEntry temp = create(getTempName(), TokenType.INTEGER);
                        generate((opv == 1) ? "mul" : "div", id1, id2, temp);
                        result = temp;
                    }
                } else if (check == 1) {
                    // if op is "div"
                    if (opv == 4) {
                        VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                        generate("ftol", id1, temp1);
                        VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                        generate("ftol", id2, temp2);
                        VariableEntry temp3 = create(getTempName(), TokenType.INTEGER);
                        generate("div", temp1, temp2, temp3);
                        result = temp3;
                    } else {
                        VariableEntry temp = create(getTempName(), TokenType.REAL);
                        generate((opv == 1) ? "fmul" : "fdiv", id1, id2, temp);
                        result = temp;
                    }
                } else if (check == 2) {
                    // if op is "div"
                    if (opv == 4) {
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
                } else {
                    // if op is "div"
                    if (opv == 4) {
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
                }
                stack.push(result);
                break;
            case 46:
                // if token is an identifier
                name = token.getValue().toString();
                SymbolTable table = (globalF) ? globalTable : localTable;
                if (token.isTypeOf(TokenType.IDENTIFIER)) {
                    if (!table.lookup(name)) {
                        throw new UndeclaredVariableException(name, lexer.getLine());
                    }
                    stack.push(table.get(name));
                    // if token is a constant
                } else {
                    entry = (ConstantEntry) constantTable.get(name);
                    if (entry == null) {
                        entry = new ConstantEntry(name, (token.isTypeOf(TokenType.INTCONSTANT) ? TokenType.INTEGER : TokenType.REAL));
                        constantTable.insert(name, entry);
                    }
                    stack.push(entry);
                }
                break;
            case 48:
                // ************* note to change in phase 3 ************** 
                offset = null;
                // ************* note to change in phase 3 ************** 
                if (offset != null) {
                }
                break;
            case 55:
                backpath(globalStore, globalMemory);
                generate("free", globalMemory);
                generate("PROCEND");
                break;
            case 56:
                generate("PROCBEGIN", "main");
                globalStore = quads.getNextQuad();
                generate("alloc", globalMemory);
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
