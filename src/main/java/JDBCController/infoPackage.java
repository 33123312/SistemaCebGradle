/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;


import java.util.ArrayList;

public class infoPackage{
    
        private final Table viewRegisters;
        private final Table visibleRegisters;

        public infoPackage(Table visibleRegisters,Table viewRegisters){
            this.viewRegisters = viewRegisters;
            this.visibleRegisters = visibleRegisters;

        }
        
        public Table getViewRegisters(){
            
            return viewRegisters;
        }
        
        public Table getVisibleRegisters(){
            
            return visibleRegisters;
        }
        

}
