package sistemaceb.form;

import sistemaceb.SubmitFrame;
import sistemaceb.genericEvents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class FormWindow extends Formulario{
    private SubmitFrame frame;
    private Section currentSection;

    public FormWindow(String title){
        super();
        deployBodyContainer();
        createNewSection();

        frame = new SubmitFrame(title);
            frame.addBody(this);
            frame.addCloseButton();
            frame.addAcceptButton();

        addEvents();
    }

    private void deployBodyContainer(){

        setLayout(new GridLayout(7,1));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

    }

    private void createNewSection(){
        currentSection = new Section(2);
        add(currentSection);
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

    }


    @Override
    protected void addElement(FormElement element) {
        super.addElement(element);

            if (currentSection.isntFull())
                currentSection.addElement(element);
            else{
                createNewSection();
                addElement(element);
            }
    }

}
