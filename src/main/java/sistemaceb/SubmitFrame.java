/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import SpecificViews.LinearHorizontalLayout;
import sistemaceb.form.proccesStateStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author escal
 */
public class SubmitFrame extends JFrame {
    
    public JPanel body;
    ArrayList<genericEvents> acceptEvents;
    ArrayList<genericEvents> dimissEvents;
    ArrayList<genericEvents> closeEvents;
    ArrayList<SubmitFrame>  childForms;
    
    
    public SubmitFrame(String title){
        super();
        setUndecorated(true);
        childForms = new ArrayList();

        body = new JPanel(new GridLayout(1,1));
        setSize(500,700);
        setLocationRelativeTo(null);
        add(deployPanelPrincipal(title));
        
        initializeEventsLists();
        initializeEvents();
        setVisible(true);

    }

    public BtnFE addButton(String text){
        BtnFE btnAceptar = new BtnFE(text);
            btnAceptar.setBackground(new Color(107, 117, 255));
            btnAceptar.setTextColor(Color.white);
            btnAceptar.setPadding(10, 20, 10, 20);
            btnAceptar.setMargins(15, 15, 15, 15,Color.white);
        buttonsContainer.add(btnAceptar);

        return btnAceptar;
    }
    
    private void initializeEventsLists(){
        acceptEvents = new ArrayList();
        dimissEvents = new ArrayList();
        closeEvents = new ArrayList();
        
    }
    
    private void initializeEvents(){
        addOnDimissEvent(
            new genericEvents(){
                public void genericEvent(){
                    closeForm();
                }
            }
        );
    }
    
    private JPanel deployPanelPrincipal(String title){
        
        final JPanel panelPrincipal=new JPanel(new BorderLayout());
            panelPrincipal.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(210,210,210)));
            panelPrincipal.setBackground(Color.white);
            
            panelPrincipal.add(deployTitleContainer(title),BorderLayout.NORTH); 
            panelPrincipal.add(body,BorderLayout.CENTER); 
            panelPrincipal.add(deployButtonsContainer(),BorderLayout.SOUTH); 
            
        return panelPrincipal;
        
    }
    
    public void addBody(JComponent component){
            setVisible(false);
            body.add(component);
            setVisible(true);

    }
    
        private JPanel deployTitleContainer(String title){
            JPanel container = new JPanel(new BorderLayout());
            container.setBorder(BorderFactory.createEmptyBorder(5,20,20,0));
            
            container.setBackground(new Color(245,245,245));
        
        JLabel titleContainer = new JLabel(title);
            titleContainer.setFont(new Font("arial", Font.BOLD, 25));
            titleContainer.setForeground(new Color(87, 95, 207)); 

        BtnFE closeButton = new BtnFE("X");
            closeButton.setFuente(new Font("sans-serif", Font.PLAIN, 20));
            closeButton.setTextColor(new Color(190,190,190));
            closeButton.setOpaque(false);
            closeButton.setMargins(0, 5, 0, 5, container.getBackground());
            closeButton.addMouseListener(new MouseAdapter(){
                
                @Override
                public void mousePressed(MouseEvent arg0){
                    closeForm();

                }
    
            }
            );
            
        JPanel closeButtonContainer = new JPanel(new BorderLayout());
            closeButtonContainer.setOpaque(false);
            closeButtonContainer.add(closeButton,BorderLayout.EAST);
            
        container.add(closeButtonContainer,BorderLayout.NORTH);
        container.add(titleContainer,BorderLayout.SOUTH);
            
            return container;
  
    }
        
        public void addOnAcceptEvent(genericEvents event){
            addEvent(acceptEvents,event);
            
        }
        
        public void addOnDimissEvent(genericEvents event){
            addEvent(dimissEvents,event);
          
            
        }
        public void addOnCloseEvent(genericEvents event){
            addEvent(closeEvents,event);
            
        }
        
        private void addEvent(ArrayList<genericEvents> eventsList, genericEvents event){
            eventsList.add(0,event);
            
        }
        
        public void triggerEvent(ArrayList<genericEvents> events){
            
            for(genericEvents event:events)
                event.genericEvent();
        }
        
        public void triggerAcceptEvents(){
            triggerEvent(acceptEvents);
        }
        
        public void triggerDimissEvents(){
            triggerEvent(dimissEvents);
        }
        
        public void triggerCloseEvents(){
            triggerEvent(closeEvents);
        }
        
        public void addChildForm(SubmitFrame childFrame){
            childForms.add(childFrame);
                      
        }
        
        public void addRuningStatusDownShoter(proccesStateStorage runState){
            runState.runing = true;
            addOnCloseEvent(
                new genericEvents(){
                    public void genericEvent(){
                        runState.runing=false;

                    }
                }
            );
        }

        private BtnFE getAcceptBtn(){
            BtnFE btnAceptar = new BtnFE("Aceptar");
            btnAceptar.setBackground(new Color(107, 117, 255));
            btnAceptar.setTextColor(Color.white);
            btnAceptar.setPadding(10, 20, 10, 20);
            btnAceptar.setMargins(15, 15, 15, 15,Color.white);
            btnAceptar.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent arg0){
                    triggerAcceptEvents();
                }

            });

            return btnAceptar;
        }

        private BtnFE getCancelBtn(){
            BtnFE btnCancelar = new BtnFE("Cancelar");
            btnCancelar.setBackground(new Color(220, 220, 220));
            btnCancelar.setTextColor(new Color(100, 100, 100));
            btnCancelar.setPadding(10, 20, 10, 20);
            btnCancelar.setMargins(15, 15, 15, 15,Color.white);
            btnCancelar.addMouseListener(
                new MouseAdapter(){
                     @Override
                     public void mousePressed(MouseEvent arg0){
                         triggerDimissEvents();
                         closeForm();

                     }
                 }
            );

            return btnCancelar;
        }

    public void addAcceptButton(){
        buttonsContainer.addElement(getAcceptBtn());
    }

    public void addCloseButton(){
        buttonsContainer.addElement(getCancelBtn());
    }

    private LinearHorizontalLayout buttonsContainer;

    private JPanel deployButtonsContainer(){
        buttonsContainer = new LinearHorizontalLayout();
            buttonsContainer.setOpaque(false);
        
        JPanel generalContainer = new JPanel(new BorderLayout());
           generalContainer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220,220,220)));
           generalContainer.setOpaque(false);
           generalContainer.add(buttonsContainer,BorderLayout.EAST);
           
        return generalContainer;
          
    }    
   
    public void closeForm(){
        closeChilds();
        triggerCloseEvents();
        setVisible(false);
        dispose();
    }
    
    protected void closeChilds(){
        for(SubmitFrame child:childForms)
            child.closeForm();
        
        childForms = new ArrayList();
    }
    
}
