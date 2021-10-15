package SpecificViews;

import JDBCController.*;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.form.ErrorChecker;
import sistemaceb.form.FormElement;
import sistemaceb.form.Formulario;
import sistemaceb.form.Global;

import java.util.ArrayList;


public class BoletaBuilder extends MultipleFormsOperation{
    private  ArrayList<String> values;
    private  Table califasBoleanas;
    private ALumnoOperator operator;
    Formulario semestresForm;


    public BoletaBuilder(OperationInfoPanel infoPanlel){
        super( infoPanlel);
        operation = "Modificar Boleta";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        thisWindow.setTitle("Modificar Boleta");
        operator = new ALumnoOperator(keyValue);
        values = getValues();
        califasBoleanas = getCaliBoleanas();
        addForms();

    }

    private void addForms(){
        addCalNumFrame();
        addAsistenciasArea("Numérica","calificaciones_numericas");
        addCalBolFrame();
        addAsistenciasArea("A/NA","calificaciones_booleanas");

        semestresForm = new SemestralCalifasChoser(operator.grupoOperator.getMateriasList("Numérica"),getConditions(),values);
        addElement("Calificaciones Semestrales",semestresForm);


    }

    @Override
    protected void submit() {
        super.submit();
        semestresForm.manageData();
    }

    public ArrayList<String> getEvaluaciones() {

        return CalifasOperator.getEvaluaciones();
    }

    private Table getCaliBoleanas(){
        ArrayList<String> caliNames = new ArrayList<>();
            caliNames.add("A");
            caliNames.add("1");

        ArrayList<String> caliKeys = new ArrayList<>();
            caliKeys.add("NA");
            caliKeys.add("0");

        ArrayList<ArrayList<String>> registers = new ArrayList<>();
            registers.add(caliNames);
            registers.add(caliKeys);

        return new Table(new ArrayList<>(),registers);
    }

    private Table getBooleanCalifasTable(String title){
        Table currentTable = new Table(califasBoleanas);
        ArrayList<String> columnTitles = new ArrayList<>();
        columnTitles.add(title);
        columnTitles.add(title);
        currentTable.setColumnTitles(columnTitles);

        return currentTable;

    }

    private void addCalBolFrame(){
        MultipleFormsPanel.FormElementBuilder builder = new MultipleFormsPanel.FormElementBuilder() {
            @Override
            public FormElement buildElement(Formulario form, String title, String row) {
               return form.addDesplegableMenu(title).
                        setOptions(getBooleanCalifasTable(title)).
                        setRequired(false);
            }
        };

        addCalForm("Boleana","calificaciones_booleanas","Materias Boleanas",builder);

    }

    private void  addCalNumFrame(){
        MultipleFormsPanel.FormElementBuilder builder = new MultipleFormsPanel.FormElementBuilder() {
            @Override
            public FormElement buildElement(Formulario form, String title,String row) {
               return form.addInput(title,dataType.FLOAT).
                        setRequired(false).addErrorChecker(new ErrorChecker() {
                   @Override
                   public String checkForError(String response) {
                       try {
                           double califa = Double.parseDouble(response);
                           if (califa > 10)
                               return "Error: La calificación ingresada es mayor a 10";

                       } catch (Exception e){
                           return "Error: La calificación no puede contener números";
                       }
                       return "";
                   }
               });
            }
        };

        addCalForm("Numérica","calificaciones_numericas","Materia Numéricas",builder);
    }


    private void addCalForm(String tipo,String table,String title, MultipleFormsPanel.FormElementBuilder builder){
        BuilderBackend2 back = new BuilderBackend2(
 table,
    "evaluacion",
    "materia",
"calificacion_clave",
            getEvaluaciones(),
        "calificaciones",
        "calificacion"

        );
        back.setConditions(getConditions());
        back.setValues(getValues());

        back.insertTitle();

        MultipleFormsPanel panel = new MultipleFormsPanel(
                back,
                operator.grupoOperator.getMateriasList(tipo),
                builder);

        addForm(title,panel);
    }



    private void addAsistenciasArea(String tipo,String tabla){
        BuilderBackend2 back = new BuilderBackend2(
                tabla,
                "evaluacion",
                "materia",
                "calificacion_clave",
                getEvaluaciones(),
                "calificaciones",
                "faltas"

        );
        back.setConditions(getConditions());
        back.setValues(getValues());
        back.insertTitle();
        MultipleFormsPanel.FormElementBuilder builder = new MultipleFormsPanel.FormElementBuilder() {
            @Override
            public FormElement buildElement(Formulario form, String title,String row) {
               return form.addInput(title,dataType.INT).
                        setRequired(false);
            }
        };
        MultipleFormsPanel panel = new MultipleFormsPanel(
                back,
                operator.grupoOperator.getMateriasList(tipo),
                builder);

        addForm("Faltas",panel);
    }

    @Override
    protected boolean hasErrors() {
        boolean errors = super.hasErrors();
        if(semestresForm.hasErrors())
            errors = true;
        return errors;
    }

    private ArrayList <String> getConditions(){
        ArrayList<String> conditions = new ArrayList<>();
            conditions.add("clave_alumno");
            conditions.add("semestre");
            conditions.add("periodo");

        return conditions;
    }

    private ArrayList<String> getValues(){
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add(operator.getTableRegister());
        conditions.add(operator.getRegisterValue("semestre"));
        conditions.add(Global.conectionData.loadedPeriodo);


        return conditions;

    }

}
