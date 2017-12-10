/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions;

import Lexer.LexicalExceptions.LexicalException;
import Parser.Parser;
import Parser.ParserExceptions.ParserException;
import SemanticActions.SemanticActionsExceptions.*;
import constants.Token;
import SymbolTable.*;
import SymbolTable.SymbolTableEntry.*;
import SymbolTable.SymbolTableExceptions.SymbolTableException;
import constants.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class SemanticActions {
    
    private static String Global_Offset_Prefix = "_";
    private static String Local_Offset_Prefix = "%";
    private static String Parameter_Offset_Prefix = "^%";
    private static String PLACEHOLDER = "_";
    
    private int tempId = 0;
    private boolean __DEBUG__;

    private Stack<Object> stack;
    private boolean insertF, globalF, arrayF;
    private SymbolTable globalTable, localTable, constantTable;
    private int globalMemory, localMemory, globalStore, localStore;
    private Quadruples quads;
    private SymbolTableEntry currentFunction;
    
    private int formalParamCount = 0;
    
    private Stack<Integer> paramCountStack = new Stack<>();
    private void incrementParamCountTop() {
        if (!paramCountStack.isEmpty()) {
            int temp = paramCountStack.pop() + 1;
            paramCountStack.push(temp);
        }
    }
    private void setParamCountTop(int i ) {
        if (!paramCountStack.isEmpty()) {
            paramCountStack.pop();
        }
        paramCountStack.push(i);
    }
    private void nextParamCount() {
        if (!paramCountStack.isEmpty()) {
            paramCountStack.pop();
        }
    }
    private Integer getParamCount() { return paramCountStack.isEmpty() ? null : paramCountStack.peek(); }
    
    private Stack<Stack<SymbolTableEntry>> nextParamStack = new Stack<>();
    private void setNextParamTop(Stack<SymbolTableEntry> s) {
        if (!nextParamStack.isEmpty()) {
            nextParamStack.pop();
        }
        nextParamStack.push(s);
    }
    private void nextParamInfo() {
        if (!nextParamStack.isEmpty()) {
            nextParamStack.pop();
        }
    }
    private Stack<SymbolTableEntry> getParamInfo() { return nextParamStack.isEmpty() ? null : nextParamStack.peek(); }
    
    // where are we in src
    private int line;

    
    enum ETYPE {
        ARITHMETIC(0),
        RELATIONAL(1);

        private final int n;

        private ETYPE(int n) {
            this.n = n;
        }

        public int getIndex() {
            return n;
        }
        
        public boolean isArithmetic() {
            return n == 0;
        }
        
        public boolean isRelational() {
            return n == 1;
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
    
    public void setLine(int line) {
        this.line = line;
    }
    
    // start searching scope from local to global
    private String prefixAddress(int address, SymbolTableEntry op) {
        int val = Math.abs(address);
        String prefix;
        
        if (op.isParameter()) {
            prefix = Parameter_Offset_Prefix; 
        } else if (localTable.lookup(op.getName())) {
            prefix = Local_Offset_Prefix;
        } else if (globalTable.lookup(op.getName())) {
            prefix = Global_Offset_Prefix;
        } else {
            // constant case
            prefix = globalF ? Global_Offset_Prefix : Local_Offset_Prefix;
        }
        
        return prefix + val;
    }
    private String getAddr(SymbolTableEntry op) throws SymbolTableException {
        int res;
        if (op.isArray()) {
            res = ((ArrayEntry) op).getAddress();
        } else if (op.isVariable()) {
            res = ((VariableEntry) op).getAddress();
        } else if (op.isConstant()) {
            VariableEntry temp = create(getTempName(), ((ConstantEntry) op).getType());
            generate("move", op.getName(), temp);
            res = temp.getAddress();
        } else {
            res = 0x7fffffff;
        }
        
        return prefixAddress(res, op);
    }

    private String getTempName() {
        return "t" + tempId++;
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
    
    private void generate(String tviCode, int op) {
        generate(tviCode, Integer.toString(op));
    }

    private void generate(String tviCode, SymbolTableEntry op1, SymbolTableEntry op2, int label) throws SymbolTableException {
        generate(tviCode, op1, op2, String.valueOf(label));
    }
    
    private void generate(String tviCode, SymbolTableEntry op1, SymbolTableEntry op2, String label) throws SymbolTableException {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = getAddr(op1);
        quad[2] = getAddr(op2);
        quad[3] =  label;
        quads.addQuad(quad);
    }
    
    private void generate(String tviCode, String op) {
        String[] quad = new String[4];
        quad[0] = tviCode;
        quad[1] = op;
        quads.addQuad(quad);
    }
    
    private void generate(String tviCode, SymbolTableEntry op) throws SymbolTableException {
        generate(tviCode, getAddr(op));
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
    
    private SymbolTable getCurrentTable() {
        return globalF ? globalTable : localTable; 
    }
    private int getMemory() {
        return (globalF) ? globalMemory++ : localMemory++;
    }
    private final static String Temp_Prefix = "$$"; 
    private String prefixTemp(String name) {
        return Temp_Prefix + name;
    }
    private VariableEntry create(String name, TokenType type) throws SymbolTableException {
        name = prefixTemp(name);
        VariableEntry entry = new VariableEntry(name, -getMemory(), type);
        getCurrentTable().insert(name, entry);
        return entry;
    }
    
    private List<Integer> makeList(int i) {
        return new ArrayList<>(Arrays.asList(i));
    }
    
    private List<Integer> merge(List<Integer> l1, List<Integer> l2) {
        List<Integer> res = new ArrayList<>(l1);
        res.addAll(l2);
        return res;
    }
    
    private void backPatch(int p, int i) {
        String[] quad = quads.getQuad(p);
        int w = -1;
        for (int j = 0; j < quad.length; j++) {
            if (PLACEHOLDER.equals(quad[j])) {
                w = j;
                break;
            }
        }
        if (w > -1) quads.setField(p, w, String.valueOf(i));
    }
    
    private void backPatch(List<Integer> ps, int i) {
        ps.stream().forEach((p) -> {
            backPatch(p, quads.getNextQuad());
        });
    }
    
    private int typeCheck(SymbolTableEntry id1, SymbolTableEntry id2) {
        TokenType t1 = id1.getType(), t2 = id2.getType();
        // both int
        if (t1.isTypeOf(TokenType.INTEGER) && t2.isTypeOf(TokenType.INTEGER)) {
            return 0;
            // both real
        } else if (t1.isTypeOf(TokenType.REAL) && t2.isTypeOf(TokenType.REAL)) {
            return 1;
            // id1 real, id2 int
        } else if (t1.isTypeOf(TokenType.REAL) && t2.isTypeOf(TokenType.INTEGER)) {
            return 2;
            // id1 int, id2 real
        } else {
            return 3;
        }
    }
    
    private List<Integer> popEList() { return (ArrayList<Integer>) stack.pop(); }
    private ETYPE popETYPE() { return (ETYPE) stack.pop(); }
    private SymbolTableEntry popSymbolTableEntry() { return (SymbolTableEntry) stack.pop(); }
    private Token popToken() { return (Token) stack.pop(); }
    
    //#1   : INSERT/SEARCH = INSERT
    private void action1() {
        insertF = true;
    }
    
    //#2   : INSERT/SEARCH = SEARCH
    private void action2() {
        insertF = false;
    }
    
    private void action3() throws SymbolTableException {
        Token type = popToken();
        if (arrayF) {
            Token ub = popToken(), lb = popToken();
            int ubi = Integer.parseInt(ub.getLexeme()), lbi = Integer.parseInt(lb.getLexeme());
            int msize = ubi - lbi + 1;
            while (!stack.isEmpty() && stack.peek() instanceof Token) {
                Token id = popToken();
                String idName = id.getLexeme();
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
            while (!stack.isEmpty() && stack.peek() instanceof Token) {
                Token id = popToken();
                String idName = id.getLexeme();
                VariableEntry entry = new VariableEntry(idName, 0, type.getType());
                if (globalF) {
                    entry.setAddress(globalMemory);
                    globalMemory++;
                    globalTable.insert(idName, entry);
                } else {
                    entry.setAddress(localMemory);
                    localMemory++;
                    localTable.insert(idName, entry);
                }
            }
        }
        arrayF = false;
    }
    
    //#4   : push TYPE
    private void action4(Token token) {
        stack.push(token);
    }
    
    private void action5() throws SymbolTableException {
        insertF = false;
        generate("PROCBEGIN", popSymbolTableEntry().getName());
        localStore = quads.getNextQuad();
        generate("alloc", PLACEHOLDER);
    }
    
    //#6   : ARRAY/SIMPLE = ARRAY
    private void action6() {
        arrayF = true;
    }
    
    //#7   : push CONSTANT
    private void action7(Token token) {
        stack.push(token);
    }
    
    private void action9() throws SymbolTableException {
        Token t1 = popToken(),
                t2 = popToken(),
                t3 = popToken(); // bottom
        String n1 = t1.getLexeme(),
                n2 = t2.getLexeme(),
                n3 = t3.getLexeme();
        SymbolTableEntry e1 = new IODeviceEntry(n1),
                e2 = new IODeviceEntry(n2),
                e3 = new ProcedureEntry(n3, 0, null);
        getCurrentTable().insert(n1, e1);
        getCurrentTable().insert(n2, e2);
        getCurrentTable().insert(n3, e3);
        insertF = false;

        generate("call", SymbolTable.BUILT_IN_MAIN, 0);
        generate("exit");
    }
    
    private void clearLocalSymbolTable() {
        localTable.clear();
    }
    private void action11() {
        currentFunction = null;
        // ??? not sure
        backPatch(localStore, localMemory);
        generate("free", localMemory);
        globalF = true;
        clearLocalSymbolTable();
        generate("PROCEND");
    }
    
    //#13  : push id
    private void action13(Token token) {
        stack.push(token);
    }
    
    private void action15(Token token) throws SymbolTableException {
        String funcName = token.getLexeme();
        FunctionEntry id = new FunctionEntry(funcName);
        
        // insert
        getCurrentTable().insert(funcName, id);
        // push
        stack.push(id);
        
        VariableEntry temp = create(funcName, TokenType.INTEGER);
        id.setResult(temp);
    
        globalF = false;
        localMemory = 0;
    }
    
    private void action16() {
        TokenType type = popToken().getType();
        // id is func
        FunctionEntry id = (FunctionEntry) stack.peek();

        // $$FUN_NAME.type
        VariableEntry temp = id.getResult();
        temp.setType(type);
        
        currentFunction = id;
    }
    
    private void action17(Token token) throws SymbolTableException {
        String procName = token.getLexeme();
        ProcedureEntry id = new ProcedureEntry(procName);
        // insert
        getCurrentTable().insert(procName, id);
        // push
        stack.push(id);
        
        globalF = false;
        localMemory = 0;
    }
    
    private void action19() {
        formalParamCount = 0;
    }
    
    private void action20() {
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        id.setNumberOfParameters(formalParamCount);
        // pop PARAMCOUNT
        nextParamCount();
    }
    
    private void action21() throws SymbolTableException {
        TokenType type = popToken().getType();
        SymbolTableEntry proc = (SymbolTableEntry) stack.get(0);

        if (arrayF) {
            Token ub = popToken(), lb = popToken();
            int ubi = Integer.parseInt(ub.getLexeme()), lbi = Integer.parseInt(lb.getLexeme());
            while (!stack.isEmpty() && stack.peek() instanceof Token) {
                Token id = popToken();
                String idName = id.getLexeme();
                ArrayEntry entry = new ArrayEntry(idName, localMemory++, type, ubi, lbi);
                entry.setIsParam(true);
                // insert symbol table entry (array, is_parameter returns true)
                getCurrentTable().insert(idName, entry);

                // increment PARAMCOUNT
                // add a new element to id.paraminfo   // id is procedure name
                formalParamCount++;
                proc.addParam(entry);
            }
        } else {
            while (!stack.isEmpty() && stack.peek() instanceof Token) {
                Token id = popToken();
                String idName = id.getLexeme();
                VariableEntry entry = new VariableEntry(idName, localMemory++, type);
                entry.setIsParam(true);// insert symbol table entry (array, is_parameter returns true)
                getCurrentTable().insert(idName, entry);
                
                // increment PARAMCOUNT
                // add a new element to id.paraminfo   // id is procedure name
                formalParamCount++;
                proc.addParam(entry);
            }
        }
        arrayF = false;
    }
    
    private Object getFromStackTop(int i) {
        return stack.get(stack.size() - 1 - i);
    }
    private void action22() throws SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isRelational()) {
            throw new SemanticActionsException("Invalid use of arithmetic operator", line);
        }
        List<Integer> eT = (List<Integer>) getFromStackTop(1);
        backPatch(eT, quads.getNextQuad());
    }
    
    private void action24() {
        // set BEGINLOOP = NEXTQUAD, push it!
        stack.push(quads.getNextQuad());
    }
    
    private void action25() throws SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isRelational()) {
            throw new SemanticActionsException("Invalid use of arithmetic operator", line);
        }
        List<Integer> eT = (List<Integer>) getFromStackTop(1);
        backPatch(eT, quads.getNextQuad());
    }
    
    private void action26() {
        List<Integer> eF = popEList();
        popEList(); // pop E.TRUE
        int beginLoop = (Integer) stack.pop();
        generate("goto", beginLoop);
        backPatch(eF, quads.getNextQuad());
    }
    
    private void action27() {
        // set SKIP_ELSE = makelist(NEXTQUAD), push it!
        stack.push(makeList(quads.getNextQuad()));
        generate("goto", PLACEHOLDER);
        // stack : SKIP_ELSE, E.FALSE
        List<Integer> eF = (List<Integer>) getFromStackTop(1);
        backPatch(eF, quads.getNextQuad());
    }
    
    private void action28() {
        // pop SKIP_ELSE, E.FALSE, E.TRUE
        List<Integer> skip_else = popEList(); popEList();  popEList(); 
        backPatch(skip_else, quads.getNextQuad());
    }
    
    private void action29() {
        // pop E.FALSE, E.TRUE
        List<Integer> eF = popEList();
        List<Integer> eT = popEList();
        backPatch(eF, quads.getNextQuad());
    }
    
    private SymbolTableEntry getId(String name) {
        if (localTable.lookup(name)) {
            return localTable.get(name);
        }
        return globalTable.get(name);
    }
    private void action30(Token token) throws UndeclaredVariableException {
        String name = token.getLexeme();
        SymbolTableEntry entry = getId(name);
        if (entry == null) {
            throw new UndeclaredVariableException(name, line);
        }
        stack.push(entry);
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action31() throws SemanticActionsException, SymbolTableException {
        ETYPE etype = popETYPE();
        if (!etype.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator", line);
        }
        SymbolTableEntry id2 = popSymbolTableEntry();
        SymbolTableEntry offset = popSymbolTableEntry();
        SymbolTableEntry id1 = popSymbolTableEntry();
        int check = typeCheck(id1, id2);
        switch (check) {
            case 3:
                throw new SemanticActionsException("Cannot assign real value to integer variable", line);
            case 2:
                VariableEntry temp = create(getTempName(), TokenType.REAL);
                generate("ltof", id2, temp);
                if (offset == null) {
                    generate("move", temp, id1);
                } else {
                    generate("stor", id2, offset, id1);
                }
                break;
            default:
                if (offset == null) {
                    generate("move", id2, id1);
                } else {
                    generate("stor", id2, offset, id1);
                }
                break;
        }
    }
    
    private void action32() throws SemanticActionsException {
        ETYPE eType = popETYPE();
        if (!eType.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator operation", line);
        }
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (!id.isArray()) {
            throw new SemanticActionsException(id.getName() + " should be an array", line);
        }
    }
    
    private ConstantEntry convertToIntegerEntry(int i) {
        return new ConstantEntry(String.valueOf(i), TokenType.INTEGER);
    }
    private void action33() throws SemanticActionsException, SymbolTableException {
        ETYPE eType = popETYPE();
        if (!eType.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator", line);
        }
        SymbolTableEntry id = popSymbolTableEntry();
        if (id.getType() != TokenType.INTEGER) {
            throw new SemanticActionsException(id.getName() + " should be an integer", line);
        }
        VariableEntry temp = create(getTempName(), TokenType.INTEGER);
        // get from bottom
        ArrayEntry array = (ArrayEntry) stack.peek(); 
        // save lower bound to actual real memory address first!
        generate("sub", id, convertToIntegerEntry(array.getLowerBound()), temp);
        stack.push(temp);
    }
    
    private void action34() throws SemanticActionsException, SymbolTableException {
        // pop ETYPE
        popETYPE();
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (id.isFunction()) {
            action52();
        } else {
            stack.push(null);
        }
    }
    
    private SymbolTableEntry searchCallerFromTop() {
        SymbolTableEntry caller = null;
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i) instanceof FunctionEntry ||
                stack.get(i) instanceof ProcedureEntry) {
                caller = (SymbolTableEntry) stack.get(i);
                break;
            }
        }
        return caller;
    }
    private Stack<SymbolTableEntry> convert2ParamStack(List<SymbolTableEntry> info) {
        Stack<SymbolTableEntry> nextParam = new Stack<>();
        for (int i = info.size() - 1; i >= 0; i--) {
            nextParam.push(info.get(i));
        }
        return nextParam;
    }
    private void action35() {
        SymbolTableEntry id = searchCallerFromTop();
        // push new element on PARAMCOUNT stack
        // PARAMCOUNT.top = 0
        paramCountStack.push(0);
        
        // push new element on NEXTPARAM stack
        // set NEXTPARAM = id.paraminfo    {info about parameters}
        nextParamStack.push(convert2ParamStack(id.getParameterInfo()));
    }
    
    private void action36() throws SemanticActionsException {
        popETYPE();
        SymbolTableEntry id = popSymbolTableEntry();
        if (id.getNumberOfParameters() != 0) {
            throw new SemanticActionsException(id.getName() + " should have 0 parameters", line);
        }
        generate("call", id.getName(), 0);
    }
    
    private void action37() throws SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator operation", line);
        }
        SymbolTableEntry id = (SymbolTableEntry) getFromStackTop(0);
        if (!(id.isArray() || id.isConstant() || id.isVariable() || id.isFunctionResult())) {
            throw new SemanticActionsException(id.getName() + " should be a value", line);
        }
        incrementParamCountTop();
        SymbolTableEntry caller = searchCallerFromTop();
        if (!(caller.getName().equals(SymbolTable.BUILT_IN_READ) || 
              caller.getName().equals(SymbolTable.BUILT_IN_WRITE))) {
            if (getParamCount() > caller.getNumberOfParameters()) {
                throw new SemanticActionsException(
                        caller.getName()
                        + " must have exactly "
                        + caller.getNumberOfParameters()
                        + " parameters", line);
            }
            SymbolTableEntry declaredParam = getParamInfo().pop();
            if (!id.getType().isTypeOf(declaredParam.getType())) {
                throw new SemanticActionsException(
                        id.getName()
                        + " should be "
                        + declaredParam.getType(), line);
            }
            if (declaredParam.isArray()) {
                if (id.getUpperBound() != declaredParam.getUpperBound() ||
                    id.getLowerBound() != declaredParam.getLowerBound()) {
                    throw new SemanticActionsException(
                        id.getName()
                        + "'s bounds should be "
                        + declaredParam.getLowerBound() 
                        + " ... " + declaredParam.getUpperBound(), line);
                }
            }
        }
    }
    
    private void action38(Token token) throws SemanticActionsException {
        ETYPE eType = popETYPE();
        if (!eType.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator", line);
        }
        // push operator
        stack.push(token);
    }
    
    private String relopToCode(int i) {
        switch(i) {
            case 1:
                return "beq";
            case 2:
                return "bne";
            case 3:
                return "blt";
            case 4:
                return "bgt";
            case 5:
                return "ble";
            default:
                return "bge";
        }
    } 
    private void action39() throws SemanticActionsException, SymbolTableException {
        ETYPE eType = popETYPE();
        if (!eType.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator", line);
        }
        SymbolTableEntry id2 = popSymbolTableEntry();
        
        // relop
        Token op = popToken();
        // see lexical analyzer for relop reference
        String tviCode = relopToCode((Integer) op.getValue()); 
        
        SymbolTableEntry id1 = popSymbolTableEntry();
        int check = typeCheck(id1, id2);
        if (check == 2) {
            SymbolTableEntry temp = create(getTempName(), TokenType.REAL);
            generate("ltof", id2, temp);
            generate(tviCode, id1, temp, PLACEHOLDER);
        } else if (check == 3) {
            SymbolTableEntry temp = create(getTempName(), TokenType.REAL);
            generate("ltof", id1, temp);
            generate(tviCode, temp, id2, PLACEHOLDER);
        } else {
            generate(tviCode, id1, id2, PLACEHOLDER);
        }
        generate("goto", PLACEHOLDER);
        
        List<Integer> E_True = makeList(quads.getNextQuad() - 2);
        List<Integer> E_False = makeList(quads.getNextQuad() - 1);
        stack.push(E_True);
        stack.push(E_False);
        stack.push(ETYPE.RELATIONAL);
    }
    
    //#40  : push sign
    private void action40(Token token) {
        stack.push(token);
    }
    
    private void action41() throws SymbolTableException, SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of relational operator", line);
        }
        //pop sign, id
        SymbolTableEntry id = popSymbolTableEntry();
        Token sign = popToken();
        if (sign.isTypeOf(TokenType.UNARYMINUS)) {
            TokenType idType = id.getType();
            VariableEntry temp = create(getTempName(), idType);
            generate((idType.isTypeOf(TokenType.INTEGER)) ? "uminus" : "fuminus", id, temp);

            stack.push(temp);
        } else {
            //pop sign, id
            stack.pop(); stack.pop();
            //push id
            stack.push(id);
        }
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private boolean isTokenOr(Token op) {
        return op.isTypeOf(TokenType.ADDOP) && (Integer) op.getValue() == 3;
    }
    private void action42(Token token) throws SemanticActionsException {
        ETYPE eType = popETYPE();
        // if operator is OR
        if (isTokenOr(token)) {
            if (!eType.isRelational()) {
                throw new SemanticActionsException("Should not be relational", line);
            }
            List<Integer> E_FALSE = (List<Integer>) stack.peek();
            backPatch(E_FALSE, quads.getNextQuad());
        } else {
            if (!eType.isArithmetic()) {
                throw new SemanticActionsException("Invalid use of relational operator", line);
            }
        }
        // push operator
        stack.push(token);
    }
    
    private void action43() throws SymbolTableException, SemanticActionsException {
        ETYPE eType = popETYPE();
        if (eType.isRelational()) {
            List<Integer> e2F = (ArrayList<Integer>) getFromStackTop(0),
                    e2T = (ArrayList<Integer>) getFromStackTop(1),
                    e1F = (ArrayList<Integer>) getFromStackTop(3),
                    e1T = (ArrayList<Integer>) getFromStackTop(4);
            Token op = (Token) getFromStackTop(2);
            if (isTokenOr(op)) {
                List<Integer> E_True = merge(e1T, e2T);
                List<Integer> E_False = e2F;
                stack.pop(); stack.pop(); stack.pop(); stack.pop(); stack.pop();
                stack.push(E_True); stack.push(E_False); stack.push(ETYPE.RELATIONAL); 
            }
            
        } else {
            if (!eType.isArithmetic()) {
                throw new SemanticActionsException("Invalid use of relational operator", line);
            }
            SymbolTableEntry result, id1, id2;
            VariableEntry temp;
            id2 = popSymbolTableEntry();
            Token op = popToken();
            id1 = popSymbolTableEntry();
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
                    VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                    generate("ltof", id2, temp1);
                    ops = (Integer) op.getValue() == 1 ? "fadd" : "fsub";
                    generate(ops, id1, temp1, temp2);
                    result = temp2;
                    break;
                default:
                    temp1 = create(getTempName(), TokenType.REAL);
                    temp2 = create(getTempName(), TokenType.REAL);
                    generate("ltof", id1, temp1);
                    ops = (Integer) op.getValue() == 1 ? "fadd" : "fsub";
                    generate(ops, temp1, id2, temp2);
                    result = temp2;
                    break;
            }
            stack.push(result);
            stack.push(ETYPE.ARITHMETIC);
        }
    }
    
    private boolean isTokenAnd(Token token) {
        return token.isTypeOf(TokenType.MULOP) && (Integer) token.getValue() == 5;
    }
    private void action44(Token token) {
        ETYPE etype = popETYPE();
        if (etype.isRelational()) {
            // if op is AND
            if (isTokenAnd(token)) {
                List<Integer> E_TRUE = (List<Integer>) this.getFromStackTop(1);
                backPatch(E_TRUE, quads.getNextQuad());
            }
        }
        stack.push(token);
    }
    
    private void action45() throws SemanticActionsException, SymbolTableException {
        ETYPE etype = popETYPE();
        if (etype.isRelational()) {
            List<Integer> e2F = (ArrayList<Integer>) getFromStackTop(0),
                    e2T = (ArrayList<Integer>) getFromStackTop(1),
                    e1F = (ArrayList<Integer>) getFromStackTop(3),
                    e1T = (ArrayList<Integer>) getFromStackTop(4);
            Token op = (Token) getFromStackTop(2);
            if (isTokenAnd(op)) {
                List<Integer> E_True = e2T;
                List<Integer> E_False = merge(e1F, e2F);
                stack.pop(); stack.pop(); stack.pop(); stack.pop(); stack.pop();
                stack.push(E_True); stack.push(E_False); stack.push(ETYPE.RELATIONAL); 
            }
        } else {
            if (!etype.isArithmetic()) {
                throw new SemanticActionsException("Invalid use of relational operator", line);
            }
            SymbolTableEntry result, id1, id2;
            VariableEntry temp;
            Token op;
            int check;
            id2 = popSymbolTableEntry();
            op = popToken();
            id1 = popSymbolTableEntry();
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
                    case 4: {
                        VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                        VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                        generate("move", id1, temp1);
                        generate("move", temp1, temp2);
                        generate("sub", temp2, id2, temp1);
                        generate("bge", temp1, id2, quads.getNextQuad() - 2);
                        result = temp1;
                        break;
                    }
                    // if op is "/"
                    case 2: {
                        VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                        VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                        VariableEntry temp3 = create(getTempName(), TokenType.REAL);
                        generate("ltof", id1, temp1);
                        generate("ltof", id2, temp2);
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
                    VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                    VariableEntry temp3 = create(getTempName(), TokenType.INTEGER);
                    // GEN(ftol,id1,$$TEMP1)
                    generate("ftol", id1, temp1);
                    // GEN(ftol,id2,$$TEMP2)
                    generate("ftol", id2, temp2);
                    // GEN(div,$$TEMP1,$$TEMP2,$$TEMP3)
                    generate("div", temp1, temp2, temp3);
                    result = temp3;
                } else {
                    temp = create(getTempName(), TokenType.REAL);
                    // GEN(f***,id1,id2,$$TEMP) 
                    generate((opv == 1) ? "fmul" : "fdiv", id1, id2, temp);
                    result = temp;
                }
            } else if (check == 2) {
                // if op is "div"
                if (opv == 3) {
                    VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                    VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                    // GEN(ftol,id1,$$TEMP1)
                    generate("ftol", id1, temp1);
                    // GEN(div,$$TEMP1,id2,$$TEMP2)
                    generate("div", temp1, id2, temp2);
                    result = temp2;
                } else {
                    //CREATE(TEMP1,REAL) 
                    VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                    // CREATE(TEMP2,REAL)
                    VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                    // GEN(ltof,id2,$$TEMP1)
                    generate("ltof", id2, temp1);
                    // GEN(f***,id1,$$TEMP1,$$TEMP2)
                    generate((opv == 1) ? "fmul" : "fdiv", id1, temp1, temp2);
                    result = temp2;
                }
            } else // if op is "div"
            {
                if (opv == 3) {
                    VariableEntry temp1 = create(getTempName(), TokenType.INTEGER);
                    VariableEntry temp2 = create(getTempName(), TokenType.INTEGER);
                    // GEN(ftol,id2,$$TEMP1)
                    generate("ftol", id2, temp1);
                    // GEN(div,id1,$$TEMP1,$$TEMP2)
                    generate("div", id1, temp1, temp2);
                    result = temp2;
                } else {
                    //CREATE(TEMP1,REAL) 
                    VariableEntry temp1 = create(getTempName(), TokenType.REAL);
                    // CREATE(TEMP2,REAL)
                    VariableEntry temp2 = create(getTempName(), TokenType.REAL);
                    // GEN(ltof,id1,$$TEMP1)
                    generate("ltof", id1, temp1);
                    // GEN(f***,$$TEMP1,id2,$$TEMP2)
                    generate((opv == 1) ? "fmul" : "fdiv", temp1, id2, temp2);
                    result = temp2;
                }
            }
            stack.push(result);
            stack.push(ETYPE.ARITHMETIC);
        }
        
        
    }
    
    private void action46(Token token) throws UndeclaredVariableException, SymbolTableException {
        // if token is an identifier
        String name = token.getLexeme();
        if (token.isTypeOf(TokenType.IDENTIFIER)) {
            SymbolTableEntry entry = getId(name);
            if (entry == null) {
                throw new UndeclaredVariableException(name, line);
            }
            stack.push(entry);
        // if token is a constant
        } else {
            ConstantEntry entry = (ConstantEntry) constantTable.get(name);
            if (entry == null) {
                entry = new ConstantEntry(name, (token.isTypeOf(TokenType.INTCONSTANT) ? TokenType.INTEGER : TokenType.REAL));
                constantTable.insert(name, entry);
            }
            stack.push(entry);
        }
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action47() throws SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isRelational()) {
            throw new SemanticActionsException("Invalid use of arithmetic operator", line);
        }
        // E.TRUE = E.FALSE
        List<Integer> eT = (ArrayList<Integer>) stack.pop();  // popped is e.false
        // E.FALSE = E.TRUE
        List<Integer> eF = (ArrayList<Integer>) stack.pop(); // popped is e.true
        stack.push(eT); stack.push(eF); stack.push(ETYPE.RELATIONAL);
    }
    
    private void action48() throws SemanticActionsException, SymbolTableException {
        if (stack.peek() instanceof ETYPE) popETYPE();
        SymbolTableEntry offset = popSymbolTableEntry();

        if (offset != null) {
            if (offset.isFunction()) {
                action52();
            } else {
                SymbolTableEntry id = popSymbolTableEntry();
                VariableEntry temp = create(getTempName(), id.getType());
                generate("load", id, offset, temp);
                stack.push(temp);
            }
        }
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action49() throws SemanticActionsException {
        ETYPE etype = popETYPE();
        if (!etype.isArithmetic()) {
            throw new SemanticActionsException("Invalid use of arithmetic operator", line);
        }
        SymbolTableEntry func = (SymbolTableEntry) getFromStackTop(0);
        if (!func.isFunction()) {
            throw new SemanticActionsException(func.getName() + " should be a function", line);
        }
        // push new element on PARAMCOUNT stack
        // PARAMCOUNT.top = 0
        paramCountStack.push(0);
        
        // push new element on NEXTPARAM stack
        // set NEXTPARAM = id.paraminfo    {info about parameters}
        nextParamStack.push(convert2ParamStack(func.getParameterInfo()));
    }
    
    private String getParamAddress(SymbolTableEntry op) throws SymbolTableException {
        String prefix;
        int res;
        if (op.isConstant()) {
            VariableEntry temp = create(getTempName(), op.getType());
            generate("move", op.getName(), temp);
            res = temp.getAddress();
        } else {
            res = op.getAddress();
        }
        res = Math.abs(res);
        
        if (op.isParameter()) {
            prefix = "%";
        } else if (localTable.lookup(op.getName())) {
            prefix = "@%";
        } else {
            prefix = "@_";
        }
        return prefix + res;
    }
    private void action50() throws SemanticActionsException, SymbolTableException {
        Stack<SymbolTableEntry> right_order = new Stack<>();
        FunctionEntry func = null;
        
        while (!stack.isEmpty()) {
            if (stack.peek() instanceof FunctionEntry) {
                func = (FunctionEntry) stack.peek();
                break;
            }
            SymbolTableEntry id = popSymbolTableEntry();
            right_order.push(id);
        }
        Collections.reverse(right_order);
        while (!right_order.isEmpty()) {
            SymbolTableEntry entry = right_order.pop();
            generate("param", getParamAddress(entry));
            localMemory++;
        }
        if (getParamCount() > func.getNumberOfParameters()) {
            throw new SemanticActionsException(
                        func.getName()
                        + " must have exactly "
                        + func.getNumberOfParameters()
                        + " parameters", line);
        }

        generate("call", func.getName(), getParamCount());
        // pop PARAMCOUNT.top, NEXTPARAM.top, (no ETYPE here)!!
        this.nextParamCount(); this.nextParamInfo(); 
        
        VariableEntry temp = create(getTempName(), func.getResult().getType());
        generate("move", func.getResult(), temp);
        // pop id
        stack.pop();
        stack.push(temp);
        stack.push(ETYPE.ARITHMETIC);
    }
    
    private void action51Write(SymbolTableEntry caller, Stack<Object> right_order) throws SymbolTableException {
        while (!right_order.isEmpty()) {
            SymbolTableEntry paramVal = (SymbolTableEntry) right_order.pop();
            generate("print", "\"" + paramVal.getName() + " = \"");
            if (paramVal.getType().isTypeOf(TokenType.REAL)) {
                generate("foutp", paramVal);
            } else {
                generate("outp", paramVal);
            }
            generate("newl");
        }
        
        nextParamCount(); stack.pop(); stack.pop();
    }
    private void action51Read(SymbolTableEntry caller, Stack<Object> right_order) throws SymbolTableException {
        while (!right_order.isEmpty()) {
            SymbolTableEntry paramVal = (SymbolTableEntry) right_order.pop();
            generate("print", "\"Enter value : \"");
            if (paramVal.getType().isTypeOf(TokenType.REAL)) {
                generate("finp", paramVal);
            } else {
                generate("inp", paramVal);
            }
        }
        
        nextParamCount(); stack.pop(); stack.pop();
    }
    private void action51() throws SymbolTableException {
        SymbolTableEntry caller = searchCallerFromTop();
        
        Stack<Object> right_order = new Stack<>();
        while (!stack.isEmpty()) {
            Object p = stack.peek();
            if (p instanceof FunctionEntry || p instanceof ProcedureEntry || !(p instanceof SymbolTableEntry)) {
                break;
            }
            right_order.push(stack.pop());
        }
        
        if (caller.getName().equals(SymbolTable.BUILT_IN_READ)) {
            action51Read(caller, right_order);
        } else if (caller.getName().equals(SymbolTable.BUILT_IN_WRITE)) {
            action51Write(caller, right_order);
        } else {
           while (!right_order.isEmpty()) {
               SymbolTableEntry paramVal = (SymbolTableEntry) right_order.pop();
               generate("param", getParamAddress(paramVal));
               localMemory++;
           }
           
           generate("call", caller.getName(), getParamCount());
           nextParamCount(); nextParamInfo();
           stack.pop(); stack.pop();
        }
    }

    private void action52() throws SemanticActionsException, SymbolTableException {
        SymbolTableEntry id = (SymbolTableEntry) stack.peek();
        if (!id.isFunction()) {
            throw new SemanticActionsException(id.getName() + " should be function", line);
        }
        if (id.getNumberOfParameters() > 0) {
            throw new SemanticActionsException(id.getName() + " should have 0 parameters. It has " + id.getNumberOfParameters() + " parameters", line);
        }
        generate("call", id.getName(), 0);
        VariableEntry temp = create(getTempName(), id.getResult().getType());
        generate("move", id.getResult(), temp);
        stack.pop(); // pop id
        stack.push(temp);
        // stack.pop(); // pop ETYPE
        stack.push(null);
    }
    
    private void action53() throws SemanticActionsException {         
        SymbolTableEntry id = (SymbolTableEntry) getFromStackTop(1);
        if (id.isFunction()) {
            if (id != currentFunction) {
                throw new SemanticActionsException(id.getName() + " is not the current function: " + currentFunction.getName(), line);
            }
            stack.pop(); // pop id
            stack.pop(); // pop ETYPE
            stack.push(((FunctionEntry) id).getResult());
            stack.push(ETYPE.ARITHMETIC);
        }
    }
    
    private void action54() throws SemanticActionsException {
        SymbolTableEntry id = (SymbolTableEntry) getFromStackTop(1);
        if (!id.isProcedure()) {
            throw new SemanticActionsException(id.getName() + " should be procedure", line);
        }
    }
    
    private void action55() {
        backPatch(globalStore, globalMemory);
        generate("free", globalMemory);
        generate("PROCEND");
    }
    
    private void action56() {
        generate("PROCBEGIN", SymbolTable.BUILT_IN_MAIN);
        globalStore = quads.getNextQuad();
        generate("alloc", PLACEHOLDER);
    }

    // Order: E.False, E.True,
    private boolean __DUMP__ = 0 == 1;
    private boolean __STACK__ = 1 == 1;
    public void execute(SemanticAction action, Token token) throws SymbolTableException, SemanticActionsException {
        int actionIndex = action.getIndex();
        boolean flag = true;
        if (1 == 0) {
             System.out.println(line + " calling action : " + actionIndex + " with token " + token);
        }
        
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
            case 5:
                action5();
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
            case 11:
                action11();
                break;
            case 13:
                action13(token);
                break;
            case 15:
                action15(token);
                break;
            case 16:
                action16();
                break;
            case 17:
                action17(token);
                break;
            case 19:
                action19();
                break;
            case 20:
                action20();
                break;
            case 21:
                action21();
                break;
            case 22:
                action22();
                break;
            case 24:
                action24();
                break;
            case 25:
                action25();
                break;
            case 26:
                action26();
                break;
            case 27:
                action27();
                break;
            case 28:
                action28();
                break;
            case 29:
                action29();
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
            case 35:
                action35();
                break;
            case 36:
                action36();
                break;
            case 37:
                action37();
                break;
            case 38:
                action38(token);
                break;
            case 39:
                action39();
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
            case 47:
                action47();
                break;
            case 48:
                action48();
                break;
            case 49:
                action49();
                break;
            case 50:
                action50();
                break;
            case 51:
                action51();
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

        if (__DUMP__ && flag) {
             System.out.println(line + " calling action : " + actionIndex + " with token " + token);
             if (__STACK__) dumpStack();
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

    private void printStack(Stack stack) {
        Object[] a = new Object[stack.size()];
        a = stack.toArray(a);
        Collections.reverse(Arrays.asList(a));
        String log = "Stack Top :==> " + Arrays.toString(a);
        System.out.println(log);
    }
    
    private void dumpStack() {
        if (__DEBUG__ || true) {
            printStack(stack);
        }
    }
    
    public static void main(String[] args) {
        String filename = "testfiles/ult-corrected.pas";
        boolean debug = false;
        if (args.length > 0) {
            filename = args[0];
            if (args.length > 1) {
                String flag = args[1].toLowerCase();
                if ("true".equals(flag)) {
                    debug = true;
                }
            }
        }
        try {
            Parser parser = new Parser(filename, debug);
            parser.parse();
            // System.out.println("\n*****************\nEnd state:\n");
            parser.dumpCode();
            // System.out.println("\nACCEPT! (Degbug mode is: " + (parser.isDebugging() ? "on" : "off") + ")\n");
        } catch (LexicalException | ParserException | IOException | SymbolTableException | SemanticActionsException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
