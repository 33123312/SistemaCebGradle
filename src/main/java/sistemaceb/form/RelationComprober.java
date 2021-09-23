package sistemaceb.form;

import JDBCController.Table;
import JDBCController.ViewSpecs;

import java.util.ArrayList;

public class RelationComprober {
    private CommonRegistersGetter comprober;
    private String tableToComprobe;
    private String tableToComprobekey;

    public RelationComprober(String registerGetter,String keyToComprobe,String tableToComprobe,String tableToComprobekey ) {

        this.tableToComprobe = tableToComprobe;
        this.tableToComprobekey = tableToComprobekey;

        ArrayList<String> tableToComprobeArray = new ArrayList<>();
            tableToComprobeArray.add(tableToComprobe);

        comprober = new CommonRegistersGetter(new ViewSpecs(registerGetter));
        comprober.setQuickOptionsGetters(tableToComprobeArray);
        comprober.useRegisterAsConditions(keyToComprobe);
    }



    public boolean canRelate(){
        Table possibleValues = comprober.getOptions(tableToComprobe);
        if(possibleValues.isEmpty())
            return false;
        else{
            ArrayList responseKeys = possibleValues.getColumn(possibleValues.getColumnCount()-1);
            return responseKeys.contains(tableToComprobekey);
        }

    }
}
