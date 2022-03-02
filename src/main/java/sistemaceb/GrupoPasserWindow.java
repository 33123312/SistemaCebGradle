package sistemaceb;

import Generals.BtnFE;
import JDBCController.Table;
import SpecificViews.GrupoOperator;
import SpecificViews.GrupoPasserInfoStorage;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import Tables.TableRow;
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
    private ArrayList<String> nextGrupos;
    private int nextGrupo;


    public GrupoPasserWindow(String grupo, String currentSemestre, Table alumnos,ArrayList<String> nextGrupos,int nextGrupo){
        this.grupo = grupo;
        this.alumnos = alumnos;
        this.nextGrupos = nextGrupos;
        this.nextGrupo = nextGrupo;

        pasador = new GrupoSemestrePasador(currentSemestre);
        setTitle("Pasar Alumnos al Siguiente Semestre - " + grupo);
        addBody();
    }

    public boolean hasInfo(){
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
        String descripci�n =
                "�sta secci�n es para determinar los alumnos que pasar�n al siguiente semestre, para indicar el<br>" +
                "grupo al que pasar�n los alumnos, seleccione el dicho en la lista de grupos, despu�s seleccione los alumnos que ir�n a ese grupo en la tabla, <br>"+
                "estos se iluminar�n con el color correspondiente al grupo que ir�n, para indicar que un alumno es una baja, simplemente d�jelo deselecionado" ;

        JLabel descLabel = new JLabel("<html><body>" + descripci�n + "</html></body>");
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
          table.createGroup("gr0");
          table.setCurrentGroup("gr0");
        }
        else{
            container.add(table.getColorsSelectionBar());
            table.updateEvr(nextGrupos);
            table.setCurrentGroup(nextGrupo);
        }

        return container;
    }



    private JPanel getUltimoSemestrePanel(){
        JPanel cont = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(
                "Los alumnos seleccionado no pasar�n a ning�n grupo, puesto que pertenecen al �ltimo semestre, adem�s, " +
                "su historial acad�mico se borrar� de la base de datos, aunque a�n estar� disponible en el respaldo de este periodo");
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


        JLabel warningMessage = new JLabel("Los cambios no se guardar�n hasta que se halla avanzado de periodo");
            warningMessage.setForeground(new Color(231, 76, 60));

        pie.add(warningMessage,BorderLayout.WEST);
        pie.add(button,BorderLayout.EAST);
        pie.setBorder(new EmptyBorder(30,0,0,0));

        return pie;

    }

    public ArrayList<String> getBajas(){
        return table.getUnselected();

    }

    public void submit(){

        if (table.hasSelectedAlumnos()){
            Map<String,ArrayList<String>> groups = table.getSelectedAlumnos();
                pasador.passAlumnos(groups);
        }


    }
}
