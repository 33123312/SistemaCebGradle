package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.*;
import sistemaceb.RegisterDetailTable;
import sistemaceb.RegisterDetailView;

import java.util.ArrayList;

public class OperationsManager {

    OperationInfoPanel infoPanel;
    ViewSpecs specs;

    public OperationsManager(ViewSpecs specs, String keyValue){
        infoPanel = new OperationInfoPanel(specs,keyValue );
        this.specs = specs;

    }

    public OperationsManager(ViewSpecs specs, String keyValue, TableRegister registerInfo){
        infoPanel = new OperationInfoPanel(specs,keyValue );
            infoPanel.setRegisterDetail(registerInfo);

        this.specs = specs;
    }

    public RegisterDetail getProps(String view){
        if (view.equals("grupos"))
            return new GrupoRegisterProps(infoPanel);

        if (view.equals("profesores"))
            return new ProfesoresRegisterDetail(infoPanel);

        if (view.equals("semestres"))
            return new SemestreRegisterProps(infoPanel);

        if (view.equals("alumnos"))
            return new AlumnosRegisterProps(infoPanel);

        if (view.equals("planes_estudio"))
            return new PlanosEstudioRegisterProps(infoPanel);

        return new RegisterDetail(infoPanel);
    }



}
