package SpecificViews;

import JDBCController.TableRegister;
import JDBCController.ViewSpecs;

public class OperationInfoPanel {
    private ViewSpecs viewSpecs;
    private String keyValue;
    private TableRegister registerDetail;


    OperationInfoPanel(
            ViewSpecs viewSpecs,
            String keyValue){

        this.viewSpecs = viewSpecs;
        this.keyValue = keyValue;


    }

    public boolean hasRegisterDetail(){
        return registerDetail != null;
    }

    public void setRegisterDetail(TableRegister registerDetail) {
        this.registerDetail = registerDetail;
    }

    public TableRegister getRegisterDetail() {
        return registerDetail;
    }

    public String getKeyValue() {
        return keyValue;
    }


    public ViewSpecs getViewSpecs() {
        return viewSpecs;
    }
}
