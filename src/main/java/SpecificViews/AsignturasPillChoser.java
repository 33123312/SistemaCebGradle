package SpecificViews;

import Generals.BtnFE;
import JDBCController.*;
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

    public AsignturasPillChoser(String view, String critKey, ViewSpecs parentSpecs) {
        super(view, critKey, parentSpecs);
        keyHiddedCoonsTableBuild build = getBuild(view,critKey,parentSpecs);
        setbuild(build);
        addLeftButton();
        addHumanTitles();
    }

    private void addHumanTitles(){
        ArrayList<String> trueTitles = new ArrayList<>();
            trueTitles.add("Clave Materia");
            trueTitles.add("Materia");
            trueTitles.add("Clave Profesor");
            trueTitles.add("Profesor");
            trueTitles.add("Aula");
        consultTable.getOutputTable().setTitles(trueTitles);
    }

    private void addLeftButton(){
        BtnFE btn = new BtnFE("Añadir asignatura");
            setBtnStyles(btn);
            btn.addMouseListener(getButtonEvent());
            addLeftControls(btn);

    }

    private MouseAdapter getButtonEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                FormWindow formWindow = getFormBuild();
                formWindow.showAll();
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
                                new FormDialogMessage("Error","El registro que se ha intentado insertar ya éxiste en la base de datos, no se puede insertar nuevamente");
                            }
                            formWindow.getFrame().closeForm();
                            consultTable.updateSearch();
                        }
                    });
            }
        };
    }

    private CrudTable currentBehavior;

    private keyHiddedCoonsTableBuild getBuild(String view, String critKey, ViewSpecs parentSpecs){
        keyHiddedCoonsTableBuild build = new keyHiddedCoonsTableBuild(view,critKey,parentSpecs);

        currentBehavior = new CrudTable(view,build){
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

        formWindow.showAll();

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

    private Table getMaterias(){
        GrupoOperator operator = new GrupoOperator(critValue);

        Table response = operator.getMaterias();

        if (response.isEmpty()){
            new FormDialogMessage("Error", "no se hn encontrado materias para este grupo,esto pueeser debia a que no hay un plan asignado, o bien, a que el plan asignado no tiene materias");
        }

        ArrayList<String> humanValues = response.getColumn(1);
        ArrayList<String> keyValues = response.getColumn(0);

        response.removeColumn(0);
        response.removeColumn(0);

        response.addColumn(0,"Materias",humanValues);
        response.addColumn(1,"materia",keyValues);

        return response;
    }


}


