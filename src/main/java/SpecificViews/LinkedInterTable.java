package SpecificViews;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.TableRow;
import sistemaceb.FormResponseManager;
import sistemaceb.LinkedTable;
import sistemaceb.TagFormBuilder;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.MultipleAdderConsultBuild;
import sistemaceb.primaryKeyedTable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LinkedInterTable extends LinkedTable {
    MultipleAdderConsultBuild build;

    public LinkedInterTable(MultipleAdderConsultBuild build) {
        super(build.getViewSpecs().getTable(), build.getRelatedKey(), build.getOutputTable());
        this.build = build;
    }

    @Override
    protected void setReferences(primaryKeyedTable table) {
        super.setReferences(table);
        table.getFactory().addRightClickEvnt(new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow row) {
                int key = row.getKey();
                DesplegableMenuFE menu = new DesplegableMenuFE(){
                    @Override
                    public BtnFE buttonsEditor(BtnFE button) {
                        button.setTextColor(Color.black);
                        return super.buttonsEditor(button);
                    }
                };
                if (!getModificableCols().isEmpty())
                    menu.addButton("Modificar", new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            modifyElement(getPrimaryKey(key));
                        }
                    });

                menu.addButton("Remover", new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        removeElemt(getPrimaryKey(key));
                    }
                });
                menu.showMenu();
            }
        });
    }

    private ArrayList<String> getModificableCols(){
        String[] cond = new String[]{build.critCol,build.getRelatedKey()};
            ArrayList<String> primaryKeys = new ArrayList<>(Arrays.asList(cond));

        ArrayList<String> colsToMod = build.getViewSpecs().getTableTags();
            colsToMod.removeAll(primaryKeys);

        return colsToMod;
    }

    private void modifyElement(String selectedKey){
        String[] cond = new String[]{build.critCol,build.getRelatedKey()};
        ArrayList<String> colsToMod = getModificableCols();

        if (!colsToMod.isEmpty()){

            String[] val = new String[]{build.critValue,selectedKey};
            FormWindow formWindow  = new FormWindow("Modificar Registro");
                new TagFormBuilder(build.getViewSpecs(),colsToMod,formWindow,true);
                formWindow.setDefaultValues(colsToMod,getDefValues(cond,val,colsToMod));

            formWindow.addDataManager(new FormResponseManager() {
                @Override
                public void manageData(Formulario form) {
                    Map<String,String> data = form.getData();

                    ArrayList<String> colToMod = new ArrayList<>(data.keySet());
                    ArrayList<String> valToMod = new ArrayList<>(data.values());

                    try {
                        build.getViewSpecs().getUpdater().update(
                                colToMod,
                                valToMod,
                                new ArrayList(Arrays.asList(cond)),
                                new ArrayList(Arrays.asList(val))
                       );

                        build.updateSearch();
                        formWindow.getFrame().closeForm();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();

                    }
                }
            });
        }
    }

    private ArrayList<String> getDefValues(String[] cond,String[] val,ArrayList<String> colsToMod){

        ArrayList<String> rawCols = viewSpecs.getCol(colsToMod);
        DataBaseConsulter consulter = new DataBaseConsulter(build.getViewSpecs().getTable());

        String[] colsToB = rawCols.toArray(new String[rawCols.size()]);

        return consulter.bringTable(colsToB,cond,val).getRegister(0).getValues();

    }

    protected void removeElemt(String selectdKey){
        String interTable = build.getViewSpecs().getTable();

        ArrayList<String> cols = new ArrayList<>();
            cols.add(build.getParentAsColumn());
            cols.add(build.getRelatedKey());

        ArrayList<String> values = new ArrayList<>();
            values.add(build.critValue);
            values.add(selectdKey);

        cols = build.getViewSpecs().getCol(cols);
        try {
            new ViewSpecs(interTable).getUpdater().delete(cols,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        build.updateSearch();
    }
}
