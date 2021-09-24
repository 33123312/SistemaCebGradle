package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import java.util.ArrayList;

public class HorarioConsulter extends TableViewerOperation {
    ArrayList<String> columnsToConsult;
    ViewSpecs horarioSpecs;

    ArrayList<String> conditions = new ArrayList<>();
    ArrayList<String> conditionsValues = new ArrayList<>();
    ArrayList<String> conditionsHorario = new ArrayList<>();

    public HorarioConsulter(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Consultar Horario";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        thisWindow.setTitle("Horario-" + viewSpecs.getInfo().getUnityName());
        determinateSearchConditions(keyValue);
        horarioSpecs = new ViewSpecs("horario");
        columnsToConsult = getColumnsToConsult();
        buildAsignaturasTable();
        buildHorarioTable();

    }


    private void determinateSearchConditions(String value) {
        conditions.add("grupo");
        conditionsValues.add(value);

        determinateSarchHorarioConditions();
    }

    private void determinateSarchHorarioConditions() {

        conditionsHorario = new ArrayList<>(conditions);
        conditionsHorario.add("hora");
        conditionsHorario.add("dia");
    }

    private ArrayList<String> getColumnsToConsult() {
        ArrayList<String> aux = horarioSpecs.getViewTags();
        aux.add("nombre_materia");

        return aux;
    }

    private ArrayList<String> consultMaterias(String hora) {
        DataBaseConsulter con = new DataBaseConsulter("horarios_view");

        ArrayList<String> materias = new ArrayList<>();

        ArrayList<String> dias = getDias();

        java.util.List<String> bring = columnsToConsult;
        java.util.List<String> cond = conditionsHorario;


        for (String dia : dias) {

            java.util.List<String> dayConditionsValues = new ArrayList(conditionsValues);
                dayConditionsValues.add(hora);
                dayConditionsValues.add(dia);

            String value =
                    con.bringTable(
                            bring.toArray(new String[bring.size()]),
                            cond.toArray(new String[cond.size()]),
                            dayConditionsValues.toArray(new String[dayConditionsValues.size()])).

                            getUniqueValue();

            if (value == null)
                materias.add("");
            else
                materias.add(value);
        }
        return materias;
    }

    private String getTurno() {
        DataBaseConsulter consulter = new DataBaseConsulter(viewSpecs.getTable());

        String[] colsBring = new String[]{"turno"};

        String[] cond = new String[]{"grupo"};

        String[] values = new String[]{keyValue};

        return consulter.bringTable(colsBring, cond, values).getUniqueValue();
    }

    private ArrayList getHoras(){
        DataBaseConsulter cons = new DataBaseConsulter("horas_clase");

        String[] conditions = new String[]{"turno"};
        String[] values = new String[]{getTurno()};

        return cons.bringTable(conditions,values).getColumn(0);
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

        for(String hora: horas)
            register.add(consultMaterias(hora));


        return register;
    }

    private void buildHorarioTable() {
        ArrayList<String> horas = getHoras();
        ArrayList<String> dias = getDias();

        ArrayList<ArrayList<String>> materias = defineRegisters();
        addLateralTable("Horarios",dias,horas,materias);

    }

    private void buildAsignaturasTable() {
        Table asignaturasTable = getAsignturasRegisters();
        ArrayList<String> visibleColumnTitles = new ArrayList<>();

            visibleColumnTitles.add("Materia");
            visibleColumnTitles.add("Nombre Completo");
            visibleColumnTitles.add("Aula");

        addNormalTable("Asignaturas",visibleColumnTitles,asignaturasTable.getRegisters());

    }

    private Table getAsignturasRegisters(){
        DataBaseConsulter con = new DataBaseConsulter("asignaturas_visible_view");
        ViewSpecs specs = new ViewSpecs("asignaturas_visible_view");

        java.util.List<String> columnsToConsult = specs.getInfo().getTableCols();
            columnsToConsult.remove("grupo");
            columnsToConsult.remove("materia");
            columnsToConsult.remove("profesor");

        java.util.List<String> cond = conditions;

        java.util.List<String> values = conditionsValues;

        Table response = con.bringTable(columnsToConsult.toArray(
                new String[columnsToConsult.size()])
                ,cond.toArray(new String[cond.size()])
                ,values.toArray(new String[values.size()]));
            response.setColumnTitles(specs.getTag(response.getColumnTitles()));

        return response;
    }
}
