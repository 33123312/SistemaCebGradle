package sistemaceb.form;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.FormResponseManager;
import sistemaceb.TagFormBuilder;
import sistemaceb.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MultipleFormWindow extends Window {

    private ArrayList<HorizontalFormPanel> forms;
    private JPanel body;
    private LinearVerticalLayout formsArea;
    private JLabel adderForm;
    private ArrayList<FormResponseManager> dataManagers;

    private final ViewSpecs specs;
    private final ArrayList<String> tags;
    boolean required;


    public MultipleFormWindow(String title, ViewSpecs specs, ArrayList<String> tags){
        super();
        this.specs = specs;
        this.tags = tags;
        this.required = true;
        dataManagers = new ArrayList<>();

        forms = new ArrayList<>();
        setTitle(title);
        setBody(getBody());
        defineAdderButton();
        addForm();
        Global.view.currentWindow.newView(this);
    }

    private void defineAdderButton(){
        adderForm = new JLabel(" + Añadir ",SwingConstants.LEFT);
            adderForm.setBorder(new EmptyBorder(10,10,10,10));
            adderForm.setForeground(new Color(129, 142, 255));
            adderForm.setFont(new Font("arial", Font.PLAIN, 25));
            adderForm.setCursor(new Cursor(Cursor.HAND_CURSOR));
            adderForm.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    addForm();
                }
            });

    }

    public void addDataManager(FormResponseManager e){
        dataManagers.add(e);
        for(HorizontalFormPanel form:forms)
            form.addDataManager(e);
    }

    private JPanel getBody(){
        body = new JPanel(new BorderLayout());
            body.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        body.add(getFormsArea(),BorderLayout.CENTER);
        body.add(getButtonsArea(),BorderLayout.SOUTH);
        return body;
    }

    private JPanel getFormsArea(){
        formsArea = new LinearVerticalLayout();
            formsArea.setBorder(BorderFactory.createEmptyBorder(0,0,40,0));

        return formsArea;
    }

    private JPanel getButtonsArea(){
        JPanel buttonsArea = new JPanel(new BorderLayout());
            buttonsArea.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
        BtnFE btnAceptar  =new BtnFE("Guardar");
            btnAceptar.setTextColor(Color.white);
            btnAceptar.setBackground(new Color(107, 117, 255));
            btnAceptar.setPadding(10,20,10,20);
            btnAceptar.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    submit();
                }
            });

        buttonsArea.add(btnAceptar,BorderLayout.EAST);

        return buttonsArea;
    }

    private void addForm(){
        HorizontalFormPanel newForm = new HorizontalFormPanel();
            new TagFormBuilder(specs,tags,newForm,required);
            addManagers(newForm);

        newForm.addLateral(getFormEliminationButton(newForm));

        formsArea.setVisible(false);
        formsArea.remove(adderForm);
        formsArea.addElement(newForm);
        formsArea.addElement(adderForm);
        formsArea.setVisible(true);
        forms.add(newForm);

    }

    private void addManagers(HorizontalFormPanel newForm){
        for(FormResponseManager manager: dataManagers)
            newForm.addDataManager(manager);
    }
    private JLabel getFormEliminationButton(HorizontalFormPanel form){
        JLabel eliminationButton = new JLabel("X");
            eliminationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            eliminationButton.setForeground(new Color(222, 58, 58));
            eliminationButton.setOpaque(true);
            eliminationButton.setBackground(new Color(255, 179, 179));
            eliminationButton.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
            eliminationButton.setFont(new Font("arial", Font.PLAIN, 20));
            eliminationButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    setVisible(false);
                    formsArea.removeElement(form);

                    forms.remove(form);

                    setVisible(true);
                }
            });
        return eliminationButton;
    }

    private void submit(){
        boolean hasError = false;
        for(HorizontalFormPanel form: forms){
            if(form.hasErrors()){
                hasError = true;
            }
        }

        if (!hasError){
            for(HorizontalFormPanel form: forms)
                form.manageData();
            Global.view.currentWindow.cut();
        }
    }

}
