/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public class SwitcheableWindowPills extends JPanel{
    
    JPanel pillsArea;
    ArrayList<BtnFE> pills;
    ArrayList<MouseListener> pillsEvent;
    int selectedPillKey;
    BtnFE selectedPill;
    
    Color pillsBackGround;
    private JPanel currentView;
    
    public SwitcheableWindowPills(){
        setLayout(new BorderLayout());
        add(deploypillsArea(),BorderLayout.NORTH);
        pills = new ArrayList();
        pillsEvent = new ArrayList();
        
        selectedPillKey=-1;
        selectedPill = null;
        
        pillsBackGround = Color.white;
        currentView= new JPanel();

    }
    
    private JPanel deploypillsArea(){
    JPanel parentContainer = new JPanel(new BorderLayout());
        parentContainer.setBackground(Color.white);
        
        pillsArea = new JPanel(new GridBagLayout());
            pillsArea.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            pillsArea.setBackground(Color.WHITE);
            
        parentContainer.add(pillsArea,BorderLayout.EAST);
    
        return parentContainer;

    }

    public void selectPill(int key){
        
        if(key != selectedPillKey){
            BtnFE pill = pills.get(key);
            giveSelectedDesign(pill);

            if(pillsEvent.get(key) != null)
                pillsEvent.get(key).mousePressed(null);

            if(selectedPill != null)
                giveDeselectedDesign(selectedPill);

            selectedPillKey = key;
            selectedPill = pill;
        }
    }

    private void giveSelectedDesign(BtnFE pill){
        pill.setBackground(new Color(245, 249, 255));
        pill.setTextColor(new Color(46, 134, 222));
        pill.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(46, 134, 222)));
    }
    
    private void giveDeselectedDesign(BtnFE pill){
        pill.setBackground(pillsBackGround);
        pill.setTextColor(new Color(100,100,100));
        pill.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,1,0));
    }

    
    private void setInitialStyle(BtnFE pill){
        
        pill.setPadding(10, 50, 10, 50);
        pill.setFuente(new Font("Arial", Font.PLAIN , 15));
        giveDeselectedDesign(pill);
        
    }
    
    public void addPill(String text,MouseListener event){
        
        BtnFE pill = new BtnFE(text);
        
            pill.addMouseListener(new MouseAdapter(){
                final int key = pills.size();
                @Override
                public void mousePressed (MouseEvent e){
                    selectPill(key);
                }
        });
            
        setInitialStyle(pill);

        GridBagConstraints position = new GridBagConstraints();
            position.gridy = 0;
            position.weightx =  1;
            position.fill = GridBagConstraints.HORIZONTAL;
            position.gridx = pills.size();

        pillsEvent.add(event);
        pills.add(pill);
        pillsArea.add(pill);

    }
    
    public void setView(JPanel view){
        setVisible(false);
        remove(currentView);
        setVisible(true);
        add(view,BorderLayout.CENTER);
        currentView = view;

    }
            
    
    
    
}
