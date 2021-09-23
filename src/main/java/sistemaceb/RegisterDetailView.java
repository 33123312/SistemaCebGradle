/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

import RegisterDetailViewProps.RegisterDetail;
import SpecificViews.DefaultRegisterDetailTable;
import SpecificViews.Operation;
import SpecificViews.OperationsManager;
import SpecificViews.RegisterDetailTableTrigerer;

/**
 *
 * @author escal
 */
public class RegisterDetailView extends Window {

    private final ViewSpecs viewSpecs;
    private final DataBaseSearcher dataConsulter;
    public final infoPackage registerInfo;
    private final String primaryKey;
    private ArrayList<RegisterDetailTableTrigerer> tablesTrigerers;
    private RegisterDetailTable currentPillTable;

    public RegisterDetailView(String view, String primaryKey) {
        super();
        tablesTrigerers = new ArrayList<>();
        viewSpecs = new ViewSpecs(view);
        this.primaryKey = primaryKey;
        dataConsulter = new DataBaseSearcher(view);
        registerInfo = searchForRegisterInfo();
        deploy();
        useOprationsManager();
    }

    private String buildWindowTitle() {
        if (viewSpecs.hasHumanKey()) {
            Table reg = registerInfo.getVisibleRegisters();
            return reg.getRegister(0).get(viewSpecs.getInfo().getHumanKey());
        } else
            return registerInfo.getViewRegisters().getRegister(0).get(viewSpecs.getPrimarykey());

    }

    @Override
    public void update() {
        super.update();
        if (currentPillTable != null)
            currentPillTable.getConsultTable().updateSearch();
    }

    private void deploy() {
        editedScrollPanel ScrollView = new editedScrollPanel();
        addBody(ScrollView);

        JPanel body = new JPanel(new BorderLayout());
        body.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));
        body.add(deployRegisterInfoContainer(), BorderLayout.NORTH);
        body.add(deployTablesPanel(), BorderLayout.CENTER);

        ScrollView.setViewportView(body);

        setTitle(buildWindowTitle());

    }

    private JPanel deployRegisterInfoContainer() {
        JPanel parentContainer = new JPanel(new BorderLayout());
        parentContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        parentContainer.setBackground(Color.white);

        JPanel controlsNTitlePanel = headerHeader();

        parentContainer.add(controlsNTitlePanel, BorderLayout.NORTH);
        JPanel reg = new LinkedUserProps(viewSpecs, registerInfo.getViewRegisters());
        parentContainer.add(reg, BorderLayout.CENTER);

        return parentContainer;
    }

    private JPanel headerHeader() {
        JPanel generalContainer = new JPanel(new BorderLayout());
        generalContainer.setOpaque(false);

        JLabel humnKeyLabel = new JLabel();

        humnKeyLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        humnKeyLabel.setText(buildWindowTitle());

        generalContainer.add(humnKeyLabel, BorderLayout.WEST);
        generalContainer.add(deployControlsPanel(), BorderLayout.EAST);

        return generalContainer;

    }

    private infoPackage searchForRegisterInfo() {
        infoPackage registerInfo;
        dataConsulter.newSearch();
        dataConsulter.addSearch(viewSpecs.getPrimarykey(), primaryKey);
        registerInfo = dataConsulter.getSearch();

        return registerInfo;
    }


    private JPanel deployTablesPanel() {
        JPanel parentContainer = new JPanel(new GridLayout(1, 1));
        parentContainer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        parentContainer.add(getPills());

        return parentContainer;
    }


    SwitcheableWindowPills pillsArea;
    private SwitcheableWindowPills getPills( ) {
        pillsArea = new SwitcheableWindowPills();
        return pillsArea;
    }



    public void addCustomPill(RegisterDetailTableTrigerer trigerer){
        tablesTrigerers.add(trigerer);
    }

    private void addPills(){
        addDefaultPills();
        setPillsTrigers();
    }

    private void addDefaultPills(){
        ArrayList<String> relatedTables = viewSpecs.getRelatedTables();
        ArrayList<String> currentPills = getCurrentPillsRelatedTables();

        for (String pill:currentPills)
            if(relatedTables.contains(pill))
                relatedTables.remove(pill);

        for (String table:relatedTables)
            tablesTrigerers.add(new RegisterDetailTableTrigerer(table,new ViewSpecs(table).getInfo().getHumanName()){
                @Override
                public RegisterDetailTable getTable( String critKey, ViewSpecs dadSpecs){
                    return new DefaultRegisterDetailTable(relatedTable,critKey,dadSpecs);
                }
            });
    }

    private ArrayList<String> getCurrentPillsRelatedTables(){
        ArrayList<String> currentPills = new ArrayList<>();
        for (RegisterDetailTableTrigerer trigerer:tablesTrigerers)
            currentPills.add(trigerer.getRelatedTable());

        return currentPills;
    }

    private void setPillsTrigers(){

        for (RegisterDetailTableTrigerer trigerer:tablesTrigerers){
            String humanName = trigerer.pillTitle;
            if(humanName != null)
                pillsArea.addPill(humanName, new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        changePill(trigerer);
                    }
                });
        }
    }

    private void changePill(RegisterDetailTableTrigerer trigerer){
        RegisterDetailTable detailTable = trigerer.getTable(primaryKey, viewSpecs);
        detailTable.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        pillsArea.setView(detailTable);
        currentPillTable = detailTable;
    }

    DesplegableMenuFE operationsMenu;
    
    private JLabel deployControlsPanel() {

        JLabel button = prepairButton("images/settings.png");
            button.setBorder(BorderFactory.createEmptyBorder(5,10,0,10));
            operationsMenu = new DesplegableMenuFE(button) {
                @Override
                public BtnFE buttonsEditor(BtnFE button) {
                    button.setTextColor(new Color(100,100,100));
                    return button;
                }
            };
            operationsMenu.setDisplayedToLeft();

        return button;
    }

    private void useOprationsManager(){
        OperationsManager operationsManager =
                new OperationsManager(
                        viewSpecs,
                        primaryKey,
                        registerInfo.getViewRegisters().getRegister(0));

        RegisterDetail detail = operationsManager.getProps(viewSpecs.getTable());
        searchForOperations(detail);
        searchForPills(detail);

    }

    private void searchForOperations(RegisterDetail operationsManager){

        ArrayList<Operation> operations = operationsManager.getOperations();
        for(Operation op: operations)
            addOperation(op);
    }

    private void searchForPills(RegisterDetail operationsManager){
        ArrayList<RegisterDetailTableTrigerer> customPills = operationsManager.getPills();
        for(RegisterDetailTableTrigerer pill: customPills)
            addCustomPill(pill);
        addPills();
    }

    private void addOperation(Operation op){
        operationsMenu.addButton(op.operation, new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                op.buildOperation();
            }
        });

    }

    private JLabel prepairButton(String url){
        JLabel button;
            button = new JLabel(new ImageIcon(this.getClass().getResource(url)));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }


    

    

}
