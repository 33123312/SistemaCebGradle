package sistemaceb;

import Generals.BtnFE;
import JDBCController.Table;
import SpecificViews.GrupoOperator;
import SpecificViews.GrupoPasserInfoStorage;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.Global;
import sistemaceb.form.HorizontalFormPanel;
import sistemaceb.form.formElementWithOptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class GrupoPasserWindow extends Window{

    public String grupo;
    private GrupoSemestrePasador pasador;
    private SelectionFIlterTable table;
    private Table alumnos;


    public GrupoPasserWindow(String grupo, String currentSemestre, Table alumnos){
        this.grupo = grupo;
        this.alumnos = alumnos;
        pasador = new GrupoSemestrePasador(currentSemestre);
        setTitle("Pasar Alumnos al Siguiente Semestre - " + grupo);
        addBody();
    }

    public boolean hasDefInfo(){
        return  table.hasSelectedAlumnos();
    }

    private JPanel getTableContainer(){
        JPanel container = new JPanel(new BorderLayout());
            container.setOpaque(false);

        LinearHorizontalLayout buttonContainer = new LinearHorizontalLayout();
            buttonContainer.addElement(getSelectAllBtn());

        container.add(buttonContainer,BorderLayout.NORTH);
        container.add(table,BorderLayout.CENTER);

        return container;
    }

    private BtnFE getSelectAllBtn(){
        BtnFE btnFE = new BtnFE("Seleccionar Todos");
        btnFE.setPadding(10,10,10,10);
        btnFE.setBackground(new Color(52, 152, 219));
            btnFE.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    table.selectAll();
                }
            });

        return btnFE;
    }

    public Map<String,ArrayList<String>>  getSelectionsInfo(){
        return table.getSelectedAlumnos();
    }

    public void setDefValues(Map<String,ArrayList<String>> defSelections){
        table.setDefaultSelections(defSelections);
    }

    private void updateGrupos(){
        table.updateEvr(pasador.getNextSemestreGrupos());

    }

    private void addBody(){
        LinearVerticalLayout panel = new LinearVerticalLayout();
            panel.addElement(getDescLabel());
            panel.addElement(getGrupoChoser());
            panel.addElement(getTableContainer());
            panel.addElement(getPie());
            panel.setBorder(new EmptyBorder(20,70,20,70));

        setBody(panel);

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

    private JPanel getGrupoChoser(){
        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(new EmptyBorder(15,0,15,0));

        table = new SelectionFIlterTable(grupo,alumnos);

        if(pasador.isLastSemestre()){
          container.add(getUltimoSemestrePanel());
          table.createGroup("004");
        }
        else{
            container.add(table.getColorsSelectionBar());
            updateGrupos();
        }

        return container;
    }



    private JPanel getUltimoSemestrePanel(){
        JPanel cont = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Los alumnos seleccionado no pasarán a ningún grupo, puesto que pertenecen al último semestre, además, " +
                "su historial académico se borrará de la base de datos, aunque aún estará disponible en el respaldo de este periodo");
            label.setForeground(Color.red);

        cont.add(label);
        return cont;
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
        return !pasador.isLastSemestre() || table.hasSelectedAlumnos();
    }

    public void submit(){
        Map<String,ArrayList<String>> groups = table.getSelectedAlumnos();
        if (pasador.isLastSemestre())
            pasador.graduarAlumnos(groups.get("004"));
        else
            pasador.passAlumnos(groups);

    }
}
