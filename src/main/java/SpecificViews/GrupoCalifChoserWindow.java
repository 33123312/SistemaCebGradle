package SpecificViews;

import Generals.BtnFE;
import Tables.AdapTableFE;
import Tables.TableRow;
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
        FormElement lastElement = formulario.getElement(formulario.getElementCount()-1);
        lastElement.addTrigerGetterEvent(new TrigerElemetGetter() {
            @Override
            public void onTrigger(FormElement element) {
                int nextRowIndex = currentManeger.getIndex() + 1;
                if (nextRowIndex < table.getRows().size())
                    table.getRow(nextRowIndex).select();
                 else
                    table.getRow(0).select();

                califForm.changeFocusTo(0);
            }
        });

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
                double res = Double.parseDouble(response);
                if (res > 10.0)
                    return "Sólo se puede calificar de 0 a 10";

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
            Map<String, String> data = califForm.getAllData();

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    int i = 2;
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        auxM.set(entry.getKey(), entry.getValue());
                        TableRow row = table.getRow(auxM.getIndex());
                        row.setCellValue(i, entry.getValue());
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
            califForm.getElement(i).setResponse(currentValue);
        }
    }

    protected void deployTable(){
        table = new AdapTableFE();
        table.setBorder(new EmptyBorder(0,0,0,20));
        table.setTitles(manager.getHumanCols());
        table.setRows(manager.getRegisters());
        table.getFactory().addGralClickSelectionEvnt(new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow tableRow){
                if(!califForm.hasErrors()){
                    if(currentManeger !=  null){
                        updatePasser();
                        if(currentManeger.getIndex() != tableRow.getKey()){
                            changeManager(tableRow.getKey());
                            tableRow.setBackground(new Color(116, 185, 255));
                        }
                    }

                    else {
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
            gc.fill = GridBagConstraints.HORIZONTAL;

            cont.add(table,gc);

            gc.gridx = 1;
            gc.weightx = 0;

            cont.add(califForm,gc);

            addElement(cont);
            addSubmitBtn();

    }
}
