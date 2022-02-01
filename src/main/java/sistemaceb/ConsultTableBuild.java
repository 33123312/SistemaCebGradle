
package sistemaceb;

import Generals.BtnFE;
import JDBCController.DataBaseSearcher;
import JDBCController.ViewSpecs;
import JDBCController.infoPackage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.proccesStateStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author escal
 */
public abstract class ConsultTableBuild {

    public ViewSpecs viewSpecs;
    protected DataBaseSearcher dataBaseConsulter;
    private final primaryKeyedTable outputTable;
    private ArrayList<String> tagsToShow;
    private ConsulterReactiveSB searchBar;
    private KeyedTableBehavior behavior;
    private String name;
    private boolean searchIsHidden;

    public ConsultTableBuild(String view) {
        updateEvents = new ArrayList<>();
        addSearchBarManager();
        dataBaseConsulter = new DataBaseSearcher(view);
        viewSpecs = dataBaseConsulter.getViewSpecs();
        name = viewSpecs.getInfo().getHumanName();

        outputTable = new primaryKeyedTable();
        setTagsToShow(viewSpecs.getVisibleTags());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBehavior(KeyedTableBehavior behavior){
        this.behavior = behavior;
    }

    public boolean hasBehavior(){
        return behavior != null;
    }

    public JPanel getInstructionsPanel(){
        JLabel label = new JLabel(behavior.getInstructions());
            label.setFont(new Font("Arial", Font.PLAIN, 17));
            label.setForeground(new Color(100,100,100));
            label.setForeground(new Color(95, 39, 205));

        JPanel contPanel = new JPanel(new GridLayout(1,1));
        contPanel.setOpaque(false);
        contPanel.add(label);
        return contPanel;
    }


    public void searchNReset(String tagName, String data){
        dataBaseConsulter.addSearch(tagName, data);
        updateSearch();
        resetSearch();
    }

    public void resetSearch(){
        dataBaseConsulter.newSearch();
    }

    public void resetNSearch(){
        resetSearch();
        updateSearch();
    }

    private ArrayList<UpdateEvent> updateEvents;

    public void updateSearch(){
        infoPackage currentSearch = dataBaseConsulter.getSearch(tagsToShow);

        outputTable.setRows(
                currentSearch.getVisibleRegisters().getRegisters(),
                currentSearch.getViewRegisters()
        );

        for (UpdateEvent e: updateEvents)
            e.OnUpdate(currentSearch);

    }

    private boolean maxRowsIsDeeterminated = false;

    public void addSearchBarManager(){
        addUpdateEvent(new ConsultTableBuild.UpdateEvent() {
            @Override
            public void OnUpdate(infoPackage currentSearch) {
                int rows = currentSearch.getVisibleRegisters().rowCount();
                if(!maxRowsIsDeeterminated){
                    if (rows < 15){
                        searchIsHidden = true;
                        viewSearchBar(false);
                    }
                    else{
                        searchIsHidden = false;
                        viewSearchBar(true);
                    }
                    maxRowsIsDeeterminated = true;
                }


            }
        });
    }

    private void viewSearchBar(boolean a){
        if (searchBar != null)
            searchBar.setVisible(a);
    }

    public void addUpdateEvent(UpdateEvent e){
        updateEvents.add(e);
    }

    public interface UpdateEvent{
        void OnUpdate(infoPackage currentSearch);
    }

    public abstract BtnFE getInsertButton();

    public primaryKeyedTable getOutputTable(){
        return outputTable;
    }

    public void setTagsToShow(ArrayList<String> tagsToShow) {
        this.tagsToShow = tagsToShow;
        outputTable.setTitles(tagsToShow);
    }

    public reactiveSearchBar getReactiveSearchBar(){
        searchBar = new ConsulterReactiveSB(viewSpecs);
        searchBar.addInputManager(new stringInputManager(){
            @Override
            public void manageData(String tagName, String data) {
                searchNReset(tagName,data);
            }
        });
        searchBar.addItems(viewSpecs.getInfo().getTags());

        if (searchIsHidden)
            searchBar.setVisible(false);

        return searchBar;
    }



    public ViewSpecs getViewSpecs(){
        return viewSpecs;
    }
  
    public DataBaseSearcher getDataBaseConsulter(){
        return dataBaseConsulter;
    }
    

    public BtnFE getInsertButton(MouseAdapter insertButtonEvent){
        return getInsertButton(viewSpecs.getInfo().getUnityName(),insertButtonEvent);
    }

    public BtnFE getInsertButton(String txt,MouseAdapter insertButtonEvent){
        BtnFE btn = new BtnFE("Añadir " + txt);
            btn.addMouseListener(insertButtonEvent);
        return btn;
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
