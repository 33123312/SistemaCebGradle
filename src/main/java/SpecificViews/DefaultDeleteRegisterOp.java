package SpecificViews;

import JDBCController.DBU;
import JDBCController.DataBaseUpdater;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Global;
import sistemaceb.genericEvents;

import java.util.ArrayList;

public class DefaultDeleteRegisterOp extends Operation{
    public DefaultDeleteRegisterOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Eliminar";
    }

    @Override
    public void buildOperation() {
        deleteRegister();
    }

    private void deleteRegister(){
        ArrayList<String> updateConditions = new ArrayList();
        ArrayList<String> updateValues = new ArrayList();

        updateConditions.add(viewSpecs.getPrimarykey());
        updateValues.add(keyValue);

        updateConditions = viewSpecs.getCol(updateConditions);

        try {
           viewSpecs.getUpdater().delete(updateConditions,updateValues);
        } catch (Exception e){
            FormDialogMessage message = new FormDialogMessage("Error al eliminar el registro",getFormMessage());
            message.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    message.closeForm();
                }
            });
        }

    Global.view.currentWindow.cut();

    }

    private String getFormMessage(){
        String unity = viewSpecs.getInfo().getUnityName();
        return
                "Error: No se puede eliminar el " +  unity + " puesto que éste se encentra presente en otras tablas de la base de datos " +
                "( " + getRelatedTablesString() + "), para eliminar el " + unity + ", antes modifique o elimine los registros que contienen " +
                "el registro de las tablas mencionadas"
                ;
    }

    private String getRelatedTablesString(){
        String stringRelated = "";
        ArrayList<String> relatedTables = viewSpecs.getRelatedTables();
        for (String table: relatedTables)
            stringRelated+= " " + table;

        return stringRelated;
    }

    }
