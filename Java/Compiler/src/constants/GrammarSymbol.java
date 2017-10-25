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
public interface GrammarSymbol {

    public int getIndex();

    public boolean isToken();

    public boolean isNonTerminal();

    public boolean isAction();
    
    public boolean eqauls(GrammarSymbol other);
}
