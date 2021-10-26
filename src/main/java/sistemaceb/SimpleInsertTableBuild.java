package sistemaceb;

import Generals.BtnFE;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Formulario;
import sistemaceb.form.MultipleFormWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class SimpleInsertTableBuild extends ConsultTableBuild {

    public SimpleInsertTableBuild(String view) {
        super(view);
        setBehavior(new LinkedTable(view,getOutputTable()));
        setTagsToShow(getTagsToShow());
    }

    @Override
    public BtnFE getInsertButton() {
        return super.getInsertButton(getButtonEvent());
    }

    protected MouseAdapter getButtonEvent(){
        ArrayList tags = viewSpecs.getTableTags();
        if(tags.size() < 6)
            return getMultipleAdder(tags);
        else
            return getWindow();


    }

    protected MouseAdapter getMultipleAdder(ArrayList<String> tags){
        return new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                MultipleFormWindow win = new MultipleFormWindow(
                        "Nuevo Registro",dataBaseConsulter.getViewSpecs(),tags,true);

                win.addDataManager(new FormResponseManager() {
                    @Override
                    public void manageData(Formulario form) {
                        Map<String, String> trueData = form.getData();
                        insertData(trueData);
                        updateSearch();
                    }
                });
            }
        };

    }

    protected MouseAdapter getWindow(){
        InsertiveForm form = new InsertiveForm(){

            @Override
            protected void manageResponse(Map<String, String> response) {
                insertData(response);
            }


        };
        form.TagsToForm = getTagsToInsert();
        return form;
    }

    public void insertData(Map<String,String> valuesToInsert){
        try {
            viewSpecs.getUpdater().insert(valuesToInsert);
        } catch (SQLException throwables) {
            FormDialogMessage form = new FormDialogMessage(
                    "COnflicto al agregar Registro",
                    "No es posible agregar un registro que ya existe a la base de datos"
            );
            form.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    form.closeForm();
                }
            });
            throwables.printStackTrace();
        }

    }

    protected ArrayList<String> getTagsToInsert() {
        return viewSpecs.getTableTags();
    }



    protected ArrayList<String> getTagsToShow() {

        return viewSpecs.getVisibleTags();
    }
}
