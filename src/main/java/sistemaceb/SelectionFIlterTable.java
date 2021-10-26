package sistemaceb;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import SpecificViews.GrupoOperator;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import Tables.TableRow;

import java.awt.*;
import java.util.ArrayList;

public class SelectionFIlterTable extends AdapTableFE {
    ArrayList<ArrayList<String>> possibleSelections;
    GrupoOperator operator;

    public SelectionFIlterTable(GrupoOperator operator) {
        super();
        this.operator= operator;
        deployTable();

    }

    public void setDefaltSelections(ArrayList<String> selections){
        if(!selections.isEmpty()){
            ArrayList<Integer> selected = getNewSelectedIndex(selections);
            setSelectedIndex(selected);
        }
    }

    private void updateTable(){
        Table alumnos = getAlumnos();

        possibleSelections = alumnos.getRgistersCopy();
        setTitles(alumnos.getColumnTitles());
        setRows(possibleSelections);
        showAll();
    }

    private ArrayList<Integer> getNewSelectedIndex(ArrayList<String> newAlumnos) {
        ArrayList<String> currentIndexes = getAluIndexes();
        ArrayList<Integer> newSelectedIndex = new ArrayList<>();

        for(String newIndex: newAlumnos){
            int aluIndex = currentIndexes.indexOf(newIndex);
            if(aluIndex != -1)
                newSelectedIndex.add(aluIndex);
        }

        return newSelectedIndex;
    }

    private ArrayList<String> getAluIndexes(){
        ArrayList<String> indexes = new ArrayList<>();
        for (ArrayList<String> register: possibleSelections){
            indexes.add(register.get(0));
        }

        return indexes;
    }


    private void deployTable(){
        addRememberSelectedRows(new Color(129, 236, 236));
        updateTable();

    }

    private static ArrayList<String> alumnoTags;

    private ArrayList<String> consultAlumnoTags(Table alumnos){
        if(alumnoTags == null)
            alumnoTags = new ViewSpecs("alumnos").getTag(alumnos.getColumnTitles());

        return alumnoTags;

    }


    private Table getAlumnos(){
        Table alumnos = operator.getAlumnos();
        ArrayList<String> trueTitles = consultAlumnoTags(alumnos);
        alumnos.setColumnTitles(trueTitles);
        return alumnos;
    }

    public ArrayList<String> getSelectedAlumnos(){
        ArrayList<String> alumnos = new ArrayList<>();

        ArrayList<TableRow> selectedRows = getSelectedRow();
        ArrayList<Integer> indexes = new ArrayList<>();

        for (TableRow selectedRow:selectedRows)
            indexes.add(selectedRow.getKey());

        for (int index:indexes)
            alumnos.add(possibleSelections.get(index).get(0));

        return alumnos;
    }


}
