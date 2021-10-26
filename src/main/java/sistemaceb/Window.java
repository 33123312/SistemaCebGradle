/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import SpecificViews.LinearHorizontalLayout;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author escal
 */
public class Window extends JPanel{
    
    private JLabel titleLabel;
    protected LinearHorizontalLayout header;
    private JPanel buttonsPlace;
    
    public Window(){
        closePermisions = new ArrayList<>();
        setLayout(new BorderLayout());
        header = new LinearHorizontalLayout();
            header.setOpaque(false);
        add(deployHeader(),BorderLayout.NORTH);
        
    }

    public interface ClosePermision{
        boolean canClose(genericEvents e);
    }

    private ArrayList<ClosePermision> closePermisions;

    public void addClosePermision(ClosePermision closePermision){
        closePermisions.add(closePermision);
    }

    public void update(){

    };

    public void tryToClose(genericEvents e){
        if (closePermisions.isEmpty())
            e.genericEvent();
        else
            for (ClosePermision permision: closePermisions)
                if (!permision.canClose(e))
                    return;

    }
    
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
