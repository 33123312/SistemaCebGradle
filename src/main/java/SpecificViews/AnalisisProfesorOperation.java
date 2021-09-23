package SpecificViews;

import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;

import java.util.ArrayList;

public class AnalisisProfesorOperation extends Operation{
    ProfesoresOperator operator;

    public AnalisisProfesorOperation(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Analisis Numérico";
    }

    @Override
    public void buildOperation() {
        operator = new ProfesoresOperator(keyValue);
        buildRegisters();

    }

    private void buildRegisters(){
        AnalisisProfesoresPdf pdf = new AnalisisProfesoresPdf();
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        Table grupos = operator.getGruposList();
        for(int i = 0;i < grupos.rowCount();i++){
            ArrayList<String> register = buildRegister(grupos.getRegister(i));
            if(register != null){
                registers.add(register);
                pdf.addRegister(register);
            }


        }
        pdf.addPromediosRegister(getPromediosRegister(registers));
        pdf.addTable();
        pdf.close();

    }

    private ArrayList<String> buildRegister(TableRegister AsignInfo){
        String materia = AsignInfo.get("materia");
        if(!ALumnoOperator.getMateriaType(materia).equals("Boleana")){
            String grupo = AsignInfo.get("grupo");
            String nombreMateria = AsignInfo.get("nombre_materia");
            GrupoOperator groupOp = new GrupoOperator(grupo);
            GrupoOperator.GrupoMateriaOperator materiaOp = groupOp.getGrupoMateriaOp(materia);

            ArrayList<String> register = new ArrayList<>();

            register.add(grupo);
            register.add(nombreMateria);
            register.add(Integer.toString(groupOp.numAlumnos));

            register.addAll(materiaOp.getPromedioUnidades());
            register.addAll(materiaOp.getPorcentajeReprobacionXUnidad());
            register.addAll(materiaOp.getSumatoriaFaltasXUnidad());
            register.addAll(materiaOp.getFaltasXAlumno());

            return register;

        }
        return null;

    }

    private ArrayList<String> getPromediosRegister(ArrayList<ArrayList<String>> registers){
        ArrayList<String> percentagesRow =new ArrayList<>();
        percentagesRow.add("Totales");
        int index = 2;
        percentagesRow.add(getColSum(registers,index));
        index++;

        for (int i = 0; i < 8;i++){
            percentagesRow.add(getColProm(registers,index));
            index++;
        }

         for (int i = 0; i < 4;i++){
             percentagesRow.add(getColProm(registers,index));
             index++;
         }

        for (int i = 0; i < 4;i++){
            percentagesRow.add(getColProm(registers,index));
            index++;
        }

        return percentagesRow;
    }

    private String getColSum(ArrayList<ArrayList<String>> registers,int index) {
        ArrayList<String> valuesToProm = new ArrayList<>();
        for (ArrayList<String> register:registers)
            valuesToProm.add(register.get(index));

        return PromsOperations.getSumaFaltas(valuesToProm);

    }

    private String getColProm(ArrayList<ArrayList<String>> registers,int index) {
        ArrayList<String> valuesToProm = new ArrayList<>();
        for (ArrayList<String> register:registers)
            valuesToProm.add(register.get(index));

        return PromsOperations.getProm(valuesToProm);

    }



}
