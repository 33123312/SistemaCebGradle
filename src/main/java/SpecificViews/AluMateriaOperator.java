package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public abstract class
AluMateriaOperator{
    String materia;
    ArrayList<String> parcialesCal;
    String materiaType;
    String origin;
    TableRegister aluInfo;
    ArrayList<String> evuacionesPar;
    String periodo;
    Table defData;

    AluMateriaOperator(String materia,String tipo,String periodo,TableRegister alumno,Table generalRes){

        this.periodo = periodo;
        this.aluInfo = alumno;
        this.materia = materia;
        getDefData(generalRes);
        evuacionesPar = getEvaluaciones();
        materiaType =  tipo;
        origin = getOrigin();
        parcialesCal = buildAlumnoParRow();

    };

    AluMateriaOperator(String materia,String tipo,String periodo,TableRegister alumno){
        this.periodo = periodo;
        this.aluInfo = alumno;
        this.materia = materia;
        evuacionesPar = getEvaluaciones();
        materiaType =  tipo;
        origin = getOrigin();
        parcialesCal = buildAlumnoParRow();

    };

    private void getDefData(Table generalRes){
        Table sub = generalRes.getSubTable(new String[]{"numero_control","materia"},new String[]{aluInfo.get("numero_control"),materia});

        if (sub.isEmpty())
            defData =  new Table(new ArrayList(), new ArrayList());
        else
            defData = sub;

    }


    public boolean isBoolean(){
        return materiaType.equals("A/NA");
    }

    public String getEvaluacionCalif(String eva){
        return getValueFromEva(eva,parcialesCal);
    }

    public String getEvaluacionFaltas(String eva){
        return getValueFromEva(eva,getFaltas());
    }

    public String getValueFromEva(String eva,ArrayList<String> values){

        int evaIndex = evuacionesPar.indexOf(eva);
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
        if(materiaType.equals("A/NA"))
            return "alumno_bol_califa_charge_view";
        else
            return  "alumno_num_califa_charge_view";

    }


    private ArrayList<String> buildAlumnoParRow(){
        return getParValue(getCalifas(),"calificacion");
    }

    public ArrayList<String> getFaltas(){
        return getParValue(getCalifas(),"faltas");
    }


    private Table getCalifas(){
        if (defData == null) {

            DataBaseConsulter consulter = new DataBaseConsulter(origin);

            Table califas = consulter.bringTable(new String[]{
                    "periodo",
                    "materia",
                    "numero_control",
            }, new String[]{
                    periodo,
                    materia,
                    aluInfo.get("numero_control")
            });

            return califas;
        } else return defData;
    }

    private ArrayList<String> getParValue(Table respon,String dataType){
        ArrayList<String>  register = new ArrayList<>();
        for (String eva:evuacionesPar){
            Table reg = respon.getSubTable(new String[]{"evaluacion"},new String[]{eva});
            if (reg.isEmpty())
                register.add("");
            else
                register.add(reg.getRegisterObject(0).get(dataType));
        }

        return register;
    }


    public abstract String getPromFinal();


    public ArrayList<String> getParCalif(){
        return new ArrayList<>(parcialesCal);
    }


}