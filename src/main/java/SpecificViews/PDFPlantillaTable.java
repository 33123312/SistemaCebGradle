package SpecificViews;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.util.ArrayList;

public abstract class PDFPlantillaTable extends PDFPlantillaGetter{
    Table table;

    public PDFPlantillaTable(String title) {
        super(title);
    }

    protected Cell getDefCell(String txt) {
        Cell currentCell = new Cell(0,0);
        return getCellDeftyle(txt,currentCell);
    }

    protected void setTable(float[] sizes){
        table = new Table(sizes);
        table.setWidthPercent(100);
    }


    protected Cell getCellDeftyle(String txt,Cell currentCell ){
        if(txt == null)
            txt = "";
        currentCell.add(txt);
        currentCell.setPadding(0).
                setPaddingLeft(1).
                setPaddingRight(1).
                setFontSize(9);
        return currentCell;
    }


    protected Cell addTitleStyle(Cell cell){
        cell.setBackgroundColor(Color.LIGHT_GRAY).setFontSize(10).
                setVerticalAlignment(VerticalAlignment.MIDDLE).
                setHorizontalAlignment(HorizontalAlignment.CENTER);;
        return cell;
    }

    public void addRegister(ArrayList<String> row){
        for (String val:row)
            table.addCell(getDefCell(val));
    }

    public void addRegister(ArrayList<String> row,int fontSize){

        for (String val:row){
            Cell cell = getDefCell(val).
                    setFontSize(fontSize);

            table.addCell(cell);
        }

    }

    public void addTable(){
        add(table);
    }


    public void addCell(Cell cell){
        table.addCell(cell);
    }


}
