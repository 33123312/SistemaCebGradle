/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    

    public void addButton(BtnFE newButton){
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = buttonsCounter;
        cons.weightx =  1;
        cons.weighty =  0.1;
        cons.anchor = GridBagConstraints.PAGE_START;
        cons.fill = GridBagConstraints.BOTH;

        buttonsContainer.add(newButton,cons);
        buttonsCounter++;

    }


    public interface WindowBuiler{

        public Window buildWindow();
    }

    public static class Button extends BtnFE{
        Window window;
        WindowBuiler builder;

        public Button(String title){
            super(title);
            setPadding(10,40,10,10);
            setBackground(new Color(51, 67, 156));
            setAlignmentWest();

        }

        public Button(String title,MouseAdapter e){
            this(title);
            addMouseListener(e);
        }

        public Button(String title,WindowBuiler builder){
            this(title);
            this.builder = builder;

        }

        public Button reBuildWindow(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Global.view.setNewView(new ViewAdapter(builder.buildWindow()));
                }
            });
            return this;
        }

        public Button storeWindow(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(window == null)
                        window = builder.buildWindow();

                    Global.view.setNewView(new ViewAdapter(window));
                }
            });
            return this;
        }


    }
 
        
    }
    
    
    

