
package sistemaceb.form;

import JDBCController.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class formElementWithOptions extends FormElement {
    
    protected ArrayList<EventListener> trigerEvents;
    protected ArrayList<TrigerElemetGetter> trigerElementEvents;
    protected ArrayList<String> GUIOptions;
    protected ArrayList<String> trueOptions;
    protected String trueTitle;

    private Map<String,Table> incomeOptions;
    
    public formElementWithOptions(String title){
        super(title);
        incomeOptions = new HashMap<>();
        trigerEvents = new ArrayList();
        trigerElementEvents = new ArrayList<>();
    }
    
    private void resetOptions(){
            GUIOptions = new ArrayList();
            trueOptions= new ArrayList();
    }
    
    private void setTrueOptions(ArrayList<String> trueO){
        trueOptions = trueO;

    }

    public abstract void selectEmptyResponse();

    @Override
    public String getResponse(){

        if(hasOptions()){
            if(hasTrueOptions())
                return getTrueResponse();
            else
                return getGUIResponse();
        } else
            return "";
    }

    private String getTrueResponse(){
        return getResponse(trueOptions);
    }

    public String getGUIResponse(){
        return getResponse(GUIOptions);

    }

    private String getResponse(ArrayList<String> options){
        if (somethingIsSelected())
            return options.get(getSelectedElementIndex());
        else
            return "";
    }

    public Boolean hasOptions(){
        return GUIOptions != null &&
                !GUIOptions.isEmpty();
    }

    protected void buildGUIOptions(){
        removetriggerEvent();
        removeGUIOptions();

        String trueOptionsShower;
        int size = GUIOptions.size();
        for(int i = 0;i < size;i++){
            trueOptionsShower =  GUIOptions.get(i);
           if(hasTrueOptions())
               trueOptionsShower+= " (" + trueOptions.get(i) + ")";
           addOption(trueOptionsShower);

        }
        setTrigerEvent();
    }

    protected abstract void setTrigerEvent();

    protected  abstract void removetriggerEvent();
    
    public boolean hasTrueOptions(){

        return GUIOptions != trueOptions;
    }
    
    public String getTrueTitle(){
        
        return trueTitle;
    }

    public formElementWithOptions setTrueTitle(String trueTitle){
        this.trueTitle = trueTitle;
        
    return this;
    }

    
    public ArrayList<String> getTrueOptions(){
        return new ArrayList(trueOptions);
    }

    protected void executeTrigerEvents(){

        for(EventListener currentEvent: trigerEvents)
            currentEvent.onTriger(getResponse());

        for(TrigerElemetGetter currentEvent: trigerElementEvents)
            currentEvent.onTrigger(this);
        
    }
    
    protected String convertResponseToTrue(String response){

        return trueOptions.get(GUIOptions.indexOf(response));

    }

    public void mergeOptions(String key,Table options){
        if(incomeOptions.size() == 0)
            setOptions(options);
        else
            setOptions(merge(key, options));

    }

    private void addIncomingOptions(String key,Table newOptions){
        if(incomeOptions.containsKey(key))
            incomeOptions.replace(key,newOptions);
        else
            incomeOptions.put(key, newOptions);
    }

    private Table merge(String key,Table newOptions){
        addIncomingOptions(key,newOptions);


        return  mergeValues();
    }

    private Table mergeValues(){
        ArrayList<Table> tables = new ArrayList<>(incomeOptions.values());
        Table primOptions = tables.get(0);
        ArrayList<ArrayList<String>> mergedRegisters = primOptions.getRgistersCopy();
        tables.remove(0);
        int truCol = primOptions.getColumnCount() - 1;

        for(Table table: tables){
            ArrayList<ArrayList<String>> newMergedRegisters = new ArrayList<>();

            ArrayList<ArrayList<String>> currentRegisters = table.getRegisters();
            for (ArrayList<String> currentRegister: currentRegisters)
                for (ArrayList<String> mergedRegister:mergedRegisters)
                    if(currentRegister.get(truCol).equals(mergedRegister.get(truCol))){
                        newMergedRegisters.add(new ArrayList<>(currentRegister));
                        break;
                    }

            mergedRegisters = newMergedRegisters;
        }

        return new Table(primOptions.getColumnTitles(),mergedRegisters);

    }

    private void addGuiOptionsToMerged(ArrayList<String> newTrueOptions,ArrayList<ArrayList<String>> registers){
        if(GUIOptions != trueOptions){
            ArrayList<String> newGuiOptions;
                newGuiOptions = getNewGuiData(newTrueOptions);
        int size = registers.size();
            for (int i = 0;i < size ;i++)
                registers.get(i).add(1,newGuiOptions.get(i));
        }
    }

    private ArrayList<String> getNewGuiData(ArrayList<String> newTrue){
        ArrayList<String> newGui = new ArrayList<>();
        for(String newOptions: newTrue)
            newGui.add(GUIOptions.get(trueOptions.indexOf(newOptions)));

        return newGui;

    }

    public void setGUIOptions(ArrayList<String> GUIO){
        setEnabled(true);
        GUIOptions = GUIO;
        buildGUIOptions();

    }

    private boolean gotOptions(ArrayList<String> visibleOptions) {
        if (visibleOptions.isEmpty()) {
            removeOptions();
            setEnabled(false);
            return false;
        } else
            return true;

    }
    
    public formElementWithOptions setOptions(ArrayList<String> visibleOptions,ArrayList<String> trueOptions){
        if(gotOptions(visibleOptions)){
            setTrueOptions(trueOptions);
            setGUIOptions(visibleOptions);
        }

        return this;
    }

    public formElementWithOptions setOptions(Table options){
        ArrayList<String> GUIoptions = new ArrayList(options.getColumn(0)) ;
            if(options.columnCount() == 2){
                ArrayList<String> trueOptions = new ArrayList(options.getColumn(1)) ;
                setOptions(GUIoptions,trueOptions);
                setTrueDefTitle(options.getColumnTitles().get(1));

            }else
                setOptions(GUIoptions);

        return this;
    }

    public void setOptions(ArrayList<String> options){
            removeOptions();
            options = new ArrayList(options);
            setOptions(options, options);

 
    }

    private void setTrueDefTitle(String newValue){
        if(trueTitle == null)
            trueTitle = newValue;

    }


    public abstract void setSelection(String selection);

    @Override
    public formElementWithOptions setRequired(boolean required){
        this.required = required;

    return this;
    }
    
    
    public formElementWithOptions addTrigerEvent(EventListener event){
        trigerEvents.add(event);
        return this;
    }

    public formElementWithOptions addTrigerGetterEvent(TrigerElemetGetter event){
        trigerElementEvents.add(event);
        return this;
    }

        
    public void removeOptions(){
        resetOptions();
        removeGUIOptions();
    }

    protected abstract boolean somethingIsSelected();

    public abstract void removeGUIOptions();
    
    public abstract void addOption(String option);


    public abstract int getSelectedElementIndex();



}
