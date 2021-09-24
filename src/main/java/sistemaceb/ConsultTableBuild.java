
package sistemaceb;

import Generals.BtnFE;
import JDBCController.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Tables.AdapTableFE;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.proccesStateStorage;

/**
 *
 * @author escal
 */
public abstract class ConsultTableBuild {

    protected ViewSpecs viewSpecs;
    protected DataBaseSearcher dataBaseConsulter;
    protected primaryKeyedTable outputTable;
    private  ArrayList<String> tagsToShow;
    private BtnFE insertBtn;


    public ConsultTableBuild(String view){
        dataBaseConsulter = new DataBaseSearcher(view);
        viewSpecs = dataBaseConsulter.getViewSpecs();
        insertBtn = new BtnFE("Añadir " + viewSpecs.getInfo().getUnityName());
        setOutputTable(new primaryKeyedTable());
    }


    public void setInsertButtonEvent(MouseAdapter insertButtonEvent) {
        insertBtn.addMouseListener(insertButtonEvent);

    }

    public void searchNReset(){
        updateSearch();
        dataBaseConsulter.newSearch();
    }
    
    public primaryKeyedTable getOutputTable(){
        return outputTable;
    }

    public void setTagsToShow(ArrayList<String> tagsToShow) {
        this.tagsToShow = tagsToShow;
        initTable();
    }

    public reactiveSearchBar getReactiveSearchBar(){
        
        ConsulterReactiveSB searchBar = new ConsulterReactiveSB(viewSpecs);
        
        searchBar.addInputManager(new stringInputManager(){
            @Override
            public void manageData(String tagName, String data) {
                dataBaseConsulter.addSearch(tagName, data);         
                searchNReset();
            }

        });
        ArrayList<String> searchTags = viewSpecs.getInfo().getTags();
        searchBar.addItems(searchTags);
        
        return searchBar;
    }    


    public void setOutputTable(primaryKeyedTable table){
        outputTable = table;
        setTagsToShow(viewSpecs.getViewTags());
    }


    private void initTable(){
        outputTable.setTitles(tagsToShow);
        outputTable.showAll();
    }

    public void updateSearch(){
        infoPackage currentSearch = dataBaseConsulter.getSearch(tagsToShow);

        outputTable.addNShow(
                currentSearch.getVisibleRegisters().getRegisters(),
                currentSearch.getViewRegisters()
        );
    }

    public ViewSpecs getViewSpecs(){
        
        return viewSpecs;
    }
  
    public DataBaseSearcher getDataBaseConsulter(){
        return dataBaseConsulter;
    }
    

    public BtnFE getInsertButton(){

        
        return insertBtn;
    }

    protected class InsertiveForm extends MouseAdapter {
        public  ArrayList<String> TagsToForm;
        public Map<String,String> virtual;
        public InsertiveForm(){
            virtual = new HashMap<>();

        }

        public void addVirtual(String title, String value){
            virtual.put(title,value);

        }

        protected void manageResponse(Map<String,String> response){

        }

        protected FormWindow buildForm(){
            FormWindow form = new FormWindow("Nuevo Registro");
            addVirtuals(form);
            new TagFormBuilder(viewSpecs,TagsToForm,form,true);

            return form;
        }

        private void addVirtuals(Formulario form){
            for (Map.Entry<String,String> entry: virtual.entrySet())
                form.addVirtualParent(entry.getKey(),entry.getValue());
        }

        proccesStateStorage runingForm = new proccesStateStorage();
        @Override
        public void mousePressed(MouseEvent arg0){

            if(!runingForm.runing){
                FormWindow insertForm = buildForm();
                insertForm.getFrame().addRuningStatusDownShoter(runingForm);
                insertForm.addDataManager(
                        new FormResponseManager(){
                            @Override
                            public void manageData(Formulario form) {
                                Map<String, String> trueData = form.getData();
                                manageResponse(trueData);
                                updateSearch();
                                insertForm.getFrame().closeForm();

                            }
                        }
                );

            }
        }

    }


}
