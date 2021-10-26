package SpecificViews;

import JDBCController.TableRegister;
import JDBCController.ViewSpecs;

public abstract class Operation{
    public String operation;
    public  ViewSpecs viewSpecs;
    protected  String keyValue;
    protected TableRegister registerInfo;

    public Operation( OperationInfoPanel registerDetail){
        viewSpecs = registerDetail.getViewSpecs();
        keyValue = registerDetail.getKeyValue();
        registerInfo = registerDetail.getRegisterDetail();

    }

    public abstract void buildOperation();

    public ViewSpecs getViewSpecs() {


        return viewSpecs;
    }

}
