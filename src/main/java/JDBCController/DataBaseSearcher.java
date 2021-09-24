/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.util.*;

import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.OptionsGetter;

/**
 *
 * @author escal
 */
public class DataBaseSearcher{

    protected final DataBaseConsulter consulterView;
    protected final DataBaseConsulter visibleTagsConsulter;

    protected Map<String,String> currentTagsToExactSearch;
    protected Map<String,String> currentTagsToAproximatedSearch;
    protected String view;
    protected ViewSpecs  specs ;
    
    OptionsGetter exctTypeChecker;


    public DataBaseSearcher(String view) {
        this.view = view;

        consulterView = new DataBaseConsulter(view);

        specs =  new ViewSpecs(view);
        visibleTagsConsulter = new DataBaseConsulter(specs.getInfo().getVisibleView());


        exctTypeChecker = new OptionsGetter(specs);
        newSearch();
        
    }

    public ViewSpecs getViewSpecs(){
        
        return specs;
    }
    
    public void newSearch(){
        currentTagsToExactSearch = new HashMap();
        currentTagsToAproximatedSearch = new HashMap();
        
    }
    
    public void removeSearch(String column){
        
        if(currentTagsToExactSearch.containsKey(column))
            currentTagsToExactSearch.remove(column);
        else if (currentTagsToAproximatedSearch.containsKey(column))
            currentTagsToAproximatedSearch.remove(column);
        else 
            System.err.println("La columna buscada no se puede remover:" 
                    + column + " no pertenece a la tabla " + view );

    }
    
    public void addSearch(Map<String,String>data ){
        for (Map.Entry<String,String> thisData: data.entrySet()){
            addSearch(thisData.getKey(),thisData.getValue());
        }                       
    }

    
    public void addExactSearch(String tagToSearch,String value){
        currentTagsToExactSearch.put(tagToSearch, value);
        
    }
    
    public void addSearch( String tagToSearch,String value) {
        Map listToAddNewSearch;
        
        if(exctTypeChecker.hasOptions(tagToSearch) ||
            specs.getPrimaryskey().contains(tagToSearch))
            listToAddNewSearch = currentTagsToExactSearch;
        else
            listToAddNewSearch = currentTagsToAproximatedSearch;
            
        listToAddNewSearch.put(tagToSearch, value);
 
    }
    

    public infoPackage getSearch(){
        
        return getSearch(new ArrayList());
    }


    private Table getViewExactFiltered(){
        Table unsortedViewsTable;

        if(currentTagsToExactSearch.isEmpty())
            unsortedViewsTable = consulterView.bringTable();
        else{
            Collection<String> exactValue = currentTagsToExactSearch.values();

            ArrayList<String> viewTagTypesArray = new ArrayList(currentTagsToExactSearch.keySet());
            String[] viewTagValuesArray = exactValue.toArray(new String[exactValue.size()]);

            List<String> temV = specs.getCol(viewTagTypesArray);
            String[] rr = new String[temV.size()];

            unsortedViewsTable= consulterView.bringTable(temV.toArray(rr), viewTagValuesArray);
        }

        unsortedViewsTable.setColumnTitles(specs.getTag(unsortedViewsTable.getColumnTitles()));
        return unsortedViewsTable;

    }

    public infoPackage getSearch(ArrayList<String> columnsToBring) {
        Table unsortedView = getViewExactFiltered();
       complexSearcher viewSearcher = getSearcher(unsortedView,specs.getViewTags());

        infoPackage pack;
        Table ratedViewRegisters;
        Table ratedVisibleRegisters;

        if (!visibleTagsConsulter.getTable().equals(specs.getTable()))
            ratedVisibleRegisters = getRatedVisibleView(unsortedView,viewSearcher);
        else
            ratedVisibleRegisters = viewSearcher.getRatedNSortedRegisters();
        removeCols(ratedVisibleRegisters,columnsToBring);
        ratedViewRegisters = viewSearcher.getRatedNSortedRegisters();

        pack = new infoPackage(ratedVisibleRegisters,ratedViewRegisters);
        return pack;
    }

