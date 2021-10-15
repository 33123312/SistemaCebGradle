package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.*;
import sistemaceb.form.MultipleAdderConsultBuild;

public class DefaultRegisterDetailTable extends RegisterDetailTable {

    public DefaultRegisterDetailTable(String view) {
        super(view);
    }

    @Override
    public void initRegister( String critKey, ViewSpecs parentSpecs) {
        super.initRegister( critKey, parentSpecs);
        setbuild(getBuild());
    }

    @Override
    protected void firstImplementation() {
        super.firstImplementation();
        addDefInsertButton();
        addSearchOptions();
    }

    private ConsultTableBuild getBuild(){
        String view = viewSpecs.getTable();
        if(viewSpecs.isMain())
            return new ConditionedRefTableBuild(view,critValue,parentSpecs);
        else if(viewSpecs.getPrimaryskey().size() == 2)
            return new IntermediaryCrudBuild(view,critValue,parentSpecs);
        else
            return new ConditionedConsultTable(view,critValue,parentSpecs);
    }


}
