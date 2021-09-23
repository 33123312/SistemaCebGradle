package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.DataBaseResManager;
import JDBCController.Table;

import java.util.ArrayList;

public class SemestreOperator {
    String semestre;

    public SemestreOperator(String semestre){
        this.semestre = semestre;
    }

    public ArrayList<String> getGrupos() {
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] cond = new String[]{"semestre"};

        String[] values = new String[]{semestre};

        return consulter.bringTable(cond,values).getColumn(0);


    }



}
