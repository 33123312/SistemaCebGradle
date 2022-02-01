
package sistemaceb;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.ViewSpecs;
import JDBCController.dataType;
import sistemaceb.form.ErrorChecker;
import sistemaceb.form.FormWindow;
import sistemaceb.form.RelationComprober;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author escalconditionedConsultTable
 */
public class ConditionedRefTableBuild extends NewRegisterGenTableBuild {


    public ConditionedRefTableBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
        setBehavior(new LinkedTable(view, getOutputTable()));
    }

    @Override
    public BtnFE getInsertButton() {
        return super.getInsertButton(getButtonEvent());
    }

    protected String getExistentTagsToInsert() {

        return viewSpecs.getPrimarykey();
    }

    private void showInsertDsplegableMenu() {
        DesplegableMenuFE menu = new DesplegableMenuFE(){
            @Override
            public BtnFE buttonsEditor(BtnFE button) {
                button.setTextColor(Color.black);
                return super.buttonsEditor(button);

            }
        };
        menu.addButton("Añadir Nuevo", getNewRegisterGenEvent());
        //menu.addButton("Añadir Existente", getAddExistentEvent());
        menu.showMenu();
    }

    private MouseAdapter getAddExistentEvent(){
        return new InsertiveForm(){
            @Override
            protected FormWindow buildForm() {
                FormWindow updateForm = new FormWindow("Añadir " + viewSpecs.getInfo().getUnityName());

                updateForm.addInput(getExistentTagsToInsert(), dataType.VARCHAR).
                        setRequired(true).
                        addErrorChecker(new ErrorChecker(){
                            @Override
                            public String checkForError(String response) {
                                return getRelationError(response);
                            }
                        });

                return updateForm;
            }

            @Override
            protected void manageResponse(Map<String, String> response) {

                ArrayList<String> updateConditions = new ArrayList();
                ArrayList<String> updateValues = new ArrayList();


                for (Map.Entry<String,String> thisData: response.entrySet()){
                    updateConditions.add(thisData.getKey());
                    updateValues.add(thisData.getValue());
                }

                ArrayList<String> responseTagTitles = new ArrayList();
                ArrayList<String> responseValues = new ArrayList();

                responseTagTitles.add(critCol);
                responseValues.add(critValue);

                ArrayList<String> responseTitles = viewSpecs.getCol(responseTagTitles);
                updateConditions =  viewSpecs.getCol(updateConditions);

                try {
                    viewSpecs.getUpdater().update(responseTitles,responseValues,updateConditions,updateValues);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };
    }

    protected MouseAdapter getButtonEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                showInsertDsplegableMenu();
            }
        };
    }

    private String getRelationError(String selectedValue){
        if(new RelationComprober(parentSpecs.getTable(), critValue,viewSpecs.getTable(), selectedValue).canRelate())
            return "";
        else
            return "No se puede añadir el" + viewSpecs.getInfo().getUnityName() +  " debido a que algunos de sus campos son incompatibles (" + viewSpecs.getFKForeignTags() + ")";
    }
}

