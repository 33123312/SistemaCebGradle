package sistemaceb;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import SpecificViews.GrupoOperator;
import sistemaceb.form.Global;

public class CalifasGroupGetter {
    GrupoOperator op;
    public CalifasGroupGetter(String grupo){
        op = new GrupoOperator(grupo);
    }



    private Table getboleta(String tipo,String[] colsToBring,String[] cond ,String[] val){
        String view;

        if(tipo.equals("Numérica"))
            view = "alumno_num_califa_charge_view";
        else if (tipo.equals("A/NA"))
            view = "alumno_bol_califa_charge_view";
        else
            view = "alumno_sem_califa_charge_view";

        DataBaseConsulter consulter = new DataBaseConsulter(view);

        return consulter.bringTable(colsToBring,cond,val);

    }


}
