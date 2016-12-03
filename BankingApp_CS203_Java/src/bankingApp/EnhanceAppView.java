package bankingApp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Method for enhancing app view
 * @author Gavin
 */
public class EnhanceAppView {
    
    /**
     * Enhance GUI visual effects
     */
    public static void enhanceVision() {
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
    
}
