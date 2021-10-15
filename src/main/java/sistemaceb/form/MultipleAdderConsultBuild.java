package sistemaceb.form;

import Generals.BtnFE;
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
    protected String columnToRelate;
    protected ViewSpecs tableToRelate;

    public MultipleAdderConsultBuild(String view, String critKey, ViewSpecs dadSpecs){
        super(view,critKey,dadSpecs);
        setRelatedTable();
        setName(tableToRelate.getInfo().getHumanName());

    }

    public BtnFE getInsertButton(){
        return super.getInsertButton(tableToRelate.getInfo().getUnityName(),getButtonEvent());
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
    protected void insertValues(ArrayList<String> data){
        ArrayList<String> columnsToInsert = new ArrayList<>();
        ArrayList<String> valuesToInsert = new ArrayList<>();

        columnsToInsert.add(critCol);
        columnsToInsert.add(columnToRelate);

        columnsToInsert = viewSpecs.getCol(columnsToInsert);

        valuesToInsert.add(critValue);
        valuesToInsert.add("");

        for (String key:data){
            valuesToInsert.set(1,key);
            try {
                viewSpecs.getUpdater().insert(columnsToInsert,valuesToInsert);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private MouseAdapter getRelationalBag(){
        return new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                buildGroup();
            }


            protected GroupRelationarForm buildGroup() {
                GroupRelationarForm groupRelationarForm =
                        new GroupRelationarForm(
                                "Añadir " + tableToRelate.getTable(),tableToRelate.getInfo().getHumanName());

                groupRelationarForm.setGetterValues(getMergedOptions());
                groupRelationarForm.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent(){
                        groupRelationarForm.closeForm();
                        insertValues(groupRelationarForm.getAddedValues());
                        updateSearch();
                    }
                });
                return groupRelationarForm;
            }
        };
    }

    private Table getMergedOptions(){
        Table options = getOptions();


        ArrayList<String> ocupedOp = getAlredyAddedElements();

        ArrayList<ArrayList<String>> registers = options.getRegisters();

        int index;
        if (options.columnCount() == 2)
            index = 1;
        else
            index = 0;

        System.out.println(ocupedOp);
        System.out.println(registers);

        for (String oc:ocupedOp){
            for (ArrayList<String> reg: registers)
                if (reg.get(index).equals(oc)){
                    registers.remove(reg);
                    break;

                }
        }

        options.setRegisters(registers);

        return options;
    }

    private Table getOptions(){
        ArrayList<String> reciclerArray = new ArrayList<>();
        CommonRegistersGetter possibleValuesGetter = new CommonRegistersGetter(parentSpecs);
        reciclerArray.add(tableToRelate.getTable());

        possibleValuesGetter.setQuickOptionsGetters(reciclerArray);
        possibleValuesGetter.useRegisterAsConditions(critValue);

        Table options = possibleValuesGetter.getOptions(tableToRelate.getTable());

        return options;
    }

    private ArrayList<String> getAlredyAddedElements(){
        DataBaseConsulter consulter = new DataBaseConsulter(viewSpecs.getTable());
        Table res = consulter.bringTable(new String[]{viewSpecs.getCol(columnToRelate)},new String[]{viewSpecs.getCol(critCol)},new String[]{critValue});

        return res.getColumn(0);
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
                try {
                    viewSpecs.getUpdater().insert(trueValuesToInsert);
                } catch (SQLException throwables) {
                    FormDialogMessage form = new FormDialogMessage(
                            "Conflicto al agregar registro",
                            "No es posible agregar registros que ya existen en la tabla, se ha cancelado la aoperación"
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
