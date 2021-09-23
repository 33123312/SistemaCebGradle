package sistemaceb.form;

import JDBCController.*;
import SpecificViews.LinkedInterTable;
import sistemaceb.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultipleAdderConsultBuild extends keyHiddedCoonsTableBuild {
    String columnToRelate;
    ViewSpecs tableToRelate;

    public MultipleAdderConsultBuild(
         String view,
         String critKey,
         ViewSpecs dadSpecs
         ){
            super(view,critKey,dadSpecs);
            setRelatedTable();
            setInsertButtonEvent(getButtonEvent());

    }

    public String getParentAsColumn(){
        return viewSpecs.getTagFromTable(parentSpecs.getTable());
    }

    public  String getRelatedKey(){
        String parentAsColumn = getParentAsColumn();
        ArrayList<String> primaryKeys = viewSpecs.getPrimaryskey();
        primaryKeys.remove(parentAsColumn);

        return primaryKeys.get(0);
    }

    private Table checkForOptions(){
        ArrayList<String> reciclerArray = new ArrayList<>();
            reciclerArray.add(tableToRelate.getTable());

        CommonRegistersGetter possibleValuesGetter = new CommonRegistersGetter(parentSpecs);
            possibleValuesGetter.setQuickOptionsGetters(reciclerArray);
            possibleValuesGetter.useRegisterAsConditions(critValue);

        return possibleValuesGetter.getOptions(tableToRelate.getTable());
    }

    private void setRelatedTable(){
        ArrayList<String> primaryKeys = viewSpecs.getPrimaryskey();
        ArrayList<String> primaryKeysTables = viewSpecs.getForeignTables(primaryKeys);

        String tableToRelate = null;
        for(String table:primaryKeysTables)
            if (!table.equals(parentSpecs.getTable()))
                tableToRelate = table;

        this.tableToRelate = new ViewSpecs(tableToRelate);
        this.columnToRelate = primaryKeys.get(primaryKeysTables.indexOf(tableToRelate));
    }

    private MouseAdapter getRelationalBag(){
        return new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                buildGroup();
            }

            private void inssertValues(ArrayList<String> data){
                ArrayList<String> columnsToInsert = new ArrayList<>();
                ArrayList<String> valuesToInsert = new ArrayList<>();

                columnsToInsert.add(critCol);
                columnsToInsert.add(columnToRelate);

                columnsToInsert = viewSpecs.getCol(columnsToInsert);

                valuesToInsert.add(critValue);
                valuesToInsert.add("");

                DataBaseUpdater updater =  new DataBaseUpdater(viewSpecs.getTable());

                for (String key:data){
                    valuesToInsert.set(1,key);
                    try {
                        updater.insert(columnsToInsert,valuesToInsert);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }

            protected GroupRelationarForm buildGroup() {
                ArrayList<String> reciclerArray = new ArrayList<>();
                CommonRegistersGetter possibleValuesGetter = new CommonRegistersGetter(parentSpecs);
                reciclerArray.add(tableToRelate.getTable());

                possibleValuesGetter.setQuickOptionsGetters(reciclerArray);
                possibleValuesGetter.useRegisterAsConditions(critValue);

                Table options = possibleValuesGetter.getOptions(tableToRelate.getTable());



                GroupRelationarForm groupRelationarForm = new GroupRelationarForm("Títulos",tableToRelate.getInfo().getHumanName());
                groupRelationarForm.setGetterValues(options);
                groupRelationarForm.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent(){
                        groupRelationarForm.closeForm();
                        inssertValues(groupRelationarForm.getAddedValues());
                        updateSearch();
                    }
                });
                return groupRelationarForm;
            }
        };
    }

    protected MouseAdapter getButtonEvent() {
        Table options = checkForOptions();

        if(options.isEmpty()){
            return getForm();
        } else {
            return getRelationalBag();
        }

    }

    private MouseAdapter getForm(){
        InsertiveForm res = new InsertiveForm() {

            @Override
            protected FormWindow buildForm() {
                FormWindow form = new FormWindow("Nuevo Registro");
                new TagFormBuilder(dataBaseConsulter.getViewSpecs(),TagsToForm,form,true);


                form.getElement(columnToRelate).addErrorChecker(
                        new ErrorChecker(){
                            @Override
                            public String checkForError(String response) {
                                return getRelationError(response);
                            }
                        }
                );


                return super.buildForm();
            }

            @Override
            protected void manageResponse(Map<String, String> valuesToInsert) {
                Map<String, String> trueValuesToInsert = new HashMap<>();
                ArrayList<String> tagsToInsert = viewSpecs.getTableTags();
                int critColIndex = tagsToInsert.indexOf(critCol);

                int i = 0;
                for (Map.Entry<String, String> entry : valuesToInsert.entrySet()) {
                    if (i == critColIndex)
                        trueValuesToInsert.put(critCol, critValue);
                    trueValuesToInsert.put(entry.getKey(), entry.getValue());
                    i++;
                }
                new ViewUpdater(viewSpecs.getInfo().getView())
                        .insert(trueValuesToInsert);
            }
        };

        res.TagsToForm = getRemovedTableTags();
        return res;
    }

    private String getRelationError(String selectedValue){
        if(new RelationComprober(parentSpecs.getTable(), critValue,viewSpecs.getTable(), selectedValue).canRelate())
            return "No se puede añadir el" + viewSpecs.getInfo().getUnityName() +  " registro selecconado";
        else
            return "";
    }

}
