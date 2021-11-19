/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import JDBCController.ViewSpecs;

import java.awt.event.MouseAdapter;


public class ConditionedConsultTable extends  NewRegisterGenTableBuild {

    public ConditionedConsultTable(String view, String critKey, ViewSpecs dadSpecs) {
        super(view, critKey, dadSpecs);
        setBehavior(new CrudTable(this));
        getOutputTable().showAll();
    }

    @Override
    public BtnFE getInsertButton() {
        return super.getInsertButton(getButtonEvent());
    }

    protected MouseAdapter getButtonEvent() {
        return getNewRegisterGenEvent();
    }
}
