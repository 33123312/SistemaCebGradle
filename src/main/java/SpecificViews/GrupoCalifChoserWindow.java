package SpecificViews;

import Generals.BtnFE;
import JDBCController.Table;
import Tables.AdapTableFE;
import Tables.TableRow;
import sistemaceb.editedScrollPanel;
import sistemaceb.form.*;
import sistemaceb.genericEvents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public abstract class GrupoCalifChoserWindow extends LinearVerticalLayout {
    protected AdapTableFE table;
    private GrupoCalifChargOpCont manager;
    private GrupoCalifChargOpCont.AlumnoManager currentManeger;
    private Formulario califForm;

    public GrupoCalifChoserWindow(GrupoCalifChargOpCont op){
        manager = op;
        manager.init();
        califForm = getForm();
        deployTable();
        deployLayout();

    }

    public boolean hasUnsavedChanges(){
        return manager
                .haveChanges();
    }

    private Formulario getForm(){
        Formulario formulario = createForm();
        FormElement firstElement = formulario.getElement(0);
        formulario.setLastElementToFocus(firstElement.getCurrentElement());
        FormElement lastElement = formulario.getElement(formulario.getElementCount()-1);

        lastElement.addTrigerGetterEvent(new TrigerElemetGetter() {
            @Override
            public void onTrigger(FormElement element) {
                int nextRowIndex = currentManeger.getIndex() + 1;
                if (nextRowIndex < table.getRows().size())
                    table.getRow(nextRowIndex).select();
                 else
                    table.getRow(0).select();

            }
        });

        formulario.setBorder(new EmptyBorder(0,0,40,0));

        return formulario;
    }

    private void addSubmitBtn(){
        JPanel buttonContainer = new JPanel(new BorderLayout());
            buttonContainer.add(getButton(),BorderLayout.EAST);
            buttonContainer.setBorder(new EmptyBorder(20,40,20,40));

        addElement(buttonContainer);
    }

    protected ErrorChecker getMaxCalifErrorComp(){
        return new ErrorChecker() {
            @Override
            public String checkForError(String response) {
                try{
                    double res = Double.parseDouble(response);
                    if (res > 10.0)
                        return "Sólo se puede calificar de 0 a 10";

                } catch (Exception e){}

                return "";

            }
        };
    }

    private BtnFE getButton(){
        BtnFE btn = new BtnFE("Guardar Calificaciones");
        btn.setBackground(new Color(52, 152, 219));
        btn.setTextColor(Color.white);
        btn.setPadding(10,10,10,10);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            submit();
            }
        });

        return btn;
    }

    protected abstract Formulario createForm();

    protected void addSelectionInput(FormElement element){
        JTextField i = (JTextField)element.getCurrentElement();
        i.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                i.selectAll();
            }
        });
    }

    private void updatePasser(){
        GrupoCalifChargOpCont.AlumnoManager auxM = currentManeger;
        if(califForm.hasBeenModified()) {
            Map<String, String> data = califForm.getData();

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    int i = 3;
                    for (String title: manager.getColsToSet()) {
                        if (data.containsKey(title)){
                            String value = data.get(title);
                            auxM.set(title, value);
                            TableRow row = table.getRow(auxM.getIndex());
                            row.setCellValue(i, value);
                        }
                        i++;
                    }

                    interrupt();
                }
            }.start();
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                ArrayList<TableRow> selected = table.getSelectedRow();
                if(!selected.isEmpty())
                    table.getRow(auxM.getIndex()).unSelect();
            }
        }.start();

    }

    private JLabel getInstructionsPanel() {
        String mess =
                "<html>Para modificar las calificaciones de un alumno, haga click en la fila correspondiente en la tabla e<br>" +
                        " ingrese sus datos en el formulario a la derecha, para pasar de un campo de texto a otro rapidamente, presione ENTER,<br>" +
                        " si todos los datos del alumno han sido llenados, se pasará automáticamente al siguiente alumno<br></html>";

        JLabel messLabel =  new JLabel(mess);
            messLabel.setBorder(new EmptyBorder(40,40,0,40));
            messLabel.setFont(new Font("Arial",Font.PLAIN,20));
            messLabel.setForeground(new Color(100,100,100));

        return messLabel;
    }

    private void changeManager(int newManIndex){
        currentManeger = manager.getManagers().get(newManIndex);
        ArrayList<String> values = currentManeger.getValuesSetted();

        for (int i = 0; i < values.size();i++){
            String currentValue = values.get(i);
            califForm.getElement(i).setDefaultValue(currentValue);
        }
    }

    private void moveScrollBar(int index){
        int newPosition = 30*index;
        scrollPanel.getVerticalScrollBar().setValue(newPosition);

    }

    protected void deployTable(){
        table = new AdapTableFE();

        scrollPanel = new editedScrollPanel();
        scrollPanel.setBorder(new EmptyBorder(0,0,0,20));

        scrollPanel.setViewportView(table);

        table.setTitles(manager.getHumanCols());
        table.setRows(manager.getRegisters());
        table.getFactory().addGralClickSelectionEvnt(new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow tableRow){
                if(!califForm.hasErrors()){
                    int currentIndex;
                    int newIndex = tableRow.getKey();

                    if(currentManeger ==  null)
                        currentIndex = -1;
                    else{
                        updatePasser();
                        currentIndex = currentManeger.getIndex();
                    }

                    if(currentIndex != newIndex){
                        moveScrollBar(newIndex);
                        changeManager(tableRow.getKey());
                        tableRow.setBackground(new Color(116, 185, 255));
                    }
                }
            }


        }, new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow tableRow) {
                    tableRow.setBackground(Color.white);
            }
        });
        table.showAll();
        table.getRow(0).select();



    }

    private editedScrollPanel scrollPanel;

    public void submit(){
        manager.submit();
        FormDialogMessage message = new FormDialogMessage(
                "Se han guardado las calificaciones ",
                "Todas las calificacione han sido guardadas exitosamente"
        );
        message.addAcceptButton();
        message.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                message.closeForm();
            }
        });
    }

    private void deployLayout(){
        addElement(getInstructionsPanel());
        JPanel cont = new JPanel(new GridBagLayout());
            cont.setBorder(new EmptyBorder(20,40,0,40));

        GridBagConstraints gc = new GridBagConstraints();
            gc.gridx = 0;
            gc.weightx = 1;

            gc.fill = GridBagConstraints.BOTH;


            cont.add(scrollPanel,gc);

            gc.gridx = 1;
            gc.weightx = 0;
            gc.weighty = 1;

            JPanel califForCont = new JPanel(new BorderLayout());
                califForCont.add(califForm,BorderLayout.NORTH);
            cont.add(califForCont,gc);


                addElement(cont);

            addSubmitBtn();

    }
}
