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
public class ratesSorter {  
    
    
    private final ArrayList<ArrayList<String>> registers;
    private final int[] registersRates;
    
    public ratesSorter(ArrayList<ArrayList<String>> registers,int[] registersRates){
        //recibimos los nombres  de las columnas y los registros
        this.registers = new ArrayList(registers);
        // los errores de cada frase
        this.registersRates = registersRates.clone();
        
    }
    
    
    
        private int partition(int low, int high) 
    { 
        int pivot = registersRates[high];  
        int i = (low-1); // index of smaller element 
        
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (registersRates[j] < pivot) 
            { 
                i++; 
                //movimiento en ambos arreglos segun los errores de cada frase
                // swap arr[i] and arr[j] 
                int tempRate = registersRates[i]; 
                registersRates[i] = registersRates[j]; 
                registersRates[j] = tempRate; 
                
                ArrayList<String> tempRegister = registers.get(i);
                registers.set(i,registers.get(j)); 
                registers.set(j, tempRegister); 
                
                
            } 
        } 
        //movimiento en ambos arreglos segun los errores de cada frase
        // swap arr[i+1] and arr[high] (or pivot) 
        int tempRate = registersRates[i+1]; 
        registersRates[i+1] = registersRates[high]; 
        registersRates[high] = tempRate;

        ArrayList<String> tempRegister = registers.get(i+1);
        registers.set(i+1,registers.get(high)); 
        registers.set(high, tempRegister);         
        
  
        return i+1; 
    } 
  
  
    /* The main function that implements QuickSort() 
      arr[] --> Array to be sorted, 
      low  --> Starting index, 
      high  --> Ending index */
    private void sort(int low, int high) 
    { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            sort(low, pi-1); 
            sort(pi+1, high); 
        } 
    } 
    
    
    // Metodo para llamar a la funcion iterativa por primera vez
    public ArrayList<ArrayList<String>> getSortRegistersRates(){
        
      //hacemos la primer llamada
        sort (0,registersRates.length-1);
        return inv(registers);
        
    }

    private ArrayList<ArrayList<String>> inv(ArrayList<ArrayList<String>> orig){
        ArrayList<ArrayList<String>> newA = new ArrayList<>();
        for(int i = orig.size()-1; i >= 0; i--)
            newA.add(orig.get(i));

        return newA;
    }
    

    
    
}
