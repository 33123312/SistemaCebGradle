package SpecificViews;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.DBU;
import JDBCController.DataBaseUpdater;
import JDBCController.ViewSpecs;
import sistemaceb.LinkedTable;
import sistemaceb.form.MultipleAdderConsultBuild;
import sistemaceb.primaryKeyedTable;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class LinkedInterTable extends LinkedTable {
    MultipleAdderConsultBuild build;

    public LinkedInterTable(String originalTable,MultipleAdderConsultBuild build) {
        super(originalTable, build.getRelatedKey(), build.getOutputTable());
        this.build = build;
    }

    @Override
    protected void setReferences(primaryKeyedTable table) {
        super.setReferences(table);
        table.addRightRowEvents(new primaryKeyedTable.clickedRowEvent(){
            @Override
            public void onClick(int key) {
                DesplegableMenuFE menu = new DesplegableMenuFE(){
                @Override
                public BtnFE buttonsEditor(BtnFE button) {
                    button.setTextColor(Color.black);
                    return super.buttonsEditor(button);
                }
            };
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

    private void removeElemt(String selectdKey){
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
