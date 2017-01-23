package Lib;

import javax.swing.JOptionPane;

/**
 * Abstract class for all controller
 * @author Gavin
 */
public class GeneralController {
    
    public static void errorMessageBox(String errorMessage) {
        JOptionPane.showMessageDialog(null, "Error!\n" + errorMessage, "Error!", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
