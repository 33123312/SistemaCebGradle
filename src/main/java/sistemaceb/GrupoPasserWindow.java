package sistemaceb;

import Generals.BtnFE;
import JDBCController.Table;
import SpecificViews.GrupoOperator;
import SpecificViews.GrupoPasserInfoStorage;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.FormElement;
import sistemaceb.form.Global;
import sistemaceb.form.HorizontalFormPanel;
import sistemaceb.form.formElementWithOptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class GrupoPasserWindow extends Window{

    public String grupo;
    GrupoSemestrePasador pasador;
    HorizontalFormPanel nextGrupoGetter;
    formElementWithOptions gruposDesplegableMenu;
    SelectionFIlterTable table;
    GrupoOperator operator;

    public GrupoPasserWindow(String grupo){
        this.grupo = grupo;
        operator = new GrupoOperator(grupo);
        pasador = new GrupoSemestrePasador(operator);
        setTitle("Pasar Alumnos al Siguiente Semestre - " + grupo);
        addBody();
    }

    public boolean hasDefInfo(){
        String nextGrupo = getNextGrupo();
        return nextGrupo != null || !table.getSelectedAlumnos().isEmpty();
    }

    public GrupoPasserInfoStorage getSelectionsInfo(){
        return new GrupoPasserInfoStorage(grupo,getNextGrupo(),table.getSelectedAlumnos());
    }



    public void setDefValues(GrupoPasserInfoStorage grupoDedInfo){
        gruposDesplegableMenu.setResponse(grupoDedInfo.getSelectedNextGrupo());
        table.setDefaltSelections(grupoDedInfo.getSelectedAluIndexes());
    }

    private void updateNextgrupos(){
        ArrayList<String> newOptions = pasador.getNextSemestreGrupos();
        gruposDesplegableMenu.setOptions(newOptions);

    }

    private void addBody(){
        JScrollPane scroll = new JScrollPane();
        LinearVerticalLayout panel = new LinearVerticalLayout();
            panel.addElement(getDescLabel());
            panel.addElement(getGroupDesplegableMenu());
            panel.addElement(getAlumnosTable());
            panel.addElement(getPie());
            panel.setBorder(new EmptyBorder(20,70,20,70));

        scroll.setViewportView(panel);
        addBody(scroll);

    }

    private JLabel getDescLabel(){
        String descripción = " Esta seccón es para determinar los alumnos que pasarán al siguiente semestre, para indicar el<br>" +
                " grupo al que pasarán los alumnos, seleccione el dicho en la lista desplegable y para seleccionar los alumnos que <br>" +
                "pasarán al grupo, presione sus nombres" +
                " en la tabla de abajo.";

        JLabel descLabel = new JLabel("<html><body>" + descripción + "</html></body>");
            descLabel.setHorizontalAlignment(SwingConstants.CENTER);
            descLabel.setForeground(new Color(100,100,100));
            descLabel.setFont(new Font("Arial", Font.PLAIN, 17));
            descLabel.setBorder(new EmptyBorder(0,0,20,0));
        return descLabel;
    }

    private JPanel getGroupDesplegableMenu(){
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(new EmptyBorder(15,0,15,0));

        if(pasador.isLastSemestre())
            container.add(getUltimoSemestrePanel());
        else
            container.add(getGrupoGetter());

        return container;
    }

    private JPanel getGrupoGetter(){
        nextGrupoGetter = new HorizontalFormPanel();
        gruposDesplegableMenu = nextGrupoGetter.addDesplegableMenu("Nuevo Grupo");
        updateNextgrupos();
        nextGrupoGetter.showAll();

        return nextGrupoGetter;

    }

    private JPanel getUltimoSemestrePanel(){
        JPanel cont = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Los alumnos seleccionado no pasarán a ningún grupo, puesto que pertenecen al último semestre");
            label.setForeground(Color.red);

        cont.add(label);
        return cont;
    }

    private SelectionFIlterTable getAlumnosTable(){
        table = new SelectionFIlterTable(operator);
        return table;
    }

    private JPanel getPie(){
        JPanel pie = new JPanel(new BorderLayout());

        BtnFE button = new BtnFE("Aceptar y volver");
            button.setTextColor(Color.white);
            button.setBackground(new Color(9, 132, 227));
            button.setBorder(new EmptyBorder(5,10,5,10));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Global.view.currentWindow.cut();
                }
            });


        JLabel warningMessage = new JLabel("Los cambios no se guardarán hasta que se halla avanzado de periodo");
            warningMessage.setForeground(new Color(231, 76, 60));

        pie.add(warningMessage,BorderLayout.WEST);
        pie.add(button,BorderLayout.EAST);
        pie.setBorder(new EmptyBorder(30,0,0,0));

        return pie;

    }

    public boolean canSubmit(){
        if (!pasador.isLastSemestre()){
            ArrayList<String> keys = table.getSelectedAlumnos();
            if(keys.isEmpty()){
                return true;
            } else {
                if(nextGrupoGetter.hasErrors())
                    return false;
                else
                    return true;
            }
        }
        return true;
    }

    private String getNextGrupo(){
        if(nextGrupoGetter == null)
            return null;
        else
            return nextGrupoGetter.getData().get("Nuevo Grupo");

    }

    public void submit(){
        ArrayList<String> keys = table.getSelectedAlumnos();
        if (pasador.isLastSemestre())
            pasador.graduarAlumnos(keys);
        else {
            String nextGrupo = getNextGrupo();
            pasador.passAlumnos(nextGrupo,keys);
        }

    }
}
