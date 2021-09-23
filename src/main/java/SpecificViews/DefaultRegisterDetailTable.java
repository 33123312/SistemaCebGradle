package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.ConsultTableBuild;
import sistemaceb.RegisterDetailTable;
import sistemaceb.ConditionedConsultTable;
import sistemaceb.ConditionedRefTableBuild;
import sistemaceb.form.MultipleAdderConsultBuild;

public class DefaultRegisterDetailTable extends RegisterDetailTable {

    public DefaultRegisterDetailTable(String view, String critKey, ViewSpecs parentSpecs) {
        super(view, critKey, parentSpecs);
        ConsultTableBuild build = getTable(view);
        setbuild(build);
        addDefInsertButton();
        addSearchOptions();
    }


    private ConsultTableBuild getTable(String view){
        if(viewSpecs.isMain())
            return new ConditionedRefTableBuild(view,critValue,parentSpecs);
        else if(viewSpecs.getPrimaryskey().size() == 2)
            return new IntermediaryTableConsultBuild(view,critValue,parentSpecs);
        else
            return new ConditionedConsultTable(view,critValue,parentSpecs);
    }


}
