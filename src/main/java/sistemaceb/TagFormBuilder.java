/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import JDBCController.dataType;
import java.util.ArrayList;
import java.util.Map;

import sistemaceb.form.FormWindow;
import sistemaceb.form.OptionsGetter;
import sistemaceb.form.formElementWithOptions;
import sistemaceb.form.Formulario;

/**
 *
 * @author escal
 */
public class TagFormBuilder {
    
    private final ViewSpecs specs;
    private final ArrayList<String> tags;
    private final OptionsGetter optionsGetter;
    private Formulario form;
    boolean required;

    public TagFormBuilder(ViewSpecs specs,ArrayList<String> tags,Formulario form,boolean required){
        this.specs = specs;
        this.tags = proccesTagsToShow(tags);
        this.required = required;
        this.form = form;
        optionsGetter = new OptionsGetter(specs);
        buildFormSections();
        form.showAll();

    }

    private ArrayList<String> proccesTagsToShow(ArrayList<String> tags){
        ArrayList<String> autoIncrTgs = specs.getAutoIncrTag();
        for (String tag:autoIncrTgs){
            int tagIndex = tag.indexOf(tag);
            if(tagIndex > - 1)
                tags.remove(tagIndex);
        }

        return tags;
    }

    public enum elementTypes{
           INPUT,
           LIST,
           DATE_INPUT

    }

    private  void buildFormSections(){
        for (String tag: tags)
                addNewElement(tag);

        setRelations();
    }

    private  void addNewElement(String tag){
        FormElementDetail element = buildElement(tag);
        addElementToForm(element);
    }

    
    private FormElementDetail buildElement(String tag){
        FormElementDetail element = new FormElementDetail();
        element.setTag(tag);
        
        if(optionsGetter.hasOptions(tag)){
            Table options = optionsGetter.getOptions(tag);
            element.setOptions(options);
            element.setType(elementTypes.LIST);
            
        } else  {
            if(specs.getColumnType(tag) == dataType.DATE)
                element.setType(elementTypes.DATE_INPUT);
            else
                element.setType(elementTypes.INPUT);
        }
        
        return element;
    }
    
    private void addElementToForm(FormElementDetail element){
        
        String tag = element.getTag();
        elementTypes type = element.getType();    
                
        switch(type){
            case INPUT:
                form.addInput(tag,element.getTagType())
                    .setRequired(required);
                break;
            case DATE_INPUT:
                form.addDateInput(tag)
                    .setRequired(required);
                break;
            case LIST:

                form.addDesplegableMenu(tag)
                    .setRequired(required)
                    .setOptions(element.getOptions());
                break;
            default:
                System.err.println("No se reconoce el tipo de tag " + type + " en el formulario");
        }
    }
    
private void setRelations(){
    Map<String,ArrayList<String>> relations = optionsGetter.getConditionedOptionsGetter().getParentalRelations();

    for(Map.Entry<String,ArrayList<String>> relation:relations.entrySet())
        setRelations(relation.getKey(),relation.getValue());
    
}

private void setRelations(String parentTag,ArrayList<String> childTags){
    for(String childTag:childTags)
        form.addElementRelation(parentTag,childTag,new formRelationEvent(){
             @Override
             public void getNewOptions(String elementInput,formElementWithOptions childElement){
                optionsGetter.getConditionedOptionsGetter().setCondition(parentTag, elementInput);
                if(optionsGetter.hasOptions(childElement.getTitle())){
                    Table childOptions = optionsGetter.getOptions(childElement.getTitle());

                    childElement.mergeOptions(parentTag,childOptions);
                }
             }
    });
}


private class FormElementDetail{
    
    private String tag;
    private Table options;
    private elementTypes type;
    private dataType tagType;

        public dataType getTagType() {
            return tagType;
        }

        
        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Table getOptions() {
            return options;
        }

        public void setOptions(Table options) {
            this.options = options;
        }

        public elementTypes getType() {
            return type;
        }

        public void setType(elementTypes type) {
            tagType = specs.getColumnType(tag);
            this.type = type;
        }
    
    
}
}


        

    

