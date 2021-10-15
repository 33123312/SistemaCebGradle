package sistemaceb;

import JDBCController.ViewSpecs;

public abstract class KeyedTableBehavior {

    protected primaryKeyedTable table ;
    protected ViewSpecs viewSpecs;
    public KeyedTableBehavior(String view,primaryKeyedTable table){
        this.table = table;
        viewSpecs = new ViewSpecs(view);

    }


    public abstract String getInstructions();
}