    private void removeCols(Table ratedVisibleRegisters,ArrayList<String> columnsToBring){
        if(!columnsToBring.isEmpty()) {
            ArrayList<String> visibleColummnTitles = ratedVisibleRegisters.getColumnTitles();
            visibleColummnTitles = new ArrayList(visibleColummnTitles);

            for (String col : columnsToBring)
                visibleColummnTitles.remove(col);

            for (String col : visibleColummnTitles)
                ratedVisibleRegisters.removeColumn(col);

        }
    }

    private Table getRatedVisibleView(Table unsortedView,complexSearcher viewSearcher){
            Table unsortedVisible = visibleTagsConsulter.bringTable();

            unsortedVisible.setColumnTitles(specs.getTag(unsortedVisible.getColumnTitles()));

            merge(unsortedVisible, unsortedView);

            complexSearcher visibleSearcher = getSearcher(unsortedVisible,specs.getVisibleTags());

            mergeRates(visibleSearcher, viewSearcher);

           return visibleSearcher.getRatedNSortedRegisters();


    }

    private complexSearcher getSearcher(Table unsortedView,ArrayList<String> tags) {
        complexSearcher viewSearcher = new complexSearcher(unsortedView);

        rateIfContains(tags, viewSearcher);
        return viewSearcher;

    }


    private void rateIfContains(ArrayList<String> columnTitles, complexSearcher viewSearcher){
        if (!currentTagsToAproximatedSearch.isEmpty()){
            for (Map.Entry<String,String> tag : currentTagsToAproximatedSearch.entrySet()){
                if(columnTitles.contains(tag.getKey()))
                    viewSearcher.rate(tag.getKey(), tag.getValue());
            }
        }
    }

    private void mergeRates(complexSearcher visible,complexSearcher view){
        int[] visibleRates = visible.getRegistersRates();

        int[] viewRates = view.getRegistersRates();

        int[] mergedRates = new int[viewRates.length];

        for (int i = 0; i < viewRates.length;i++)
            mergedRates[i] = viewRates[i] + visibleRates[i];



        visible.setRates(mergedRates);
        view.setRates(mergedRates.clone());
    }

    private void addTempKeys(Table visibleTable, Table viewTable){
        ArrayList<String> tempKeys = new ArrayList();
        int rowCount = viewTable.rowCount();
        for (int i = 0; i < rowCount; i++){
            tempKeys.add(Integer.toString(i));
        }

        viewTable.addColumn(0,"primary_key",new ArrayList<>(tempKeys));
        visibleTable.addColumn(0,"primary_key",new ArrayList<>(tempKeys));

    }

    private String getPrimaryKey(Table visibleTable, Table viewTable){
        String prim = specs.getPrimarykey();
        if(prim == null){
            addTempKeys(visibleTable,viewTable);
            prim = "primary_key";
        }

        return prim;
    }

    private void merge(Table visibleTable, Table viewTable) {
        String prim = getPrimaryKey(visibleTable,viewTable);

        ArrayList<ArrayList<String>> newRegisters = new ArrayList();
        ArrayList<ArrayList<String>> visibleTableReg = visibleTable.getRegisters();

        ArrayList<String> viewTablePrim = viewTable.getColumn(prim);
        ArrayList<String> visibleTablePrim = visibleTable.getColumn(prim);

        for (String view:viewTablePrim){
            int index = visibleTablePrim.indexOf(view);
            if(index > -1){
                ArrayList<String> registerToAdd = visibleTableReg.get(index);
                newRegisters.add( registerToAdd);
                visibleTableReg.remove(index);
                visibleTablePrim.remove(index);
            }
        }
        visibleTable.setRegisters(newRegisters);

        if(viewTable.columnTitles.contains("primary_key")){
            viewTable.removeColumn(0);
            visibleTable.removeColumn(0);
        }

    }





}
