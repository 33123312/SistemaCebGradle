package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.form.FormElement;
import sistemaceb.form.Formulario;
import sistemaceb.form.HorizontalFormPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleFormsPanel extends LinearVerticalLayout {

    protected ArrayList<keyedHForm> forms;
    private opBackend back;
    private ArrayList<String> titles;
    private Table rowsTitles;
    private ArrayList<String> visibleRowTitles;
    private FormElementBuilder builder;
    int maxtitleSize;
    public boolean isSeted;

    public MultipleFormsPanel(opBackend back, Table rowsTitles, FormElementBuilder builder){
        commonCons(back,rowsTitles);
        addBuilder(builder);
    }

    public MultipleFormsPanel(opBackend back, Table rowsTitles){
        commonCons(back,rowsTitles);
    }

    private void commonCons(opBackend back, Table rowsTitles){
        forms = new ArrayList<>();
        this.back = back;
        this.titles = back.getTitles();
        this.rowsTitles = rowsTitles;
        visibleRowTitles = rowsTitles.getColumn(1);
        setBorder(BorderFactory.createEmptyBorder(0,0,40,0));
    }

    public void addBuilder(FormElementBuilder builder){
        this.builder = builder;
        deploy();
    }

    public boolean containsElement(Component element){
        Component[] components = getComponents();

        for (Component component: components)
                if (component == element)
                    return true;

        return false;
    }

    public boolean isSeted(){
        return isSeted;
    }

    private void deploy(){
        ArrayList<String> lateralTitles = getLateraltrueTiles();
        for(String lateralTitle:lateralTitles)
            addForm(getNewForm(lateralTitle));
        isSeted = true;
    }

    public String getElementRowValue(FormElement element){
        int rowIndex = getRow(element);
        return rowsTitles.getColumn(0).get(rowIndex);
    }

    public String getElementColValue(FormElement element){

        return titles.get(element.getIndex());
    }

    private int getRow(FormElement element){
        for (keyedHForm form:forms){
            Component[] componnts = form.getElementsArea().getComponents();
            ArrayList<Component> componentsList = new ArrayList<>(Arrays.asList(componnts));
            if (componentsList.contains(element))
                return forms.indexOf(form);
        }

        return -23;
    }

    private int getColCount(){
        return forms.get(0).getElementsArea().getComponentCount();
    }

    private ArrayList<String> getLateraltrueTiles(){
        return rowsTitles.
                getColumn(0);
    }

    private keyedHForm getNewForm(String rowKey){
       keyedHForm newForm = new keyedHForm(rowKey);

        for(String title:titles){
           FormElement ele =  builder.buildElement(newForm,title,rowKey);
           String curentValue = trytoGetCurrentValue(title,rowKey);
           if(curentValue != null)
                ele.setResponse(curentValue);
        }

        newForm.addDataManager(back.getResponseManager());
        newForm.addTitle(visibleRowTitles.get(getCount()));

        return newForm;
    }

    private String trytoGetCurrentValue(String title, String row){
        DataBaseConsulter consulter = new DataBaseConsulter(new ViewSpecs(back.objTable).getInfo().getView());

        String[] colsToBring = new  String[]{back.keyCol};

        List<String> cond = new ArrayList<>();
            cond.add(back.titleCol);
            cond.add(back.rowCol);
            cond.addAll(back.getConditions());

        List<String> values = new ArrayList<>();
            values.add(title);
            values.add(row);
            values.addAll(back.getValues());
        return  consulter.bringTable(colsToBring,cond.toArray(new String[cond.size()]),values.toArray(new String[values.size()])).getUniqueValue();

    };

    public interface FormElementBuilder{
         FormElement buildElement(Formulario form, String title, String rowKey);
    }

    public boolean checkErrors(){
        boolean error = false;
        for (keyedHForm form:forms)
            if(form.hasErrors())
                error =  true;

        return error;
    }

    public void submit(){

        for (keyedHForm form:forms)
            form.manageData();

    };

    protected void addForm(keyedHForm newForm){
        setVisible(false);
        addElement(newForm);
        checkBiggerWidth(newForm);
        newForm.getLateral().setPreferredSize(new Dimension(maxtitleSize + 10,25));
        setVisible(true);
        forms.add(newForm);

    }

    private void checkBiggerWidth(keyedHForm newForm){
        int currenWidth = newForm.getLateral().getPreferredSize().width;
        if (maxtitleSize < currenWidth){
            maxtitleSize = currenWidth;
            setLateralSizes();
        }

    }

    private void setLateralSizes(){
        for (HorizontalFormPanel form: forms){
            form.getLateral().setPreferredSize(new Dimension(maxtitleSize + 10,25));
        }
    }

    public class keyedHForm extends HorizontalFormPanel {
        private String hora;
        public keyedHForm(String hora){
            super();
            this.hora = hora;
        }

        public String getHora(){
            return hora;
        }


    }}
