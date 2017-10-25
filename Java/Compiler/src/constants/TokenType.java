/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author Gavin
 */
public enum TokenType implements GrammarSymbol {
    PROGRAM(0),
    BEGIN(1),
    END(2),
    VAR(3),
    FUNCTION(4),
    PROCEDURE(5),
    RESULT(6),
    INTEGER(7),
    REAL(8),
    ARRAY(9),
    OF(10),
    IF(11),
    THEN(12),
    ELSE(13),
    WHILE(14),
    DO(15),
    NOT(16),
    IDENTIFIER(17),
    INTCONSTANT(18),
    REALCONSTANT(19),
    RELOP(20),
    MULOP(21),
    ADDOP(22),
    ASSIGNOP(23),
    COMMA(24),
    SEMICOLON(25),
    COLON(26),
    RIGHTPAREN(27),
    LEFTPAREN(28),
    RIGHTBRACKET(29),
    LEFTBRACKET(30),
    UNARYMINUS(31),
    UNARYPLUS(32),
    DOUBLEDOT(33),
    ENDMARKER(34),
    ENDOFFILE(35);

    private int n;

    private TokenType(int n) {
        this.n = n;
    }

    public int getIndex() {
        return n;
    }

    public boolean isToken() {
        return true;
    }

    public boolean isNonTerminal() {
        return false;
    }

    public boolean isAction() {
        return false;
    }
    
    public boolean eqauls(GrammarSymbol other) {
        return other.isToken() && this.n == other.getIndex();
    }
    
    public String toInputString() {
        switch(n) {
            case 23: return ":=";
            case 24: return ",";
            case 25: return ";";
            case 26: return ":";
            case 27: return "(";
            case 28: return ")";
            case 29: return "[";
            case 30: return "]";
            case 31: return "--";
            case 32: return "++";
            case 33: return "..";
            case 34: return ".";
            default: return name().toLowerCase();
        }
    }
}
