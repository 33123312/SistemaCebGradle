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
public class complexSearcher{

    Table table;
    private int[] registersRates;
    
    public complexSearcher(Table table){
        this.table = table;
        registersRates = getRegisterRatesStorage();

    }
    
    public void setRates(int[] rates){
        registersRates = rates;
    }

    public int[] getRegistersRates(){
        return registersRates.clone();
    }
    
    public void rate(String tag,String sentenceToSearch ){
            rateWithMultipleCollumnSearch(tag,sentenceToSearch);

    }
    
    // hacemos la busqueda en las columnas especificadas
    private void rateWithMultipleCollumnSearch(String tag,String sentenceToSearch){

        int columnsIndex = table.getColumnTitles().indexOf(tag);
        ArrayList<ArrayList<String>> registers = table.getRegisters();

        for (int i = 0;i < registers.size();i++){
            ArrayList<String> currentRegister = registers.get(i);
            String sentenceToCompare = currentRegister.get(columnsIndex);
            int thisMissMatchRate;
            thisMissMatchRate = new textProccesor().sentenceCoincidence(sentenceToSearch, sentenceToCompare);

            registersRates[i]+= thisMissMatchRate;

            }
        }

    private int[] getRegisterRatesStorage() {
       int[] registersRates = new int[table.getRegisters().size()];
        for (int registersRate:registersRates )
            registersRate = 0;

        return registersRates;
    }

    
    public Table getRatedNSortedRegisters(){
        
        ArrayList<ArrayList<String>> ratedNSortedRegisters;
        ratesSorter sorter= new ratesSorter(table.getRegisters(),registersRates);
        ratedNSortedRegisters = sorter.getSortRegistersRates();
        
        return new Table(table.getColumnTitles(),ratedNSortedRegisters);
        
    }

    
}
