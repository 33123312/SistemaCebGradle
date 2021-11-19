package SpecificViews;

import Generals.BtnFE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class SubmitOperation extends OperationWindow{

    JPanel body;

    public SubmitOperation(OperationInfoPanel infoPanlel){
        super(infoPanlel);
    }

    private JScrollPane getScroll(){
        JScrollPane scroll = new JScrollPane();
            scroll.setBorder(new EmptyBorder(0,0,0,0));
        body = new JPanel(new GridBagLayout());
        scroll.setViewportView(body);

        return scroll;

    }

    private JPanel getBody(){
        JPanel cont = new JPanel(new BorderLayout());
            cont.setBorder(BorderFactory.createEmptyBorder(15,30,15,30));

        cont.add(getScroll(),BorderLayout.CENTER);
        cont.add(getButtonsArea(),BorderLayout.SOUTH);

        return cont;
    }

    private JPanel getButtonsArea(){
        JPanel buttonsArea = new JPanel(new BorderLayout());
        buttonsArea.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
        BtnFE btnAceptar  =new BtnFE("Aceptar");
        btnAceptar.setTextColor(Color.white);
        btnAceptar.setBackground(new Color(107, 117, 255));
        btnAceptar.setPadding(10,20,10,20);
        btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                submit();
            }
        });

        buttonsArea.add(btnAceptar,BorderLayout.EAST);

        return buttonsArea;
    }

    @Override
    public void buildOperation(){
        super.buildOperation();
        thisWindow.setBody(getBody());
    }

    protected abstract void submit();
}
