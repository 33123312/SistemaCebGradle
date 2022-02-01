package SpecificViews;

import com.itextpdf.layout.element.Cell;

import java.util.ArrayList;

public class ConcentradoTodasMateriasPDF extends PDFPlantillaTable{

    ArrayList<String> materias;

    public ConcentradoTodasMateriasPDF(ArrayList maerias) {
        super("Concentrado de Calificaciones por Etapa");
        this.materias = new ArrayList<>(maerias);
        adddMembreteCorto();


    }

    private ArrayList<String> getTopTitles(){
        ArrayList<String> titles = new ArrayList<>();
            titles.add("Nombre");

        return titles;

    }


    public void deploy(){
        setTable(getSizes());
        addFirstTitles();
        addSecondTitles();
    }

    private  void addFirstTitles(){
        for (String title: getTopTitles()){
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
            Cell title1Cell  = addTitleStyle(getDefCell("C"));
            Cell title2Cell  = addTitleStyle(getDefCell("F"));
            table.addCell(title1Cell);
            table.addCell(title2Cell);
        }

    }


    private float[] getSizes(){
        int sizesLngth = 1 + materias.size()*2;
        float[] sizes = new float[sizesLngth];
            sizes[0] = 1;
            sizes[0] = 11;
            for (int i = 0; i < sizesLngth; i++)
                sizes[i] = 1;
        return sizes;
    }

}
