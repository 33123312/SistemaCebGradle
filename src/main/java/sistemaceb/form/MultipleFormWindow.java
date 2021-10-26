package sistemaceb.form;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
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
    GridBagConstraints cons;
    private JPanel body;
    private JPanel formsArea;
    private JLabel adderForm;
    private ArrayList<FormResponseManager> dataManagers;
    private JLabel filler;

    private final ViewSpecs specs;
    private final ArrayList<String> tags;
    boolean required;


    public MultipleFormWindow(String title, ViewSpecs specs, ArrayList<String> tags, boolean required){
        super();
        this.specs = specs;
        this.tags = tags;
        this.required = required;
        dataManagers = new ArrayList<>();
        filler = new JLabel();
        add(filler);

        forms = new ArrayList<>();
        setTitle(title);
        addBody(getBody());
        defineAdderButton();
        buildNewForm();
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

    private void defineCons(){
        cons = new GridBagConstraints();
        cons.gridy = 0;
        cons.weightx = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.LINE_START;

    }

    public void addDataManager(FormResponseManager e){
        dataManagers.add(e);
        for(HorizontalFormPanel form:forms)
            form.addDataManager(e);
    }

    private JPanel getBody(){
        defineCons();
        body = new JPanel(new BorderLayout());
            body.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        body.add(getFormsArea(),BorderLayout.CENTER);
        body.add(getButtonsArea(),BorderLayout.SOUTH);
        return body;
    }

    private JPanel getFormsArea(){
        defineCons();
        formsArea = new JPanel(new GridBagLayout());
            formsArea.setBorder(BorderFactory.createEmptyBorder(0,0,40,0));

        return formsArea;
    }

    private JPanel getButtonsArea(){
        JPanel buttonsArea = new JPanel(new BorderLayout());
            buttonsArea.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
        BtnFE btnAceptar  =new BtnFE("Aceptar");
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

    public void addForm(){
        formsArea.remove(adderForm);
        buildNewForm();

    }

    private void buildNewForm(){
        remove(filler);


        HorizontalFormPanel newForm = new HorizontalFormPanel();
            new TagFormBuilder(specs,tags,newForm,required);
            addManagers(newForm);

        newForm.addLateral(getFormEliminationButton(newForm));

        formsArea.setVisible(false);
        formsArea.add(newForm,cons);
        cons.gridy++;
        formsArea.add(adderForm,cons);
        addFiller();
        formsArea.setVisible(true);
        forms.add(newForm);


    }

    private void addFiller(){
        cons.gridy++;
        cons.weighty = 1;
        formsArea.add(filler,cons);
        cons.weighty = 0;
        cons.gridy--;
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
                    formsArea.remove(forms.indexOf(form));
                    formsArea.remove(adderForm);
                    formsArea.remove(filler);

                    forms.remove(form);

                    cons.gridy--;
                    formsArea.add(adderForm,cons);
                    addFiller();
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
