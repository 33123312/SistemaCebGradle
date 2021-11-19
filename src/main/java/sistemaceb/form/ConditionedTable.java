/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author escal
 */
public class
ConditionedTable {
    
    private  Map<String,String> conditionsTranslations;
    private  ArrayList<ConditionedTable> conditions;
    public  ViewSpecs specs;
    public String objectiveTable;

    public  String value;
    private String name;
    private String table;

    private ViewSpecs parentSpecs;

    public ConditionedTable(String name,String thisView){
        build(name,thisView);
        objectiveTable = table;
    }

    public ConditionedTable(String name,String thisView,String parent){
        parentSpecs = new ViewSpecs(parent);
        objectiveTable = determinateObjective(parent,parentSpecs.getCol(name));
        build(name,thisView);
    }

    private String determinateObjective(String parent, String column){
        ViewSpecs parentSpecs = new ViewSpecs(parent);
        String childTable = parentSpecs.getTableFromTColumn(column);
        ViewSpecs childSpecs = new ViewSpecs(childTable);
        if(childSpecs.getPrimaryskey().size() == 2){
            String foreignColumn = parentSpecs.getForeignColumnFromColumn(column);
            return determinateObjective(childTable,foreignColumn);

        } else
            return childTable;
    }

    private void build(String name,String thisView){
        specs = new ViewSpecs(thisView);
        conditions = new ArrayList();
        conditionsTranslations = new HashMap();

        this.name = name;
        table = thisView;
        value = "";
    }

    public String getTable() {
        return table;
    }

    public String getName(){
        
        return name;
    }
    
    public String getView(){
        return specs.getInfo().getView();
    }

    public boolean isTag(String tag){

        return name.equals(tag);
    }
    
    public Table getOptions(){
        if (conditionsAreSetted())
            return consultOptions();
        else
            return new Table(new ArrayList<>(), new ArrayList());

    }

    private boolean conditionsAreSetted(){
        if(conditions.isEmpty())
            return true;
        else {
            for (ConditionedTable condition : conditions) {
                if (!condition.value.equals(""))
                    return true;
            }
            return false;
        }

    }
    
    private Table consultOptions(){
        if(specs.getPrimaryskey().size() == 2)
            return getIntermediumOptions();
        else
            return  getNormalTableOptions();
        
    }

    private void getTrueConditions(List<String> bringConditions,List<String> conditionsValues){

        for (Map.Entry<String,String> condition:conditionsTranslations.entrySet()){
            for(ConditionedTable table :conditions)

                if(table.getTable().equals(condition.getValue()) && !table.value.equals("")){
                    bringConditions.add(condition.getKey());
                    conditionsValues.add(table.value);
                }
        }
    }

    private Table getDefaultOptions(String[] columnsToBring){
        DataBaseConsulter optionsConsulter = new  DataBaseConsulter(specs.getInfo().getVisibleView());

        List<String> cond = new ArrayList();
        List<String> conditionsValues = new ArrayList();

        getTrueConditions(cond,conditionsValues);

        Table options =
                optionsConsulter.bringTable(
                        columnsToBring,
                        cond.toArray(new String[cond.size()]),
                        conditionsValues.toArray(new String[conditionsValues.size()]));
        return options;
    }

    private Table getNormalTableOptions(){
        ArrayList<String> colsToBring = new ArrayList();
        String viewHumanKey = specs.getInfo().getHumanKey();
        String viewPrimaryKey = specs.getPrimarykey();

        colsToBring.add(viewPrimaryKey);

        if(specs.hasHumanKey())
            if(!specs.getInfo().getHumanKey().equals(viewPrimaryKey))
                colsToBring.add(0,viewHumanKey);

        List<String> bring = specs.getCol(colsToBring);

        return getDefaultOptions(bring.toArray(new String[bring.size()]));


    }

    private Table getIntermediumOptions(){
        String[] colsToBring = new String[]{getThisIntermediumKey()};

        Table defaultResponse = getDefaultOptions(colsToBring);

        return getHumansKeyFromObjective(defaultResponse);
    }


    private Table getHumansKeyFromObjective(Table keys){
        ViewSpecs objectiveSpecs = new ViewSpecs(objectiveTable);
        if(objectiveSpecs.hasHumanKey()){

            DataBaseConsulter consulter = new DataBaseConsulter(objectiveSpecs.getInfo().getVisibleView());

            String humanKey = objectiveSpecs.getCol(objectiveSpecs.getInfo().getHumanKey());
            String[] columnsToBring = new String[]{humanKey};

            String primaryKey = objectiveSpecs.getCol(objectiveSpecs.getPrimarykey());
            String[] conditions = new String[]{primaryKey};

            ArrayList<ArrayList<String>> registers = keys.getRegisters();
            ArrayList<String> primaryKeys = keys.getColumn(0);

            for(int i = 0; i < primaryKeys.size();i++){
                ArrayList<String> currentRow = registers.get(i);
                String key = primaryKeys.get(i);
                String[] value = new String[]{key};
                String human = consulter.bringTable(columnsToBring,conditions,value).getUniqueValue();
                currentRow.add(0,human);
            }


            ArrayList<String> columns  = keys.getColumnTitles();

            columns.add(0,objectiveSpecs.getInfo().getHumanKey());

            keys.setColumnTitles(columns);
            keys.setRegisters(registers);

        }

        return keys;

    }

    private String getThisIntermediumKey(){
        String nameAsColumn = parentSpecs.getCol(name);
        String thisColumn = parentSpecs.getForeignColumnFromColumn(nameAsColumn);

        return  thisColumn;
    }

    public String getObjectiveTable() {

        return objectiveTable;
    }

    public void determinateViewRelations(ArrayList<ConditionedTable> getters){

        ArrayList<String> thisForeignViewsNameCol = specs.getInfo().getForeignRawCols();
        ArrayList<String> thisForeignViewsName = specs.getTag(thisForeignViewsNameCol);


        for(ConditionedTable viewToEvaluate:getters){
            int arraySize = thisForeignViewsName.size();
            for(int i = 0; i < arraySize; i++) {
                String columnToCompare = thisForeignViewsName.get(i);
                String nativeViewToCompare = determinateObjective(table, thisForeignViewsNameCol.get(i));

                if (!nativeViewToCompare.equals(objectiveTable) &&
                    viewToEvaluate.getObjectiveTable().equals(nativeViewToCompare)) {

                    conditions.add(viewToEvaluate);
                    conditionsTranslations.put(columnToCompare, nativeViewToCompare);

                    thisForeignViewsName.remove(i);
                    thisForeignViewsNameCol.remove(i);
                    arraySize--;
                    i--;
                }
            }
            }
    }
    
    public ArrayList<ConditionedTable> getConditionals(){
        return new ArrayList(conditions);
    }
}
