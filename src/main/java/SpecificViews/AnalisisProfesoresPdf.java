package SpecificViews;

import com.itextpdf.layout.element.Cell;

import java.util.ArrayList;

public class AnalisisProfesoresPdf extends PDFPlantillaTable{

    public AnalisisProfesoresPdf() {
        super("Análisis Numérico");
        etapas = getEtapas();
        deploy();
    }

    private void deploy(){
        setTable(getSizes());
        addFirstTitles();
        addSecondTitles();
    }

    public void addPromediosRegister(ArrayList<String> promedios){
        table.addCell(addTitleStyle(getCellDeftyle(promedios.get(0),new Cell(0,2))));
        ArrayList<String> currentPromedios = new ArrayList<>(promedios);
        currentPromedios.remove(0);
        for (String prom:currentPromedios)
            table.addCell(getDefCell(prom));

    }

    private float[] getSizes(){
        float[] sizes =
                new float[]{1,1,1, 1,1,1,1 ,1,1,1,1 ,1,1,1,1 ,1,1,1,1};

        return sizes;
    }

    private void addFirstTitles(){
        table.addCell(addTitleStyle(getCellDeftyle("Grupo",new Cell(2,0))));
        table.addCell(addTitleStyle(getCellDeftyle("Materia",new Cell(2,0))));
        table.addCell(addTitleStyle(getCellDeftyle("alumnos",new Cell(2,0))));

        table.addCell(addTitleStyle(getCellDeftyle("Promedios en Unidades",new Cell(0,4))));
        table.addCell(addTitleStyle(getCellDeftyle("Porcentaje de Reprobacion",new Cell(0,4))));
        table.addCell(addTitleStyle(getCellDeftyle("Sumatoria de Aaltas",new Cell(0,4))));
        table.addCell(addTitleStyle(getCellDeftyle("Faltas por Alumno",new Cell(0,4))));


    }

    private void addSecondTitles(){
        for (int i = 0;i < 4;i++)
            addEtapasTitle();
    }

    private void  addEtapasTitle(){
        for (String etapa: etapas){
            table.addCell(addTitleStyle(getDefCell(etapa)));
        }
    }

    private ArrayList<String> etapas;

    private ArrayList<String> getEtapas(){
        ArrayList<String> etapas = new ArrayList<>();
            etapas.add("1ra");
            etapas.add("2da");
            etapas.add("3ra");
            etapas.add("4ta");

        return etapas;
    }

}

