package SpecificViews;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.awt.*;
import java.util.ArrayList;

public class BitacoraMateriaGrupoPDF extends PDFPlantillaTable{

    private ArrayList<ArrayList<String>> alumnos;
    private String profesor;
    final int fillerCols;
    int rowCont;

    public BitacoraMateriaGrupoPDF(ArrayList<ArrayList<String>> alumnos,String profesor) {
        super("");
        this.profesor = profesor;
        fillerCols = 25;
        this.alumnos = alumnos;
        rowCont = 1;

    }

    public void deployTable(){
        setTableUp();
        addRows();
        addTable();
        addFirmaProfesor();
        close();
    }

    private void setTableUp(){
        setTable(getSizes());
        addFirstTitles();
        addSecondTitles();

    }

    private void addFirstTitles(){
        addCell(addTitleStyle(getCellDeftyle("#",new Cell(2,0))));
        addCell(addTitleStyle(getCellDeftyle("Matr.",new Cell(2,0))));
        addCell(addTitleStyle(getCellDeftyle("Nombre",new Cell(2,0))));

        addCell(addTitleStyle(getCellDeftyle("Eval.",new Cell(0,3))));
        addCell(addTitleStyle(getCellDeftyle("Asistencias",new Cell(0,18))));
        addCell(addTitleStyle(getCellDeftyle("Totales",new Cell(0,3))));
        addCell(addTitleStyle(getDefCell("Calif.")));

    }

    private void addSecondTitles(){
        addFillers(21);

        addCell(addTitleStyle(getCellDeftyle("FI.",new Cell(0,0))));
        addCell(addTitleStyle(getCellDeftyle("FJ.",new Cell(0,0))));
        addCell(addTitleStyle(getCellDeftyle("FA.",new Cell(0,0))));

        addFillers(1);

    }

    private void addRows(){
        for (ArrayList<String> alumnoInfo: alumnos){
            addRow(alumnoInfo);
        }
    }

    private void addRow(ArrayList<String> aluInfo){
        ArrayList<String> register = new ArrayList<>();
            register.add(rowCont + "");
            register.addAll(aluInfo);
            addRegister(register,8);
            addFillers();
            rowCont++;

    }
    private void addFillers(int limit){
        for(int i = 0; i < limit;i++){
            Cell currentCell = getDefCell("").setPaddingLeft(8);
            table.addCell(currentCell);
        }
    }

    private void addFillers(){
        addFillers(fillerCols);
    }

    private float[] getSizes(){
        float[] sizes = new float[3+fillerCols];
            sizes[0] = 1;
            sizes[1] = 2;
            sizes[2] = 6;
            for (int i = 0; i < fillerCols;i++)
                sizes[i] = 1;
        return sizes;

    }

    private void addFirmaProfesor(){
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
                setFontSize(10).add(profesor);

        firma.addCell(nombreCell);

        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(firma);


    }




}
