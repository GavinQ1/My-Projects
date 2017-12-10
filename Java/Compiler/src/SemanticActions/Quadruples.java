/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticActions;

import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author Gavin
 */
public class Quadruples {

    private Vector<String[]> Quadruple;
    private int nextQuad;

    public Quadruples() {
        Quadruple = new Vector<>();
        nextQuad = 0;
        
        String[] dummy_quadruple = new String[4];
        dummy_quadruple[0] = dummy_quadruple[1] = dummy_quadruple[2] = dummy_quadruple[3] = null;
        Quadruple.add(nextQuad, dummy_quadruple);
        nextQuad++;
        
    }

    public String getField(int quadIndex, int field) {
        return Quadruple.elementAt(quadIndex)[field];
    }

    public void setField(int quadIndex, int index, String field) {
        Quadruple.elementAt(quadIndex)[index] = field;
    }

    public int getNextQuad() {
        return nextQuad;
    }

    public void incrementNextQuad() {
        nextQuad++;
    }

    public String[] getQuad(int index) {
        return (String[]) Quadruple.elementAt(index);
    }

    public void addQuad(String[] quad) {
        Quadruple.add(nextQuad, quad);
        nextQuad++;
    }

    public void print() {
        //int quadLabel = 0;
        int quadLabel = 1;
        String separator;

        System.out.println("CODE");
        Enumeration<String[]> e = this.Quadruple.elements();
        e.nextElement();
        
        while (e.hasMoreElements()) {
            String[] quad = e.nextElement();
            separator = " ";
            System.out.print(quadLabel + ":  " + quad[0]);
            if (quad[1] != null) {
                System.out.print(separator + quad[1]);
            }
            if (quad[2] != null) {
                separator = ", ";
                System.out.print(separator + quad[2]);
            }
            if (quad[3] != null) {
                System.out.print(separator + quad[3]);
            }

            System.out.println();
            quadLabel++;
        }
    }
    
    public void print(PrintWriter out) {
        //int quadLabel = 0;
        int quadLabel = 1;
        String separator;

        out.println("CODE");
        Enumeration<String[]> e = this.Quadruple.elements();
        e.nextElement();
        
        while (e.hasMoreElements()) {
            String[] quad = e.nextElement();
            separator = " ";
            out.print(quadLabel + ":  " + quad[0]);
            if (quad[1] != null) {
                out.print(separator + quad[1]);
            }
            if (quad[2] != null) {
                separator = ", ";
                out.print(separator + quad[2]);
            }
            if (quad[3] != null) {
                out.print(separator + quad[3]);
            }

            out.println();
            quadLabel++;
        }
    }
}
