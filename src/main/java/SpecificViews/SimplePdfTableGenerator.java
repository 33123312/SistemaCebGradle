package SpecificViews;

import JDBCController.Table;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.Map;

public class SimplePdfTableGenerator {
    PDFPlantillaGetter plantilla;

    SimplePdfTableGenerator(Table table, float[] sizes, Map<String,String> params){
        plantilla = new PDFPlantillaGetter("Concentrado de Calificaciones por Materia");
        plantilla.addParams(params);
        addTable(table,sizes);

    }

     private void addTable(Table table,float[] sizes){
         com.itextpdf.layout.element.Table table1 = new com.itextpdf.layout.element.Table(sizes);
            table1.setWidth(new UnitValue(UnitValue.PERCENT, 100));
         ArrayList<String> titles = table.getColumnTitles();

         addTitleRow(titles,table1);

         ArrayList<ArrayList<String>> registers = table.getRgistersCopy();

         for (ArrayList<String> register: registers)
             addRow(register,table1);

         plantilla.add(table1);
         plantilla.close();

     }

    private void addTitleRow(ArrayList<String> vals,com.itextpdf.layout.element.Table table1){
        for(String val:vals){
            if(val == null)
                val = "";
            Cell newCell = new Cell(1,1).
                    add(val).
                    setFontSize(10).
                    setPadding(0).
                    setPaddingLeft(1).
                    setPaddingRight(1).setBackgroundColor(Color.LIGHT_GRAY);

            table1.addCell(newCell);
        }

    }

     private void addRow(ArrayList<String> vals,com.itextpdf.layout.element.Table table1){
        for(String val:vals){
            if(val == null)
                val = "";
            Cell newCell = new Cell(1,1).
                    add(val).
                    setFontSize(10).
                    setPadding(0).
                    setPaddingLeft(1).
                    setPaddingRight(1);

            table1.addCell(newCell);
        }

     }

}
