package SpecificViews;

import JDBCController.Table;

import java.util.ArrayList;

public class GrupoPassPdfBuilder extends PDFPlantillaTable{

    public GrupoPassPdfBuilder(String grupo) {
        super("Grupo " + grupo);
        setTable(new float[]{1,1,1});
    }

    public void addRows(ArrayList<ArrayList<String>> rows){
        for (ArrayList<String> row: rows){
            addRow(row);
        }

        addTable();
        close();
    }

    public void addRow(ArrayList<String> row){
        for (String cell:row)
            table.addCell(getDefCell(cell));
    }
}
