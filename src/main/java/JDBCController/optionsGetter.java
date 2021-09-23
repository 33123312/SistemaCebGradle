/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

/**
 *
 * @author escal
 */
public interface optionsGetter {
        
    public boolean hasOptions(String Tag);
    
    public Table getOptions(String Tag);

    
}
