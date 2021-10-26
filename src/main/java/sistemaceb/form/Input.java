/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.dataType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 *
 * @author escal
 */
public class Input extends FormElement{
    
    private JTextField textField;
    private final dataType type;

    public Input(String title,dataType type,JTextField txt){
        super(title,"");
        addWrongTypeError();
        this.type = type;
        setTextField(txt);
    }
  
    public Input(String title,dataType type){
        this(title,type, new JTextField());
      
    }

    public Input(String title, dataType type, int size){
        this(title,type, new limitedImput(size));
    }

    private void setTextField(JTextField textField){
        this.textField= deployTextField(textField);
        currentElement = textField;
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeTrigerEvents();
            }
        });
        addElement(textField);
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
    
    private  JTextField deployTextField(JTextField localTextField){
        localTextField.setForeground(new Color(120,120,120));
        localTextField.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),2));
        localTextField.setPreferredSize(new Dimension(70,30));
        return localTextField;
    }

    @Override
    public void useDefval() {
        textField.setText(getDefaultValue());
    }

    @Override
    protected String getResponseConfig(){
        
        return textField.getText().trim();
    }

    @Override
    public void setResponse(String txt) {
        super.setResponse(txt);
        setText(txt);
    }

    private Input setText(String text){
        textField.setText(text);
        return this;
    }

    private boolean isRightType(String response){
        if(!response.isEmpty())
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
