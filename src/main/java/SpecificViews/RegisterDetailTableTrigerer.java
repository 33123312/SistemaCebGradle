package SpecificViews;

import JDBCController.ViewSpecs;
import sistemaceb.RegisterDetailTable;

public abstract class RegisterDetailTableTrigerer {

   public String relatedTable;
   public String pillTitle;


    protected RegisterDetailTableTrigerer(String relatedTable,String pillTitle){
        this.relatedTable = relatedTable;
        this.pillTitle = pillTitle;

    }

    public String getRelatedTable() {
        return relatedTable;
    }

    public abstract RegisterDetailTable getTable(String critKey, ViewSpecs dadSpecs);


}
