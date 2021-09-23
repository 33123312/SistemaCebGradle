package SpecificViews;

import Generals.BtnFE;
import JDBCController.*;
import RegisterDetailViewProps.GrupoRegisterProps;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.RegisterDetailView;
import sistemaceb.Window;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
