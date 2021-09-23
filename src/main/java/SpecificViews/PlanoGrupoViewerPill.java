package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.ConsultTableBuild;
import sistemaceb.LinkedTable;
import sistemaceb.RegisterDetailTable;
import sistemaceb.form.MultipleAdderConsultBuild;

public class PlanoGrupoViewerPill extends RegisterDetailTable {

    public PlanoGrupoViewerPill(String view, String critKey, ViewSpecs parentSpecs) {
        super(view, critKey, parentSpecs);
        setbuild(getBuild(view,critKey,parentSpecs));

    }

    private ConsultTableBuild getBuild(String view, String critKey, ViewSpecs parentSpecs){
        MultipleAdderConsultBuild build =
            new MultipleAdderConsultBuild(view,critKey,parentSpecs);
        new LinkedTable(view,build.getOutputTable());
        return build;
    }


}
