/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.OptionsGetter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author escal
 */
public class DataBaseSearcher {
    protected final DataBaseConsulter consulterView;
    protected DataBaseConsulter visibleTagsConsulter;

    protected ArrayList<TagToSearch> currentTagsToExactSearch;
    protected ArrayList<TagToSearch> currentTagsToAproximatedSearch;

    protected String table;
    protected ViewSpecs specs;

    private OptionsGetter exctTypeChecker;

    public class TagToSearch {
        private String tag;
        private String value;
        private boolean isPermanent;

        public TagToSearch(String tag, String value) {
            isPermanent = false;
            this.tag = tag;
            this.value = value;
        }

        @Override
        public String toString() {
            return "TagToSearch{" +
                    "tag='" + tag + '\'' +
                    ", value='" + value + '\'' +
                    ", isPermanent=" + isPermanent +
                    '}';
        }

        public String getTag() {
            return tag;
        }

        public String getValue() {
            return value;
        }

        public void setAsPermnent() {
            isPermanent = true;
        }

        public boolean isPermanent() {
            return isPermanent;
        }

    }

    public DataBaseSearcher(String table) {
        this.table = table;
        specs = new ViewSpecs(table);

        currentTagsToAproximatedSearch = new ArrayList<>();
        currentTagsToExactSearch = new ArrayList<>();

        String view = specs.getInfo().getView();
        String visibleView = specs.getInfo().getVisibleView();

        consulterView = new DataBaseConsulter(view);

        if (!view.equals(visibleView))
            visibleTagsConsulter = new DataBaseConsulter(visibleView);

        exctTypeChecker = new OptionsGetter(specs);
        newSearch();

    }

    public ViewSpecs getViewSpecs() {

        return specs;
    }

    public void newSearch() {
        currentTagsToExactSearch = getNewTags(currentTagsToExactSearch);
        currentTagsToAproximatedSearch = getNewTags(currentTagsToAproximatedSearch);

    }

    private ArrayList<TagToSearch> getNewTags(ArrayList<TagToSearch> tags) {
        ArrayList<TagToSearch> newTags = new ArrayList<>();

        for (TagToSearch tag : tags)
            if (tag.isPermanent())
                newTags.add(tag);

        return newTags;

    }

    private boolean listContainsKey(ArrayList<TagToSearch> tags, String tagToCompare) {
        if (getIndex(tags, tagToCompare) > -1)
            return true;

        return false;

    }

