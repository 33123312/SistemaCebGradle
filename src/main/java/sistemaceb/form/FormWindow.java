package sistemaceb.form;

import sistemaceb.SubmitFrame;
import sistemaceb.genericEvents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class FormWindow extends Formulario{
    SubmitFrame frame;
    private final ArrayList<Section> sections;

    public FormWindow(String title){
        super();
        deployBodyContainer();
        sections = new ArrayList();
        frame = new SubmitFrame(title);
            frame.addBody(this);


        addSection();
        addEvents();
        frame.addCloseButton();
        frame.addAcceptButton();
        frame.setVisible(true);
    }

    private void deployBodyContainer(){

        setLayout(new GridLayout(7,1));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

    }

    private void addSection(){
        Section newSection = new Section(2);
        sections.add(newSection);
        add(newSection);
    }

    public SubmitFrame getFrame(){
        return frame;
    }

    private void addEvents(){
        frame.addOnAcceptEvent(

                new genericEvents(){
                    public void genericEvent(){
                        if(!hasErrors())
                            manageData();
                    }
                }
        );

        frame.addOnDimissEvent(
                new genericEvents(){
                    public void genericEvent(){
                        frame.closeForm();
                    }
                }
        );

    }

    int sectionsCounter = 0;

    @Override
    protected void addElement(FormElement element) {
        super.addElement(element);
        Section currentSection = sections.get(sectionsCounter);

            if (currentSection.isntFull())
                currentSection.addElement(element);
            else{
                addSection();
                sectionsCounter++;
                addElement(element);
        }
    }

}
