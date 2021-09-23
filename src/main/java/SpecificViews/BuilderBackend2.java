package SpecificViews;

import JDBCController.DBU;
import JDBCController.ViewSpecs;
import sistemaceb.FormResponseManager;
import sistemaceb.form.Formulario;

import java.util.ArrayList;
import java.util.Map;

public class BuilderBackend2 extends opBackend{

    private boolean row;
    private boolean title;

    public  String insertCol;
    public  String titleCol;
    public  String rowCol;

    public  String insertTable;
    public  String consultTable;

    public  String keyCol;
    public  String parentTable;

    public  ArrayList<String> conditions;
    public  ArrayList<String> values;



    public BuilderBackend2(String insertTable,
                           String titleCol,
                           String rowCol,
                           String insertCol,
                           ArrayList<String> titles,
                           String parentTable,
                           String keyColumn){
        super(titles,titleCol,rowCol,insertTable,keyColumn);

        this.insertCol = insertCol;
        this.titleCol = titleCol;
        this.rowCol = rowCol;

        this.insertTable = insertTable;
        this.consultTable = new ViewSpecs(insertTable).getInfo().getView();

        this.parentTable = parentTable;
        this.keyCol = keyColumn;

        row = false;
        title = false;

    }

    public void insertRow(){
        row = true;
    }

    public void insertTitle(){
        title = true;
    }


    public void setConditions(ArrayList<String> defaultCols) {
        super.setConditions(defaultCols);
        this.conditions = defaultCols;
    }

    public void setValues(ArrayList<String> defaultValues) {
        super.setValues(defaultValues);
        this.values = defaultValues;
    }

    public ArrayList<String> getTitles(){
        return new ArrayList<>(titles);
    }


    public  FormResponseManager getResponseManager(){
        return new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                String rowValue = ((MultipleFormsPanel.keyedHForm)form).getHora();
                Map<String,String> data = form.getData();
                    manageDatas(rowValue,data);
            }
        };
    }

    public void manageDatas(String rowValue, Map<String, String> calif){
        for(String title:titles){
            CellPropManager manager = getManager(title,rowValue);
            if(calif.containsKey(title))
                manager.insertValues(calif.get(title));
             else
                manager.setEmpty();

        }
    }

    private CellPropManager getManager(String title, String rowValue){
        CellPropManager manager = new CellPropManager(title,rowValue,this);
        if(this.title)
            manager.insertTitle();
        if (row)
            manager.insertRow();

        return manager;
    }


}


