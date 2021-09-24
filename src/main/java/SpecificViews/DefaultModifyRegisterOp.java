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

    private void setDefaultValues(Formulario form){
        TableRegister viewRegiter = registerInfo;
        form.setDefaultValues(viewRegiter.getColumnTitles(),viewRegiter.getValues());

    }

    protected void deployModifyForm(){
        ArrayList<String> columnsToUpdate = viewSpecs.getTableTags();
        updateForm = new FormWindow("Modificar registro");
        new TagFormBuilder(viewSpecs, columnsToUpdate,updateForm,true);
        setDefaultValues(updateForm);
        updateForm.addDataManager(new FormResponseManager(){
            @Override
            public void manageData(Formulario form) {
                    updateForm.getFrame();

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
                    updateForm.getFrame().addChildForm(confirmationForm);
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
                            updateForm.getFrame().closeForm();
                        }
                    });
                }
            });
        }


}
