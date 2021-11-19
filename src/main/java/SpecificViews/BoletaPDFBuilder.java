package SpecificViews;

import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.util.ArrayList;

public class BoletaPDFBuilder extends PDFPlantillaTable{
    ArrayList<String> evaluaciones;
    public BoletaPDFBuilder(ArrayList<String> evaluaciones) {
        super("Boleta de Calificaciones");
        this.evaluaciones = evaluaciones;
        addMembreteLargo();
    }


    protected void setTable() {
        super.setTable(getSizes());
        deployFirstTitles();
        deploySecondTitles();

    }

    private void deployFirstTitles(){
        table.addCell(addTitleStyle(getCellDeftyle("Materia",new Cell(2,0))));
        addEtapasNames();
        table.addCell(addTitleStyle(getCellDeftyle("Promedio",new Cell(2,0))));
        table.addCell(addTitleStyle(getCellDeftyle("Semestral",new Cell(2,0))));
        addDoubleTittle("Finales");


    }

    public void addFirmaProfesor(){
        float[] sizes = new float[]{1};

        Table firma = new Table(sizes).
                setWidthPercent(33);

        Cell lineCell = new Cell().setBorder(Border.NO_BORDER).
                setBorderBottom(new SolidBorder(1)).
                setMarginBottom(10);

        firma.addCell(lineCell);

        Cell nombreCell = new Cell().
                setHorizontalAlignment(HorizontalAlignment.CENTER).
                setBorder(Border.NO_BORDER).
                setFontSize(10).add("Firma del Tutor");

        firma.addCell(nombreCell);

        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(firma);


    }

    public void addCalifasReg(ArrayList<String> register){
        for (String value: register)
            table.addCell(getCalifCell(value));

    }

    private Cell getCalifCell(String txt){
        Cell newCell = getDefCell(txt);
            newCell.setBorder(Border.NO_BORDER);
            newCell.setBorderLeft(new SolidBorder(1));
            newCell.setBorderRight(new SolidBorder(1));

        return newCell;
    }


    public void addPromediosRegister(ArrayList<String>proms){
        for (String prom: proms)
            table.addCell(addTitleStyle(getDefCell(prom)));
    }

    private void addEtapasNames(){
        for (String evaluacion: evaluaciones)
            addDoubleTittle(evaluacion);
    }

    private void addDoubleTittle(String txt){
        table.addCell(addTitleStyle(getCellDeftyle(txt,new Cell(0,2))));
    }

    private void deploySecondTitles(){
        for (String evaluacione:evaluaciones){
            table.addCell(addTitleStyle(getDefCell("Cal")));
            table.addCell(addTitleStyle(getDefCell("Faltas")));
        }

        table.addCell(addTitleStyle(getDefCell("Cal")));
        table.addCell(addTitleStyle(getDefCell("Faltas")));

    }


    private float[] getSizes(){
        float[] sizes = new float[]{
                1,
                1,1,1,1,1,1,1,1,
                1,1,1,1};
        return sizes;
    }



}
