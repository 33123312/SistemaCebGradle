package sistemaceb;

import Generals.BtnFE;
import JDBCController.DBSTate;
import JDBCController.DataBaseUpdater;
import JDBCController.dataType;
import SpecificViews.GrupoPasserInfoStorage;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.Global;
import sistemaceb.form.HorizontalFormPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class SemestrePasador extends Window{

    LinearVerticalLayout body;
    LinearVerticalLayout controlsPanel;
    ArrayList<String> semestres;
    ArrayList<GrupoPasserInfoStorage> currentPasadoresInfoPackage;
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
        JScrollPane scroll = new JScrollPane();
        body = new LinearVerticalLayout();
            body.setBorder(new EmptyBorder(20,70,0,70));
            body.addElement(getDescLabel());
            body.addElement(getPeriodoNameChoser());
            body.addElement(getAvanzarDeSemetreBtn());
            addControlsPanel();

        scroll.setViewportView(body);
        addBody(scroll);

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
            newPeriodoGetter.addInput("Ingresar el Nombre del Siguiente Periodo", dataType.VARCHAR).setRequired(true);
            newPeriodoGetter.showAll();

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
                    Global.initGlobal();
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
        addGrupos();

        controlsPanel.addElement(getControlsSection("Manejar Grupos",gruposPanel));
    }

    private ArrayList<GrupoPasserInfoStorage> collectInfoFromPassers(){
        ArrayList<GrupoPasserInfoStorage> infos = new ArrayList<>();
        for (GrupoPasserWindow window:grupoPassers)
            if (window.hasDefInfo())
                infos.add(window.getSelectionsInfo());

        return infos;
    }

    public void update(){
        addGrupos();
    }

    private void addGrupos(){
        currentPasadoresInfoPackage = collectInfoFromPassers();
        grupoPassers = new ArrayList<>();
        gruposPanel.removeAll();
        for (String semestre:semestres)
            gruposPanel.addElement(getGruposToPassPanel(semestre));
        setDefInfo();

    }

    private GrupoPasserInfoStorage gotDefInfo(String grupo){

        for (GrupoPasserInfoStorage storage:currentPasadoresInfoPackage){
            if(storage.getGrupo().equals(grupo))
                return storage;
        }

        return null;
    }

    private JPanel getErrorGrupoMessage(){
        JPanel container = new JPanel(new GridLayout());
        errorGrupoMessage = new JLabel("Error: uno de los grupos no tiene un grupo al que pasar los alumnos asigndo");
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

        for (String grupo : grupos) {
            BtnFE grupoBtn = getBtnGrupoPasser(grupo);
            gruposPanel.addElement(grupoBtn);
        }

        return gruposPanel;
    }

    private ArrayList<String> getSemestreGrupos(String semestre){
        SemestreOperator operator = new SemestreOperator(semestre);
        return operator.getGrupos();

    }

    private JLabel getSemestreTitleLabel(String semestre){
        JLabel semetreTitle = new JLabel("Semestre: " + semestre);
            semetreTitle.setFont(new Font("arial", Font.PLAIN, 17));
            semetreTitle.setBorder(new EmptyBorder(0,0,15,0));

        return semetreTitle;
    }

    private void setDefInfo(){
        for(GrupoPasserWindow passer:grupoPassers){
            GrupoPasserInfoStorage storage = gotDefInfo(passer.grupo);
            if (storage != null)
            passer.setDefValues(storage);
        }
    }

    private BtnFE getBtnGrupoPasser(String grupo){
        BtnFE btn = new BtnFE(grupo);
            btn.setPadding(3,10,3,10);
            btn.setBackground(new Color(41, 128, 185));
            btn.setTextColor(Color.white);
            btn.setMargins(5,0,5,10,Color.white);
            btn.setFuente(new Font("arial", Font.PLAIN, 15));

            GrupoPasserWindow passer;

            passer = new GrupoPasserWindow(grupo);

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
            for(GrupoPasserWindow pasador:grupoPassers)
                pasador.submit();

            String nextPeriodo = getNextPeriodo();
            setPeriodo(nextPeriodo);

        }
    }

    private void submitPasers(){
        if(!newPeriodoGetter.hasErrors())
        submit();

    }

    private void setPeriodo(String nextPeriodo){
        String currentPeriodo = Global.conectionData.loadedPeriodo;

        ArrayList<String> periodoString = new ArrayList<>();
            periodoString.add("periodo");
            periodoString.add("paridad");

        ArrayList<String> periodoNewValue = new ArrayList();
            periodoNewValue.add(nextPeriodo);
            periodoNewValue.add(getNewParidad());

        try {
            new DataBaseUpdater("periodos").insert(periodoString,periodoNewValue);
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
            new DataBaseUpdater("currentperiodo").
                update(
                    periodoStringToMod,
                    newPeriodoArray,
                    periodoString,
                    periodoOldValue
                );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Global.conectionData.resetPeriodo();
    }

}
