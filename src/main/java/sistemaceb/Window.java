/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import DInamicPanels.View;
import SpecificViews.LinearHorizontalLayout;
import sistemaceb.form.Global;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public  class Window extends JPanel{
    
    private JLabel titleLabel;
    protected LinearHorizontalLayout header;
    private JPanel buttonsPlace;
    
    public Window(){
        setLayout(new BorderLayout());
        header = new LinearHorizontalLayout();
            header.setOpaque(false);
        add(deployHeader(),BorderLayout.NORTH);
        
    }

    public void update(){

    };
    
    public void addToHeader(JComponent componet){
        
        header.add(componet);
    }

    JComponent body;
    public void addBody(JComponent componet){
        setVisible(false);
        if(body != null)
        remove(body);
        body = componet;
        add(componet,BorderLayout.CENTER);
        setVisible(true);
    }
    
    public void setTitle(String title){
        titleLabel.setText(title);
        
    }

    public void addButtons(JPanel buttons){
        buttonsPlace.removeAll();
        buttonsPlace.add(buttons);

    }
            
    private JPanel deployHeader(){
        
    GridBagConstraints backFurBtnConstraint = new GridBagConstraints();
        backFurBtnConstraint.gridx = 0;
        backFurBtnConstraint.gridwidth = 1;
        backFurBtnConstraint.weightx =  0;
        backFurBtnConstraint.anchor = GridBagConstraints.WEST;
        

    GridBagConstraints titleConstraint = new GridBagConstraints();
        titleConstraint.gridx = 1;
        titleConstraint.gridwidth = 1;
        titleConstraint.weightx =  1;
        titleConstraint.anchor = GridBagConstraints.WEST;

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD,20));
        titleLabel.setForeground(new Color(100,100,100));

    GridBagConstraints navContainerConstrains = new GridBagConstraints();
        titleConstraint.gridx = 3;
        titleConstraint.gridwidth = 1;
        titleConstraint.weightx =  1;
        navContainerConstrains.anchor = GridBagConstraints.WEST;


        buttonsPlace = new JPanel();
            buttonsPlace.setOpaque(false);

        JPanel generalContainer = new JPanel(new GridBagLayout());
            generalContainer.setBackground(Color.white);
            generalContainer.setBorder(BorderFactory.createEmptyBorder(13,10,13,10));
            generalContainer.add(buttonsPlace,backFurBtnConstraint);
            generalContainer.add(titleLabel,titleConstraint);
            generalContainer.add(header,navContainerConstrains);
        
        
    return generalContainer;
    }
    
}
