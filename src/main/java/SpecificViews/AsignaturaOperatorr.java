package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public class AsignaturaOperatorr {

    private String grupo;
    private String materia;

    public AsignaturaOperatorr(String grupo,String materia){
        this.grupo = grupo;
        this.materia = materia;
    }

    public TableRegister getProfesor(){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_visible_view");

        String[] colsToBring = new String[]{"profesor","nombre_completo"};

        String[] cond = new String[]{"grupo","materia"};

        String[] values = new String[]{grupo,materia};

        Table response =  consulter.bringTable(colsToBring,cond,values);

        if(response.isEmpty())
            return null;
        else
            return response.getRegister(0);

    }

    public String getNombreMateria(){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"nombre_materia"};

        String[] cond = new String[]{"clave_materia"};

        String[] values = new String[]{materia};

        return consulter.bringTable(colsToBring,cond,values).getUniqueValue();
    }
}
