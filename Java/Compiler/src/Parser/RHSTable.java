/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import constants.NonTerminal;
import constants.TokenType;
import constants.SemanticAction;
import constants.GrammarSymbol;

/**
 *
 * @author Gavin
 */
public class RHSTable {

    final GrammarSymbol[][] rules;

    public RHSTable() {
        rules = new GrammarSymbol[][]{
            //dummy element 0
            {},
            //production 1         
            {TokenType.PROGRAM, TokenType.IDENTIFIER, SemanticAction.action13, TokenType.LEFTPAREN, NonTerminal.identifier_list,
                TokenType.RIGHTPAREN, SemanticAction.action9, TokenType.SEMICOLON, NonTerminal.declarations,
                NonTerminal.sub_declarations, SemanticAction.action56, NonTerminal.compound_statement,
                SemanticAction.action55},
            //production 2		         
            {TokenType.IDENTIFIER, SemanticAction.action13, NonTerminal.identifier_list_tail},
            //production 3		         
            {TokenType.COMMA, TokenType.IDENTIFIER, SemanticAction.action13, NonTerminal.identifier_list_tail},
            //production 4		         
            {},
            //production 5		         
            {TokenType.VAR, SemanticAction.action1, NonTerminal.declaration_list, SemanticAction.action2},
            //production 6		         
            {},
            //production 7		         
            {NonTerminal.identifier_list, TokenType.COLON, NonTerminal.type, SemanticAction.action3,
                TokenType.SEMICOLON, NonTerminal.declaration_list_tail},
            //production 8		         
            {NonTerminal.identifier_list, TokenType.COLON, NonTerminal.type, SemanticAction.action3,
                TokenType.SEMICOLON, NonTerminal.declaration_list_tail},
            //production 9		         
            {},
            //production 10		         
            {NonTerminal.standard_type},
            //production 11		         
            {NonTerminal.array_type},
            //production 12		         
            {TokenType.INTEGER, SemanticAction.action4},
            //production 13		         
            {TokenType.REAL, SemanticAction.action4},
            //production 14		         
            {SemanticAction.action6, TokenType.ARRAY, TokenType.LEFTBRACKET, TokenType.INTCONSTANT,
                SemanticAction.action7, TokenType.DOUBLEDOT, TokenType.INTCONSTANT, SemanticAction.action7,
                TokenType.RIGHTBRACKET, TokenType.OF, NonTerminal.standard_type},
            //production 15		         
            {NonTerminal.subprogram_declaration, NonTerminal.sub_declarations},
            //production 16		         
            {},
            //production 17		         
            {SemanticAction.action1, NonTerminal.subprogram_head, NonTerminal.declarations,
                SemanticAction.action5, NonTerminal.compound_statement, SemanticAction.action11},
            //production 18		         
            {TokenType.FUNCTION, TokenType.IDENTIFIER, SemanticAction.action15, NonTerminal.arguments,
                TokenType.COLON, TokenType.RESULT, NonTerminal.standard_type, TokenType.SEMICOLON,
                SemanticAction.action16},
            //production 19		         
            {TokenType.PROCEDURE, TokenType.IDENTIFIER, SemanticAction.action17, NonTerminal.arguments,
                TokenType.SEMICOLON},
            //production 20		         
            {TokenType.LEFTPAREN, SemanticAction.action19, NonTerminal.parameter_list, TokenType.RIGHTPAREN,
                SemanticAction.action20},
            //production 21		         
            {},
            //production 22		         
            {NonTerminal.identifier_list, TokenType.COLON, NonTerminal.type, SemanticAction.action21,
                NonTerminal.parameter_list_tail},
            //production 23		         
            {TokenType.SEMICOLON, NonTerminal.identifier_list, TokenType.COLON, NonTerminal.type, SemanticAction.action21,
                NonTerminal.parameter_list_tail},
            //production 24		         
            {},
            //production 25		         
            {TokenType.BEGIN, NonTerminal.statement_list, TokenType.END},
            //production 26		         
            {NonTerminal.statement, NonTerminal.statement_list_tail},
            //production 27		         
            {TokenType.SEMICOLON, NonTerminal.statement, NonTerminal.statement_list_tail},
            //production 28		         
            {},
            //production 29		         
            {NonTerminal.elementary_statement},
            //production 30		         
            {TokenType.IF, NonTerminal.expression, SemanticAction.action22, TokenType.THEN,
                NonTerminal.statement, NonTerminal.else_clause},
            //production 31		         
            {TokenType.WHILE, SemanticAction.action24, NonTerminal.expression, SemanticAction.action25,
                TokenType.DO, NonTerminal.statement, SemanticAction.action26},
            //production 32		         
            {TokenType.ELSE, SemanticAction.action27, NonTerminal.statement, SemanticAction.action28},
            //production 33		         
            {SemanticAction.action29},
            //production 34		         
            {TokenType.IDENTIFIER, SemanticAction.action30, NonTerminal.es_tail},
            //production 35		         
            {NonTerminal.compound_statement},
            //production 36		         
            {SemanticAction.action53, NonTerminal.subscript, TokenType.ASSIGNOP, NonTerminal.expression,
                SemanticAction.action31},
            //production 37		         
            {SemanticAction.action54, NonTerminal.parameters},
            //production 38		         
            {SemanticAction.action32, TokenType.LEFTBRACKET, NonTerminal.expression, TokenType.RIGHTBRACKET,
                SemanticAction.action33},
            //production 39		         
            {SemanticAction.action34},
            //production 40		         
            {SemanticAction.action35, TokenType.LEFTPAREN, NonTerminal.expression_list, TokenType.RIGHTPAREN,
                SemanticAction.action51},
            //production 41		         
            {SemanticAction.action36},
            //production 42		         
            {NonTerminal.expression, SemanticAction.action37, NonTerminal.expression_list_tail},
            //production 43		         
            {TokenType.COMMA, NonTerminal.expression, SemanticAction.action37, NonTerminal.expression_list_tail},
            //production 44		         
            {},
            //production 45		         
            {NonTerminal.simple_expression, NonTerminal.expression_tail},
            //production 46		         
            {TokenType.RELOP, SemanticAction.action38, NonTerminal.simple_expression, SemanticAction.action39},
            //production 47		         
            {},
            //production 48		         
            {NonTerminal.term, NonTerminal.simple_expression_tail},
            //production 49		         
            {NonTerminal.sign, SemanticAction.action40, NonTerminal.term, SemanticAction.action41,
                NonTerminal.simple_expression_tail},
            //production 50		         
            {TokenType.ADDOP, SemanticAction.action42, NonTerminal.term, SemanticAction.action43,
                NonTerminal.simple_expression_tail},
            //production 51		         
            {},
            //production 52		         
            {NonTerminal.factor, NonTerminal.term_tail},
            //production 53		         
            {TokenType.MULOP, SemanticAction.action44, NonTerminal.factor, SemanticAction.action45,
                NonTerminal.term_tail},
            //production 54		  
            {},
            //production 55					   
            {TokenType.IDENTIFIER, SemanticAction.action46, NonTerminal.factor_tail},
            //production 56		         
            {NonTerminal.constant, SemanticAction.action46},
            //production 57		         
            {TokenType.LEFTPAREN, NonTerminal.expression, TokenType.RIGHTPAREN},
            //production 58		         
            {TokenType.NOT, NonTerminal.factor, SemanticAction.action47},
            //production 59		         
            {NonTerminal.actual_parameters},
            //production 60		         
            {NonTerminal.subscript, SemanticAction.action48},
            //production 61		         
            {SemanticAction.action49, TokenType.LEFTPAREN, NonTerminal.expression_list, TokenType.RIGHTPAREN, SemanticAction.action50},
            //production 62		         
            {SemanticAction.action52},
            //production 63		         
            {TokenType.UNARYPLUS},
            //production 64		         
            {TokenType.UNARYMINUS},
            //production 65		         
            {NonTerminal.program, TokenType.ENDMARKER},
            //production 66		         
            {TokenType.INTCONSTANT},
            //production 67		         
            {TokenType.REALCONSTANT}
        };
    }

    public GrammarSymbol[] getRule(int n) {
        return rules[n];
    }

    public void dumpTable() {
        for (int i = 1; i < rules.length; ++i) {
            System.out.print("RULE : ");
            GrammarSymbol[] rule = getRule(i);
            for (GrammarSymbol j : rule) {
                System.out.print(" " + j);
            }
            System.out.println();
        }
    }

    public void printrule(int n) {
        GrammarSymbol[] rule = getRule(n);

        System.out.print("Rule " + n);
        for (GrammarSymbol j : rule) {
            System.out.print(" " + j);
        }
        System.out.println();
    }
}
