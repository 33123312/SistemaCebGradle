/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.Table;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import sistemaceb.form.DesplegableMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author escal
 *
 */
public class
primaryKeyedTable extends AdapTableFE {
    private ArrayList<clickedRowEvent> rowEvents;
    private ArrayList<clickedRowEvent> rowRightEvents;
    protected Table currentRegisters;


    public primaryKeyedTable(){
        super();
        rowEvents = new ArrayList<>();
        rowRightEvents = new ArrayList<>();
        setRowTrigger();
        setRightRowTrigger();
        
    }

    public void addNShow(ArrayList<ArrayList<String>> rows,Table backRegisters) {
        super.addNShow(rows);
        currentRegisters = new Table(backRegisters);
    }

    public ArrayList<String> getTrueData(int index){

        return new ArrayList(currentRegisters.getRegisters().get(index));
    }

    public void addRowEvents(clickedRowEvent e){
        rowEvents.add(e);
    }
    public void addRightRowEvents(clickedRowEvent e){
        rowRightEvents.add(e);
    }

    private void triggerRowEvent(int key){
        for (clickedRowEvent e: rowEvents)
            e.onClick(key);
    }

    private void triggerRightClickEvent(int key){
        for (clickedRowEvent e: rowRightEvents)
            e.onClick(key);
    }

    public interface  clickedRowEvent {
        void onClick(int key);

    }

    private void setRowTrigger() {
        addRowStyle(new StyleRowModel(){
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row component) {

                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                component.addMouseListener(new MouseAdapter(){

                    public void mouseClicked(MouseEvent e){
                        if (e.getModifiers() == MouseEvent.BUTTON1_MASK && e.getClickCount() == 1)
                            triggerRowEvent(component.getKey());
                    }
                });
                return component;
            }

        });
    }

    private void setRightRowTrigger() {
        addRowStyle(new StyleRowModel(){
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row component) {
                component.addMouseListener(new MouseAdapter(){

                    public void mouseClicked(MouseEvent e) {
                        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
                            triggerRightClickEvent(component.getKey());

                        }
                    }
                });
                return component;
            }

        });
    }
    
}
