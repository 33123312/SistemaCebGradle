/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import JDBCController.dataType;
import com.mysql.cj.protocol.a.NativeConstants;
import sistemaceb.FormResponseManager;
import sistemaceb.formRelationEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author escal
 */
public abstract class Formulario extends JPanel {
    protected final ArrayList<String> elementTitles;
    protected final ArrayList<FormElement> elements;
    protected final ArrayList<String> elementWithOptionsTitles;
    protected final ArrayList<formElementWithOptions> elementsWithOptions;
    protected Map<String, String> virtualParents;
    protected final List<FormResponseManager> dataManagers;
    protected final Map<String, ArrayList<String>> relations;

    private boolean usesfocus;

    protected int focusedElementIndex;

    public Formulario() {
        super();
        usesfocus = true;
        relations = new HashMap<>();
        dataManagers = new ArrayList();
        elementTitles = new ArrayList();
        elements = new ArrayList();
        elementWithOptionsTitles = new ArrayList();
        elementsWithOptions = new ArrayList();
        virtualParents = new HashMap<>();

    }

    public int getElementCount(){
        return elements.size();
    }

    public FormElement getElement(int i){
        return elements.get(i);
    }

    public void setDefaultValues(ArrayList<String> titles, ArrayList values){
        for (String element:titles)
             setValue(element,titles,values);
    }

    private void setValue(String title,ArrayList<String> titles,ArrayList<String> values) {
        FormElement element = getElement(title);
        if (element.hasBeenModified())
            return;
        else{
            ArrayList<String> parents = getParents(title);
            for (String parent:parents)
                setValue(parent,titles,values);

            String value = values.get(titles.indexOf(title));
            element.setDefaultValue(value);

        }
    }

    private boolean parentsAreSetted(String tag){
        ArrayList<String> parents = getParents(tag);
        for (String parent: parents){
            boolean parentIsSetted = getElement(parent).hasBeenModified();
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

    public HourInput addHourInput(String title){
        HourInput input = new HourInput(title);
        addElement(title,input);

        return input;
    }
    
    public DesplegableMenu addDesplegableMenu(String title){
        
        DesplegableMenu newMenu = new DesplegableMenu(title);
        addElement(title,newMenu);
        addElementWithOptions(title,newMenu);
        
        return newMenu;
    }

    public boolean hasBeenModified(){
        for (FormElement element:elements)
            if (element.hasBeenModified())
                return true;

        return false;
    }
    
    private void addElement(String title,FormElement newElement){
        if(!elementTitles.contains(title)){
            newElement.setIndex(elements.size());
            elements.add(newElement);
            elementTitles.add(title);
            addElement(newElement);
        }
    }
    
    private void addElementWithOptions(String title,formElementWithOptions newElement){
        elementsWithOptions.add(newElement);
        elementWithOptionsTitles.add(title);     
        
    }

    private boolean isVirtual(String title){
        return virtualParents.containsKey(title);
    }

    public void addElementRelation(
            String parentElementName,
            String childElementName,
            formRelationEvent event){

        formElementWithOptions childElement= getElementWithOptionsFromTitle(childElementName);

            TrigerElemetGetter triggerEvent = new TrigerElemetGetter() {
                @Override
                public void onTrigger(FormElement element) {
                    if(!(parentsAreSetted(childElementName) && isVirtual(childElementName)))
                        event.getNewOptions(element.getAbsoluteResponse(),childElement);
                }
            };

            if(virtualParents.containsKey(parentElementName)){
                Input auxCtrl = new Input("",dataType.VARCHAR);
                    auxCtrl.setResponse(virtualParents.get(parentElementName));

                triggerEvent.onTrigger(auxCtrl);
            } else {
                addRelation(parentElementName,childElementName);
                formElementWithOptions parentElement= getElementWithOptionsFromTitle(parentElementName);
                parentElement.addTrigerGetterEvent(triggerEvent);
            }
    }

    private void addRelation(String parent,String child){
        if(!relations.containsKey(parent))
            relations.put(parent,new ArrayList<>());

        relations.get(parent).add(child);
    }


    public void removeElement(int i){
        if(elementWithOptionsTitles.contains(elementTitles.get(i))){
            int indOp = elementWithOptionsTitles.indexOf(elementTitles.get(i));
            elementsWithOptions.remove(indOp);
            elementWithOptionsTitles.remove(indOp);
        }

        elements.remove(i);
        elementTitles.remove(i);

    };

    public void removeElement(String title){

        int index =  elementTitles.indexOf(title);
        if(index > -1)
            removeElement(index);

    };

    public void setUsesfocus(boolean usesfocus) {
        this.usesfocus = usesfocus;
    }

    protected void addElement(FormElement element){

        element.getCurrentElement().
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                super.focusGained(e);
                focusedElementIndex = elements.indexOf(element);
                }
            });

        element.addTrigerGetterEvent(new TrigerElemetGetter() {
            @Override
            public void onTrigger(FormElement element) {
                changeFocusToNext();
            }
        });
    };

    public void changeFocusTo(int i){
        if(usesfocus)
        changeFocusTo(elements.get(i).currentElement);
    }
    public void changeFocusTo(JComponent comp){

        comp.requestFocus();
    }

    private JComponent lastElementToFocus;

    public void setLastElementToFocus(JComponent lastElementToFocus) {
        this.lastElementToFocus = lastElementToFocus;
    }

    private void changeFocusToNext(){
        int nextIndex = focusedElementIndex+1;

        if(!elements.get(focusedElementIndex).currentElement.hasFocus())
           return;

        if (nextIndex < elements.size()) {
            if (elements.get(nextIndex).getCurrentElement().isEnabled())
                changeFocusTo(nextIndex);
        }
        else if(lastElementToFocus != null)
            changeFocusTo(lastElementToFocus);

    }

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
            if(validateElement(element))
                data.put(element.getTrueTitle(),element.getResponse());

        return data;
    }

    public Map<String,String> getGUIData(){
        Map<String,String> data = new HashMap();
        for(FormElement element: elements)
            if(validateElement(element))
                data.put(element.getTitle(),element.getResponse());

        return data;
    }

    private boolean validateElement(FormElement element){
       return !element.hasErrors() && element.hasBeenModified();
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
