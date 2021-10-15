package sistemaceb;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
import java.util.ArrayList;

public class keyHiddedCoonsTableBuild extends ConsultTableBuild{
    public   String critCol;
    public   String critValue;
    protected  ViewSpecs parentSpecs;

    public keyHiddedCoonsTableBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view);
        parentSpecs = dadSpecs;
        critCol = viewSpecs.getTagFromTable(parentSpecs.getTable());
        critValue = critKey;
        dataBaseConsulter.addPermanentSearch(critCol,critKey);
        setTagsToShow(getTagsToShow());

    }

    protected ArrayList<String> getRemovedVisibleTags() {
        ArrayList<String> tagsToInsert = viewSpecs.getVisibleTags();

        return getProcList(tagsToInsert);
    }

    protected ArrayList<String> getRemovedTableTags(){
        ArrayList<String> tagsToInsert = viewSpecs.getTableTags();

        return getProcList(tagsToInsert);
    }

    private ArrayList<String> getProcList(ArrayList<String> list){
        list.remove(critCol);
        if(parentSpecs.hasHumanKey()) {
            String humanKey = parentSpecs.getHumanTag();
            if (list.contains(humanKey))
                list.remove(humanKey);
        }
        return list;
    }

    protected ArrayList<String> getTagsToShow() {

        return getRemovedVisibleTags();
    }

    @Override
    public BtnFE getInsertButton() {
        return null;
    }
}
