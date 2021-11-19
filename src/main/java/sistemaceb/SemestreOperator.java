package sistemaceb;

import JDBCController.DataBaseConsulter;
import SpecificViews.TableOperator;

import java.util.ArrayList;

public class SemestreOperator extends TableOperator {
    public SemestreOperator(String semestre) {
        super(semestre,"semestres");
    }

    public ArrayList<String> getGrupos() {
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] coltToB = new String[]{"grupo"};

        String[] cond = new String[]{"semestre"};

        String[] values = new String[]{tableRegister};

        return consulter.bringTable(coltToB,cond,values).getColumn(0);


    }



}
