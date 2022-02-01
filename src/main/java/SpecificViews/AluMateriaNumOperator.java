package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public class AluMateriaNumOperator extends AluMateriaOperator{

    private String calificacionSemestral;
    private TableRegister semestreBoleta;

    AluMateriaNumOperator(String materia, String periodo, TableRegister aLumnoOperator, Table req) {
        super(materia,"Numérica",periodo, aLumnoOperator,req);

    }

    AluMateriaNumOperator(String materia, String periodo, TableRegister aLumnoOperator, Table req,Table semestreBoleta) {
        super(materia,"Numérica",periodo, aLumnoOperator,req);
        this.semestreBoleta = getSemestralRegister(semestreBoleta);

    }

    AluMateriaNumOperator(String materia, String periodo, TableRegister aLumnoOperator) {
        super(materia,"Numérica",periodo, aLumnoOperator);

    }

    private TableRegister getSemestralRegister(Table semestreBoleta){
        Table sem = semestreBoleta.getSubTable(new String[]{"materia"},new String[]{materia});

        if (sem.isEmpty())
            return new TableRegister(sem.getColumnTitles(),new ArrayList<>());
        else
            return sem.getRegister(0);
    }

    public String getCalifSemestral(){
        if(calificacionSemestral == null)
            calificacionSemestral = detSemetrCalif();

        return calificacionSemestral;
    }

    private String detSemetrCalif(){
        String semestral;

        if(semestreBoleta == null){
            DataBaseConsulter consulter = new DataBaseConsulter("calificaciones_semestrales_view");

            String[] colsToBring = new String[]{"calificacion"};

            String[] cond = new String[]{"periodo","clave_alumno","semestre","materia"};

            String[] val = new String[]{periodo,aluInfo.get("numero_control"),aluInfo.get("semestre"),materia};

            semestral = consulter.bringTable(colsToBring,cond,val).getUniqueValue();
        } else if (semestreBoleta.getValues().isEmpty())
            semestral = null;
        else
            semestral = semestreBoleta.get("calificacion");

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
