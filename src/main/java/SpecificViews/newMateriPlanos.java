package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.ViewSpecs;
import sistemaceb.PlanEstudioMateriasUpdater;
import sistemaceb.RegisterDetailTable;
import sistemaceb.form.MultipleAdderConsultBuild;

import java.util.ArrayList;

public class newMateriPlanos extends RegisterDetailTable {

    ArrayList<PlanEstudioMateriasUpdater> updaters;

    public newMateriPlanos(String view, String critKey, ViewSpecs parentSpecs) {
        super(view, critKey, parentSpecs);
        restartUpdaters();
        setbuild(getBuild(view));
        addDefInsertButton();
        addHumanTitles();

    }

    private void addHumanTitles(){
        ArrayList<String> trueTitles = new ArrayList<>();

        trueTitles.add("Clave Materia");
        trueTitles.add("Materia");
        trueTitles.add("Tipo");

        consultTable.getOutputTable().setTitles(trueTitles);
    }

    private MultipleAdderConsultBuild getBuild(String view){
        return new IntermediaryTableConsultBuild(view,critValue,parentSpecs){
            @Override
            public void updateSearch() {
                super.updateSearch();
                makeUpdates();
                restartUpdaters();
            }
        };
    }

    private void makeUpdates(){
        for (PlanEstudioMateriasUpdater updater: updaters)
            updater.makeUpdate();
    }

    private void restartUpdaters(){
        updaters = new ArrayList();
        ArrayList<String> grupos = getGrupos();
        for (String grupo:grupos)
            updaters.add(new PlanEstudioMateriasUpdater(grupo));

    }

    private ArrayList<String> getGrupos(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] toBring = new String[]{"grupo"};

        String[] cond = new String[]{"plan"};

        String[] value = new String[]{critValue};

        return consulter.bringTable(toBring,cond,value).getColumn(0);
    }
}
