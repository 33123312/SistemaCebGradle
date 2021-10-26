package SpecificViews;

import Generals.BtnFE;
import JDBCController.DataBaseConsulter;
import sistemaceb.form.HorizontalFormPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CuadroResumenOP extends OperationWindow{

    HorizontalFormPanel form;

    public CuadroResumenOP(OperationInfoPanel infoPanlel){
        super(infoPanlel);
        operation = "Cuadro Resumen";
    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        deploy();

    }

    private void deploy(){
        thisWindow.addToHeader(defineEvaForm());
        thisWindow.addToHeader(getBtn());

    }

    private BtnFE getBtn(){
        BtnFE btn = new BtnFE("Mostrar PDF");
            btn.setPadding(5,10,5,10);
            btn.setTextColor(Color.black);
            btn.addMouseListener(getBtnEvent());

        return btn;
    }

    private MouseAdapter getBtnEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(!form.hasErrors()){
                    String response = form.getData().get("Evaluacion");
                        new CuadroResumenPdfBuilder(keyValue,response);
                }

            }
        };
    }

    private HorizontalFormPanel defineEvaForm(){
        form = new HorizontalFormPanel();
        form.addDesplegableMenu("Evaluacion").setRequired(true).setOptions(getFormOptions());

        return form;
    }

    private ArrayList<String> getFormOptions(){
        DataBaseConsulter consulter = new DataBaseConsulter("evaluaciones");
        ArrayList<String> cosas = consulter.bringTable().getColumn(0);
            cosas.remove("semestral");
        return cosas;

    }

}
