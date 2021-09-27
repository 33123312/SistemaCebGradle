package SpecificViews;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.RegisterDetailView;
import sistemaceb.form.*;
import sistemaceb.genericEvents;

import javax.swing.*;
import javax.swing.text.html.FormSubmitEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HorarioBuilder extends MultipleFormsOperation{
    private ArrayList<String> dias;


    public HorarioBuilder(OperationInfoPanel infoPanlel){
        super(infoPanlel);
        operation = "Modificar Horario";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        thisWindow.setTitle("Modificar Horario");
        dias = getDias();
        if(hasPlan())
            if(hasAllAsignaturas() )
                addFormArea();

    }

    private boolean hasAllAsignaturas(){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas");

        Table materiasTtable = new GrupoOperator(keyValue).getMaterias();

        ArrayList<String> materias = materiasTtable.getColumn(0);
        ArrayList<String> materiasNombres = materiasTtable.getColumn(0);

        ArrayList<String> misingMaterias = new ArrayList<>();

        for (String materia: materias){


            Table response  = consulter.bringTable(new String[]{"grupo","materia"},new String[]{keyValue,materia});

            if (response.isEmpty())
               misingMaterias.add(materiasNombres.get(materias.indexOf(materia)));

        }
        if(misingMaterias.isEmpty())
            return true;
        else {
            FormDialogMessage form = new FormDialogMessage("Error","Las materias " + misingMaterias + " no tienen un profasor asignado.");
                    form.addOnAcceptEvent(new genericEvents() {
                        @Override
                        public void genericEvent() {
                            form.closeForm();
                        }
                    });
            return false;
        }
    }

    public ArrayList<String> getDias() {
        ArrayList<String> dias = new ArrayList<>();
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miércoles");
            dias.add("Jueves");
            dias.add("Viernes");

        return dias;
    }

    private ArrayList getHorasClase(){

        return CalifasOperator.getHorasClase(getTurno());
    }



    private String getTurno(){
        DataBaseConsulter consulter = new DataBaseConsulter(viewSpecs.getTable());

        String[] colsBring = new String[]{"turno"};

        String[] cond = new String[]{"grupo"};

        String[] values = new String[]{keyValue};

        return  consulter.bringTable(colsBring,cond,values).getUniqueValue();
    }

    private Table getMaterias(){
        Table materia = new GrupoOperator(keyValue).getMaterias();

        ArrayList<String> keys =materia.getColumn(0);
        ArrayList<String> human = materia.getColumn(1);

        ArrayList<String> titles = materia.getColumnTitles();

        materia.removeColumn(0);
        materia.removeColumn(0);

        materia.addColumn(0,titles.get(1),human);
        materia.addColumn(1,titles.get(0),keys);

        return materia;

    }

    private void deployMissingPlanMSG(){
        JLabel missigLabel = new JLabel("     Error: éste grupo no tiene un plan de estudio asignado.");
        missigLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        missigLabel.setForeground(new Color(150,150,150));

        thisWindow.addBody(missigLabel);
    }

    private boolean hasPlan(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] columnsToBring = new String[]{"plan"};

        String[] cond = new String[]{"grupo"};

        String[] value = new String[]{keyValue};

        boolean hasPlan = !consulter.bringTable(columnsToBring,cond,value).isEmpty();
        if (hasPlan == false)
            deployMissingPlanMSG();
        return hasPlan;
    }





    private Map<String,String> getCurrentMaterias(String hora){
        DataBaseConsulter con = new DataBaseConsulter("horarios_view");

        String[] columnsToBring = new String[]{"nombre_materia","dia"};

        String[] cond = new String[]{"hora","grupo"};

        String[] value = new String[]{hora,keyValue};

            Table asign = con.bringTable(columnsToBring,cond,value);

            ArrayList<String> dias = asign.getColumn("dia");
            ArrayList<String> materias = asign.getColumn("nombre_materia");

            Map<String,String> response = new HashMap<>();

            for (int i = 0; i < dias.size();i++)
                response.put(dias.get(i),materias.get(i));

            return response;

    }

    private void addFormArea() {
        BuilderBackend back = new BuilderBackend(
                "horarios",
                "dia",
                "hora",
                "materia",
                dias);

        ArrayList colsCondition = new ArrayList();
        colsCondition.add("grupo");

        ArrayList values = new ArrayList();
        values.add(keyValue);

        back.setDefaultCols(colsCondition);
        back.setDefaultValues(values);

        back.insertTitle();
        back.insertRow();

        MultipleFormsPanel panel = new MultipleFormsPanel(back, getRowTitlesTable());
            panel.addBuilder(getBuilder(panel));


        addForm("Horario", panel);
    }

    private MultipleFormsPanel.FormElementBuilder getBuilder(MultipleFormsPanel panel){
        return  new MultipleFormsPanel.FormElementBuilder() {
            @Override
            public FormElement buildElement(Formulario form, String title, String row) {

                Map<String,String> currentValues = getCurrentMaterias(row);

                formElementWithOptions newElement = form.addDesplegableMenu(title).
                        setRequired(false).
                        setOptions(getNewMaterias(title)).
                        addTrigerGetterEvent(getElementTrigererEvent(panel));

                if(currentValues.containsKey(title))
                    newElement.setSelection(currentValues.get(title));

                return newElement;
            }
        };
    }

    private TrigerElemetGetter getElementTrigererEvent(MultipleFormsPanel panel){
        return new TrigerElemetGetter() {
            @Override
            public void onTrigger(formElementWithOptions element) {
                if(panel.isSeted)
                alredyExists(element,panel);

            }
        };
    }

    private String getProfesor(formElementWithOptions element){
        String grupo = keyValue;
        String materia = element.getResponse();

        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas");

        String[] bring = new String[]{"profesor"};

        String[] cond = new String[]{"grupo","materia"};

        String[] value = new String[]{grupo,materia};


        Table res = consulter.bringTable(bring, cond, value);

        String prof = res.getUniqueValue();
        return prof;
    }

    private void alredyExists(formElementWithOptions element,MultipleFormsPanel panel) {
        String hora = panel.getElementRowValue(element);
        String dia = panel.getElementColValue(element);

        String prof = getProfesor(element);

        if (checkIfHoraIsOcupada(prof,hora,dia)) {

            FormDialogMessage errorMesagge = new FormDialogMessage("Error al asignar materia",
                    "No se puede asignar la materia, pues el profesor que imparte esta materia ya tine una clase a esa hora");

            errorMesagge.btnAceptar.setText("Mostrar horario del profesor");

            errorMesagge.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    showHorarioPDF(prof);
                }
            });

            element.setResponse("");

        }
    }

    private boolean checkIfHoraIsOcupada(String profesor, String hora, String dia){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_horario_view");

        String[] cond = new String[]{"dia","hora","profesor"};

        String[] value = new String[]{dia,hora,profesor};

        return !consulter.bringTable(cond,value).isEmpty();

    }

    private void showHorarioPDF(String prof){
        OperationInfoPanel pack = new OperationInfoPanel(
                new ViewSpecs("profesores"),prof);

        new ProfesorHorario(pack).buildOperation();



    }

    private Table getRowTitlesTable(){
        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        ArrayList<String> horasClase = getHorasClase();

            for (String columnCell: horasClase){
                ArrayList<String> newRegister = new ArrayList<>();
                    newRegister.add(columnCell);
                    newRegister.add(columnCell);
                registers.add(newRegister);
            }

        return new Table(new ArrayList<>(),registers);
    }


    private Table getNewMaterias(String dia){
        Table materias = getMaterias();
        Table newMaterias = new Table(materias);
        ArrayList<String> titles = newMaterias.getColumnTitles();
        titles.set(1,dia);
        newMaterias.setColumnTitles(titles);
        return newMaterias;
    }











}
