package SpecificViews;
import JDBCController.ViewSpecs;
import sistemaceb.ConsultTableBuild;
import sistemaceb.LinkedTable;
import sistemaceb.RegisterDetailTable;
import sistemaceb.form.MultipleAdderConsultBuild;
import sistemaceb.keyHiddedCoonsTableBuild;

public class PlanesestudioViewerMat extends RegisterDetailTable {

    public PlanesestudioViewerMat() {
        super("planes_estudio_materias");

    }

    @Override
    public void initRegister( String critKey, ViewSpecs parentSpecs) {
        super.initRegister(critKey, parentSpecs);
        setbuild(getBuild());
    }

    private ConsultTableBuild getBuild(){
        MultipleAdderConsultBuild build = new MultipleAdderConsultBuild(viewSpecs.getTable(),critValue,parentSpecs);
        build.setBehavior(new LinkedTable(viewSpecs.getTable(),"clave_plan",build.getOutputTable()));
        return build;
    }

}


