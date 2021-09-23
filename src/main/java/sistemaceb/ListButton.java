/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import DInamicPanels.DinamicPanel;
import Generals.BtnFE;
import JDBCController.CrudCerator;
import sistemaceb.form.Global;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public  class ListButton extends JPanel {
    
    private final JPanel buttonsContainer;
    int buttonsCounter;
    

    public ListButton(String title, BufferedImage icon){
        buttonsCounter = 0;
        buttonsContainer = new JPanel(new GridBagLayout());
        buttonsContainer.setOpaque(false);
        setOpaque(false);
        setLayout(new GridLayout());
        add(deployGeneralContainer(title,icon));
        
    }

    
    
    public JPanel deployGeneralContainer(String title,BufferedImage icon){
        
        JPanel generalContainer = new JPanel(new BorderLayout());
            generalContainer.setOpaque(false);
            generalContainer.add(deployTitleSection(title,icon),BorderLayout.NORTH);
            generalContainer.add(buttonsContainer,BorderLayout.CENTER);
            
        return generalContainer;
    
    }
    
    private JPanel deployTitleSection(String title,BufferedImage icon){
        
        JLabel titleLabel = new JLabel(" "+title);
            titleLabel.setForeground(new Color(240,240,240));
            titleLabel.setFont(new Font("arial", Font.PLAIN,17));
            
        JPanel titleContainer = new JPanel(new BorderLayout());
            titleContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            titleContainer.setOpaque(false);            
            titleContainer.add(new JLabel(new ImageIcon(icon)),BorderLayout.WEST);   
            titleContainer.add(titleLabel,BorderLayout.CENTER);           
            
        return titleContainer;
        
    }
    
    public void addButton(String title, CrudCerator creator){
        
        MouseAdapter event = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent arg0){
                Global.view.setView(new ViewAdapter(creator.create()));

            }
        };

        addBtn(title,event);
            }

    public void addButton(String title, Window window){

        MouseAdapter event = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent arg0){
                Global.view.setView(new ViewAdapter(window));
            }
        };

        addBtn(title,event);
    }

    private void addBtn(String title,MouseAdapter event){
        BtnFE newButton = buildButton(title);
        newButton.addMouseListener(event);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = buttonsCounter;
        cons.weightx =  1;
        cons.weighty =  0.1;
        cons.anchor = GridBagConstraints.PAGE_START;
        cons.fill = GridBagConstraints.BOTH;

        buttonsContainer.add(newButton,cons);
        buttonsCounter++;

    }
    private BtnFE buildButton(String title){
        BtnFE newButton = new BtnFE(title);
        newButton.setPadding(10,40,10,10);
        newButton.setBackground(new Color(51, 67, 156));
        newButton.setAlignmentWest();
        return newButton;
    }   
 
        
    }
    
    
    

