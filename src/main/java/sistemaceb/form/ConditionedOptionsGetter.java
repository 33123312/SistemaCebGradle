/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.ViewSpecs;

import java.util.ArrayList;


/**
 *
 * @author escal
 */
public class ConditionedOptionsGetter extends CommonRegistersGetter{

    public ConditionedOptionsGetter(ViewSpecs specs){
        super(specs);
            setOptionsGetter(getColumnsOptionsGetters());
    }

    public ArrayList<ConditionedTable> getColumnsOptionsGetters(){
        ArrayList<ConditionedTable> conditionedTables = new ArrayList<>();

        ArrayList<String> tables = specs.getInfo().getForeignTables();
        ArrayList<String> cols  = specs.getForeignTags();

        int size = tables.size();
        for(int i = 0;i < size;i++)
            conditionedTables.add(new ConditionedTable(cols.get(i),tables.get(i),specs.getTable()));

        return conditionedTables;
    }
    
}
