/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author Gavin
 */
public class AlarmClockApplication {
    
    /**
     * Enhance GUI visual effects
     */
    public static void enhanceVisualEffects() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // run the app
    public static void main(String[] args) {
        AlarmClockApplication.enhanceVisualEffects();
        AlarmClockGUI.main(null);
    }
}