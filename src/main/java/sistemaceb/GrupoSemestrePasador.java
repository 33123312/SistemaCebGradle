package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import SpecificViews.GrupoOperator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class GrupoSemestrePasador {
    private String nextSemestre;
    private String currentSemestre;



    public GrupoSemestrePasador(String currentSemestre){
        this.currentSemestre = currentSemestre;
        nextSemestre = getNextSemestre();

    }

    public boolean isLastSemestre(){
        return nextSemestre.equals("7");
    }

    private String getNextSemestre(){

        int currentSemestreInt = Integer.parseInt(currentSemestre);
        int nextSemestre = currentSemestreInt + 1;

        return Integer.toString(nextSemestre);
    }

    public void passAlumnos(Map<String,ArrayList<String>> grupos){
        for (Map.Entry<String,ArrayList<String>> group:grupos.entrySet()){
            passAlumnos(group.getKey(),group.getValue());
        }
    }

    private void passAlumnos(String nextGroup, ArrayList<String> alumnos){
        ArrayList<String> colsToSet = new ArrayList<>();
            colsToSet.add("grupo");
            colsToSet.add("semestre");

        ArrayList<String> values = new ArrayList<>();
            values.add(nextGroup);
            values.add(nextSemestre);

        ArrayList<String> cond = new ArrayList<>();
        ArrayList<String> condVal = new ArrayList<>();


        for (String alumnoKey:alumnos){
            cond.add("numero_control");
            condVal.add(alumnoKey);
        }

        try {
            new ViewSpecs("alumnos").getUpdater().updateOr(colsToSet,values,cond,condVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
