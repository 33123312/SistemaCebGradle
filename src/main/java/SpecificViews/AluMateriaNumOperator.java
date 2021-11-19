package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public class AluMateriaNumOperator extends AluMateriaOperator{

    private String calificacionSemestral;

    AluMateriaNumOperator(String materia, String periodo, TableRegister aLumnoOperator, Table req) {
        super(materia,"Num�rica",periodo, aLumnoOperator,req);

    }

    AluMateriaNumOperator(String materia, String periodo, TableRegister aLumnoOperator) {
        super(materia,"Num�rica",periodo, aLumnoOperator);


    }

    public String getCalifSemestral(){
        if(calificacionSemestral == null)
            calificacionSemestral = detSemetrCalif();

        return calificacionSemestral;
    }

    private String detSemetrCalif(){
        DataBaseConsulter consulter = new DataBaseConsulter("calificaciones_semestrales_view");

        String[] colsToBring = new String[]{"calificacion"};

        String[] cond = new String[]{"periodo","clave_alumno","semestre","materia"};

        String[] val = new String[]{periodo,aluInfo.get("numero_control"),aluInfo.get("semestre"),materia};

        String semestral = consulter.bringTable(colsToBring,cond,val).getUniqueValue();
        if( semestral == null)
            semestral = "";

        return semestral;

    }

    @Override
    public String getPromFinal() {
        ArrayList valuesToProm = new ArrayList();
            valuesToProm.add(getCalifSemestral());
            valuesToProm.add(getParProm());

        return PromsOperations.getProm(valuesToProm);
    }

    public String getParProm(){

        return PromsOperations.getProm(parcialesCal);
    }
}
