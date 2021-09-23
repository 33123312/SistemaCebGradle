/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.StaticOptionsGetter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import JDBCController.optionsGetter;


/**
 *
 * @author escal
 */
public class OptionsGetter implements optionsGetter{
    
    private final ConditionedOptionsGetter relatedOptionsGetter;
    private final StaticOptionsGetter staticOptionsGetter;

        
    public OptionsGetter(ViewSpecs specs){
        relatedOptionsGetter = new ConditionedOptionsGetter(specs);
        staticOptionsGetter = new StaticOptionsGetter(specs);
    }
    
    public ConditionedOptionsGetter getConditionedOptionsGetter(){
        
        return  relatedOptionsGetter;
    }
    
   

        
    @Override
    public Table getOptions(String tag){

        return determinateOptionsGetter(tag).getOptions(tag);
    }
    
    @Override
    public boolean hasOptions(String tag){
        optionsGetter getter = determinateOptionsGetter(tag);

        return getter != null;
    }
    
    public optionsGetter determinateOptionsGetter(String tag){


        if(relatedOptionsGetter.hasOptions(tag))
            return relatedOptionsGetter;
        else if(staticOptionsGetter.hasOptions(tag))
            return staticOptionsGetter;
        else
            return null;

    }
    
    
}
