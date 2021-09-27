package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class AluMateriaOperator{
    String materia;
    ArrayList<String> parcialesCal;
    String materiaType;
    String origin;
    ALumnoOperator aLumnoOperator;
    ArrayList<String> evuacionesPar;
    ArrayList<String> evas;
    String periodo;

    AluMateriaOperator(String materia,String type,String periodo,ALumnoOperator aLumnoOperator){
        this.periodo = periodo;
        evas = getEvaluaciones();
        this.aLumnoOperator = aLumnoOperator;
        this.materia = materia;
        evuacionesPar = getEvaluaciones();
        materiaType =  type;
        origin = getOrigin();
        parcialesCal = buildAlumnoParRow();

    };



    public boolean isBoolean(){
        return materiaType.equals("Boleana");
    }

    public String getEvaluacionCalif(String eva){
        return getValueFromEva(eva,parcialesCal);
    }

    public String getEvaluacionFaltas(String eva){
        return getValueFromEva(eva,getFaltas());
    }

    public String getValueFromEva(String eva,ArrayList<String> values){

        int evaIndex = evas.indexOf(eva);
        String value  = values.get(evaIndex);

        if(value == null)
            return "";
        return value;
    }

    public ArrayList<String> getEvaluaciones() {

        return CalifasOperator.getEvaluaciones();
    }

    public String getSumaFaltas(){

        return PromsOperations.getSumaFaltas(getFaltas());
    }

    private String getOrigin(){
        if(materiaType.equals("Boleana"))
            return "calificaciones_booleanas_view";
        else
            return  "calificaciones_numericas_view";

    }


    private ArrayList<String> buildAlumnoParRow(){
        return getParValue("calificacion");
    }

    public ArrayList<String> getFaltas(){
        return getParValue("faltas");
    }

    public ArrayList<String> getParValue(String dataToBring){
        ArrayList<String>  register = new ArrayList<>();
        for (String eva:evuacionesPar){
            String calificacionString = getCalificacionCell(eva,origin,dataToBring);
            register.add(calificacionString);
        }

        return register;
    }


    private String getCalificacionCell(String evaluacion,String table,String dataToBring){
        DataBaseConsulter consulter = new DataBaseConsulter(table);

        String[] colsToBring = new String[]{dataToBring};

        String[] cond = new String[]{
                "periodo",
                "semestre",
                "materia",
                "evaluacion",
                "clave_alumno"
        };

        String[] values = new String[]{
                periodo,
                aLumnoOperator.aluInfo.get("semestre"),
                materia,
                evaluacion,
                aLumnoOperator.aluMatr


        };

        String calif = consulter.bringTable(colsToBring,cond,values).getUniqueValue();

        if (calif == null)
            calif = "";


        return calif;
    }



    public abstract String getPromFinal();


    public ArrayList<String> getParCalif(){
        return new ArrayList<>(parcialesCal);
    }


}