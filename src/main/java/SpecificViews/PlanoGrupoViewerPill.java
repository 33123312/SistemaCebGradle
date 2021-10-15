package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.ConsultTableBuild;
import sistemaceb.LinkedTable;
import sistemaceb.RegisterDetailTable;
import sistemaceb.form.MultipleAdderConsultBuild;

public class PlanoGrupoViewerPill extends RegisterDetailTable {

    public PlanoGrupoViewerPill() {
        super("plan_grupo");
    }

    @Override
    public void initRegister( String critKey, ViewSpecs parentSpecs) {
        super.initRegister(critKey, parentSpecs);
        setbuild(getBuild());
    }


    private ConsultTableBuild getBuild(){
        MultipleAdderConsultBuild build = new MultipleAdderConsultBuild(viewSpecs.getTable(),critValue,parentSpecs);
            build.setBehavior(new LinkedTable(viewSpecs.getTable(),"Clave_plan",build.getOutputTable()));
        return build;
    }


}
