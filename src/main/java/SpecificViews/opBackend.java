package SpecificViews;

import sistemaceb.FormResponseManager;

import java.util.ArrayList;

public abstract class opBackend {

    private ArrayList<String> conditions;
    private ArrayList<String> values;
    protected ArrayList<String> titles;
    public String titleCol;
    public String rowCol;
    public String objTable;
    public String keyCol;

    public opBackend(ArrayList<String> titles,String titleCol,String rowCol,String objTable,String keyCol){
        this.titles = titles;
        this.titleCol = titleCol;
        this.rowCol = rowCol;
        this.objTable = objTable;
        this.keyCol = keyCol;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public abstract FormResponseManager getResponseManager();
    public ArrayList<String> getTitles(){
        return titles;
    };

}
