package SpecificViews;

import Generals.BtnFE;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class TableViewerPDFOp extends TableViewerOperation{

    public TableViewerPDFOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
    }

    private void addImprimirButton(){
        BtnFE imprimirBtn = new BtnFE("Mostrar PDF");
        imprimirBtn.setTextColor(new Color(9, 132, 227));
        imprimirBtn.setPadding(15,15,15,15);
        imprimirBtn.setMargins(0,10,0,10,Color.white);
        imprimirBtn.addMouseListener(getImprimirBtnEvent());

        thisWindow.addToHeader(imprimirBtn);
    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        addImprimirButton();
    }

    private MouseAdapter getImprimirBtnEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                buildPDF();
            }
        };
    }


    protected abstract void buildPDF();




}
