package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Global;
import sistemaceb.genericEvents;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeletePlanosOp extends Operation{

    public DeletePlanosOp(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Eliminar";
    }

    @Override
    public void buildOperation() {
        if (hasAsignedGroups())
            showAdvMessage();
        else
            removePlan();
    }

    private void removePlan(){
        removeMateria();
        deleteRegister();

    }

    private void removeMateria(){
        ArrayList<String> cond = new ArrayList<>();
        cond.add("clave_plan");

        ArrayList<String> val = new ArrayList<>();
        val.add(keyValue);

        try {
            new ViewSpecs("planes_estudio_materias").getUpdater().delete(cond,val);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showAdvMessage(){
        FormDialogMessage formDialogMessage = new FormDialogMessage(
                "No se puede eliminar el plan",
                "Hay algunos grupos que están usando éste plan, favor de cambiar el plan de dichos grupos antes de borrar el plan");
        formDialogMessage.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                formDialogMessage.closeForm();
            }
        });
    }

    private boolean hasAsignedGroups(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        Table res = consulter.bringTable(new String[]{"plan"},new String[]{keyValue});

        return !res.isEmpty();
    }

    private void deleteRegister(){
        ArrayList<String> updateConditions = new ArrayList();
        ArrayList<String> updateValues = new ArrayList();

        updateConditions.add(viewSpecs.getPrimarykey());
        updateValues.add(keyValue);

        updateConditions = viewSpecs.getCol(updateConditions);

        try {
            viewSpecs.getUpdater().delete(updateConditions,updateValues);
            Global.view.currentWindow.cut();
        } catch (Exception e){

        }

    }
}
