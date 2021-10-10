package sistemaceb;
import Generals.BtnFE;
import JDBCController.DBSTate;
import SpecificViews.LinearHorizontalLayout;
import SpecificViews.LinearVerticalLayout;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Global;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class RespaldoChargerWindow extends Window {

    String periodo;
    RespaldosManager resManager;

    public RespaldoChargerWindow(){
        super();
        resManager = new RespaldosManager();
        periodo = Global.conectionData.loadedPeriodo;
        setTitle("Respaldos");
        addBody(getBody());
    }

    private JPanel getBody(){
        SwitcheableWindowPills pillsPanel = new SwitcheableWindowPills();
            pillsPanel.setBorder(new EmptyBorder(20,40,20,40));

        pillsPanel.addPill("Periodos", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pillsPanel.setView(getPeriodoSelectorPanel());
            }
        });

        pillsPanel.addPill("Respaldos", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pillsPanel.setView(getBackupSelectorPanel());
            }
        });

        return pillsPanel;
    }


    private JPanel getPeriodoSelectorPanel(){
        PanelPeriodoSelector periodoSelector = new PanelPeriodoSelector(resManager.getPriodosBackUps());

        BtnFE seePreiodoBtn = getButton("Ver Periodo",(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String file = periodoSelector.getSelectedFile();

                if (file != null)
                    Global.chargePeriodoRes(periodoSelector.getSelectedFile());
            }
        }));

        BtnFE deletePeriodoBtn = getButton("Borrar Respaldo",(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String file = periodoSelector.getSelectedFile();
                if (file != null){
                    FormDialogMessage form = new FormDialogMessage("Borrar Respaldo","Está cpmpletamente seguro de querer borrar el repaldo " + file);
                        form.addOnAcceptEvent(new genericEvents() {
                            @Override
                            public void genericEvent() {
                                form.closeForm();
                                resManager.deleteResDir(periodoSelector.getSelectedFile());
                                periodoSelector.setOptions(resManager.getPriodosBackUps());
                            }
                        });
                }
            }
        }));

        BtnFE usePeriodoBtn = getButton("Cargar como periodo actual",(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String file = periodoSelector.getSelectedFile();
                if (file != null) {
                    FormDialogMessage dialog = new FormDialogMessage("Cargar respaldo","Se usara ete respaldo como periodo actual, aún así, se creará un respaldo para el periodo " + Global.conectionData.loadedPeriodo);
                        dialog.addOnAcceptEvent(new genericEvents() {
                            @Override
                            public void genericEvent() {
                                dialog.closeForm();
                                resManager.chargePeriodoAsMainDatabase(periodoSelector.getSelectedFile());
                            }
                        });
                }
            }
        }));

        ArrayList<BtnFE> buttons = new ArrayList<>();
            buttons.add(seePreiodoBtn);
            buttons.add(deletePeriodoBtn);
            buttons.add(usePeriodoBtn);

        return getSelectorPanel(periodoSelector,buttons);
    }

    private JPanel getBackupSelectorPanel(){
        PanelPeriodoSelector backupSelector = new PanelPeriodoSelector(resManager.getBackUps(periodo));

        BtnFE seePreiodoBtn = getButton("Ver respaldo",new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String file = backupSelector.getSelectedFile();
                if (file != null)
                    Global.chargeBackup(backupSelector.getSelectedFile());

            }
        });

        ArrayList<BtnFE> buttons = new ArrayList<>();
            buttons.add(seePreiodoBtn);

        if (DBSTate.usingMainDatabase()) {
            BtnFE usePeriodoBtn = getButton("Cargar respaldo", (new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    String file = backupSelector.getSelectedFile();
                    if (file != null) {
                        FormDialogMessage dialog = new FormDialogMessage("Cargar respaldo", "Se usará este respaldo como periodo actual, aún así, se creará un respaldo para el periodo que se está utilizando");
                        dialog.addOnAcceptEvent(new genericEvents() {
                            @Override
                            public void genericEvent() {
                                resManager.chargeBackupAsMainDatabase(backupSelector.getSelectedFile());
                            }
                        });

                    }
                }
            }));
            buttons.add(usePeriodoBtn);
        }


        return getSelectorPanel(backupSelector,buttons);
    }

    private JLabel getErrorLabel(){
        JLabel label = new JLabel("Por favor, Seleccione un respaldo");
            label.setForeground(Color.white);

        return label;
    }

    private BtnFE getButton(String title,MouseAdapter e){
        BtnFE seePreiodoBtn = new BtnFE(title);
            seePreiodoBtn.setPadding(10,10,10,10);
            seePreiodoBtn.setMargins(10,10,10,10,Color.white);
            seePreiodoBtn.setBackground(new Color(52, 152, 219));
            seePreiodoBtn.addMouseListener(e);

        return seePreiodoBtn;
    }

    private JPanel getSelectorPanel(PanelPeriodoSelector periodoSelector,ArrayList<BtnFE> btns){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);

        JPanel buttonsArea = new JPanel(new BorderLayout());
        buttonsArea.setOpaque(false);

        LinearHorizontalLayout butonsContainer = new LinearHorizontalLayout();
        butonsContainer.setOpaque(false);

        for (BtnFE btn: btns)
            butonsContainer.addElement(btn);

        buttonsArea.add(butonsContainer,BorderLayout.EAST);


        panel.add(periodoSelector,BorderLayout.CENTER);
        panel.add(buttonsArea,BorderLayout.SOUTH);

        JLabel errorLabel = getErrorLabel();
        buttonsArea.add(errorLabel,BorderLayout.WEST);

        for (BtnFE btn: btns){
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(periodoSelector.getSelectedFile() == null)
                        errorLabel.setForeground(Color.red);
                }
            });
        }

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(panel);

        JPanel cont = new JPanel(new GridLayout(1,1));
        cont.add(scroll);

        return cont;
    }


    private class PanelPeriodoSelector extends LinearVerticalLayout{

        private JLabel selectedRow;
        private String selectedText;

        public PanelPeriodoSelector(ArrayList<String> options){
            setOptions(options);
        }

        public void setOptions(ArrayList<String> options){
            setVisible(false);
            removeAll();
            for(String option: options)
                addElement(getRowButton(option));
            setVisible(true);
        }

        private JPanel getRowButton(String file){
            JPanel rowContainer = new JPanel(new GridLayout(1,1));
                JLabel row = new JLabel(file);
                    row.setOpaque(true);
                    row.setBorder(new EmptyBorder(10,10,10,10));
                    row.setForeground(new Color(100,100,100));
                    row.setBackground(Color.white);
                    row.setCursor(new Cursor(Cursor.HAND_CURSOR));

            rowContainer.setBorder(new MatteBorder(1,0,0,0,new Color(230,230,230)));
            rowContainer.add(row);

            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    changeSelection(row);
                }
            });

            return rowContainer;
        }

        private void changeSelection(JLabel row){
            selectedText = row.getText();
            row.setBackground(new Color(50,50,50));

            if(selectedRow != null)
                selectedRow.setBackground(Color.white);

            selectedRow = row;

        }

        public String getSelectedFile(){

            return selectedText;
        }

    }


}
