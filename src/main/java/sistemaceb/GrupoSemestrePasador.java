package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.DataBaseUpdater;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import SpecificViews.GrupoOperator;

import java.sql.SQLException;
import java.util.ArrayList;

public class GrupoSemestrePasador {
    private String nextSemestre;
    private boolean isLastSemestre;
    private TableRegister grupoInfo;


    public GrupoSemestrePasador(GrupoOperator grupo){
        grupoInfo = grupo.getTableInfo();
        nextSemestre = getNextSemestre();
        isLastSemestre = nextSemestre.equals("7");

    }

    public boolean isLastSemestre(){
        return isLastSemestre;
    }

    public ArrayList<String> getNextSemestreGrupos(){
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] bring = new String[]{"grupo"};

         String[] cols = new String[]{"semestre","turno"};

        String[] val = new String[]{nextSemestre,grupoInfo.get("turno")};

        return consulter.bringTable(bring,cols,val).getColumn(0);
    }

    public void graduarAlumnos(ArrayList<String> alumnos){
        for (String alumno:alumnos)
            new AlumnoRemover(alumno);

    }

    public void passAlumnos(String nextGroup, ArrayList<String> alumnos){
        for (String alumnoKey:alumnos)
            passAlumno(alumnoKey,nextGroup);
    }

    private String getNextSemestre(){
        String currentSemestre = grupoInfo.get("semestre");
        int currentSemestreInt = Integer.parseInt(currentSemestre);
        int nextSemestre = currentSemestreInt + 1;

        return Integer.toString(nextSemestre);
    }

    private void passAlumno(String alumno, String newGroup){
        ArrayList<String> colsToSet = new ArrayList<>();
            colsToSet.add("grupo");
            colsToSet.add("semestre");

        ArrayList<String> values = new ArrayList<>();
            values.add(newGroup);
            values.add(nextSemestre);

        ArrayList<String> cond = new ArrayList<>();
            cond.add("numero_control");

        ArrayList<String> condVal = new ArrayList<>();
            condVal.add(alumno);

        try {
            new ViewSpecs("alumnos").getUpdater().update(colsToSet,values,cond,condVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
