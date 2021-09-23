package sistemaceb;

import JDBCController.ViewSpecs;

public class KeyedTableBehavior {

    protected primaryKeyedTable table ;
    protected ViewSpecs viewSpecs;
    public KeyedTableBehavior(String view,primaryKeyedTable table){
        this.table = table;
        viewSpecs = new ViewSpecs(view);

    }
}
