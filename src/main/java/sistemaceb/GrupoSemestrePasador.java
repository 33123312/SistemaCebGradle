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
        grupoInfo = grupo.getGrupoInfo();
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
        for (String alumno:alumnos){
            removeBoletas(alumno);
            graduarAlmno(alumno);
        }
    }

    private void  removeBoletas(String alumno){
        removeCalificaciones(alumno);
        removeContact(alumno);
        removeTutores(alumno);
        removeAlumno(alumno);

    }

    private void removeAluVal(String alumno,String condition,String table){
        ArrayList<String> cond = new ArrayList<>();
        cond.add(condition);

        ArrayList<String> val = new ArrayList<>();
        val.add(alumno);

        try {
            new DataBaseUpdater(table).delete(cond,val);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void removeContact(String alumno){
        removeAluVal(alumno,"numero_control","alumnos_contacto");
    }

    private void removeTutores(String alumno){
        removeAluVal(alumno,"numero_control","tutores");
    }

    private void removeAlumno(String alumno){
        removeAluVal(alumno,"numero_control","alumnos");

    }

    private void removeCalificaciones(String alumno){
        ArrayList<String> califasKeys = getCalificacionesKeys(alumno);
        removeCalificacionesEtapas(califasKeys);

        DataBaseUpdater califasUpdater = new DataBaseUpdater("calificaciones");

            ArrayList<String> colToDelete = new ArrayList<>();
                colToDelete.add("clave_alumno");

            ArrayList<String> values = new ArrayList<>();
                values.add(alumno);

            try {
                califasUpdater.delete(colToDelete,values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }

    private void removeEtapasFromTable(String table,ArrayList<String> califasKeys){
        DataBaseUpdater updater = new DataBaseUpdater(table);

        ArrayList<String> cols = new ArrayList<>();
            cols.add("calificacion_clave");

        for (String key:califasKeys){
            ArrayList<String> valueBol = new ArrayList<>();
            valueBol.add(key);

            try {
                updater.delete(cols,valueBol);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    private void removeCalificacionesEtapas(ArrayList<String> califasKeys){
        removeEtapasFromTable("calificaciones_booleanas",califasKeys);
        removeEtapasFromTable("calificaciones_numericas",califasKeys);
        removeEtapasFromTable("calificaciones_semestrales",califasKeys);

    }

    private ArrayList<String> getCalificacionesKeys(String alumno){
        String[] colsToSet = new String[]{"clave_alumno"};

        String[] values = new String[]{alumno};

        DataBaseConsulter consulter = new DataBaseConsulter("calificaciones");
        ArrayList<String> calificaciones = consulter.bringTable(colsToSet,values).getColumn("calificacion_clave");
        return calificaciones;
    }

    private void graduarAlmno(String alumno){
        ArrayList<String> colsToSet = new ArrayList<>();
            colsToSet.add("numero_control");

        ArrayList<String> values = new ArrayList<>();
            values.add(alumno);

        try {
            new DataBaseUpdater("alumnos").delete(colsToSet,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
