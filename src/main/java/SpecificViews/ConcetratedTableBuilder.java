package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import sistemaceb.form.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConcetratedTableBuilder {
    String materiaKey;
    String materiaName;
    GrupoCalifConsulter califasChoser;

    float[] sizes;
    Map<String,String> params;
    Table registers;

    public ConcetratedTableBuilder(String materiaKey,String materiaName,GrupoCalifConsulter califasChoser){
        this.materiaKey  = materiaKey;
        this.materiaName = materiaName;
        this.califasChoser = califasChoser;

        params = getParams();
        deploy();

    }

    private void deploy(){
        califasChoser.removeAllTables();
        buildRegisters();

    }



    private void buildRegisters(){
        String type = MateriaOperator.getMateriaType(materiaKey);

        if(type.equals("A/NA"))
            addBolTable();
        else if(type.equals("Numérica"))
            addNumTable();

    }


    private void addTable(ArrayList<String> titles,ArrayList<ArrayList<String>> registers,float[] sizes){
        this.sizes = sizes;
        this.registers = new Table(titles,registers);
        califasChoser.addNormalTable(materiaName +"",titles,registers);

    }

    public void addBolTable(){
        ArrayList<ALumnoOperator> alumnos = califasChoser.groupoOerator.getAlumnosOpUsBol();
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        for (ALumnoOperator alumno:alumnos)
            registers.add(buildAlumnoRowBol(alumno));
        addTable(getBolTitles(),registers,new float[]{1,1,1,1,1,1});

    }

    private ArrayList<String> buildAlumnoRowBol(ALumnoOperator operator){

        ArrayList<String>  register = new ArrayList<>();
            register.add(operator.getTableInfo().get("numero_control"));
            register.add(operator.getTableInfo().get("nombre_completo"));
        AluMateriaBolOperator op = new AluMateriaBolOperator(materiaKey, Global.conectionData.loadedPeriodo,operator.getTableInfo(),operator.boleta);
        ArrayList<String> evas = op.getParCalif();
        evas.add(op.getPromFinal());

        register.addAll(evas);

        return register;
    }

    public void addNumTable(){
        ArrayList<ALumnoOperator> alumnos = califasChoser.groupoOerator.getAlumnosOpUsNum();
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        for (ALumnoOperator alumno:alumnos)
            registers.add(buildAlumnoRowNUm(alumno));
        addTable(getNumTitles(),registers,new float[]{1,1,1,1,1,1,1,1});

    }

    private ArrayList<String> buildAlumnoRowNUm(ALumnoOperator operator){
        ArrayList<String> register = new ArrayList<>();
            register.add(operator.getTableInfo().get("numero_control"));
            register.add(operator.getTableInfo().get("nombre_completo"));
        AluMateriaNumOperator op = new AluMateriaNumOperator(materiaKey, Global.conectionData.loadedPeriodo,operator.getTableInfo(),operator.boleta);
        ArrayList<String> evas = op.getParCalif();
        evas.add(op.getParProm());
        evas.add(op.getCalifSemestral());
        evas.add(op.getPromFinal());


        register.addAll(evas);

        return register;
    }

    public void generatePDF(){
        new SimplePdfTableGenerator(registers,sizes,params);
    }

    private Map<String,String> getParams(){
        Map<String,String> params= new HashMap<>();
            params.put("Semestre",califasChoser.groupoOerator.getRegisterValue("semestre"));
            params.put("Materia",materiaName);

        return params;
    }



    private AluMateriaOperator getDefMateriaManager(ArrayList<String> alumnoInfo){
        String alumnoMatr = alumnoInfo.get(0);
        AluMateriaOperator materiaOperator = new ALumnoOperator(alumnoMatr).getMateriaState(materiaKey);
        ArrayList<String> evas = materiaOperator.getParCalif();
        alumnoInfo.addAll(evas);

        return  materiaOperator;
    }




    private ArrayList<String> getNumTitles(){
        ArrayList<String> titles = new ArrayList<>();
        titles.addAll(getHumnaanEvas());
        titles.add("Prom.");
        titles.add("Semestral");
        titles.add("Final");

        return titles;
    }

    private ArrayList<String> getBolTitles(){
        ArrayList<String> titles = new ArrayList<>();
        titles.addAll(getHumnaanEvas());

        titles.add("Final");

        return titles;
    }

    private ArrayList<String> getHumnaanEvas(){
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Maatrícula");
        titles.add("Nombre");
        titles.addAll(CalifasOperator.getEvaluaciones());
        return titles;
    }
}
