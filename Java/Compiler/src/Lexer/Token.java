/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author Gavin
 * @param <E>
 */
public class Token<E> {
    private String tag;
    private E value;
    
    public Token(String tag, E value) {
        this.tag = tag;
        this.value = value;
    }
    
    public void setValue(E value) { this.value = value; }
    
    public E getValue() { return this.value; }
    
    public void setTag(String tag) { this.tag = tag; }
    
    public String getTag() { return this.tag; }
    
    public String toString() {
        return "[" + tag + ", " + value + "]";
    }
}
