/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapViewer;

import javafx.scene.control.Label;

/**
 *
 * @author Gavin
 */
public class LabelCell extends Cell {

    public LabelCell(String id, String txt) {
        super(id);

        Label view = new Label(txt);

        view.setStyle("-fx-background-color: gold;"
                + "-fx-padding: 50px;"
                + "-fx-background-radius: 10px;");

        setView(view);
    }

}
