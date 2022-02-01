package SpecificViews;

import JDBCController.Table;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.Map;

public class SimplePdfTableGenerator extends PDFPlantillaTable{

    SimplePdfTableGenerator(Table table, float[] sizes, Map<String,String> params){
        super("Concentrado de Calificaciones por Materia");
        adddMembreteCorto();
        addParams(params);
        addTable(table,sizes);

    }

     private void addTable(Table table,float[] sizes){
        setTable(sizes);
         ArrayList<String> titles = table.getColumnTitles();

         addTitleRow(titles);

         ArrayList<ArrayList<String>> registers = table.getRgistersCopy();

         for (ArrayList<String> register: registers)
             addRow(register);

         addTable();
         close();

     }

    private void addTitleRow(ArrayList<String> vals){
        for(String val:vals){
            if(val == null)
                val = "";
            addCell(getCellDeftyle(val,getDefCell("")));
        }

    }

     private void addRow(ArrayList<String> vals){
        for(String val:vals){
            if(val == null)
                val = "";

            addCell(getDefCell(val));
        }

     }

}
