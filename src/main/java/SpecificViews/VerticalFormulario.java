package SpecificViews;

import sistemaceb.form.FormElement;
import sistemaceb.form.Formulario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VerticalFormulario extends Formulario {
    private LinearVerticalLayout elementsArea;
    private JPanel generalArea;

    public VerticalFormulario(){
        super();
        setLayout(new GridLayout(1,1));
        setBorder(new EmptyBorder(10,0,0,0));
        generalArea =  new JPanel(new BorderLayout());
        generalArea.setForeground(Color.white);
        add(generalArea);
        deploy();

    }

    private void  deploy(){
        setBackground(Color.white);
        setBorder(BorderFactory.createMatteBorder(1,0,1,0,new Color(240,240,240)));
        generalArea.add(buildForm(),BorderLayout.CENTER);


    }

    public void addTitle(String title){
        JLabel titleGUI = getTitle(title);
        titleGUI.setBorder(new EmptyBorder(0,0,10,0));
        generalArea.add(titleGUI,BorderLayout.NORTH);
    }

    private JLabel getTitle(String hora){
        JLabel horaLabel = new JLabel(hora);
        horaLabel.setFont(new Font("arial", Font.PLAIN, 20));
        horaLabel.setBorder(new EmptyBorder(0,10,0,10));
        horaLabel.setForeground(new Color(100,100,100));

        return horaLabel;
    }


    private JPanel buildForm(){
        elementsArea = new LinearVerticalLayout();
        elementsArea.setBackground(Color.white);
        elementsArea.setBorder(new EmptyBorder(10,0,0,0));

        return elementsArea;
    }

    @Override
    protected void addElement(FormElement element) {
        super.addElement(element);
        elementsArea.addElement(element);
    }



}
