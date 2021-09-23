/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;


import JDBCController.dataType;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JTextField;


/**
 *
 * @author escal
 */
public class Input extends FormElement{
    
    private final JTextField textField;
    private final dataType type;
  
    public Input(String title,dataType type){
        super(title);
        addWrongTypeError();
        textField= deployTextField();   
        addElement(textField);
        this.type = type;
      
    }
    
    private void addWrongTypeError(){
        this.addErrorChecker(new ErrorChecker(){
            @Override
            public String checkForError(String response) {
                String currentValue = getResponse();
                    if(isRightType(currentValue))
                        return "";
                    else
                        return "Tipo de Dato incorrecto";
                }
            }
        );
    }
    
    private  JTextField deployTextField(){
        
        JTextField localTextField = new JTextField();
            localTextField.setForeground(new Color(120,120,120));
            localTextField.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),2));
            localTextField.setPreferredSize(new Dimension(70,30));
            
            
            return localTextField;
        
    }
    
    @Override
    protected String getResponseConfig(){
        
        return textField.getText().trim();
        
    }

    @Override
    public void setResponse(String txt) {
        setText(txt);
    }

    private Input setText(String text){
        textField.setText(text);
        return this;
    }


    private boolean isRightType(String response){
        switch(type){
            case INT:
                try{
                    Integer.parseInt(response);
                }catch(NumberFormatException e){
                    return false;
                }
                break;

            case FLOAT:
                try{
                    Double.parseDouble(response);
                }catch(NumberFormatException e){
                   return false;
                }
                break;



        }

        return true;
                
}
}
