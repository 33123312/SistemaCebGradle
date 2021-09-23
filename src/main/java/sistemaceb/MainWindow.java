/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import JDBCController.DBSTate;
import sistemaceb.form.Global;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author escal
 */
public class MainWindow extends JFrame {
    

    public MainWindow() throws IOException{
        add(deployGeneralContainer());
         buildWindow();

    }
    
    private void buildWindow(){
        Rectangle size = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setBounds(size);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(200,200,200)));
        setVisible(true);
    }
    
    private JPanel deployGeneralContainer() throws IOException{
        JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.add(deployMenu(),BorderLayout.WEST);
            newPanel.add(deployRight(),BorderLayout.CENTER);
            
        return newPanel;
    }

    private JPanel deployRight(){
        JPanel generalContainer = new JPanel(new BorderLayout());
            generalContainer.add(deployCloseBar(),BorderLayout.NORTH);
            generalContainer.add(Global.view,BorderLayout.CENTER);

        return generalContainer;
    }
    
    private JPanel deployCloseBar(){
        BtnFE closeButton = new BtnFE("X");
            closeButton.setFuente(new Font("sans-serif", Font.PLAIN, 16));
            closeButton.setForeground(Color.white);
            closeButton.setOpaque(false);
            closeButton.addMouseListener(new MouseAdapter(){

                @Override
                public void mousePressed(MouseEvent arg0){
                    Global.closeSystem();

                }});

        JPanel closeBar = new JPanel(new BorderLayout());
          closeBar.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));
          closeBar.add(closeButton,BorderLayout.EAST);
          closeBar.setBackground(new Color(57, 43, 120));

          return closeBar;
        
    }

    private JPanel deployMenu(){
        JPanel menu = new JPanel(new BorderLayout());
            menu.setBackground(new Color(63, 81, 181));
            menu.add(deployHeader(),BorderLayout.NORTH);

            MainWindowMenu sectionsMenu = new MainWindowMenu();
            menu.add(sectionsMenu,BorderLayout.CENTER);

         return menu;

    }

    private JPanel deployHeader(){

        JLabel logoText =new JLabel("Ceb 6/4");
            logoText.setForeground(Color.white);
            logoText.setFont(new Font("arial", Font.PLAIN, 30));
            logoText.setBorder(BorderFactory.createEmptyBorder(20,55,6,55));

        JLabel periodoTxt = new JLabel(Global.conectionData.loadedPeriodo);
            periodoTxt.setForeground(Color.white);
            periodoTxt.setAlignmentX(SwingConstants.CENTER);
            periodoTxt.setFont(new Font("arial", Font.PLAIN, 17));
            periodoTxt.setBorder(BorderFactory.createEmptyBorder(0,55,20,0));


        JPanel header = new JPanel(new BorderLayout());
            header.add(logoText,BorderLayout.NORTH);
            header.add(periodoTxt,BorderLayout.CENTER);
            header.setOpaque(false); 

        return header;
    }

    public void closeForm(){
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(false);
        dispose();

    }
    
}