    private int getIndex(ArrayList<TagToSearch> tags, String tagToCompare) {

        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getTag().equals(tagToCompare))
                return i;
        }

        return -1;
    }

    public void removeSearch(String column) {

        if (listContainsKey(currentTagsToExactSearch, column))
            currentTagsToExactSearch.remove(getIndex(currentTagsToExactSearch, column));
        else if (listContainsKey(currentTagsToAproximatedSearch, column))
            currentTagsToAproximatedSearch.remove(getIndex(currentTagsToAproximatedSearch, column));
        else
            System.err.println("La columna buscada no se puede remover:"
                    + column + " no pertenece a la tabla " + table);

    }

    public void addSearch(Map<String, String> data) {
        for (Map.Entry<String, String> thisData : data.entrySet()) {
            addSearch(thisData.getKey(), thisData.getValue());
        }
    }

    private void addSearch(TagToSearch tag) {
        if (exctTypeChecker.hasOptions(tag.getTag()) || specs.getPrimaryskey().contains(tag.getTag()))
            currentTagsToExactSearch.add(tag);
        else
            currentTagsToAproximatedSearch.add(tag);
    }

    public void addSearch(String tagToSearch, String value){
        addSearch(new TagToSearch(tagToSearch, value));
    }

    public void addPermanentSearch(String tagToSearch, String value){
        TagToSearch tag = new TagToSearch(tagToSearch, value);
            tag.setAsPermnent();
        addSearch(tag);
    }

    public infoPackage getSearch() {

        return getSearch(new ArrayList());
    }

    private String[] getTags(ArrayList<TagToSearch> tags){
        ArrayList<String> tagsToK = new ArrayList<>();
        for (TagToSearch tag:tags)
            tagsToK.add(tag.getTag());

        ArrayList<String> trueTags = specs.getCol(tagsToK);

        return trueTags.toArray(new String[trueTags.size()]);
    }

    private String[] getValues(ArrayList<TagToSearch> tags){
        int size = tags.size();
        String[] titles = new String[size];
        for (int i = 0;i < size;i++){
            TagToSearch tag = tags.get(i);
            titles[i] = tag.getValue();
        }

        return titles;
    }


    private Table getViewExactFiltered(){
        Table unsortedViewsTable;

        if(currentTagsToExactSearch.isEmpty())
            unsortedViewsTable = consulterView.bringTable();

        else{
            String[] viewTagTypesArray = getTags(currentTagsToExactSearch);

            String[] viewTagValuesArray = getValues(currentTagsToExactSearch);

            unsortedViewsTable= consulterView.bringTable(
                    viewTagTypesArray,
                    viewTagValuesArray);
        }

        unsortedViewsTable.setColumnTitles(specs.getTag(unsortedViewsTable.getColumnTitles()));

        return unsortedViewsTable;
    }

    private Table getUnsortedVisible(){
        Table unsortedVisible = visibleTagsConsulter.bringTable();
        unsortedVisible.setColumnTitles(specs.getTag(unsortedVisible.getColumnTitles()));
        return unsortedVisible;
    }

    public infoPackage getSearch(ArrayList<String> columnsToBring) {

        Table unsortedView = getViewExactFiltered();

        Table unsortedVisible;

        if (visibleTagsConsulter != null){
            unsortedVisible = getUnsortedVisible();
            merge(unsortedVisible, unsortedView);
        }else
            unsortedVisible = new Table(unsortedView);

        if(!currentTagsToAproximatedSearch.isEmpty())
            return getRatedValues(unsortedView,unsortedVisible,columnsToBring);

        removeCols(unsortedVisible,columnsToBring);

        return new infoPackage(unsortedVisible,unsortedView);
    }

    private infoPackage getRatedValues(Table unsortedView,Table unsortedVisible,ArrayList<String> colsToBring){

        Table ratedViewRegisters;
        Table ratedVisibleRegisters;

        complexSearcher viewSearcher = getSearcher(unsortedView,specs.getViewTags());

        if (hasVisibleView())
            ratedVisibleRegisters = getRatedVisibleView(unsortedVisible,viewSearcher);
        else
            ratedVisibleRegisters = viewSearcher.getRatedNSortedRegisters();

        ratedViewRegisters = viewSearcher.getRatedNSortedRegisters();

        //removeCols(ratedViewRegisters,colsToBring);

        removeCols(ratedVisibleRegisters,colsToBring);

        return new infoPackage(ratedVisibleRegisters,ratedViewRegisters);

    }

    private boolean hasVisibleView(){
        String table = specs.getTable();

       return !table.equals(specs.getInfo().getView()) && !table.equals(specs.getInfo().getVisibleView());
    }

    private void removeCols(Table sortedTable,ArrayList<String> columnsToBring){
        if(!columnsToBring.isEmpty()) {
            ArrayList<String> visibleColummnTitles = new ArrayList(sortedTable.getColumnTitles());

            for (String col : visibleColummnTitles)
                if (!columnsToBring.contains(col))
                    sortedTable.removeColumn(col);
        }
    }

    private Table getRatedVisibleView(Table unsortedVisible,complexSearcher viewSearcher){
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
            for (TagToSearch tag : currentTagsToAproximatedSearch){
                if(columnTitles.contains(tag.getTag()))
                    viewSearcher.rate(tag.getTag(), tag.getValue());
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

    private ArrayList<String> getPrimaryKey(Table visibleTable, Table viewTable){
        ArrayList<String> prim = specs.getPrimaryskey();
        if(prim.isEmpty()){
            addTempKeys(visibleTable,viewTable);
            prim.add("primary_key");
        }

        return prim;
    }

    private int[] getPrimIndex(ArrayList<String> keys,Table res){
        int[] index = new int[keys.size()];

        for (int i = 0; i < keys.size();i++)
            index[i] = res.columnTitles.indexOf(keys.get(i));

        return index;

    }

    private void merge(Table visibleTable, Table viewTable) {
        ArrayList<String> prim = getPrimaryKey(visibleTable,viewTable);

        ArrayList<ArrayList<String>> newRegisters = new ArrayList();
        ArrayList<ArrayList<String>> visibleTableReg = visibleTable.getRegisters();

        int[] visiblePrimIndex = getPrimIndex(prim,visibleTable);
        int[] viewPrimIndex = getPrimIndex(prim,viewTable);

        ArrayList<ArrayList<String>> viewRegisters = viewTable.getRegisters();
        ArrayList<ArrayList<String>> visibleRegisters = visibleTable.getRegisters();

            for (ArrayList<String> viewRow: viewRegisters)
                for (ArrayList<String> visibleRow: visibleRegisters){
                    boolean hasCoincidence = true;
                    for (int i = 0; i < prim.size();i++){
                        int visibleKey = visiblePrimIndex[i];
                        int viewKey = viewPrimIndex[i];

                        if(!viewRow.get(viewKey).equals(visibleRow.get(visibleKey))){
                            hasCoincidence = false;
                            break;
                        }

                    }
                    if(hasCoincidence)
                        newRegisters.add(visibleRow);

                }


        visibleTable.setRegisters(newRegisters);

        if(viewTable.columnTitles.contains("primary_key")){
            viewTable.removeColumn(0);
            visibleTable.removeColumn(0);
        }

    }





}
