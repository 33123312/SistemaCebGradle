/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.Table;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;

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
    protected Table currentRegisters;

    public primaryKeyedTable(){
        super();
    }

    public void setRows(ArrayList<ArrayList<String>> rows,Table backRegisters) {
        setRows(rows);
        currentRegisters = new Table(backRegisters);
    }

    public ArrayList<String> getTrueData(int index){
        return new ArrayList(currentRegisters.getRegisters().get(index));
    }

}
