package SpecificViews;

import Generals.BtnFE;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.*;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.formElementWithOptions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class AsignturasPillChoser extends RegisterDetailTable {

    public AsignturasPillChoser() {
        super("asignaturas");
    }

    @Override
    public void initRegister( String critKey, ViewSpecs parentSpecs) {
        super.initRegister( critKey, parentSpecs);
        setbuild(getBuild());
        consultTable.getOutputTable().showAll();
    }

    @Override
    protected void firstImplementation() {
        super.firstImplementation();
        addLeftButton();
    }

    private void addLeftButton(){
        BtnFE btn = new BtnFE("Añadir asignatura");
            setBtnStyles(btn);
            btn.addMouseListener(getButtonEvent());
            addLeftControls(btn);

    }

    private void deployForm(){
        FormWindow formWindow = getFormBuild();
        formWindow.addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                Map<String,String> formResponse = form.getData();
                ArrayList<String> cond = new ArrayList<>(formResponse.keySet());
                    cond.add("grupo");
                ArrayList<String> values = new ArrayList<>(formResponse.values());
                    values.add(critValue);

                try {

                    new ViewSpecs("asignaturas").getUpdater().insert(cond,values);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    FormDialogMessage mess = new FormDialogMessage("Error","El registro que se ha intentado insertar ya éxiste en la base de datos, no se puede insertar nuevamente");
                    mess.addAcceptButton();
                    mess.addOnAcceptEvent(new genericEvents() {
                        @Override
                        public void genericEvent() {
                            mess.closeForm();
                        }
                    });
                }
                formWindow.getFrame().closeForm();
                consultTable.updateSearch();
            }
        });
    }

    private MouseAdapter getButtonEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (hasMaterias())
                    deployForm();

            }
        };
    }

    private CrudTable currentBehavior;

    private keyHiddedCoonsTableBuild getBuild(){
        keyHiddedCoonsTableBuild build = new keyHiddedCoonsTableBuild(viewSpecs.getTable(),critValue,parentSpecs);
        build.setName("Asignaciones de materias");
        currentBehavior = new CrudTable(build){
            @Override
            public MouseAdapter defineModifyButtonEvent() {
                return new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        deployModifyWindow();
                    }
                };
            }
        };

        return build;
    }

    private ArrayList<String> getTagValues(Table currentSearch){
        ArrayList<String> defaultConditions =currentSearch.getColumnTitles();
            defaultConditions.set(defaultConditions.indexOf("materia"),"Materia");
            defaultConditions.set(defaultConditions.indexOf("aula"),"Aula");
            defaultConditions.set(defaultConditions.indexOf("profesor"),"Profesor");

        return defaultConditions;
    }

    private void deployModifyWindow(){
        FormWindow formWindow = getFormBuild();

        Table currentSearch= currentBehavior.build.getDataBaseConsulter().getSearch().getViewRegisters();

        ArrayList<String> defaultConditions = getTagValues(currentSearch);
        ArrayList<String> defautValues = currentSearch.getRegisters().get(currentBehavior.selectedRow);

        int grupoIndex = defaultConditions.indexOf("grupo");

        defaultConditions.remove(grupoIndex);
        defautValues.remove(grupoIndex);

        formWindow.setDefaultValues(defaultConditions,defautValues);

        formWindow.addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                Map<String,String> formResponse = form.getData();

                ArrayList<String> cond = new ArrayList<>(formResponse.keySet());
                    cond.add("grupo");
                ArrayList<String> values = new ArrayList<>(formResponse.values());
                    values.add(critValue);

                Table currentSearch= currentBehavior.build.getDataBaseConsulter().getSearch().getViewRegisters();

                ArrayList<String> updateConditions =currentSearch.getColumnTitles();
                ArrayList<String> updateValues = currentSearch.getRegisters().get(currentBehavior.selectedRow);

                ArrayList<String> updateConditionsCols = viewSpecs.getCol(updateConditions);
                try {
                    new ViewSpecs("asignaturas").getUpdater().update(cond,values,updateConditionsCols,updateValues);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                formWindow.getFrame().closeForm();
                consultTable.updateSearch();
            }
        });
    }

    private FormWindow getFormBuild() {

        FormWindow form = new FormWindow("");

        form.addVirtualParent("grupo", critValue);

        form.addDesplegableMenu("Materia").setRequired(true).setOptions(getMaterias());
        form.addDesplegableMenu("Profesor").setRequired(true);
        form.addDesplegableMenu("Aula").setRequired(true).setOptions(getAulas());

        form.addElementRelation("Materia", "Profesor", new formRelationEvent() {
            @Override
            public void getNewOptions(String elementInput, formElementWithOptions conditionatedElement) {
                Table profesoresOptions = getProfesoresFromMateria(elementInput);
                conditionatedElement.setOptions(profesoresOptions);
            }
        });
        return form;
    }

    private Table getAulas(){
        DataBaseConsulter consulter = new DataBaseConsulter("aulas");
        return consulter.bringTable();
    }

    private Table getProfesoresFromMateria(String materiaKey){
        DataBaseConsulter consulter = new DataBaseConsulter("materia_profesor_view");

        String[] colsToBring = new String[]{"nombre_completo","profesor"};

        String[] cond = new String[]{"materia"};

        String[] values = new String[]{materiaKey};

        return consulter.bringTable(colsToBring,cond,values);
    }

    private Table getMat(){
        GrupoOperator operator = new GrupoOperator(critValue);
        Table response = operator.getMaterias();

        return response;
    }

    private boolean hasMaterias(){
        Table res = getMat();
            if (res.isEmpty()){
                FormDialogMessage form = new FormDialogMessage(
                        "No se han encontrado materias para este grupo",
                        " Esto puede ser debido a que no hay un plan asignado, o bien, a que el plan asignado no tiene materias");
                form.addAcceptButton();
                form.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent() {
                        form.closeForm();
                    }
                });

        }
        return !getMaterias().isEmpty();
    }

    private Table getMaterias(){
        Table response = getMat();

        ArrayList<String> humanValues = response.getColumn(1);
        ArrayList<String> keyValues = response.getColumn(0);

        response.removeColumn(0);
        response.removeColumn(0);

        response.addColumn(0,"Materias",humanValues);
        response.addColumn(1,"materia",keyValues);

        return response;
    }


}


