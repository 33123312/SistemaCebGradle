package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.ViewSpecs;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoRemover {

    public AlumnoRemover(String alumno){
        removeBoletas(alumno);
        graduarAlumno(alumno);
    }

    private void removeAlumno(String alumno){
        removeAluVal(alumno,"numero_control","alumnos");

    }

    private void removeCalificaciones(String alumno){
        ArrayList<String> califasKeys = getCalificacionesKeys(alumno);
        removeCalificacionesEtapas(califasKeys);

        ArrayList<String> colToDelete = new ArrayList<>();
        colToDelete.add("clave_alumno");

        ArrayList<String> values = new ArrayList<>();
        values.add(alumno);

        try {
            new ViewSpecs("calificaciones").getUpdater().delete(colToDelete,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void removeEtapasFromTable(String table,ArrayList<String> califasKeys){
        ViewSpecs updater = new ViewSpecs(table);

        ArrayList<String> cols = new ArrayList<>();
        cols.add("calificacion_clave");

        for (String key:califasKeys){
            ArrayList<String> valueBol = new ArrayList<>();
            valueBol.add(key);

            try {
                updater.getUpdater().delete(cols,valueBol);
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
            new ViewSpecs(table).getUpdater().delete(cond,val);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void graduarAlumno(String alumno){
        ArrayList<String> colsToSet = new ArrayList<>();
        colsToSet.add("numero_control");

        ArrayList<String> values = new ArrayList<>();
        values.add(alumno);

        try {
            new ViewSpecs("alumnos").getUpdater().delete(colsToSet,values);
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
}
