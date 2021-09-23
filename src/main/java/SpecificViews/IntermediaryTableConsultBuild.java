package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.form.MultipleAdderConsultBuild;
import sistemaceb.keyHiddedCoonsTableBuild;

import java.util.ArrayList;

public class IntermediaryTableConsultBuild extends MultipleAdderConsultBuild {
    String columnToRelate;
    ViewSpecs tableToRelate;
    public IntermediaryTableConsultBuild(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
        new LinkedInterTable(view,this);
    }



}
