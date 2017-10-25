/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

import java.util.*;

/**
 *
 * @author Gavin
 */
public final class ReservedWords {
    public static final Map<String, TokenType> RESERVED_WORDS_TOKEM_MAP = ReservedWords.createReservedWordMap();
    public static final Map<String, TokenType> RESERVED_OP_TOKEN_MAP = ReservedWords.createReservedOpMap();
    public static final Map<String, TokenType> RESERVED_PUNC_TOKEN_MAP = ReservedWords.createReservedPuncMap();
    
    private static Map<String, TokenType> createReservedOpMap() {
        Map<String, TokenType> map = new HashMap<>();
        map.put("or", TokenType.ADDOP);
        map.put("+", TokenType.ADDOP);
        map.put("-", TokenType.ADDOP);
        map.put("and", TokenType.MULOP);
        map.put("*", TokenType.MULOP);
        map.put("/", TokenType.MULOP);
        map.put("div", TokenType.MULOP);
        map.put("mod", TokenType.MULOP);
        return map;
    }
    
    private static Map<String, TokenType> createReservedPuncMap() {
        Map<String, TokenType> map = new HashMap<>();
        map.put(",", TokenType.COMMA);
        map.put(";", TokenType.SEMICOLON);
        map.put(":", TokenType.COLON);
        map.put(")", TokenType.RIGHTPAREN);
        map.put("(", TokenType.LEFTPAREN);
        map.put("]", TokenType.RIGHTBRACKET);
        map.put("[", TokenType.LEFTBRACKET);
        map.put("..", TokenType.DOUBLEDOT);
        map.put(".", TokenType.ENDMARKER);
        return map;
    }
    
    private static Map<String, TokenType> createReservedWordMap() {
        Map<String, TokenType> map = new HashMap<>();
        map.put("program", TokenType.PROGRAM);
        map.put("begin", TokenType.BEGIN);
        map.put("end", TokenType.END);
        map.put("var", TokenType.VAR);
        map.put("function", TokenType.FUNCTION);
        map.put("procedure", TokenType.PROCEDURE);
        map.put("result", TokenType.RESULT);
        map.put("integer", TokenType.INTEGER);
        map.put("real", TokenType.REAL);
        map.put("array", TokenType.ARRAY);
        map.put("of", TokenType.OF);
        map.put("if", TokenType.IF);
        map.put("then", TokenType.THEN);
        map.put("else", TokenType.ELSE);
        map.put("while", TokenType.WHILE);
        map.put("do", TokenType.DO);
        map.put("not", TokenType.NOT);
        map.put("identifier", TokenType.IDENTIFIER);
        return map;
    }
    
    public static boolean isReservedWord(String word) {
        return ReservedWords.RESERVED_WORDS_TOKEM_MAP.containsKey(word.toLowerCase());
    }
    
    public static boolean isReservedOp(String op) {
        return ReservedWords.RESERVED_OP_TOKEN_MAP.containsKey(op.toLowerCase());
    }
    
    public static TokenType mapReservedWordType(String word) {
        return ReservedWords.RESERVED_WORDS_TOKEM_MAP.get(word.toLowerCase());
    }
    
    public static TokenType mapReservedOpType(String op) {
        return ReservedWords.RESERVED_OP_TOKEN_MAP.get(op.toLowerCase());
    }
}
