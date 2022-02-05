package JDBCController;
import sistemaceb.RespaldosManager;
import sistemaceb.form.Global;

import java.util.ArrayList;

public class PastPeriodoAlumnoSearcher {

    String nombre;

    public PastPeriodoAlumnoSearcher(String alumno){
        this.nombre = alumno;
    }

    public Table getRatedAlumnos(){

        ArrayList<String> periodos =
                new DataBaseConsulter("periodos").bringTable().getColumn(0);

        periodos.remove(Global.conectionData.loadedPeriodo);

        ArrayList<String> titels = new ArrayList<>();
        ArrayList<ArrayList<String>> unratedAlumnos = new ArrayList<>();


        for (String periodo:periodos){
            Table currT = searchInPeriodo(periodo);

            if (titels.isEmpty())
                titels = currT.getColumnTitles();

            unratedAlumnos.addAll(currT.getRegisters());

        }

        Table unsortedTable = new Table(titels,unratedAlumnos);

        complexSearcher searcher = new complexSearcher(unsortedTable);

        searcher.rate("nombre_completo",nombre);

        return searcher.getRatedNSortedRegisters();

    }

    private Table searchInPeriodo(String periodo){

        new RespaldosManager().chargePeriodoBackup(periodo);

        DataBaseConector conector = new DataBaseConector("");

        Table alumnos
            = new DataBaseConsulter(
        "bajas_visible_view",conector.getMyStatment()).
            bringTable();

        conector.endConection();

        complexSearcher searcher = new complexSearcher(alumnos);

        searcher.rate("nombre_completo",nombre);

        Table ratedAlumnos = searcher.getRatedNSortedRegisters();

        addPeriodo(periodo,ratedAlumnos);

        return new Table(ratedAlumnos.getColumnTitles(),getBetterRated(ratedAlumnos));
    }

    private void addPeriodo(String periodo,Table rated){
        ArrayList<String> periodoCol = new ArrayList<>();

        for (int i = 0;i < rated.rowCount();i++){
            periodoCol.add(periodo);

        }

        rated.addColumn(rated.columnCount(),"periodo",periodoCol);
    }

    private ArrayList<ArrayList<String>> getBetterRated(Table ratedAlumnos){
        ArrayList<ArrayList<String>> betterRated = new ArrayList<>();
        for (int i = 0;i < 30;i++)
            betterRated.add(ratedAlumnos.getRegister(i).getValues());

        return betterRated;
    }

}
