/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapViewer;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RectangleCell extends Cell {

    public RectangleCell(String id) {
        super(id);

        Rectangle view = new Rectangle( 50,50);

        view.setStroke(Color.DODGERBLUE);
//        view.setFill(Color.DODGERBLUE);
        
        

        Text text = new Text("Test");

        StackPane stack = new StackPane();
        stack.getChildren().addAll(view, text);

        setView(view);

    }

}