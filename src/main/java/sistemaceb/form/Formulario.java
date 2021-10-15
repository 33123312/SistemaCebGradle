/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;
import JDBCController.dataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import sistemaceb.FormResponseManager;
import sistemaceb.formRelationEvent;

/**
 *
 * @author escal
 */
public abstract class Formulario extends JPanel {
    protected final ArrayList<String> elementTitles;
    protected final ArrayList<FormElement> elements;

    protected final ArrayList<String> elementWithOptionsTitles;
    protected final ArrayList<formElementWithOptions> elementsWithOptions;

    protected Map <String,String> virtualParents;

    protected final List<FormResponseManager> dataManagers;

    protected final Map<String,ArrayList<String>> relations;

    public Formulario(){
        super();
        relations = new HashMap<>();
        dataManagers = new ArrayList();
        elementTitles = new ArrayList();
        elements = new ArrayList();
        elementWithOptionsTitles = new ArrayList();
        elementsWithOptions = new ArrayList();
        virtualParents = new HashMap<>();

    }
    
    public void setDefaultValues(ArrayList<String> titles, ArrayList values){
        for (String element:titles)
             setValue(element,titles,values);
    }

    private void setValue(String title,ArrayList<String> titles,ArrayList<String> values) {
        FormElement element = getElement(title);
        if (element.isSetted())
            return;
        else{
            ArrayList<String> parents = getParents(title);
            for (String parent:parents)
                setValue(parent,titles,values);

            String value = values.get(titles.indexOf(title));
            element.setResponse(value);

        }
    }

    private boolean parentsAreSetted(String tag){
        ArrayList<String> parents = getParents(tag);
        for (String parent: parents){
            boolean parentIsSetted = getElement(parent).isSetted();
            if(!parentIsSetted)
                return false;
        }

        return true;
    }

    public ArrayList<String> getParents(String child){
        ArrayList<String> parents = new ArrayList<>();
        for (Map.Entry<String,ArrayList<String>> relaation:relations.entrySet())
             if (relaation.getValue().contains(child))
                 parents.add(relaation.getKey());
        return parents;
    }

    public void addVirtualParent(String parent,String value){
        virtualParents.put(parent,value);
    }

    public formElementWithOptions getElementWithOptionsFromTitle(String title){
        if(elementWithOptionsTitles.contains(title))
            return elementsWithOptions.get(elementWithOptionsTitles.indexOf(title));
        else
            return  null;
    }

    public FormElement getElement(String element){

        return elements.get(elementTitles.indexOf(element));
    }

    public Input addInput(String title,dataType type){
        Input newInput = new Input(title,type);
        addElement(title,newInput);

        return newInput;
    }

    public Input addInput(String title,dataType type,int size){
        Input newInput = new Input(title,type,size);
        addElement(title,newInput);

        return newInput;
    }
    
    public DateInput addDateInput(String title){
        DateInput newInput = new DateInput(title);
        addElement(title,newInput);
            
        return newInput;
    }
    
    public DesplegableMenu addDesplegableMenu(String title){
        
        DesplegableMenu newMenu = new DesplegableMenu(title);
        addElement(title,newMenu);
        addElementWithOptions(title,newMenu);
        
        return newMenu;
    }
    
    void addElement(String title,FormElement newElement){
        newElement.setIndex(elements.size());
        elements.add(newElement);
        elementTitles.add(title); 
    }
    
    private void addElementWithOptions(String title,formElementWithOptions newElement){
        elementsWithOptions.add(newElement);
        elementWithOptionsTitles.add(title);     
        
    }

    private boolean isVirtual(String title){
        return virtualParents.containsKey(title);
    }

    public void addElementRelation(String parentElementName,String childElementName,formRelationEvent event){
            formElementWithOptions childElement= getElementWithOptionsFromTitle(childElementName);

            EventListener triggerEvent = new EventListener(){
                @Override
                public void onTriger(String elementInput){
                    if(!(parentsAreSetted(childElementName) && isVirtual(childElementName))){
                        event.getNewOptions(elementInput,childElement);
                    }

                }
            };

            if(virtualParents.containsKey(parentElementName)){
                triggerEvent.onTriger(virtualParents.get(parentElementName));
            } else {
                addRelation(parentElementName,childElementName);
                formElementWithOptions parentElement= getElementWithOptionsFromTitle(parentElementName);
                parentElement.addTrigerEvent(triggerEvent);
            }
    }

    private void addRelation(String parent,String child){
        if(!relations.containsKey(parent))
            relations.put(parent,new ArrayList<>());

        relations.get(parent).add(child);
    }


    public void  showAll(){
        for(FormElement section:elements)
            addElement(section);
            
    }

    protected abstract void addElement(FormElement element);
    


    public boolean hasErrors(){
        boolean error = false;
        for(FormElement element: elements)
            if(element.hasErrors())
                error =  true;

        return error;
    }
    
public Map<String,String> getData(){

    Map<String,String> data = new HashMap();

    for(FormElement element: elements)
        if(validateElement(element)){
                String title = element.getTitle();
                String response;

                if(elementWithOptionsTitles.contains(title)){
                    formElementWithOptions optionedElement = (formElementWithOptions)element;
                    if(optionedElement.hasTrueOptions())
                        title = optionedElement.getTrueTitle();
                }
                response = element.getResponse();
                data.put(title,response);
        }
    return data;
}



    public Map<String,String> getGUIData(){
        Map<String,String> data = new HashMap();
        for(FormElement element: elements){
            if(validateElement(element)){
                    String title;
                    String response;
                    if(elementWithOptionsTitles.contains(element.getTitle()) ){
                        formElementWithOptions optionedElement = (formElementWithOptions)element;
                        if(optionedElement.hasTrueOptions())
                            response = optionedElement.getGUIResponse();
                        else
                            response = element.getResponse();
                    } else{
                         response = element.getResponse();
                    }

                    title = element.getTitle();

                    data.put(title,response);
                }
            }

        return data;

    }

    private boolean validateElement(FormElement element){
        if(element.hasErrors())
            return false;
        else
            if(element.isEmpty())
              return false;

        return true;
    }

    public void manageData(){
        dataManagers.forEach((dataManager) -> {
            dataManager.manageData(this);
            });

    }

    public void addDataManager (FormResponseManager newDataManager){
        dataManagers.add(newDataManager);
    }
    
}
