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
public class StaticOptionsGetter implements optionsGetter{
   ViewSpecs specs; 
   Table options;
   
    
   public StaticOptionsGetter(ViewSpecs specs){
       this.specs = specs;
       options = null;
   }
    
    public void checkInOptionsTable(String tag){
        DataBaseConsulter tagIdConsulter = new DataBaseConsulter("viewSpecs.tags");
        //options = tagIdConsulter.call("viewsSpecs.getOptions( '" + tag + " ', '" + specs.getTable() + " ')");
        options = new Table(new ArrayList(),new ArrayList());
    }
    
   @Override
    public boolean hasOptions(String tag){
        checkInOptionsTable(tag);
        return !options.isEmpty() ;
    }
    
   @Override
    public Table getOptions(String tag){
       
        return options;
    }

    
}
