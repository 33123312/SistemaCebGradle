package SpecificViews;

import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MultipleFormsOperation extends SubmitOperation{

    LinearVerticalLayout formsArea;
    protected ArrayList<MultipleFormsPanel> forms;


    public MultipleFormsOperation(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
    }

    protected boolean hasErrors(){
        boolean error = false;
        for (MultipleFormsPanel form:forms)
            if(form.checkErrors())
                error = true;


        return error;
    }

    protected  void submit(){
        if (!hasErrors()){
            for (MultipleFormsPanel form:forms)
                form.submit();
            cutOperation();
        }
    };

    protected void addForm(String txt,MultipleFormsPanel newForm){
        addElement(txt,newForm);
        forms.add(newForm);

    }

    protected void addElement(String txt,JComponent newForm){
        formsArea.setVisible(false);
        formsArea.addElement(getTitledArea(txt,newForm));
        formsArea.setVisible(true);
    }

    private JPanel getTitledArea(String txt, JComponent com){
        JPanel container = new JPanel(new BorderLayout());

        container.add(getLabelArea(txt),BorderLayout.NORTH);
        container.add(com,BorderLayout.CENTER);

        return container;
    }

    private JLabel getLabelArea (String title){
        JLabel titleL = new JLabel(title);
            titleL.setFont(new Font("arial", Font.PLAIN, 20));
            titleL.setForeground(new Color(108, 92, 231));
            titleL.setBorder(new EmptyBorder(10,0,10,0));

        return titleL;
    }


    @Override
    public void buildOperation() {
        super.buildOperation();
        forms = new ArrayList();
        setBody();
    }
    private JPanel setBody(){
        body.setLayout(new GridLayout(1,1));
        body.add(getFormsArea());
        return body;
    }

    private JPanel getFormsArea(){
        formsArea = new LinearVerticalLayout();
        formsArea.setBorder(BorderFactory.createEmptyBorder(0,0,40,0));

        return formsArea;
    }


}
