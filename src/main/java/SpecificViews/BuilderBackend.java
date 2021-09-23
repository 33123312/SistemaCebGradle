package SpecificViews;

import JDBCController.*;
import sistemaceb.FormResponseManager;
import sistemaceb.form.Formulario;

import java.util.ArrayList;
import java.util.Map;

public class BuilderBackend extends opBackend{

    private boolean row;
    private boolean title;
    String colsultTable;


    public BuilderBackend(String insertTable,String titleCol,String rowCol,String keyColumn,ArrayList<String> titles){
        super(titles,titleCol,rowCol,insertTable,keyColumn);

        row = false;
        title = false;
        colsultTable = new ViewSpecs(objTable).getInfo().getVisibleView();

    }

    public void insertRow(){
        row = true;
    }

    public void insertTitle(){
        title = true;
    }

    public void setDefaultCols(ArrayList<String> defaultCols) {
        setConditions(defaultCols);
    }

    public void setDefaultValues(ArrayList<String> defaultValues) {
        setValues(defaultValues);
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
            CellManager manager = getManager(title,rowValue);
            if(calif.containsKey(title))
                manager.insertValues(calif.get(title));
             else
                manager.setEmpty();

        }
    }

    private CellManager getManager(String title, String rowValue){
        CellManager manager = new CellManager(title,rowValue,this);
        if(this.title)
            manager.insertTitle();
        if (row)
            manager.insertRow();

        return manager;

    }


}


