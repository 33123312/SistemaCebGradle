package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.ViewSpecs;
import sistemaceb.PlanEstudioMateriasUpdater;
import sistemaceb.RegisterDetailTable;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.MultipleAdderConsultBuild;
import sistemaceb.genericEvents;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlanosMateriasPills extends RegisterDetailTable {
    ArrayList<PlanEstudioMateriasUpdater> updaters;

    public PlanosMateriasPills() {
        super("planes_estudio_materias");
    }

    @Override
    public void initRegister(String critKey, ViewSpecs parentSpecs) {
        super.initRegister(critKey, parentSpecs);
        restartUpdaters();
        setbuild(getBuild());

    }

    @Override
    protected void firstImplementation() {
        super.firstImplementation();
        addDefInsertButton();
    }

    private MultipleAdderConsultBuild getOverRidenBuild(){
        return new MultipleAdderConsultBuild(viewSpecs.getTable(),critValue,parentSpecs){
            @Override
            protected void insertValues(ArrayList<String> data) {
                ArrayList<String> columnsToInsert = new ArrayList<>();
                ArrayList<String> valuesToInsert = new ArrayList<>();

                columnsToInsert.add(critCol);
                columnsToInsert.add(columnToRelate);
                columnsToInsert = viewSpecs.getCol(columnsToInsert);
                columnsToInsert.add("orden");

                valuesToInsert.add(critValue);
                valuesToInsert.add("");
                valuesToInsert.add("");

                int matCount = getMatCount();

                for (int i = 0; i < data.size();i++){
                    String key = data.get(i);
                    valuesToInsert.set(1,key);
                    valuesToInsert.set(2,Integer.toString(matCount + i +1));

                    try {
                        viewSpecs.getUpdater().insert(columnsToInsert,valuesToInsert);
                    } catch (SQLException throwables) {
                        FormDialogMessage form = new FormDialogMessage(
                                "No se pudieron añadir las materias",
                                "Se intentó añadir materias que ya estaban en el plan ded estudios, " +
                                        "asi que se canceló la operación, asegurese de añadir únicamente materias que no están ya en el plan de estudios");
                        form.addAcceptButton();
                        form.addOnAcceptEvent(new genericEvents() {
                            @Override
                            public void genericEvent() {
                                form.closeForm();
                            }
                        });
                        throwables.printStackTrace();
                    }
                }
            }

            @Override
            public void updateSearch() {
                super.updateSearch();
                makeUpdates();
                restartUpdaters();
            }
        };
    }
    private int getMatCount(){
        return outputTable.getRows().size();
    }

    private MultipleAdderConsultBuild getBuild(){
        MultipleAdderConsultBuild build = getOverRidenBuild();
            build.setBehavior(getBehavior(build));
        return build;
    }

    private LinkedInterTable getBehavior(MultipleAdderConsultBuild build){
        LinkedInterTable customBrhavior = new LinkedInterTable(build){
            @Override
            protected void removeElemt(String selectdKey) {
                FormDialogMessage form = new FormDialogMessage(
                        "Advertencia",
                        "Al remover ésta materia del plan de estudios, también se removerá de los horarios y asignaturas de todos los                                   grupos que tengan éste plan <br><br> presonee ACEPTAR para eliminar la materia del plan ");
                form.addCloseButton();
                form.addAcceptButton();
                form.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent() {
                        form.closeForm();
                        rem(selectdKey);

                    }
                });
            }

            private void rem(String selectdKey){
                super.removeElemt(selectdKey);
            }
        };
        return customBrhavior;
    }

    private void makeUpdates(){
        for (PlanEstudioMateriasUpdater updater: updaters)
            updater.makeUpdate();
    }

    private void restartUpdaters(){
        updaters = new ArrayList();
        ArrayList<String> grupos = getGrupos();
        for (String grupo:grupos)
            updaters.add(new PlanEstudioMateriasUpdater(grupo));

    }

    private ArrayList<String> getGrupos(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] toBring = new String[]{"grupo"};

        String[] cond = new String[]{"plan"};

        String[] value = new String[]{critValue};

        return consulter.bringTable(toBring,cond,value).getColumn(0);
    }
}
