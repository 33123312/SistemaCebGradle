/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.dataType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author escal
 */
public abstract class FormElement extends JPanel{
    
    private final JLabel errorLabel;
    public String title;
    protected boolean required;
    protected JComponent currentElement;
    private ArrayList<ErrorChecker> errorChecker;
    private int index;

    public FormElement(String title){
        errorChecker = new ArrayList();
        setBackground(Color.white);
        this.title = title;
        addEmptyError();
        setLayout(new BorderLayout());
        errorLabel = deployErrorLabel();
        setBorder(BorderFactory.createEmptyBorder(0,20,0,20)); 
        setOpaque(false);
        
        add(deployTitle(title),BorderLayout.NORTH);
        add(errorLabel,BorderLayout.SOUTH);
        
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
        desactivateErrorMesage();
        String response = getResponseConfig();
        response.trim();
        return response;
    }
    
    protected String getResponseConfig(){
        
        return "";
    }
    
public boolean hasErrors(){
    String error = searchForError();
    boolean hasError = !"".equals(error);
    if(hasError)
        activateErrorMesage(error);
    
    return hasError;
}

public boolean isEmpty(){
    
    return getResponse().equals("");
}

private void addEmptyError(){
    addErrorChecker(new ErrorChecker(){
        @Override
        public String checkForError(String response) {
            if(response.equals(""))
                if(isRequired())
                    return "Campo Obligatorio";
                 else
                    return "";
            else
                return "";
        }
    });
}

public abstract void setResponse(String txt);

public boolean isSetted(){ return !getResponse().equals("");
}


protected String searchForError(){

    if(getResponse().equals(""))
        if(isRequired())
            return "Campo Obligatorio";
        else
            return "";
    else
        return  checkErrors();

}

private String checkErrors(){
    for(ErrorChecker checker:errorChecker){
        String error = checker.checkForError(getResponse());
        if(!error.equals(""))
            return error;
    }

    return "";
}

public FormElement addErrorChecker(ErrorChecker checker){
    errorChecker.add(checker);
    
    return this;
}



}
