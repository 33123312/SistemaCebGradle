package SpecificViews;

import Generals.BtnFE;
import JDBCController.Table;
import sistemaceb.Window;
import sistemaceb.form.*;
import sistemaceb.genericEvents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class GrupoCalifChoserOp extends OperationWindow{
    Formulario form;
    Table materias;
    public GrupoCalifChoserOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Subir Calificaciones";

    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        thisWindow.setTitle(keyValue);
        thisWindow.getScrollPanel().setPreferredSize(new Dimension(10000,1000000));
        materias = getMaterias();
        if (materias.isEmpty())
            showMateriasErrorMessage();
        else
            thisWindow.addToHeader(getCompArea());
        addCloseEven();
    }

    private void addCloseEven(){
        thisWindow.addClosePermision(new Window.ClosePermision() {
            @Override
            public boolean canClose(genericEvents ev) {
                if(!hasUnsavedChanged(new genericEvents() {
                    @Override
                    public void genericEvent() {
                        ev.genericEvent();
                    }
                }))
                    ev.genericEvent();
                return false;
            }
        });
    }

    private void showMateriasErrorMessage(){
        FormDialogMessage dialogMessage =
                new FormDialogMessage(
                        "El grupo no tiene materias",
                        "El grupo actual no tiene materias, esto puede ser por que no tiene un plan asignado," +
                                " o bien, porque el plan no tiene materias asignadas");

            dialogMessage.addAcceptButton();
            dialogMessage.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    Global.view.currentWindow.cut();
                    dialogMessage.closeForm();
                }
            });

            dialogMessage.addOnDimissEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    Global.view.currentWindow.cut();
                }
            });
    }

    private JPanel getCompArea(){
        LinearHorizontalLayout layout = new LinearHorizontalLayout();
            layout.addElement(getForm());
            layout.addElement(getButton());

        return layout;
    }

    private BtnFE getButton(){
        BtnFE btn = new BtnFE("Mostrar");
            btn.setBackground(new Color(52, 152, 219));
            btn.setTextColor(Color.white);
            btn.setPadding(0,10,0,10);
            btn.setMargins(15,0,15,0,Color.white);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                    if (currentWindow == null)
                        changeWindow();
                    else
                    if(!hasUnsavedChanged(new genericEvents() {
                        @Override
                        public void genericEvent() {
                            changeWindow();
                        }
                    }))
                    changeWindow();
                }
            });

        return btn;
    }

    private boolean hasUnsavedChanged(genericEvents ev){
        if ( currentWindow != null && currentWindow.hasUnsavedChanges() ){
            FormDialogMessage formDialogMessage =
                    new FormDialogMessage(
                            "Hay cambios sin guardar",
                        "Algunas calificaciones han sido modificadas pero no guardadas, si sale ahora, los cambios se perderán<br>                                  <br>¿Desea guardar los cambios antes de salir de la ventana?");

            formDialogMessage.addCloseButton();
            formDialogMessage.addButton("Salir sin guardar").addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    ev.genericEvent();
                    formDialogMessage.closeForm();
                }
            });
            formDialogMessage.addButton("Guardar Cambios").addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    currentWindow.submit();
                    ev.genericEvent();
                    formDialogMessage.closeForm();
                }
            });
            return true;
        }
        return false;
    }

    private void chargeWindow(GrupoCalifChoserWindow newWindow){
        currentWindow = newWindow;
        thisWindow.changeBody(newWindow);
    }

    private GrupoCalifChoserWindow currentWindow;

    private void changeWindow(){
        if(!form.hasErrors()){
            Map<String,String> res = form.getData();
            String materia = res.get("materia");
            String evaluacion = res.get("Evaluación");
            String tipo = MateriaOperator.getMateriaType(materia);

            if (tipo.equals("Numérica")){
                if (evaluacion.equals("Semestral"))
                    chargeWindow(new GrupoSemCalChoserWindow(keyValue,materia));
                else
                    chargeWindow(new GrupoNumCalChoserWindow(keyValue,materia,evaluacion));
            } else
                chargeWindow(new GrupoBolCalChoserWindow(keyValue,materia,evaluacion));

        }
    }

    private Formulario getForm(){
        HorizontalFormPanel panel = new HorizontalFormPanel();
            panel.addDesplegableMenu("Materia").setOptions(materias).addTrigerGetterEvent(new TrigerElemetGetter() {
                private int isNum = -1;

                private void removeEva(){
                    panel.removeElement("Evaluación");
                }

                private ArrayList<String> getEvas(){

                    return CalifasOperator.getEvaluaciones();
                }

                private ArrayList<String> getFullEvas(){
                    ArrayList<String> evas = new ArrayList<>(getEvas());
                        evas.add("Semestral");

                    return evas;
                }

                private void addeEva(ArrayList<String> op){
                    panel.
                        addDesplegableMenu("Evaluación")
                        .setOptions(op).
                        setRequired(true);
                }

                @Override
                public void onTrigger(FormElement element) {
                    String response = element.getResponse();
                    String type = MateriaOperator.getMateriaType(response);

                    if (!response.isEmpty())
                    if (type.equals("Numérica")){
                        if (isNum != 1){
                            removeEva();
                            addeEva(getFullEvas());

                            isNum = 1;
                        }
                    } else if (type.equals("A/NA")){
                        if (isNum != 0){
                            removeEva();
                            addeEva(getEvas());
                            isNum = 0;
                        }
                    }

                };
            }).setRequired(true);

        form = panel;
        return panel;

    }


    private Table getMaterias(){
        GrupoOperator op = new GrupoOperator(keyValue);
            Table materias = op.getMaterias();

        if(materias.isEmpty())
            return new Table(new ArrayList<>(),new ArrayList<>());

        ArrayList<String> human = materias.getColumn(1);
        ArrayList<String> prim = materias.getColumn(0);

        ArrayList<String> cols = materias.getColumnTitles();

        materias.removeColumn(0);
        materias.removeColumn(0);

        materias.addColumn(0,cols.get(1),human);
        materias.addColumn(1,cols.get(0),prim);

        return materias;
    }
}
