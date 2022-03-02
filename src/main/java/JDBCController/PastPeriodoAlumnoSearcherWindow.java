package JDBCController;

import Generals.BtnFE;
import SpecificViews.LinearVerticalLayout;
import Tables.AdapTableFE;
import Tables.TableRow;
import sistemaceb.*;
import sistemaceb.Window;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Global;
import sistemaceb.form.formElementWithOptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class PastPeriodoAlumnoSearcherWindow extends Window {
    private primaryKeyedTable table;


    public PastPeriodoAlumnoSearcherWindow(){
        setBody(getBody());
    }

    private JPanel getBody(){
        LinearVerticalLayout container = new LinearVerticalLayout();

            container.addElement(getInstructions());
            container.addElement(getSaerchBar());
            container.addElement(getTable());

        return container;
    }

    private JLabel getInstructions(){
        String mesage ="<html><body>" + "Para recuperar un alumno, ingrese su nombre completo en la barra de busqueda, después,<br>" +
                " seleccionelo en la tabla y proporcione el semestre y grupo al que será enviado" + "</body></html>"
                ;

        JLabel msg = new JLabel(mesage);
            msg.setBorder(new EmptyBorder(10,30,10,30));
            msg.setFont(new Font("Arial", Font.PLAIN, 20));


        return msg;
    }

    private JPanel getSaerchBar(){
        JPanel searchBarCont = new JPanel(new BorderLayout());
            JTextField textField = new JTextField();
            BtnFE btnFE = new BtnFE("buscar");
                btnFE.setPadding(10,10,10,10);
                btnFE.setBackground(new Color(52, 152, 219));

        btnFE.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                searchAlumno(textField.getText());

            }
        });

        searchBarCont.add(textField,BorderLayout.CENTER);
        searchBarCont.add(btnFE,BorderLayout.EAST);

        searchBarCont.setBorder(new EmptyBorder(10,30,10,30));

        return searchBarCont;
    }

    private primaryKeyedTable getTable(){
        table = new primaryKeyedTable();
        table.showAll();


        table.setBorder(new EmptyBorder(10,10,10,10));

        table.getFactory().addGralClickEvnt(new AdapTableFE.rowSelectionEvnt() {
            @Override
            public void whenSelect(TableRow tableRow) {
                ArrayList<String> alumnoData = table.getTrueData(tableRow.getKey());
                traerAlumno(alumnoData);
            }
        });

        return table;
    }

    private void searchAlumno(String nombre){
        Table alumnos = new PastPeriodoAlumnoSearcher(nombre).getRatedAlumnos();

        table.setTitles(alumnos.getColumnTitles());

        table.setRows(alumnos.getRegisters(),alumnos);

    }

    private void traerAlumno(ArrayList<String> alumno){
        if(Global.conectionData.loadedPeriodo.equals(alumno.get(alumno.size()-1))){
            traerDelAtcual(alumno);
        } else {
            traerDePasado(alumno);
        }


    }

    private void traerDelAtcual(ArrayList<String> alumno){
        FormWindow formWindowm = new FormWindow("Elegir nuevo grupo");

        formWindowm.addDesplegableMenu("Grupo").
            setOptions(new DataBaseConsulter("grupos").bringTable(
                        new String[]{"grupo"},
                        new String[]{"semestre"},
                        new String[]{alumno.get(3)}
                ));

        formWindowm.getFrame().addOnAcceptEvent(
                new genericEvents() {
                    @Override
                    public void genericEvent() {
                        if (!formWindowm.hasErrors()){
                            formWindowm.getFrame().closeForm();
                            Map<String,String> data = formWindowm.getData();
                            restaurarAlumno(alumno.get(0),data.get("Grupo"));
                            FormDialogMessage dialogMessage = new FormDialogMessage(
                                    "Éxito","Se ha restauraado el alumno de manera exitosa");
                            dialogMessage.addAcceptButton();
                            dialogMessage.addOnAcceptEvent(new genericEvents() {
                                @Override
                                public void genericEvent() {
                                    dialogMessage.closeForm();
                                }
                            });
                        }
                    }
                });
    }

    private void traerDePasado(ArrayList<String> alumno){
        String periodo = alumno.get(alumno.size()-1);
        FormWindow formWindowm = new FormWindow("Elegir nuevo grupo");


        formWindowm.addDesplegableMenu("Semestre").
                setOptions(Global.conectionData.getSemestres());

        formWindowm.addDesplegableMenu("Grupo");

        formWindowm.
                addElementRelation("Semestre", "Grupo",
                        new formRelationEvent() {
                            @Override
                            public void getNewOptions(String elementInput, formElementWithOptions conditionatedElement) {

                                conditionatedElement.setOptions(
                                        new DataBaseConsulter("grupos").bringTable(
                                                new String[]{"grupo"},
                                                new String[]{"semestre"},
                                                new String[]{elementInput}
                                        ));
                            }
                        });

        formWindowm.getFrame().addOnAcceptEvent(
                new genericEvents() {
                    @Override
                    public void genericEvent() {
                        if (!formWindowm.hasErrors()){
                            formWindowm.getFrame().closeForm();
                            Map<String,String> data = formWindowm.getData();
                            crearAlumno(alumno,data.get("Grupo"),data.get("Semestre"),periodo);
                            FormDialogMessage dialogMessage = new FormDialogMessage(
                                    "Éxito","Se ha restauraado el alumno de manera exitosa");
                            dialogMessage.addAcceptButton();
                            dialogMessage.addOnAcceptEvent(new genericEvents() {
                                @Override
                                public void genericEvent() {
                                    dialogMessage.closeForm();
                                }
                            });
                        }


                    }
                }
        );


    }

    private void restaurarAlumno(String numC, String nuevG){
        ArrayList<String> colSet = new ArrayList<>();
            colSet.add("grupo");

        ArrayList<String> valSet = new ArrayList<>();
            valSet.add(nuevG);

        ArrayList<String> colcon = new ArrayList<>();
            colcon.add("numero_control");

        ArrayList<String> valCon = new ArrayList<>();
            valCon.add(numC);

        try {
            new ViewSpecs("alumnos").getUpdater().update(colSet,valSet,colcon,valCon);

            ArrayList<String> colConDel = new ArrayList<>();
                colConDel.add("numero_control");

            ArrayList<String> valConDel = new ArrayList<>();
                valConDel.add(numC);

            new ViewSpecs("bajas_periodo")
                .getUpdater()
                .delete(colConDel,valConDel);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




    }

    private void crearAlumno(ArrayList<String> alumnoInfo,String grupo,String semestre,String periodo){

        new RespaldosManager().chargePeriodoBackup(periodo);

        DataBaseConector conector = new DataBaseConector("");

        String[] cond = new String[]{"numero_control"};

        String[] val = new String[]{alumnoInfo.get(0)};

        restoreAlumno(cond,val,conector.getMyStatment(),grupo,semestre);
        restoreAlumnoData(cond,val,"webUsers",conector.getMyStatment());
        restoreAlumnoData(cond,val,"alumnos_contacto",conector.getMyStatment());
        restoreAlumnoData(cond,val,"tutores",conector.getMyStatment());

        conector.endConection();
    }

    private void restoreAlumno(String[] cond, String[] values,Statement stat,String semestre,String grupo){
        Table alumno = getdatafromRespaldo(cond,values,"alumnos",stat);
        ArrayList<String> alumnoR = alumno.getRegisters().get(0);

        alumnoR.remove(alumno.columnTitles.indexOf("grupo"));
        alumnoR.add(alumno.columnTitles.indexOf("grupo"),semestre);

        alumnoR.remove(alumno.columnTitles.indexOf("semestre"));
        alumnoR.add(alumno.columnTitles.indexOf("semestre"),grupo);

        restoreAlumnoData("alumnos",alumno);

    }

    private void restoreAlumnoData(String table, Table data){

        if(!data.isEmpty()) {
            try {
                new ViewSpecs(table).getUpdater().insert(data.getColumnTitles(),data.getRegister(0).getValues());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void restoreAlumnoData(String[] cond, String[] val, String table, Statement stat){
        Table data = getdatafromRespaldo(cond,val,table,stat);
        restoreAlumnoData(table,data);

    }

    private Table getdatafromRespaldo(String[] cond, String[] val, String table, Statement stat){
        return  new DataBaseConsulter(
                table,stat).
                bringTable(cond,val);
    }





}
