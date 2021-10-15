/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.util.ArrayList;

/**
 *
 * @author escal
 */
public class Table {
    
    ArrayList<String> columnTitles;
    ArrayList<ArrayList<String>> registers;
    
    public Table(ArrayList<String> columnTitles,ArrayList<ArrayList<String>> registers){
        this.columnTitles = columnTitles;
        this.registers = getNewRegisters(registers);
    }
    public Table(Table model){
        this.columnTitles = new ArrayList<>(model.getColumnTitles());
        this.registers = new ArrayList<>(model.getRegisters());
    }

    private ArrayList<ArrayList<String>> getNewRegisters(ArrayList<ArrayList<String>> registers){
        ArrayList<ArrayList<String>> newRegisters  = new ArrayList<>();

        for (ArrayList<String> list: registers)
            newRegisters.add(new ArrayList<>(list));

        return newRegisters;
    }

    public ArrayList<ArrayList<String>> getRgistersCopy(){
        return getNewRegisters(registers);
    }
    
    public String getUniqueValue(){
        if(isEmpty())
            return null;
        else
             return getRegister(0).get(0);
    }
    
    public ArrayList<String> getColumn(int index){
        if ( rowCount() == 0 || columnCount() == 0)
            return new ArrayList<>();

        ArrayList<String> column = new ArrayList();
         for(ArrayList<String> register: registers)
            column.add(register.get(index));
             
         return column;
       
    }
    
    public boolean isEmpty(){
        
        return registers.isEmpty();
    }
    
    public void setColumnTitles(ArrayList<String> newTitles){
        columnTitles = new ArrayList(newTitles);
        
    }
    public String getTitle(int index ){
        
        return columnTitles.get(index);
    }
    
    public ArrayList<String> getColumn(String columnName){
        int selectedColumnIndex = columnTitles.indexOf(columnName);

         return getColumn(selectedColumnIndex);
    }

    public int getColumnCount() {
        if(!columnTitles.isEmpty())
            return columnTitles.size();
        else if(!registers.isEmpty())
            return registers.get(0).size();
        return 0;
    }
    

    public ArrayList<String> getColumnTitles(){
        
        return new ArrayList(columnTitles);
    }
    
    public ArrayList<ArrayList<String>> getRegisters(){
        
        return new ArrayList(registers);
    }

    public void setRegisters(ArrayList<ArrayList<String>> newRegisters){
        registers = new ArrayList<>(newRegisters);
    }
    
    public ArrayList<TableRegister> getRegistersObject(){
        ArrayList<TableRegister> returnRegisters = new ArrayList();
        int size = registers.size();
        for(int i = 0; i < size;i++)
            returnRegisters.add(getRegisterObject(i));
            
        return returnRegisters;
    }
    
    public TableRegister getRegisterObject(int index){
        
        return new TableRegister(columnTitles,registers.get(index));
    }
    
    
        public TableRegister getRegister(int index){
        
        return new TableRegister(columnTitles,registers.get(index));
    }

    @Override
    public String toString() {
        System.out.println("------------------------------------");
        for (ArrayList<String> register: registers)
            System.out.println(register);

        return "";
    }

    public int rowCount(){
        return registers.size();
    }
    
    public int columnCount(){
        if(columnTitles.size() != 0)
            return columnTitles.size();
        else if (rowCount() != 0)
            if (registers.get(0).size() != 0)
                return registers.get(0).size();


        return 0;
    }
        
    public TableRegister getRegister(String tag,String value){
        TableRegister returnRegister;
        ArrayList<String> coincidentRegister = new ArrayList();
        short columnIndex = (short) columnTitles.indexOf(tag);
        
        for(ArrayList<String> currentRegister:registers){
            String cell = currentRegister.get(columnIndex);
            if (cell.equals(value))
                coincidentRegister = new ArrayList(currentRegister);
        }
        returnRegister =new  TableRegister(columnTitles,coincidentRegister);
        return returnRegister;
    }

    private int getColumnIndex(String col){
        return columnTitles.indexOf(col);
    }

    public void removeColumn(String index){
        removeColumn(getColumnIndex(index));
    }

    public void addColumn(int index,String title,ArrayList<String> extraCol){
        columnTitles.add(index,title);
        int size = extraCol.size();
        for (int i = 0; i < size; i++){
            ArrayList<String> currentRegister = registers.get(i);
                currentRegister.add(index,extraCol.get(i));

        }
    }

    public void removeColumn(int index){
        if(index > -1) {
            columnTitles.remove(index);
            for (ArrayList<String> register : registers)
                register.remove(index);
        }
    }

}
