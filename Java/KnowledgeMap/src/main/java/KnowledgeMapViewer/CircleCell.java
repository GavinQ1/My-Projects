/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapViewer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Gavin
 */
public class CircleCell extends Cell {
    
    public CircleCell(String id) {
        super(id);

        Circle cellView = new Circle(50);

        cellView.setStroke(Color.DODGERBLUE);
        cellView.setFill(Color.DODGERBLUE);

        setView( cellView);
    }

}
