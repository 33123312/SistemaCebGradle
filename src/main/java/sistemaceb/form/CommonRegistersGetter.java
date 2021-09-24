package sistemaceb.form;

import JDBCController.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonRegistersGetter implements optionsGetter {


    public ViewSpecs specs;
    public ArrayList<ConditionedTable> conditionTags;
    public ArrayList<ConditionedTable> optionsGetters;

    public CommonRegistersGetter(ViewSpecs specs){
        this.specs = specs;

    }

    public void setOptionsGetter(ArrayList<ConditionedTable> optionsGetters){
        this.optionsGetters = optionsGetters;
        setConditionsLists();

    }

    public void setQuickOptionsGetters(ArrayList<String> tablesToRelate) {
        ArrayList<ConditionedTable> tables = new ArrayList<>();
        for (String table : tablesToRelate)
            tables.add(new ConditionedTable(table, table));

        setOptionsGetter(tables);
    }

    private TableRegister getRegister(String primaryKey){
        DataBaseConsulter consulter = new DataBaseConsulter(specs.getTable());

        String[] conditions = new String[]{specs.getCol(specs.getPrimarykey())};

        String[] values = new String[]{primaryKey};

        Table registers = consulter.bringTable(conditions,values);
        registers.setColumnTitles(specs.getTag(registers.getColumnTitles()));

        TableRegister register= registers.getRegisterObject(0);

        return register;
    }

    public void useRegisterAsConditions(String primaryKey){
        TableRegister register = getRegister(primaryKey);
            for(ConditionedTable tableData: conditionTags)
                tableData.value = register.get(tableData.getName());
    }


    private ArrayList<ConditionedTable> detNativeForTags(){
        ArrayList<ConditionedTable> forTags = new ArrayList();

        ArrayList<String> tables = specs.getInfo().getForeignTables();
        ArrayList<String> tags = specs.getForeignCols();

        for(int i = 0;i < tables.size();i++){
            ConditionedTable tableComprober = getTableFfromTable(tables.get(i));
            if (tableComprober == null)
                forTags.add(new ConditionedTable(tags.get(i), tables.get(i)));
            else
                forTags.add(tableComprober);
        }
        return forTags;
    }

    public ConditionedTable getCondition(String tag){

        for(ConditionedTable table:conditionTags)
            if(table.getName().equals(tag))
                return table;

        return null;

    }

    public ConditionedTable getTable(String tag){
        for(ConditionedTable table:optionsGetters){
            if(table.isTag(tag))
                return table;
        }
        return null;
    }

    public ConditionedTable getTableFfromTable(String tableS){
        for(ConditionedTable table:optionsGetters){
            if(table.getTable().equals(tableS))
                return table;
        }
        return null;
    }

    private void setConditionsLists(){
        conditionTags = new ArrayList<>();
        ArrayList<ConditionedTable> tables = detNativeForTags();

        for(ConditionedTable table:optionsGetters){
            table.determinateViewRelations(tables);
            if(table.specs.hasOptions()){
                addConditions(table.getConditionals());
            }
        }
    }

    private void addConditions(ArrayList<ConditionedTable> conditionTables){
        for(ConditionedTable table:conditionTables)
            addCondition(table);
    }


    public void addCondition(ConditionedTable table){
        if(!conditionTags.contains(table))
            conditionTags.add(table);
    }



    public void setCondition(String tag,String value){

        getCondition(tag).value = value;
    }

    @Override
    public Table getOptions(String tag){

        if (hasOptions(tag))
            return getTable(tag).getOptions();

        return new Table(new ArrayList<>(),new ArrayList<>());
    }

    @Override
    public boolean hasOptions(String tag){

        ConditionedTable table = getTable(tag);

        return table != null;

    }

    public Map<String,ArrayList<String>> getParentalRelations(){
        Map<String,ArrayList<String>> parentalRelations = new HashMap();

        for(ConditionedTable table:optionsGetters){
            ArrayList<ConditionedTable> currentTableConditions = table.getConditionals();

            for(ConditionedTable condition:currentTableConditions){
                String conditionTag= condition.getName();

                if(!parentalRelations.containsKey(conditionTag))
                    parentalRelations.put(conditionTag,new ArrayList());

                String currentConditionalTag = table.getName();;
                parentalRelations.get(conditionTag).add(currentConditionalTag);
            }
        }

        return parentalRelations;
    }


}
