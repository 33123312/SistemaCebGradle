package SpecificViews;

import JDBCController.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoletaConsulter extends TableViewerPDFOp{
    private ArrayList<String> evaluaciones;
    private ALumnoOperator aluOperator;

    public BoletaConsulter(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Consulta de calificaciones";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        evaluaciones = getEvaluaciones();
        aluOperator = new ALumnoOperator(keyValue);
        deploy();

    }

    private ArrayList<String> getEvaluaciones(){

        return CalifasOperator.getEvaluaciones();
    }

    private void deploy(){
        addTable("Numérica","Calificaciones Numéricas","calificacion");
        addTable("Numérica","Faltas","faltas");

        addTable("A/NA","Calificaciones Boleanas","calificacion");
        addTable("A/NA","Faltas","faltas");

    }

    protected void buildPDF(){
        Table materias = aluOperator.grupoOperator.getMaterias();
        ArrayList<ArrayList<String>> registers = materias.getRegisters();

        BoletaPDFBuilder pdf = new BoletaPDFBuilder(getEvaluaciones());
            pdf.setTable();

        for (ArrayList<String> register: registers)
            addRegister(register.get(1),register.get(0),pdf);

        addProms(pdf);
        pdf.addParams(getParams());
        pdf.addTable();
        pdf.addFirmaProfesor();
        pdf.close();

    }

    private void addProms(BoletaPDFBuilder pdf){
        ArrayList<String> proms = new ArrayList<>();
            proms.add("Promedios");

        ALumnoOperator.TodasMateriasOps op = aluOperator.getMateriaOperators();

        ArrayList<String> califas = op.getPromUnidad();
        ArrayList<String> faltas = op.getSumatoriaFaltas();

        for (int i = 0; i < califas.size();i++){
            proms.add(califas.get(i));
            proms.add(faltas.get(i));
        }

        proms.add(op.getPromUnidades());
        proms.add(op.getPromSemestrales());
        proms.add(op.getPromFinal());
        proms.add(op.getSumFinalFaltas());

       pdf.addPromediosRegister(proms);

    }

    private void addRegister(String materiaName,String materiaKey,BoletaPDFBuilder pdf){
        ArrayList<String> newRegister = new ArrayList<>();
        newRegister.add(materiaName);
        AluMateriaOperator materiaOerator = aluOperator.getMateriaState(materiaKey);

        ArrayList<String> califas = materiaOerator.getParCalif();
        ArrayList<String> faltas = materiaOerator.getFaltas();

        int size = califas.size();
        for(int i = 0; i < size;i++){
            newRegister.add(califas.get(i));
            newRegister.add(faltas.get(i));
        }

        if(materiaOerator.isBoolean()){
            newRegister.add("");
            newRegister.add("");
        } else{
            AluMateriaNumOperator numOperator = (AluMateriaNumOperator)materiaOerator;
            newRegister.add(numOperator.getParProm());
            newRegister.add(numOperator.getCalifSemestral());
        }

        newRegister.add(materiaOerator.getPromFinal());
        newRegister.add(materiaOerator.getSumaFaltas());
        pdf.addCalifasReg(newRegister);

    }

    private Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();
            params.put("Alumno",aluOperator.getRegisterValue("nombre_completo"));
            params.put("Semestre",aluOperator.getRegisterValue("semestre"));
        return params;
    }

    private void addTable(String tipo,String title,String dato){
        Table materias = aluOperator.grupoOperator.getMateriasList(tipo);

        ArrayList<String> eval = new ArrayList<>(evaluaciones);
        ArrayList<String> lateral = materias.getColumn(1);

        ArrayList<ArrayList<String>> registers;

        if(dato.equals("faltas"))
            registers = getFaltasRegisters(tipo);
        else
            registers= getRegisters(tipo);

        if(tipo.equals("Numérica") && dato.equals("calificacion"))
            getCalifOperations(lateral,eval,registers);

        addLateralTable(
                title,
                eval,
                lateral,
                registers
        );
    }

    private void getCalifOperations(ArrayList<String> lateral,ArrayList<String> titles,ArrayList<ArrayList<String>> registers){
        titles.add("Promedio en parciales");
        titles.add("Semestral");
        titles.add("Promedio en Materia");
        lateral.add("Promedio en Unidad");
        registers.add(getMateriasPromedios(registers));

    }

    private ArrayList<String> getMateriasPromedios(ArrayList<ArrayList<String>> registers){
        ArrayList<String> promedios = new ArrayList();
        ALumnoOperator.TodasMateriasOps materiasOperator =  aluOperator.getMateriaOperators();

        promedios.addAll(materiasOperator.getPromUnidad());
        promedios.add(materiasOperator.getPromUnidades());
        promedios.add(materiasOperator.getPromSemestrales());
        promedios.add(materiasOperator.getPromFinal());

        return promedios;
    }

    private ArrayList<ArrayList<String>> getFaltasRegisters(String type) {
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        ArrayList<String> materias = aluOperator.grupoOperator.getMateriasList(type).getColumn(0);

        for (String materia : materias) {
            AluMateriaOperator operator = aluOperator.getMateriaState(materia);
            ArrayList<String> currentRow = operator.getFaltas();
            registers.add(currentRow);

        }

        return registers;
    }

    private ArrayList<ArrayList<String>> getRegisters(String type) {
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        ArrayList<String> materias = aluOperator.grupoOperator.getMateriasList(type).getColumn(0);

        if (isBol(type))
            for (String materia : materias) {
                AluMateriaOperator operator = aluOperator.getMateriaState(materia);
                ArrayList<String> currentRow = operator.getParCalif();
                getBolCols(operator, currentRow);
                registers.add(currentRow);

            }
        else
            for (String materia : materias) {
                AluMateriaOperator operator = aluOperator.getMateriaState(materia);
                ArrayList<String> currentRow = operator.getParCalif();
                getNumCols(operator, currentRow);
                registers.add(currentRow);

            }

        return registers;
    }

    private boolean isBol(String type){
        return type.equals("A/NA");
    }

    private void getBolCols(AluMateriaOperator operator,ArrayList<String> register){
        AluMateriaBolOperator  bolOperator= (AluMateriaBolOperator)operator;
        register.add(bolOperator.getPromFinal());
    }

    private void getNumCols(AluMateriaOperator operator,ArrayList<String> register){
        AluMateriaNumOperator numOperator = (AluMateriaNumOperator)operator;
            register.add(numOperator.getParProm());
            register.add(numOperator.getCalifSemestral());
            register.add(numOperator.getPromFinal());
    }

}
