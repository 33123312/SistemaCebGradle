package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

public class AsignaturaOperator{

    private String grupo;
    private String materia;

    public AsignaturaOperator(String grupo, String materia){
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

    public ProfesoresOperator getProfesorOperator(){
        return new ProfesoresOperator(getProfesor().get("profesor"));
    }

    public String getNombreMateria(){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"nombre_abr"};

        String[] cond = new String[]{"clave_materia"};

        String[] values = new String[]{materia};

        return consulter.bringTable(colsToBring,cond,values).getUniqueValue();
    }
}
