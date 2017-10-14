/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;
import Lexer.*;
import java.util.*;
import java.io.IOException;

/**
 *
 * @author Gavin
 */
public class Parser {
    private final Lexer lexer;
    
    public Parser(String filename) throws IOException {
        lexer = new Lexer(filename);
    }
    
    
}
