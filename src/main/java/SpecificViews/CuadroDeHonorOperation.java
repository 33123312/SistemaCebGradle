package SpecificViews;

import Generals.BtnFE;
import sistemaceb.SemestreOperator;
import sistemaceb.form.DesplegableMenu;
import sistemaceb.form.HorizontalFormPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CuadroDeHonorOperation extends OperationWindow{

    PdfCuadroHonorBuilder pdf;
    ArrayList<String> evaluaciones;

    public CuadroDeHonorOperation(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Ver cuadro de honor";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        evaluaciones = getEvaluaciones();
        deployLayout();
    }

    private void deployLayout() {
        HorizontalFormPanel formPanel = new HorizontalFormPanel();
            DesplegableMenu menuEva = formPanel.addDesplegableMenu("Evaluación");
            menuEva.setOptions(evaluaciones);
            menuEva.setRequired(true);

        JComboBox desplegableMenu = menuEva.getMenu();

        BtnFE btn = new BtnFE("Mostrar Pdf");
            btn.setPadding(10,20,10,20);
            btn.setTextColor(new Color(100,100,100));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (!formPanel.hasErrors()){
                        int selectedIndex =desplegableMenu.getSelectedIndex();
                        pdf = new PdfCuadroHonorBuilder(keyValue,evaluaciones.get(selectedIndex));
                        addRows(selectedIndex);
                    }
                }
            });

        JPanel btnContainer = new JPanel(new GridBagLayout());
            btnContainer.setOpaque(false);
            btnContainer.add(btn);

        LinearHorizontalLayout layout = new LinearHorizontalLayout();
            layout.addElement(formPanel);
            layout.addElement(btnContainer);

        layout.setOpaque(false);

        thisWindow.addToHeader(layout);
    }



    private ArrayList<String> getEvaluaciones(){
        ArrayList<String> evas = new ArrayList<>();
            evas.addAll(CalifasOperator.getEvaluaciones());
            evas.add("Semestral");
            evas.add("Final");

        return evas;
    }

    private void addRows(int eva){
        SemestreOperator operator = new SemestreOperator(keyValue);
            ArrayList<String> grupos = operator.getGrupos();

        for (String grupo: grupos)
             getBigerAlumnos(grupo,eva);



        pdf.addTable();
        pdf.close();
    }

    private void getBigerAlumnos(String grupo,int eva){
        GrupoOperator operator = new GrupoOperator(grupo);
        ArrayList<ArrayList<String>> alumnos = operator.getAlumnos().getRegisters();

        String bigerAlumno = "";
        double bigerAlumnoCalifa = 0;

        CalifaGeter geter = getCalifaGeter(eva);

            for (ArrayList<String> list: alumnos){
                String alumno = list.get(0);
                String alumnoName = list.get(1);
                ALumnoOperator op = new ALumnoOperator(alumno);
                ALumnoOperator.TodasMateriasOps materiaState = op.getMateriaOperators();
                String califa = geter.getCalifa(materiaState);

                if(califa != null && !califa.isEmpty()){
                    double doubleCalifa = Double.parseDouble(califa);
                    if (doubleCalifa > bigerAlumnoCalifa){
                        bigerAlumnoCalifa = doubleCalifa;
                        bigerAlumno = alumnoName;
                    }
                }

            }

        pdf.addRow(grupo,bigerAlumno,bigerAlumnoCalifa + "");
    }

    private CalifaGeter getCalifaGeter(int eva) {
        if (eva >= 0 && eva < 4)
            return new CalifaGeter() {
                @Override
                public String getCalifa(ALumnoOperator.TodasMateriasOps materiaState) {

                    return materiaState.getPromUnidad().get(eva);
                }
            };
        else if (eva == 4)
            return new CalifaGeter() {
                @Override
                public String getCalifa(ALumnoOperator.TodasMateriasOps materiaState) {
                    return materiaState.getPromSemestrales();
                }
            };
        else if (eva == 5)
            return  new CalifaGeter() {
                @Override
                public String getCalifa(ALumnoOperator.TodasMateriasOps materiaState) {
                    return materiaState.getPromFinal();
                }
            };

        return null;
    }


    private interface CalifaGeter{
        public String getCalifa(ALumnoOperator.TodasMateriasOps  materiaState);
    }

}
