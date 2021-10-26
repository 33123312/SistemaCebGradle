package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;

public class ProfesoresOperator extends TableOperator{

    public ProfesoresOperator(String profesorMatricula){
        super(profesorMatricula,"profesores");

    }

    public Table getGruposList(){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_visible_view");

        String[] colsToBring = new String[]{"grupo","materia","nombre_abr"};

        String[] conditions = new String[]{"profesor"};

        String[] values = new String[]{tableRegister};

        return consulter.bringTable(colsToBring,conditions,values);
    }

    public GrupoOperator getGrupoStatus(String claveGrupo){
        return new GrupoOperator(claveGrupo);
    }

}
