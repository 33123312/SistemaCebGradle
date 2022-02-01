package SpecificViews;

import JDBCController.TableRegister;
import sistemaceb.FormResponseManager;
import sistemaceb.TagFormBuilder;
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

                try {
                    viewSpecs.getUpdater().update(responseTitles,responseValues,updateConditions,updateValues);
                    formWindow.getFrame().closeForm();
                    Global.view.currentWindow.cut();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    protected void addToTagFormBuilder(ArrayList<String> columnsToUpdate){
        new TagFormBuilder(viewSpecs, columnsToUpdate,updateForm,false);
    }

    protected void deployModifyForm(){
        updateForm = new FormWindow("Modificar registro");
        addToTagFormBuilder(viewSpecs.getTableTags());
        setDefaultValues(updateForm);
        addDefDataManagerToForm(updateForm);

    }


}
