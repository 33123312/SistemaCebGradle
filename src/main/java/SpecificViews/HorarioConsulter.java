package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.genericEvents;

import java.util.ArrayList;

public class HorarioConsulter extends Operation {
    String[] columnsToConsult;
    ViewSpecs horarioSpecs;
    ArrayList<String> conditionsValues;
    String[] conditionsHorario;

    public HorarioConsulter(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Consultar Horario";

    }

    @Override
    public void buildOperation() {
        //super.buildOperation();
        //.setTitle("Horario-" + viewSpecs.getInfo().getUnityName());
        if (hasHorario()){
            determinateSearchConditions();
            horarioSpecs = new ViewSpecs("horario");
            columnsToConsult = getColumnsToConsult();
            defineRegisters();
        } else {
            FormDialogMessage form = new FormDialogMessage(
                    "Horario no Asignaddo",
                    "Éste grupo no tiene un horario asignado");

            form.addAcceptButton();
            form.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    form.closeForm();
                }
            });

        }

        //buildHorarioTable();

    }

    private void determinateSearchConditions() {
        conditionsValues = new ArrayList<>();
            conditionsValues.add(keyValue);

        determinateSarchHorarioConditions();
    }

    private void determinateSarchHorarioConditions(){
        conditionsHorario = new String[]{
            "grupo",
            "hora",
            "dia"
        };
    }

    private String[] getColumnsToConsult() {
        String[] cols = new String[]{"nombre_abr","nombre_completo","aula"};
        return cols;
    }

    private boolean hasHorario(){
        DataBaseConsulter con = new DataBaseConsulter("horarios");
        return !con.bringTable(
                new String[]{"grupo"},
                new String[]{keyValue}
        ).isEmpty();

    }

    private ArrayList<ArrayList<String>> getCellDataList(String hora){
        ArrayList<ArrayList<String>> cellData = new ArrayList<>();
        ArrayList<String> horaCell = new ArrayList<>();
        horaCell.add(hora);
        cellData.add(horaCell);

        return cellData;
    }

    private ArrayList<ArrayList<String>> consultMaterias(String hora) {
        DataBaseConsulter con = new DataBaseConsulter("asignaturas_horario_view");
        ArrayList<String> materias = new ArrayList<>();
        ArrayList<ArrayList<String>> cellData = getCellDataList(hora);

        ArrayList<String> dias = getDias();

        for (String dia : dias) {
            ArrayList<String> dayConditionsValues = new ArrayList(conditionsValues);
                dayConditionsValues.add(hora);
                dayConditionsValues.add(dia);

            Table res = con.bringTable(
                columnsToConsult,
                conditionsHorario,
                dayConditionsValues.toArray(new String[dayConditionsValues.size()])
            );

            if (res.isEmpty())
                cellData.add(new ArrayList<>());
            else{
                ArrayList<String> cell = res. getRegister(0).
                        getValues();
                cellData.add(cell);
            }

        }

        return cellData;
    }


    private String getTurno() {
        DataBaseConsulter consulter = new DataBaseConsulter(viewSpecs.getTable());

        String[] colsBring = new String[]{"turno"};

        String[] cond = new String[]{"grupo"};

        String[] values = new String[]{keyValue};

        return consulter.bringTable(colsBring, cond, values).getUniqueValue();
    }

    private ArrayList<String> getHoras(){

        return CalifasOperator.getHorasClase(getTurno());
    }

    private ArrayList<String> getDias(){
        ArrayList<String> dias = new ArrayList<>();
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miércoles");
            dias.add("Jueves");
            dias.add("Viernes");

        return dias;
    }

    private ArrayList<ArrayList<String>> defineRegisters(){
        ArrayList<ArrayList<String>> register = new ArrayList<>();
        ArrayList<String> horas = getHoras();

        PdfHorarioGrupoBuilder pdf = new PdfHorarioGrupoBuilder();
        for(String hora: horas)
            pdf.addRowHora(consultMaterias(hora));
            //register.add(consultMaterias(hora));
        pdf.addTable();
        pdf.close();

        return register;
    }

    private void buildHorarioTable() {
        ArrayList<String> horas = getHoras();
        ArrayList<String> dias = getDias();

        ArrayList<ArrayList<String>> materias = defineRegisters();
        //addLateralTable("Horarios",dias,horas,materias);

    }

}
