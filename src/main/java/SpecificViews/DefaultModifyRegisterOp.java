package SpecificViews;

import JDBCController.DBU;
import JDBCController.DataBaseUpdater;
import JDBCController.TableRegister;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.*;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.Global;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DefaultModifyRegisterOp extends Operation{
    FormWindow updateForm;

    public DefaultModifyRegisterOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Modificar";
    }

    @Override
    public void buildOperation() {
        deployModifyForm();
    }

    protected void setDefaultValues(Formulario form){
        TableRegister viewRegiter = registerInfo;
        form.setDefaultValues(viewRegiter.getColumnTitles(),viewRegiter.getValues());

    }

    protected void addDefDataManagerToForm(FormWindow formWindow){
        formWindow.addDataManager(new FormResponseManager(){

            @Override
            public void manageData(Formulario form) {
                formWindow.getFrame();

                Map<String, String> trueData = form.getData();

                ArrayList<String> responseTagTitles = new ArrayList<>(trueData.keySet());
                ArrayList<String> responseValues = new ArrayList<>(trueData.values());

                ArrayList<String> updateTagConditions = new ArrayList();

                updateTagConditions.add(viewSpecs.getPrimarykey());

                ArrayList<String> updateConditions;

                ArrayList<String> responseTitles = viewSpecs.getCol(responseTagTitles);

                updateConditions =  viewSpecs.getCol(updateTagConditions);

                ArrayList<String> updateValues = new ArrayList();
                updateValues.add(keyValue);

                rowUpdateConfirmationFrame confirmationForm =
                        new rowUpdateConfirmationFrame("Se modificara en siguiente registro",responseTitles,responseValues);
                formWindow.getFrame().addChildForm(confirmationForm);
                confirmationForm.addOnAcceptEvent(new genericEvents(){
                    @Override
                    public void genericEvent(){
                        try {

                            viewSpecs.getUpdater().update(responseTitles,responseValues,updateConditions,updateValues);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        Global.view.currentWindow.cut();
                        confirmationForm.closeForm();
                        formWindow.getFrame().closeForm();
                    }
                });
            }
        });
    }

    protected void addToTagFormBuilder(ArrayList<String> columnsToUpdate){
        new TagFormBuilder(viewSpecs, columnsToUpdate,updateForm,true);
    }

    protected void deployModifyForm(){
        updateForm = new FormWindow("Modificar registro");
        addToTagFormBuilder(viewSpecs.getTableTags());
        setDefaultValues(updateForm);
        addDefDataManagerToForm(updateForm);

    }


}
