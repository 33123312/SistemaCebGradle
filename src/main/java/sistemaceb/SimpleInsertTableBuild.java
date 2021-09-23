package sistemaceb;

import JDBCController.ViewUpdater;
import Tables.AdapTableFE;
import sistemaceb.form.Formulario;
import sistemaceb.form.MultipleFormWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleInsertTableBuild extends ConsultTableBuild {

    public SimpleInsertTableBuild(String view) {
        super(view);
        new LinkedTable(view,getOutputTable());
        setInsertButtonEvent(getButtonEvent());
        setTagsToShow(getTagsToShow());
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
        new ViewUpdater(viewSpecs.getInfo().getView())
                .insert(valuesToInsert);

    }

    protected ArrayList<String> getTagsToInsert() {
        return viewSpecs.getTableTags();
    }



    protected ArrayList<String> getTagsToShow() {

        return viewSpecs.getVisibleTags();
    }
}
