package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
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

    private String getMateriaType(){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"tipo_calificacion"};

        String[] cond = new String[]{"clave_materia"};

        String[] val = new String[]{materiaKey};

        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();

    }

    private void buildRegisters(){
        String type = getMateriaType();

        if(type.equals("Boleana"))
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
        ArrayList<ArrayList<String>> alummnos = califasChoser.alumnos.getRgistersCopy();
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        for (ArrayList<String> alumno:alummnos)
            registers.add(buildAlumnoRowBol(alumno));
        addTable(getBolTitles(),registers,new float[]{1,1,1,1,1,1,1});

    }

    public void addNumTable(){
        ArrayList<ArrayList<String>> alummnos = califasChoser.alumnos.getRgistersCopy();
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        for (ArrayList<String> alumno:alummnos)
            registers.add(buildAlumnoRowNUm(alumno));
        addTable(getNumTitles(),registers,new float[]{1,1,1,1,1,1,1,1,1});
    }

    public void generatePDF(){
        new SimplePdfTableGenerator(registers,sizes,params);
    }

    private Map<String,String> getParams(){
        Map<String,String> params= new HashMap<>();
            params.put("Semestre",califasChoser.groupoOerator.getGrupoInfo().get("semestre"));
            params.put("Materia",materiaName);

        return params;
    }

    private ArrayList<String> buildAlumnoRowBol(ArrayList<String> alumnoInfo){

        ArrayList<String>  register = alumnoInfo;
        String alumnoMatr = alumnoInfo.get(0);
        ALumnoOperator operator = new ALumnoOperator(alumnoMatr);
        AluMateriaBolOperator op = (AluMateriaBolOperator)operator.getMateriaState(materiaKey);
        ArrayList<String> evas = op.getParCalif();
            evas.add(op.getPromFinal());

        register.addAll(evas);

        return register;
    }

    private AluMateriaOperator getDefMateriaManager(ArrayList<String> alumnoInfo){
        String alumnoMatr = alumnoInfo.get(0);
        AluMateriaOperator materiaOperator = new ALumnoOperator(alumnoMatr).getMateriaState(materiaKey);
        ArrayList<String> evas = materiaOperator.getParCalif();
        alumnoInfo.addAll(evas);

        return  materiaOperator;
    }

    private ArrayList<String> buildAlumnoRowNUm(ArrayList<String> alumnoInfo){
        AluMateriaNumOperator operator = (AluMateriaNumOperator)getDefMateriaManager(alumnoInfo);

        alumnoInfo.add(operator.getParProm());
        alumnoInfo.add(operator.getCalifSemestral());
        alumnoInfo.add(operator.getPromFinal());

        return alumnoInfo;
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
        titles.add("1ra.");
        titles.add("2da.");
        titles.add("3ra.");
        titles.add("4ta");

        return titles;
    }
}
