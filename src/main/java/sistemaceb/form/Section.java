/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public class Section extends JPanel{
    
            int currentElements = 0;
            int maxCols;

            public Section (int cols){
                maxCols = cols;
                setOpaque(false);
            }
            

            public void addElement (Component a){

                setLayout(new GridLayout(1,currentElements));

                add(a);
                currentElements++;
               
            }
            
            public boolean isntFull(){
                return maxCols > currentElements;
            }
            
    
}
