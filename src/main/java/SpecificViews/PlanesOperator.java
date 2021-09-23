package SpecificViews;

import JDBCController.DataBaseConsulter;

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;

public class PlanesOperator {
    public String plan;
    public PlanesOperator(String plan){
        this.plan = plan;
    }

    public ArrayList<String> getMAterias(){
        DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio_materias");

        String[] cond = new String[]{"clave_plan"};

        String[] values = new String[]{plan};

        return consulter.bringTable(cond,values).getColumn(0);

    }


}
