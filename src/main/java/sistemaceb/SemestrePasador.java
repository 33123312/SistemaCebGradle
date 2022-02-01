package sistemaceb;

import Generals.BtnFE;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import JDBCController.dataType;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.ErrorChecker;
import sistemaceb.form.Global;
import sistemaceb.form.HorizontalFormPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemestrePasador extends Window{

    LinearVerticalLayout body;
    LinearVerticalLayout controlsPanel;
    ArrayList<String> semestres;
    Map<String,Map<String,ArrayList<String>>> currentPasadoresInfoPackage;
    private ArrayList<GrupoPasserWindow> grupoPassers;

    public SemestrePasador(){
        setTitle("Avanzar de Periodoo");
        initClass();
    }

    private void initClass(){
        semestres = Global.conectionData.getSemestres();
        grupoPassers = new ArrayList<>();
        deployLayout();
    }

    private void deployLayout(){
        body = new LinearVerticalLayout();
            body.setBorder(new EmptyBorder(20,70,0,70));
            body.addElement(getDescLabel());
            body.addElement(getPeriodoNameChoser());
            body.addElement(getAvanzarDeSemetreBtn());
            addControlsPanel();

        setBody(body);

    }

    private JLabel getDescLabel(){
        String descripción = " Esta sección es una herramienta destinada a pasar de periodo escolar";
        JLabel descLabel = new JLabel(descripción);
            descLabel.setHorizontalAlignment(SwingConstants.CENTER);
            descLabel.setForeground(new Color(100,100,100));
            descLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            descLabel.setBorder(new EmptyBorder(0,0,20,0));
        return descLabel;
    }

    HorizontalFormPanel newPeriodoGetter;

    private JPanel getPeriodoNameChoser(){
        JPanel cont = new JPanel(new GridBagLayout());
        newPeriodoGetter = new HorizontalFormPanel();
            newPeriodoGetter.addInput("Ingresar el Nombre del Siguiente Periodo", dataType.VARCHAR).setRequired(true).addErrorChecker(new ErrorChecker() {
                @Override
                public String checkForError(String response) {
                    String trueResponse = response.trim();
                    if (trueResponse.contains(" "))
                        return "Los nombres de periodo no pueden contener espacios";
                    else
                        return "";
                }
            });

        cont.setBorder(new EmptyBorder(10,0,10,0));
        cont.add(newPeriodoGetter);

        return cont;
    }

    private String getNextPeriodo(){
        ArrayList<String> values = new ArrayList<>(newPeriodoGetter.getData().values());
        return values.get(0);
    }

    private JPanel getAvanzarDeSemetreBtn(){
        JPanel cont = new JPanel(new GridBagLayout());
        BtnFE btn = new BtnFE("Avanzar de Semestre");
            btn.setTextColor(Color.white);
            btn.setBackground(new Color(52, 152, 219));
            btn.setPadding(10,20,10,20);
            btn.setFuente(new Font("Arial", Font.PLAIN, 20));
            btn.addMouseListener(getAvanzarSemestreEvent());

        cont.setBorder(new EmptyBorder(0,0,30,0));
        cont.add(btn);
        return cont;
    }

    private MouseAdapter getAvanzarSemestreEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(!newPeriodoGetter.hasErrors()){
                    submitPasers();
                }
            }
        };
    }

    private void addControlsPanel(){
        controlsPanel = new LinearVerticalLayout();
        controlsPanel.setBorder(new EmptyBorder(10,20,10,20));
        controlsPanel.setBackground(Color.white);
        addGruposPanels();
        controlsPanel.add(getErrorGrupoMessage());
        body.addElement(controlsPanel);

    }

    private JLabel errorGrupoMessage;
    private LinearVerticalLayout gruposPanel;

    private void addGruposPanels(){
        gruposPanel = new LinearVerticalLayout();
        gruposPanel.setOpaque(false);
        controlsPanel.addElement(getControlsSection("Manejar Grupos",gruposPanel));
    }

    private Map<String,Map<String,ArrayList<String>>> collectInfoFromPassers(){
        Map<String,Map<String,ArrayList<String>>> infos = new HashMap<>();
        for (GrupoPasserWindow window:grupoPassers)
            if (window.hasDefInfo()){
                infos.put(window.grupo,window.getSelectionsInfo());
            }


        return infos;
    }

    public void update(){
        if (checkWindowThing())
            addGrupos();
    }


    private void addGrupos(){
        currentPasadoresInfoPackage = collectInfoFromPassers();
        //la informacion de los passers nunca se pierde, pero reiniciamos loa passers por si se cambio un alumno o lago
        grupoPassers = new ArrayList<>();
        gruposPanel.removeAll();

        for (String semestre:semestres)
            gruposPanel.addElement(getGruposToPassPanel(semestre));


    }

    private boolean checkWindowThing(){
        String objClassName = "class sistemaceb.GrupoPasserWindow";
        String className;
        ViewAdapter win = Global.view.currentWindow;
        if (win == null)
            className = "";
        else
            className = win.thisWindow.getClass().toString();

        return !className.equals(objClassName);

    }

    private Map<String,ArrayList<String>> gotDefInfo(String grupo){

        return  currentPasadoresInfoPackage.get(grupo);
    }

    private JPanel getErrorGrupoMessage(){
        JPanel container = new JPanel(new GridLayout());
        errorGrupoMessage = new JLabel("Uno de los grupos no tiene un grupo al que pasar los alumnos asigndo");
            errorGrupoMessage.setAlignmentX(SwingConstants.CENTER);
            errorGrupoMessage.setForeground(Color.red);
            errorGrupoMessage.setBorder(new EmptyBorder(10,0,10,0));
            errorGrupoMessage.setVisible(false);
        container.add(errorGrupoMessage);
        container.setOpaque(false);
            return container;
    }

    private JPanel getGruposToPassPanel(String semestre){
        JPanel gruposPanel = new JPanel(new BorderLayout());
            gruposPanel.setOpaque(false);
            gruposPanel.add(getSemestreTitleLabel(semestre),BorderLayout.NORTH);
            gruposPanel.add(getSemestreGruposLine(semestre),BorderLayout.CENTER);
            gruposPanel.setBorder(new EmptyBorder(10,0,10,0));

        return gruposPanel;
    }

    private JPanel getControlsSection(String title,JPanel panelBody){
        JPanel section = new JPanel(new BorderLayout());
            section.setOpaque(false);
            section.setBorder(new EmptyBorder(0,0,10,0));

        JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("arial", Font.PLAIN, 25));
            titleLabel.setForeground(new Color(9, 132, 227));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        section.add(titleLabel,BorderLayout.NORTH);
        section.add(panelBody,BorderLayout.CENTER);

        return section;
    }

    private LinearHorizontalLayout getSemestreGruposLine(String semestre) {
        LinearHorizontalLayout gruposPanel = new LinearHorizontalLayout();
            gruposPanel.setOpaque(false);

        ArrayList<String> grupos = getSemestreGrupos(semestre);
        Table alumnos = getAlumnos(semestre);

        for (String grupo : grupos) {
            BtnFE grupoBtn = getBtnGrupoPasser(grupo,semestre,alumnos);
            gruposPanel.addElement(grupoBtn);
        }

        return gruposPanel;
    }

    private ArrayList<String> getSemestreGrupos(String semestre){
        SemestreOperator operator = new SemestreOperator(semestre);
        return operator.getGrupos();

    }

    private Table getAlumnos(String semestre){
        DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");

        return consulter.bringTable();

    }

    private JLabel getSemestreTitleLabel(String semestre){
        JLabel semetreTitle = new JLabel("Semestre: " + semestre);
            semetreTitle.setFont(new Font("arial", Font.PLAIN, 17));
            semetreTitle.setBorder(new EmptyBorder(0,0,15,0));

        return semetreTitle;
    }



    private BtnFE getBtnGrupoPasser(String grupo,String semestre,Table alumnos){
        BtnFE btn = new BtnFE(grupo);
            btn.setPadding(3,10,3,10);
            btn.setBackground(new Color(41, 128, 185));
            btn.setTextColor(Color.white);
            btn.setMargins(5,0,5,10,Color.white);
            btn.setFuente(new Font("arial", Font.PLAIN, 15));

            GrupoPasserWindow passer = new GrupoPasserWindow(grupo,semestre, alumnos);
            Map<String,ArrayList<String>> storage = gotDefInfo(passer.grupo);
            if (storage != null)
                passer.setDefValues(storage);

            grupoPassers.add(passer);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Global.view.currentWindow.newView(passer);
                }
            });

        return btn;
    }


    private void submit(){
        boolean hasErrors = false;

        for(GrupoPasserWindow pasador:grupoPassers)
            if(!pasador.canSubmit()){
                errorGrupoMessage.setVisible(true);
                hasErrors = true;
            }

        if(!hasErrors){
            new RespaldosManager().orderPeriodoRes();
            for(GrupoPasserWindow pasador:grupoPassers)
                pasador.submit();

            setNewPeriodo();
            Global.resetPriodo();

        }
    }

    private void submitPasers(){
        if(!newPeriodoGetter.hasErrors())
            submit();

    }

    private void setNewPeriodo(){
        String nextPeriodo = getNextPeriodo();

        new RespaldosManager().createResDir(nextPeriodo);

        String currentPeriodo = Global.conectionData.loadedPeriodo;

        ArrayList<String> periodoString = new ArrayList<>();
            periodoString.add("periodo");
            periodoString.add("paridad");

        ArrayList<String> periodoNewValue = new ArrayList();
            periodoNewValue.add(nextPeriodo);
            periodoNewValue.add(getNewParidad());

        try {
            new ViewSpecs("periodos").getUpdater().insert(periodoString,periodoNewValue);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        updateCurrentPeriodo(nextPeriodo,currentPeriodo);

    }

    private String getNewParidad(){
        if (Global.conectionData.periodIsPair)
            return "1";
        else
            return "0";
    }

    private void updateCurrentPeriodo(String newPeriodo,String oldPeriodo){

        ArrayList<String> periodoStringToMod = new ArrayList<>();
            periodoStringToMod.add("periodo");

        ArrayList<String> newPeriodoArray = new ArrayList();
            newPeriodoArray.add(newPeriodo);

        ArrayList<String> periodoString = new ArrayList<>();
            periodoString.add("periodo");

        ArrayList<String> periodoOldValue = new ArrayList<>();
            periodoOldValue.add(oldPeriodo);

        try {
            ViewSpecs specs = new ViewSpecs("currentperiodo");
            specs.getUpdater().
                insert(
                    periodoStringToMod,
                    newPeriodoArray);
            specs.getUpdater().delete(periodoString,periodoOldValue);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
