package SpecificViews;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.util.ArrayList;

public class ConcentradoTodasMateriasPDF extends PDFPlantillaTable{

    ArrayList<String> topTitles;
    ArrayList<String> materias;

    public ConcentradoTodasMateriasPDF(ArrayList maerias) {
        super("Concentrado de Calificaciones por Etapa");
        topTitles = getTopTitles();
        this.materias = new ArrayList<>(maerias);


    }

    private ArrayList<String> getTopTitles(){
        ArrayList<String> titles = new ArrayList<>();
            titles.add("Matricula");
            titles.add("Nombre");

        return titles;

    }


    public void deploy(){
        setTable(getSizes());
        addFirstTitles();
        addSecondTitles();
    }

    private  void addFirstTitles(){
        for (String title: topTitles){
            Cell currentCell = addTitleStyle(getCellDeftyle(title, new Cell(2,0)));
            table.addCell(currentCell);
        }

        for (String materia:materias){
            Cell currentCell = addTitleStyle(getCellDeftyle(materia, new Cell(0,2)));
            table.addCell(currentCell);
        }

    }

    @Override
    public void addTable() {
        super.addTable();
        close();
    }

    private  void addSecondTitles(){
        for (String materia:materias){
            Cell title1Cell  = addTitleStyle(getDefCell("Cal."));
            Cell title2Cell  = addTitleStyle(getDefCell("Falt."));
            table.addCell(title1Cell);
            table.addCell(title2Cell);
        }

    }


    private float[] getSizes(){
        int sizesLngth = 2 + materias.size()*2;
        float[] sizes = new float[sizesLngth];
            sizes[0] = 1;
            sizes[0] = 11;
            for (int i = 0; i < sizesLngth; i++)
                sizes[i] = 1;
        return sizes;
    }

}
