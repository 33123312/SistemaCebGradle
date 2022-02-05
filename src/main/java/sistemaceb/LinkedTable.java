/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.ViewSpecs;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.TableRow;
import sistemaceb.form.Global;

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

    public LinkedTable(String originalTable, String mainKey,primaryKeyedTable table ){
        super(getObjectiveTable(new ViewSpecs(originalTable),mainKey),table);
        primarykey = mainKey;
        setReferences(table);

    }


    @Override
    public String getInstructions() {
        return "Da click en un registro para conocer los detalles del mismo";
    }


    protected static String getObjectiveTable(ViewSpecs viewSpecs,String primaryKey){
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
        System.out.println(viewSpecs.getTable());
        System.out.println(primarykey);
        System.out.println(table.currentRegisters.getColumnTitles());

        String keyValue = table.currentRegisters.getRegister(key).get(primarykey);
        return keyValue;
    }

    protected void setReferences(primaryKeyedTable table) {
        table.getFactory().addLeftClickEvnt(new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow row) {

                Global.view.currentWindow.newView(
                    new RegisterDetailView(
                        viewSpecs.getTable(),
                        getPrimaryKey(row.getKey())
                    )
                );
            }
        });
        table.showAll();

    }

}
