package SpecificViews;

import JDBCController.Table;
import JDBCController.dataType;
import Tables.TableRow;
import sistemaceb.form.FormElement;
import sistemaceb.form.Formulario;
import sistemaceb.form.Input;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GrupoBolCalChoserWindow extends GrupoEvacalWindow{

    public GrupoBolCalChoserWindow(String grupo, String materia, String eva) {
        super(grupo, materia, eva,"calificaciones_booleanas","bol_calif_con_pro");
    }


    protected Formulario createForm() {
        VerticalFormulario form = new VerticalFormulario();
            form.addTitle("Ingresar Valores");
            form.addDesplegableMenu("Calificación").setOptions(getCalOptions());
            addSelectionInput(form.addInput("Faltas", dataType.INT).setTrueTitle("faltas"));

        return form;
    }

    private Table getCalOptions() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Calificación");
        titles.add("calificacion");

        ArrayList<ArrayList<String>> registers = new ArrayList<>();
        ArrayList<String> reg1 = new ArrayList();
        reg1.add("A");
        reg1.add("1");

        ArrayList<String> reg2 = new ArrayList();
        reg2.add("NA");
        reg2.add("0");

        registers.add(reg1);
        registers.add(reg2);

        return new Table(titles, registers);
    }

    @Override
    protected void deployTable() {
        super.deployTable();
        for (TableRow row:table.getRows()){
            JLabel label  = row.getDataLabels().get(2);

            if(label.getText().equals("1"))
                label.setText("A");
            else if (label.getText().equals("0"))
                label.setText("NA");

            label.addPropertyChangeListener("text",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if(label.getText().equals("1"))
                        label.setText("A");
                    else if (label.getText().equals("0"))
                        label.setText("NA");
                }
            });

        }
    }
}
