/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 *
 * @author escal
 */
public class editedScrollPanel extends JScrollPane {

    public editedScrollPanel(){
        
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setOpaque(false);
        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200,200,200);
            }
        });
        getVerticalScrollBar().setUnitIncrement(16);
        removeArrows();
        changeScrollBarWidth();
        getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        

    }
    
    private void removeArrows(){
            getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {   
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override    
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

    }

    private void changeScrollBarWidth() {
        getVerticalScrollBar().setPreferredSize(new Dimension(10,20));
    }
}
