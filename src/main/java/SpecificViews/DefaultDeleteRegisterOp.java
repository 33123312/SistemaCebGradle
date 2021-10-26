package SpecificViews;
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
           Global.view.currentWindow.cut();
        } catch (Exception e){
            FormDialogMessage message = new FormDialogMessage(
                    "No se puede eliminar el " + viewSpecs.getInfo().getUnityName(),getFormMessage());
            message.addAcceptButton();
            message.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    message.closeForm();
                }
            });
        }

    }

    private String getFormMessage(){
        String unity = viewSpecs.getInfo().getUnityName();
        return
                "El " +  unity + " está siendo utilizado en otras tablas de la base datos " +
                "( " + getRelatedTablesString() + "), elimine " +
                "el " + unity + " de las tablas mencionadas para poder quitarlo del sistema"
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
