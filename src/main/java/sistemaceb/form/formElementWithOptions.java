
package sistemaceb.form;

import JDBCController.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class formElementWithOptions extends FormElement {
    
    protected ArrayList<String> GUIOptions;
    protected ArrayList<String> trueOptions;

    private Map<String,Table> incomeOptions;
    
    public formElementWithOptions(String title){
        super(title);
        incomeOptions = new HashMap<>();
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
    public String getResponseConfig(){
            if(hasTrueOptions())
                return getTrueResponse();
            else
                return getGUIResponse();

    }

    private String getTrueResponse(){
        return getResponse(trueOptions);
    }

    public String getGUIResponse(){
        return getResponse(GUIOptions);

    }

    private String getResponse(ArrayList<String> options){
        int selectedIndex = getSelectedElementIndex();

        if(selectedIndex == -1)
            return null;
        
        if(askingForEmpty(selectedIndex,options))
            return "";
        else
            return options.get(selectedIndex);

    }

    private boolean askingForEmpty(int index,ArrayList options){
        return index == options.size();
    }

    public Boolean hasOptions(){
        return GUIOptions != null &&
                !GUIOptions.isEmpty();
    }

    protected void buildGUIOptions(){
        removeGUIOptions();

        String trueOptionsShower;
        int size = GUIOptions.size();
        for(int i = 0;i < size;i++){
            trueOptionsShower =  GUIOptions.get(i);
           if(hasTrueOptions())
               trueOptionsShower+= " (" + trueOptions.get(i) + ")";
           addOption(trueOptionsShower);

        }
    }
    
    public boolean hasTrueOptions(){

        return GUIOptions != trueOptions;
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

    private boolean gotOptions(ArrayList<String> visibleOptions) {
        if (visibleOptions.isEmpty()) {
            removeOptions();
            setEnabled(false);
            return false;
        } else
            return true;

    }

    private void setGUIOptions(ArrayList<String> GUIO){
        setEnabled(true);
        GUIOptions = GUIO;
        buildGUIOptions();

    }

    protected void setOptions(ArrayList<String> visibleOptions,ArrayList<String> trueOptions){
        if(gotOptions(visibleOptions)){
            setTrueOptions(trueOptions);
            setGUIOptions(visibleOptions);
        }
    }

    public formElementWithOptions setOptions(Table options){
        System.out.println(options.getColumnTitles());
        System.out.println(options.getRegisters());
        ArrayList<String> GUIoptions = new ArrayList(options.getColumn(0));

        if(options.columnCount() == 2){
            setOptions(GUIoptions,options.getColumn(1));
            setTrueTitle(options.getColumnTitles().get(1));
        }else
            setOptions(GUIoptions);

        return this;
    }

    public formElementWithOptions setOptions(ArrayList<String> options){
            removeOptions();
            options = new ArrayList(options);
            setOptions(options, options);

        return this;
    }

    public abstract void setSelection(String selection);

    @Override
    public formElementWithOptions setRequired(boolean required){
        this.required = required;

    return this;
    }

    public void removeOptions(){
        resetOptions();
        removeGUIOptions();
    }

    public abstract void removeGUIOptions();
    
    public abstract void addOption(String option);


    public abstract int getSelectedElementIndex();



}
