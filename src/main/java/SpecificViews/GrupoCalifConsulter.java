package SpecificViews;

import JDBCController.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class GrupoCalifConsulter extends TableViewerPDFOp{

    Table alumnos;

    ArrayList<String> evaluaciones;
    ArrayList<String> materiasKeys;
    ArrayList<String> materiasNams;

    GrupoOperator groupoOerator;

    public GrupoCalifConsulter(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Concentrado x Materia";
    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        groupoOerator = new GrupoOperator(keyValue);
        getMaterias();
        evaluaciones = getEvaluaciones();
        alumnos = groupoOerator.getAlumnos();
        addMateriaTitle();
        addDesplegable();
        setTableBuilder(0);

    }

    ConcetratedTableBuilder currentBUilder;

    private void setTableBuilder(int index){
        currentBUilder = new ConcetratedTableBuilder(
                getMateriaKey(index),
                getMateriaName(index),
                this);

    }

    @Override
    protected void buildPDF() {
        currentBUilder.generatePDF();
    }

    private void addMateriaTitle(){
        JLabel titleLabel = new JLabel("Materia:");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        thisWindow.addToHeader(titleLabel);
    }

    private void addDesplegable(){
        JComboBox desplegableMenu = new JComboBox();
            desplegableMenu.setPreferredSize(new Dimension(100,30));
        for (String materia: materiasNams)
            desplegableMenu.addItem(materia);
        desplegableMenu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    int selectedIndex = desplegableMenu.getSelectedIndex();
                    setTableBuilder(selectedIndex);
                }
            }
        });
        thisWindow.addToHeader(desplegableMenu);
    }

    private String getMateriaKey(int key){
        return materiasKeys.get(key);
    }

    private String getMateriaName(int key){
        return materiasNams.get(key);
    }


    private void getMaterias(){

        Table materias = groupoOerator.getMaterias();
        materiasKeys = materias.getColumn(0);
        materiasNams = materias.getColumn(1);

    }



    public ArrayList<String> getEvaluaciones() {

        return CalifasOperator.getEvaluaciones();
    }



}
