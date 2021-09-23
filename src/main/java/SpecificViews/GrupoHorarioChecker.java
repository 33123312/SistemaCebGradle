package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.DataBaseUpdater;
import JDBCController.Table;

import java.sql.SQLException;
import java.util.ArrayList;

public class GrupoHorarioChecker {

    public String groupToCheck;
    private GrupoOperator grupoOp;

    public GrupoHorarioChecker(String groupToChck){
        grupoOp = new GrupoOperator(groupToChck);
    }

    private void checkMaterias(){

    }

    private void deleteIncompatibleMaterias(ArrayList<String> removeIncompatibleMaterias){
        ArrayList<String> cond = new ArrayList<>();
        ArrayList<String> val = new ArrayList<>();

        for (String materia:removeIncompatibleMaterias){
            cond.add("materia");
            val.add(materia);
        }

        try {
            new DataBaseUpdater("horarios").delete(cond,val);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private ArrayList<String> getIncompatibleHorarios(){
        ArrayList<String> horarioMateria = getHorarioMaterias();
        ArrayList<String> planMaterias = grupoOp.getMaterias().getColumn(0);
        ArrayList<String> incompatibleMaterias = new ArrayList<>();

        for (String hora:horarioMateria)
            if (!planMaterias.contains(hora))
                incompatibleMaterias.add(hora);

        return incompatibleMaterias;

    }

    private ArrayList<String> getHorarioMaterias(){
        DataBaseConsulter consulter = new DataBaseConsulter("horarios");

        String[] colsToBring = new String[]{"materia"};

        String[] cond = new String[]{"grupo"};

        String[] values = new String[]{groupToCheck};

        Table response = consulter.bringTable(colsToBring,cond,values);

        if (response.isEmpty())
            return new ArrayList<>();
        else
            return response.getColumn(0);

    }




}
