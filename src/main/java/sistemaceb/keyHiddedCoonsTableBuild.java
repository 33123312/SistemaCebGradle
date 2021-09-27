package sistemaceb;

import JDBCController.ViewSpecs;
import java.util.ArrayList;

public class keyHiddedCoonsTableBuild extends ConsultTableBuild{
    public   String critCol;
    public   String critValue;
    protected  ViewSpecs parentSpecs;


    public keyHiddedCoonsTableBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view);
        commonCons(critKey,dadSpecs);
        setTagsToShow(getTagsToShow());

    }

    private void commonCons( String critKey,ViewSpecs dadSpecs){
        parentSpecs = dadSpecs;
        critCol = viewSpecs.getTagFromTable(parentSpecs.getTable());
        critValue = critKey;
        dataBaseConsulter.addExactSearch(critCol,critKey);
    }

    protected ArrayList<String> getRemovedVisibleTags() {
        ArrayList<String> tagsToInsert = viewSpecs.getVisibleTags();
            tagsToInsert.remove(critCol);

        if (viewSpecs.hasHumanKey())
            tagsToInsert.remove(viewSpecs.getInfo().getHumanKey());
        return tagsToInsert;
    }

    protected ArrayList<String> getRemovedTableTags(){
        ArrayList<String> tagsToInsert = viewSpecs.getTableTags();
            tagsToInsert.remove(critCol);

        return tagsToInsert;
    }

    protected ArrayList<String> getTagsToShow() {

        return getRemovedVisibleTags();
    }
}
