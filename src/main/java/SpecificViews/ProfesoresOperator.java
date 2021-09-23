package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public class ProfesoresOperator {

    String claveProfesor;
    TableRegister profesorInfo;
    public ProfesoresOperator(String profesorMatricula){
        claveProfesor = profesorMatricula;
        profesorInfo = getInfo();


    }

    private TableRegister getInfo(){
        DataBaseConsulter consulter = new DataBaseConsulter("profesores_visible_view");

        String[] conditions = new String[]{"clave_profesor"};

        String[] values = new String[]{claveProfesor};


        return consulter.bringTable(conditions,values).getRegister(0);
    }

    public Table getGruposList(){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_visible_view");

        String[] colsToBring = new String[]{"grupo","materia","nombre_materia"};

        String[] conditions = new String[]{"profesor"};

        String[] values = new String[]{claveProfesor};

        return consulter.bringTable(colsToBring,conditions,values);
    }

    public GrupoOperator getGrupoStatus(String claveGrupo){
        return new GrupoOperator(claveGrupo);
    }

}
