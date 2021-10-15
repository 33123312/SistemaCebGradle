package sistemaceb;

import JDBCController.ViewSpecs;
import SpecificViews.LinkedInterTable;
import sistemaceb.form.MultipleAdderConsultBuild;

public class IntermediaryCrudBuild extends MultipleAdderConsultBuild {
    public IntermediaryCrudBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
        setBehavior(new LinkedInterTable(this));
    }
}
