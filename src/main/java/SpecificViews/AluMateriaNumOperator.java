package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;

import java.util.ArrayList;

public class AluMateriaNumOperator extends AluMateriaOperator{

    String calificacionSemestral;
    AluMateriaNumOperator(String materia,String type,String periodo,ALumnoOperator aLumnoOperator) {
        super(materia,type,periodo, aLumnoOperator);

        calificacionSemestral = detSemetrCalif();
    }

    public String getCalifSemestral(){
        return calificacionSemestral;
    }

    private String detSemetrCalif(){
        DataBaseConsulter consulter = new DataBaseConsulter("calificaciones_semestrales_view");

        String[] colsToBring = new String[]{"calificacion"};

        String[] cond = new String[]{"periodo","clave_alumno","semestre","materia"};

        String[] val = new String[]{periodo,aLumnoOperator.getTableRegister(),aLumnoOperator.getRegisterValue("semestre"),materia};

        String semestral = consulter.bringTable(colsToBring,cond,val).getUniqueValue();
        if( semestral == null)
            semestral = "";

        return semestral;

    }

    @Override
    public String getPromFinal() {
        ArrayList valuesToProm = new ArrayList();
            valuesToProm.add(calificacionSemestral);
            valuesToProm.add(getParProm());

        return PromsOperations.getProm(valuesToProm);
    }

    public String getParProm(){

        return PromsOperations.getProm(parcialesCal);
    }
}
