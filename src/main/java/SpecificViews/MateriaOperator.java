package SpecificViews;

import JDBCController.DataBaseConsulter;

public  class MateriaOperator {

    public static String getMateriaType(String materiaKey){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"tipo_calificacion"};

        String[] cond = new String[]{"clave_materia"};

        String[] val = new String[]{materiaKey};

        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();

    }
}
