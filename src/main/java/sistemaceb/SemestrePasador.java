package sistemaceb;

import Generals.BtnFE;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import JDBCController.dataType;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.ErrorChecker;
import sistemaceb.form.Global;
import sistemaceb.form.HorizontalFormPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemestrePasador extends Window{

    LinearVerticalLayout body;
    LinearVerticalLayout controlsPanel;
    ArrayList<String> semestres;
    Map<String,Map<String,ArrayList<String>>> currentPasadoresInfoPackage;
    ArrayList<String> currentGrupos;
    private ArrayList<SemestrePasser> semestrePassersPassers;
    Table alumnos;


    public SemestrePasador(){
        setTitle("Avanzar de Periodoo");
        currentGrupos = new ArrayList<>();
        alumnos = getAlumnos();
        initClass();
    }

    private void initClass(){
        semestres = Global.conectionData.getSemestres();
        semestrePassersPassers = new ArrayList<>();
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

        for (SemestrePasser passer:semestrePassersPassers)
            infos.putAll(passer.getInfo());

        return infos;
    }

    public void update(){
        if (checkWindowThing())
            addGrupos();
    }


    private void addGrupos(){
        currentPasadoresInfoPackage = collectInfoFromPassers();
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
            gruposPanel.add(createSemestrePasser(semestre),BorderLayout.CENTER);
            gruposPanel.setBorder(new EmptyBorder(10,0,10,0));

        return gruposPanel;
    }

    private JPanel createSemestrePasser(String semestre){
        SemestrePasser passer = new SemestrePasser(Integer.parseInt(semestre),alumnos,currentPasadoresInfoPackage);
        semestrePassersPassers.add(passer);

        return passer.getSemestreGruposLine();
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

    private Table getAlumnos(){
        DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");

        return consulter.bringTable();

    }

    private JLabel getSemestreTitleLabel(String semestre){
        JLabel semetreTitle = new JLabel("Semestre: " + semestre);
            semetreTitle.setFont(new Font("arial", Font.PLAIN, 17));
            semetreTitle.setBorder(new EmptyBorder(0,0,15,0));

        return semetreTitle;
    }

    private void submit(){
        registerBajas();

        try {
            new RespaldosManager().orderPeriodoRes();

            deleteBajas();

            makeGrupos();

            submitPassrs();

            deleteGrupos();

            setNewPeriodo();

            Global.resetPriodo();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void truncateBajas(String bajas){
        try {
            Global.SQLConector.getMyStatment().executeUpdate("truncate cebdatabase." + bajas);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void submitPassrs(){
        for (SemestrePasser passer:semestrePassersPassers){
            passer.submit();
        }
    }

    private void registerBajas(){
        ArrayList<String> allBajas = new ArrayList<>();

        for(SemestrePasser pasador:semestrePassersPassers){
            ArrayList<String> semestreBaja = pasador.getBajas();
            allBajas.addAll(semestreBaja);
        }

        allBajas.addAll(getBajasDelPeriodo());

        registerBajas(allBajas);
    }

    private ArrayList<String> getBajasDelPeriodo(){
        ArrayList<String> bajas = new DataBaseConsulter("bajas_periodo").
                bringTable(new String[]{"numero_control"}).getColumn(0);


        return  bajas;
    }

    private ArrayList<String> getAllPeriodoBaja(){
        return new DataBaseConsulter("bajas").
            bringTable(new String[]{"numero_control"}).getColumn(0);
    }

    private void deleteBajas (){
        ArrayList<String> bajas = getAllPeriodoBaja();
        truncateBajas("bajas");
        truncateBajas("bajas_periodo");
        deleteAlumnos(bajas);

    }

    private void registerBajas(ArrayList<String> bajas){
        ArrayList<String> cols = new ArrayList<>();
            cols.add("numero_control");

        ArrayList<ArrayList<String>> registers = new ArrayList<>();

        for (String baja:bajas) {
            ArrayList<String> reg = new ArrayList<>();
                reg.add(baja);

            registers.add(reg);
        }

        try {
            new ViewSpecs("bajas").getUpdater().insertMany(cols,registers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteAlumnos (ArrayList<String> bajas){
        new AlumnoRemover(bajas);
    }

    private void makeGrupos(){
        ArrayList<ArrayList<String >> newGroups = new ArrayList<>();

        for (SemestrePasser passer:semestrePassersPassers){
            newGroups.addAll(passer.getNewGrupos());
        }


        ArrayList<String> cols = new ArrayList<>();
            cols.add("grupo");
            cols.add("semestre");
            cols.add("turno");

        try {
            new ViewSpecs("grupos").getUpdater().insertMany(cols,newGroups);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteGrupos(){

        ArrayList<String> conditions = new ArrayList<>();
            conditions.add("semestre");
            conditions.add("semestre");
            conditions.add("semestre");

        ArrayList<String> values = new ArrayList<>();
            values.addAll(semestres);

        ArrayList<String> grupos =
                new DataBaseConsulter("grupos").bringTableOr(
                        new String[]{"grupo"},
                        conditions.toArray(new String[conditions.size()]),
                        values.toArray(new String[conditions.size()])
                ).getColumn(0);

        delete(grupos,"grupo","horarios");
        delete(grupos,"grupo","asignaturas");
        delete(grupos,"grupo","plan_grupo");
        delete(grupos,"grupo","grupos");

    }

    private void delete(ArrayList<String> keys,String keyCol,String table){
        ArrayList<String> cols = new ArrayList<>();

        for (String key:keys){
            cols.add(keyCol);
        }

        try {
            new ViewSpecs(table).getUpdater().deleteOr(cols,keys);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void submitPasers(){
        if(!newPeriodoGetter.hasErrors())
            submit();

    }

    private void setNewPeriodo(){
        String nextPeriodo = getNextPeriodo();
        checkThatPerioDoesntExists(nextPeriodo);
        try {
            new RespaldosManager().createResDir(nextPeriodo);
            String currentPeriodo = Global.conectionData.loadedPeriodo;
            updateCurrentPeriodo(nextPeriodo,currentPeriodo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    private void checkThatPerioDoesntExists(String newPeriodo){

            ArrayList<String> periodoString = new ArrayList<>();
            periodoString.add("periodo");
            periodoString.add("paridad");

            ArrayList<String> periodoNewValue = new ArrayList();
            periodoNewValue.add(newPeriodo);
            periodoNewValue.add(getNewParidad());

            try {
                new ViewSpecs("periodos").getUpdater().insert(periodoString,periodoNewValue);
                new RespaldosManager().deleteResDir(newPeriodo);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


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
