package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.FormResponseManager;
import sistemaceb.PlanEstudioMateriasUpdater;
import sistemaceb.form.Formulario;

import java.util.ArrayList;

public class PlanosModificationOp extends DefaultModifyRegisterOp{

    ArrayList<PlanEstudioMateriasUpdater> updaters;

    public PlanosModificationOp(OperationInfoPanel infoPanlel){
        super(infoPanlel);

    }

    @Override
    public void buildOperation() {
        setUpdaters();
        deployModifyForm();

    }

    @Override
    protected void deployModifyForm() {
        super.deployModifyForm();
        updateForm.addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                updateGroups();
            }
        });
    }

    private void updateGroups(){
        for (PlanEstudioMateriasUpdater updater: updaters)
            updater.makeUpdate();
    }

    private void setUpdaters(){
        updaters = new ArrayList<>();
        ArrayList<String> grupos = getThisPlanGrupos();

        for (String grupo:grupos)
            updaters.add(new PlanEstudioMateriasUpdater(grupo));
    }

    private ArrayList<String> getThisPlanGrupos(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] colsToBring = new String[]{"grupo"};

        String[] cond = new String[]{"plan"};

        String[] value = new String[]{keyValue};

        Table res = consulter.bringTable(colsToBring,cond,value);

        return res.getColumn(0);

    }

}
