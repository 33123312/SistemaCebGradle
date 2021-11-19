package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;

public class TableOperator {
    protected ViewSpecs specs;
    protected String tableRegister;
    protected TableRegister tableInfo;
    protected DataBaseConsulter consulter;

    public TableOperator(String tableRegister,String table){
        specs = new ViewSpecs(table);
        consulter = new DataBaseConsulter(specs.getInfo().getVisibleView());
        this.tableRegister = tableRegister;
        setInfo();

    }


    public TableOperator(String tableRegister,String table,TableRegister tableInfo){
        specs = new ViewSpecs(table);
        consulter = new DataBaseConsulter(specs.getInfo().getVisibleView());
        this.tableRegister = tableRegister;
        this.tableInfo = tableInfo;

    }

    public String getTableRegister() {
        return tableRegister;
    }

    private void setInfo(){
        Table res = consulter.bringTable(
                new String[]{specs.getCol(specs.getPrimarykey())},
                new String[]{tableRegister});

        if(res.isEmpty())
            tableInfo = null;
        else
            tableInfo = res.getRegister(0);

    }

    public TableRegister getTableInfo() {
        return tableInfo;
    }

    public String getRegisterValue(String getCol){
        return tableInfo.get(getCol);
    }
}
