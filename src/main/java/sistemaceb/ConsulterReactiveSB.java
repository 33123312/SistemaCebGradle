/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import sistemaceb.form.OptionsGetter;

/**
 *
 * @author escal
 */
public class ConsulterReactiveSB extends reactiveSearchBar{
    
    ViewSpecs specs;
    OptionsGetter getter;
    Map<String,String> GUITranslations;
    
    public ConsulterReactiveSB(ViewSpecs specs){
        this.specs = specs;
        getter= new OptionsGetter(specs);
        GUITranslations = new HashMap();
        
    }

    @Override
    public ArrayList<String> giveOptions(String selectedTag){

        if(getter.hasOptions(selectedTag))
            return getGUIOptions(selectedTag);
        else
            return new ArrayList();
        
    }
    
    private ArrayList<String> getGUIOptions(String selectedTag){
        ArrayList GUIOptions = new ArrayList();
        Table options =getter.getOptions(selectedTag);
        
        for(ArrayList<String> register :options.getRegisters()){
            String registroPK = register.get(0);
            String visibleOption = registroPK;
            if(register.size()==2)
                visibleOption+= " " + "(" + register.get(1)+ ")";
            
            GUITranslations.put(visibleOption, registroPK);
            GUIOptions.add(visibleOption);
            
        }
            
        return GUIOptions;
        
    }


    
    @Override
        protected void executeInputManagers(String tag,String value){
        if(GUITranslations.containsKey(value)){
            value = GUITranslations.get(value);
        }
        
        super.executeInputManagers(tag, value);

    }
    
    
}
