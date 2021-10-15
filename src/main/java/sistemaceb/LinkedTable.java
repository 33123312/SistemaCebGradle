/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.*;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *n
 * @author escal
 */
public class LinkedTable extends KeyedTableBehavior {
    String primarykey;

    public LinkedTable(String view,primaryKeyedTable table){
        super(view,table);
        setDefultPrimaryKey();
        setReferences(table);

    }

    @Override
    public String getInstructions() {
        return "Da click en un registro para conocer los detalles del mismo";
    }

    public LinkedTable(String originalTable, String mainKey,primaryKeyedTable table ){
        super(getObjectiveTable(new ViewSpecs(originalTable),mainKey),table);
        primarykey = mainKey;
        setReferences(table);

    }

    private static String getObjectiveTable(ViewSpecs viewSpecs,String primaryKey){
        ArrayList<String> foreignCols =  viewSpecs.getForeignTags();
        ArrayList<String> tables = viewSpecs.getInfo().getForeignTables();

        int index = foreignCols.indexOf(primaryKey);
        String objectiveTable = tables.get(index);

        return objectiveTable;
    }

    private void setDefultPrimaryKey(){
        primarykey = viewSpecs.getPrimarykey();

    }

    protected String getPrimaryKey(int key){
        String keyValue = table.currentRegisters.getRegister(key).get(primarykey);
        return keyValue;
    }

    protected void setReferences(primaryKeyedTable table) {
        table.addRowEvents(new primaryKeyedTable.clickedRowEvent(){
            @Override
            public void onClick(int key) {
                Global.view.currentWindow.newView(
                    new RegisterDetailView(
                            viewSpecs.getTable(),getPrimaryKey(key)));
            }
        });
    }

}
