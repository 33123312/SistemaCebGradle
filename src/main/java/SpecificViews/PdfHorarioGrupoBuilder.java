package SpecificViews;

import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;

import java.util.ArrayList;

public class PdfHorarioGrupoBuilder extends PDFPlantillaTable{
    public PdfHorarioGrupoBuilder() {
        super("Horario de Grupo");
        initTable();
    }

    private void initTable(){
        setTable(getSize());
        addTitles();
    }

    private void addTitles(){
        ArrayList<String> dias = getDias();
        for (String dia:dias)
            table.addCell(addTitleStyle(getDefCell(dia)));

    }

    public void addRowHora(ArrayList<ArrayList<String>> newRow){
        table.addCell(addTitleStyle(getCell(newRow.get(0).get(0))));
            newRow.remove(0);

        for (int i = 0;i < 3;i++ )
            for (ArrayList<String> cell : newRow ){
                Cell newCell;
                if(cell.isEmpty())
                    newCell = getCellA("");
                else
                    newCell = getCellA(cell.get(i));

                if( i == 2)
                    newCell.setBorderBottom(new SolidBorder(1));

                table.addCell(newCell);
            }

    }

    private Cell getCellA(String txt){
        return getDefCell(txt).
                setBorder(Border.NO_BORDER).
                setBorderLeft(new SolidBorder(1)).
                setBorderRight(new SolidBorder(1));
    }

    private Cell getCell(String txt){
        return getCellDeftyle(txt,new Cell(3,0));

    }

    private ArrayList<String> getDias(){
        ArrayList<String> dias = new ArrayList<>();
        dias.add("");
        dias.add("Lunes");
        dias.add("Martes");
        dias.add("Miercoles");
        dias.add("Jueves");
        dias.add("Viernes");

        return dias;
    }

    private float[] getSize(){
        float[] sizes = new float[]{1,1,1,1,1,1};
        return sizes;
    }
}
