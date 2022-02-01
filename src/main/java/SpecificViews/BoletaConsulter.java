package SpecificViews;

import JDBCController.Table;
import sistemaceb.BoletaPdfBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoletaConsulter extends Operation{

    public BoletaConsulter(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Consulta de calificaciones";

    }

    @Override
    public void buildOperation() {
        deploy();

    }

    private void deploy(){
        new BoletaPdfBuilder(keyValue);

    }



}
