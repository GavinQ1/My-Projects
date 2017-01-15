/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gavin
 */
public interface KnowledgeMap extends Cloneable, Serializable {

    void addCatagory(String catagoryName);

    void addKnowledgeNodeTo(String catagoryName, KnowledgeNode k) throws InvalidInputException;

    Object clone() throws CloneNotSupportedException;

    boolean containsCatagory(String catagoryName);

    void deleteCatagory(String catagoryName);

    void deleteKnowledgeNodeFrom(String catagoryName, KnowledgeNode k);

    void deleteKnowledgeNodeFrom(String catagoryName, String knowledgeNodeName);

    ArrayList<KnowledgeNode> getAllKnowledgeNodes();

    ArrayList<KnowledgeNode> getCatagory(String catagoryName);

    KnowledgeNode getKnowldegeNodeFrom(String catagoryName, String knowledgeNodeName) throws InvalidInputException;

    HashMap<String, ArrayList<KnowledgeNode>> getMap();

    String getName();

    void setName(String newName);
    
    void setMap(Map<String, ArrayList<KnowledgeNode>> map);
    
    void setCatagoryName(String oldName, String newName);

    Integer size();
    
}
