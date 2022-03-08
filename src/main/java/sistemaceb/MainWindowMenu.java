/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import JDBCController.DBSTate;
import JDBCController.PastPeriodoAlumnoSearcherWindow;
import JDBCController.Table;
import JDBCController.TableRegister;
import SpecificViews.ProfPassGen;
import sistemaceb.form.Global;
import sistemaceb.form.MenuListsContainer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 *
 * @author escal
 */
public class MainWindowMenu extends MenuListsContainer {

    public MainWindowMenu(){
        super();
        addTablas();
        addToolsSubMenu();
    }
    
    private void addTablas()  {
        ListButton list = addNewSubmenu("Tablas","images/tablasIcon.png");

        Table consulTableTables = Global.conectionData.getMainTables();

        for(TableRegister register:consulTableTables.getRegistersObject() ){
            String
                viewName = register.get("table_name"),
                viewAlias = register.get("alias");


    list.addButton(
            new ListButton.Button(viewAlias, new ListButton.WindowBuiler() {
                @Override
                public Window buildWindow() {
                    return new CrudWindow(viewName);
                }
            }).reBuildWindow());
        }
    }

    private void addToolsSubMenu() {
        ListButton list = addNewSubmenu("Herramientas", "images/tablasIcon.png");

            list.addButton(new ListButton.Button("Respaldos", new ListButton.WindowBuiler() {
                @Override
                public Window buildWindow() {
                    return new RespaldoChargerWindow();
                }
            }).storeWindow());



            if (DBSTate.usingMainDatabase()){
                list.addButton(new ListButton.Button("Pasar de periodo", new ListButton.WindowBuiler() {
                    @Override
                    public Window buildWindow() {
                        return new SemestrePasador();
                    }
                }).storeWindow());

                list.addButton(new ListButton.Button("Generar Claves Profesores",new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        new ProfPassGen();
                    }
                } ));

                list.addButton(new ListButton.Button("Generar Claves Alumnos",new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        new AluPassGenerator();
                    }
                } ));

                list.addButton(new ListButton.Button("Recuperar Bajas", new ListButton.WindowBuiler() {
                    @Override
                    public Window buildWindow() {
                        return new PastPeriodoAlumnoSearcherWindow();
                    }
                }).reBuildWindow());
            } else
                list.addButton(new ListButton.Button("Salir del Respaldo",new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        Global.chargeMainDatabase();
                    }
                }));

    }

}
