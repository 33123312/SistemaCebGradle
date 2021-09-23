/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import JDBCController.ViewSpecs;
import JDBCController.ViewUpdater;


public class ConditionedConsultTable extends  NewRegisterGenTableBuild {

    public ConditionedConsultTable(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
        new CrudTable(view, this);

        setInsertButtonEvent(getButtonEvent());

    }



    protected MouseAdapter getButtonEvent() {
        return getNewRegisterGenEvent();
    }
}
