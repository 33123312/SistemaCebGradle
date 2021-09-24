package sistemaceb;

import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import JDBCController.ViewUpdater;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Global;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewRegisterGenTableBuild extends keyHiddedCoonsTableBuild {
    public NewRegisterGenTableBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
    }

    protected MouseAdapter getNewRegisterGenEvent() {
        Map<String,String> virtualTags = new HashMap<>();
            virtualTags.putAll(getVirtuals());
            virtualTags.put(critCol, critValue);

        InsertiveForm res = new InsertiveForm() {
            @Override
            protected void manageResponse(Map<String, String> valuesToInsert) {
                valuesToInsert.putAll(virtualTags);

                new ViewUpdater(viewSpecs.getInfo().getView())
                        .insert(valuesToInsert);
            }
        };


        res.virtual = virtualTags;
        res.TagsToForm = getRemovedVirtualsTags(res.virtual);

        return res;
    }

    private ArrayList<String > getRemovedVirtualsTags(Map<String,String> virtualMap){
        ArrayList<String> tags = getRemovedTableTags();
        ArrayList<String> virtual = new ArrayList<>(virtualMap.keySet());

        for (String virtualTag:virtual)
            tags.remove(virtualTag);

        return tags;
    }

    private Map<String,String> getVirtuals() {
        Map<String,String> virtuals = new HashMap<>();
        ArrayList<String> parentTables = parentSpecs.getInfo().getForeignTables();
        ArrayList<String> thisTables = viewSpecs.getInfo().getForeignTables();

        ArrayList<String> mergedParentCols = new ArrayList<>();
        ArrayList<String> mergedThisCols = new ArrayList<>();

        for (String parentTable : parentTables)
            if (thisTables.contains(parentTable)) {
                mergedParentCols.add(parentSpecs.getTagFromTable(parentTable));
                mergedThisCols.add(viewSpecs.getTagFromTable(parentTable));
            }

        RegisterDetailView parentWindow = (RegisterDetailView)Global.view.currentWindow.getWIndow();


        Table parentViewRegister = parentWindow.registerInfo.getViewRegisters();

        TableRegister register = parentViewRegister.getRegister(0);

        ArrayList<String> values = new ArrayList<>();
        for (String col : mergedParentCols)
            values.add(register.get(col));

        for (int i = 0; i < mergedThisCols.size();i++)
            virtuals.put(mergedThisCols.get(i),values.get(i));

        return virtuals;
    }


}
