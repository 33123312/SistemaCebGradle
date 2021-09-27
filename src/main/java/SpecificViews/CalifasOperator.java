package SpecificViews;

import JDBCController.DataBaseConsulter;

import java.util.ArrayList;

public class CalifasOperator {

    private static ArrayList<String> diasClase;
    private static ArrayList<String> evaluaciones;

    public CalifasOperator(){

    }

    public static ArrayList<String> getHorasClase(){

        return new DataBaseConsulter("horas_clase")
                .bringTable().getColumn(0);
    }

    public static ArrayList<String> getHorasClase(String turno){

        return new DataBaseConsulter("horas_clase")
                .bringTable(
                        new String[]{"turno"},
                        new String[]{turno}).
                        getColumn(0);
    }

    public static ArrayList<String> getDiasClase(){
        if (diasClase == null)
            diasClase = new DataBaseConsulter("dias_clase").bringTable().getColumn(0);

        return diasClase;

    }

    public static ArrayList<String> getEvaluaciones(){
        if(evaluaciones == null)
            evaluaciones = new DataBaseConsulter("evaluaciones").bringTable().getColumn(0);

        return evaluaciones;

    }

}
