/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.util.*;

/**
 *
 * @author Gavin
 */
public final class TokenTypes {

    public static final String PROGRAM = "PROGRAM";
    public static final String BEGIN = "BEGIN";
    public static final String END = "END";
    public static final String VAR = "VAR";
    public static final String FUNCTION = "FUNCTION";
    public static final String PROCEDURE = "PROCEDURE";
    public static final String RESULT = "RESULT";
    public static final String INTEGER = "INTEGER";
    public static final String REAL = "REAL";
    public static final String ARRAY = "ARRAY";
    public static final String OF = "OF";
    public static final String IF = "IF";
    public static final String THEN = "THEN";
    public static final String ELSE = "ELSE";
    public static final String WHILE = "WHILE";
    public static final String DO = "DO";
    public static final String NOT = "NOT";
    public static final String IDENTIFIER = "IDENTIFIER";
    public static final String INTCONSTANT = "INTCONSTANT";
    public static final String REALCONSTANT = "REALCONSTANT";
    public static final String RELOP = "RELOP";
    public static final String MULOP = "MULOP";
    public static final String ADDOP = "ADDOP";
    public static final String ASSIGNOP = "ASSIGNOP";
    public static final String COMMA = "COMMA";
    public static final String SEMICOLON = "SEMICOLON";
    public static final String COLON = "COLON";
    public static final String RIGHTPAREN = "RIGHTPAREN";
    public static final String LEFTPAREN = "LEFTPAREN";
    public static final String RIGHTBRACKET = "RIGHTBRACKET";
    public static final String LEFTBRACKET = "LEFTBRACKET";
    public static final String UNARYMINUS = "UNARYMINUS";
    public static final String UNARYPLUS = "UNARYPLUS";
    public static final String DOUBLEDOT = "DOUBLEDOT";
    public static final String ENDMARKER = "ENDMARKER";
    public static final String ENDOFFILE = "ENDOFFILE";

    public static final Map<String, String> RESERVED_WORDS_TOKEM_MAP = TokenTypes.createReservedWordMap();
    public static final Map<String, String> RESERVED_OP_TOKEN_MAP = TokenTypes.createReservedOpMap();
    public static final Map<String, String> RESERVED_PUNC_TOKEN_MAP = TokenTypes.createReservedPuncMap();
    
    private static Map<String, String> createReservedOpMap() {
        Map<String, String> map = new HashMap<>();
        map.put("or", TokenTypes.ADDOP);
        map.put("+", TokenTypes.ADDOP);
        map.put("-", TokenTypes.ADDOP);
        map.put("and", TokenTypes.MULOP);
        map.put("*", TokenTypes.MULOP);
        map.put("/", TokenTypes.MULOP);
        map.put("div", TokenTypes.MULOP);
        map.put("mod", TokenTypes.MULOP);
        return map;
    }
    
    private static Map<String, String> createReservedPuncMap() {
        Map<String, String> map = new HashMap<>();
        map.put(",", TokenTypes.COMMA);
        map.put(";", TokenTypes.SEMICOLON);
        map.put(":", TokenTypes.COLON);
        map.put(")", TokenTypes.RIGHTPAREN);
        map.put("(", TokenTypes.LEFTPAREN);
        map.put("]", TokenTypes.RIGHTBRACKET);
        map.put("[", TokenTypes.LEFTBRACKET);
        map.put("..", TokenTypes.DOUBLEDOT);
        map.put(".", TokenTypes.ENDMARKER);
        return map;
    }
    
    private static Map<String, String> createReservedWordMap() {
        Map<String, String> map = new HashMap<>();
        map.put("program", TokenTypes.PROGRAM);
        map.put("begin", TokenTypes.BEGIN);
        map.put("end", TokenTypes.END);
        map.put("var", TokenTypes.VAR);
        map.put("function", TokenTypes.FUNCTION);
        map.put("procedure", TokenTypes.PROCEDURE);
        map.put("result", TokenTypes.RESULT);
        map.put("integer", TokenTypes.INTEGER);
        map.put("real", TokenTypes.REAL);
        map.put("array", TokenTypes.ARRAY);
        map.put("of", TokenTypes.OF);
        map.put("if", TokenTypes.IF);
        map.put("then", TokenTypes.THEN);
        map.put("else", TokenTypes.ELSE);
        map.put("while", TokenTypes.WHILE);
        map.put("do", TokenTypes.DO);
        map.put("not", TokenTypes.NOT);
        map.put("identifier", TokenTypes.IDENTIFIER);
        return map;
    }
    
    public static boolean isReservedWord(String word) {
        return TokenTypes.RESERVED_WORDS_TOKEM_MAP.containsKey(word.toLowerCase());
    }
    
    public static boolean isReservedOp(String op) {
        return TokenTypes.RESERVED_OP_TOKEN_MAP.containsKey(op.toLowerCase());
    }
    
    public static String mapReservedWordType(String word) {
        return TokenTypes.RESERVED_WORDS_TOKEM_MAP.get(word.toLowerCase());
    }
    
    public static String mapReservedOpType(String op) {
        return TokenTypes.RESERVED_OP_TOKEN_MAP.get(op.toLowerCase());
    }
}
