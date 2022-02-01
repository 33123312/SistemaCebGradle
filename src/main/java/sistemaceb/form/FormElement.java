/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author escal
 */
public abstract class
FormElement extends JPanel{
    
    private final JLabel errorLabel;
    private String title;
    private String trueTitle;
    protected boolean required;
    protected JComponent currentElement;
    private ArrayList<ErrorChecker> errorChecker;
    private int index;
    protected ArrayList<TrigerElemetGetter> trigerElementEvents;
    protected String defaultValue;

    public FormElement(String title){
        defaultValue = "";
        trigerElementEvents = new ArrayList();
        errorChecker = new ArrayList();
        setBackground(Color.white);
        this.title = title;
        trueTitle = title;
        setLayout(new BorderLayout());
        errorLabel = deployErrorLabel();
        setBorder(BorderFactory.createEmptyBorder(0,20,0,20)); 
        setOpaque(false);
        add(deployTitle(title),BorderLayout.NORTH);
        add(errorLabel,BorderLayout.SOUTH);
        
    }

    public FormElement setTrueTitle(String trueTitle) {
        this.trueTitle = trueTitle;
        return this;
    }




    public String getDefaultValue() {
        return defaultValue;
    }

    protected void executeTrigerEvents(){
        for(TrigerElemetGetter currentEvent: trigerElementEvents)
            currentEvent.onTrigger(this);

    }

    public String getTrueTitle(){
        return trueTitle;
    };

    public FormElement addTrigerGetterEvent(TrigerElemetGetter event){
        trigerElementEvents.add(event);
        return this;
    }


    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;

    }

    public String getTitle(){
        return title;
    }
    
    public FormElement setRequired(boolean required){
            this.required = required;
            
        return this;
    }
    
    public boolean isRequired(){
        return required;
    }

        private  JLabel deployTitle(String title){
        
        JLabel titleContainer = new JLabel(title);
            titleContainer.setFont(new Font("arial", Font.BOLD, 13));
            titleContainer.setForeground(new Color(70,70,70));
            titleContainer.setBorder((BorderFactory.createEmptyBorder(0,0,10,0)));


        return titleContainer;
    }
        
    private JLabel deployErrorLabel(){
       JLabel errorMesage = new JLabel();
            errorMesage.setSize(100,10);
            errorMesage.setFont(new Font("arial", Font.PLAIN, 10));
            errorMesage.setText(".");
            errorMesage.setForeground(Color.white);
            errorMesage.setBorder((BorderFactory.createEmptyBorder(3,0,3,0)));

        return errorMesage;
       
   }
    
    public  JComponent getCurrenttComponent(){

        return currentElement;
    }

    public JComponent getCurrentElement() {
        return currentElement;
    }

    protected void addElement(JComponent component){
        currentElement = component;
        add(component,BorderLayout.CENTER);
        
    }

    private void activateErrorMesage(String mesage){
        errorLabel.setText(mesage);
        errorLabel.setForeground(Color.red);
    }
     
    private void desactivateErrorMesage(){
        errorLabel.setForeground(Color.white);
    }
    
    public String getResponse(){
        String response = getAbsoluteResponse();

        if (response != null)
            if (!response.equals(defaultValue))
                return response;

        return null;
    }

    public String getAbsoluteResponse(){
        desactivateErrorMesage();
        String response = getResponseConfig();
        if(response != null)
        response.trim();

        return response;
    }

    protected String getResponseConfig(){

        return "";
    }
    
    public boolean hasErrors(){
        String error = checkErrors();
        boolean hasError = !error.isEmpty();
        if(hasError)
            activateErrorMesage(error);
        return hasError;
    }

    private String checkErrors(){
            if (hasBeenModified()){
                String response = getResponse();
                for (ErrorChecker checker : errorChecker) {
                    String error = checker.checkForError(response);
                    if (!error.isEmpty())
                        return error;
                }
            } else if(isRequired())
                return "Campo Obligatorio";

        return "";
    }

    public void setResponse(String response){

    };

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        setResponse(defaultValue);
    }

    public boolean hasBeenModified(){
        return getResponse() != null;
    }



public FormElement addErrorChecker(ErrorChecker checker){
    errorChecker.add(checker);
    
    return this;
}



}
