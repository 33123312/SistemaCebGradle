package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoRemover {

    ArrayList<String> alumnoCalifasKeys;
    ArrayList<String> alumnos;

    public AlumnoRemover(ArrayList<String> alumnos){
        this.alumnos = alumnos;
        removeBoletas(alumnos);
        graduarAlumno(alumnos);
    }

    public AlumnoRemover(ArrayList<String> alumnos, Table califakeys){
        this.alumnos = alumnos;

        alumnoCalifasKeys = califakeys.getColumn(0);

        removeBoletas(alumnos);
        graduarAlumno(alumnos);
    }

    private  ArrayList<String> getCalifasKeys(){
        if (alumnoCalifasKeys==null){
            alumnoCalifasKeys = getCalificacionesKeys(alumnos);
        }

        return alumnoCalifasKeys;
    }

    private ArrayList<String> getCalificacionesKeys(ArrayList<String> alumnos){
        String[] colToBring = new String[]{"calificacion_clave"};

        String[] cond = new String[alumnos.size()];

        for (int i = 0; i < cond.length;i++)
            cond[i] = "clave_alumno";


        String[] values = alumnos.toArray(new String[alumnos.size()]);

        DataBaseConsulter consulter = new DataBaseConsulter("calificaciones");

        ArrayList<String> calificaciones =
                consulter.bringTableOr(colToBring,cond,values).getColumn(0);

        return calificaciones;
    }

    private void  removeBoletas(ArrayList<String> alumnos){

        removeWebUsers();
        removeCalificacionesEtapas();
        removeCalificaciones(alumnos);
        removeContact(alumnos);
        removeTutores(alumnos);
        removeAlumno(alumnos);

    }

    private void removeWebUsers(){
        delete(alumnos,"numero_control","webUsers");
    }

    private void delete(ArrayList<String> keys,String keyCol,String table){
        ArrayList<String> cols = new ArrayList<>();

        for (String key:keys){
            cols.add(keyCol);
        }

        try {
            new ViewSpecs(table).getUpdater().deleteOr(cols,keys);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void removeAlumno(ArrayList<String> alumnos){
        delete(alumnos,"numero_control","alumnos");

    }

    private void removeCalificaciones(ArrayList<String> alumnos) {

        delete(alumnos,"clave_alumno","calificaciones");
    }

    private void removeEtapasFromTable(String table){
        delete(getCalifasKeys(),"calificacion_clave",table);

    }

    private void removeCalificacionesEtapas(){
        removeEtapasFromTable("calificaciones_booleanas");
        removeEtapasFromTable("calificaciones_numericas");
        removeEtapasFromTable("calificaciones_semestrales");

    }





    private void graduarAlumno(ArrayList<String> alumnos){
        delete(alumnos,"numero_control","alumnos");
    }


    private void removeContact(ArrayList<String> alumnos){
        delete(alumnos,"numero_control","alumnos_contacto");
    }

    private void removeTutores(ArrayList<String> alumnos){
        delete(alumnos,"numero_control","tutores");
    }
}
