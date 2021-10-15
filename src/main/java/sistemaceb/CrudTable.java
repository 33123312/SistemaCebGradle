package sistemaceb;

import Generals.BtnFE;
import Generals.DesplegableMenuFE;
import JDBCController.*;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.proccesStateStorage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class CrudTable extends keyedBuildBehavior {

    public int selectedRow;
    public keyHiddedCoonsTableBuild build;

    public CrudTable(keyHiddedCoonsTableBuild build){
        super(build);
        this.build = build;
        deploy();
    }

    private void deploy(){
        table.addRowStyle(new StyleRowModel() {
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row row) {
                row.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        selectedRow = row.getKey();
                    }
                });
                DesplegableMenuFE menu = new DesplegableMenuFE(row) {
                    @Override
                    public BtnFE buttonsEditor(BtnFE button) {
                        button.setTextColor(new Color(100,100,100));
                        button.setPadding(0,0,10,0);
                        return button;
                    }
                };
                menu.setSize(new Dimension(150,100));
                    menu.addButton("Modificar", defineModifyButtonEvent());
                    menu.addButton("Eliminar",defineDeleteButtonEvent() );
                return row;
            }
        });
    }

    public MouseAdapter defineDeleteButtonEvent(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ArrayList<String> updateConditions  = viewSpecs.getInfo().getTableCols();
                ArrayList<String> updateValues = table.getTrueData(selectedRow);

                rowUpdateConfirmationFrame confirmationForm =
                    new rowUpdateConfirmationFrame(
                    "¿Está completamente seguro de eliminar el siguiente registro?",updateConditions,updateValues);
                    confirmationForm.addOnAcceptEvent(new genericEvents(){
                          @Override
                          public void genericEvent(){
                              try {
                                  viewSpecs.getUpdater().delete(updateConditions,updateValues);
                              } catch (SQLException throwables) {
                                  throwables.printStackTrace();
                              }
                              build.updateSearch();
                              confirmationForm.closeForm();
                          }
                      }
                );
            }
        };
    }



    public MouseAdapter defineModifyButtonEvent() {

        return new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                ArrayList<String> columnsToUpdate = build.getTagsToShow();
                FormWindow updateForm = new FormWindow("Modificar registro");
                new TagFormBuilder(viewSpecs, columnsToUpdate, updateForm, false);

                Table currentSearch = build.getDataBaseConsulter().getSearch().getViewRegisters();

                ArrayList<String> updateConditions = currentSearch.getColumnTitles();
                ArrayList<String> selectedRegister = currentSearch.getRegisters().get(selectedRow);

                ArrayList<String> updateConditionsRemovedPK = new ArrayList<>(updateConditions);
                    updateConditionsRemovedPK.remove(build.critCol);

                ArrayList<String> selectedRegisterRemovedPK = new ArrayList<>(selectedRegister);
                    selectedRegisterRemovedPK.remove(build.critValue);

                updateForm.setDefaultValues(updateConditionsRemovedPK,selectedRegisterRemovedPK);

                updateForm.addDataManager(new FormResponseManager() {
                    @Override
                    public void manageData(Formulario form) {
                        Map<String, String> trueData = form.getData();

                        ArrayList<String> responseTagTitles = new ArrayList(trueData.keySet());
                        ArrayList<String> responseValues = new ArrayList(trueData.values());


                        ArrayList<String> responseTitles = viewSpecs.getCol(responseTagTitles);

                        rowUpdateConfirmationFrame confirmationForm = new rowUpdateConfirmationFrame("Se modificara en siguiente registro", responseTagTitles, responseValues);
                        updateForm.getFrame().addChildForm(confirmationForm);
                        ArrayList<String> updateConditions2 = viewSpecs.getCol(updateConditions);
                        confirmationForm.addOnAcceptEvent(new genericEvents() {
                                  @Override
                                  public void genericEvent() {
                                      try {
                                          viewSpecs.getUpdater().update(responseTitles, responseValues, updateConditions2, selectedRegister);
                                      } catch (SQLException throwables) {
                                          throwables.printStackTrace();
                                          new FormDialogMessage("Conflicto al insertar registro",
                                                  "El registro que se ha intentdo añadir ya existe");
                                      }
                                      build.updateSearch();
                                      ((FormWindow) form).getFrame().closeForm();
                                  }
                              }
                        );
                    }
                });
                }
            };
        }


    @Override
    public String getInstructions() {
        return "Da click en un registro para modificarlo o eliminarlo";
    }
}
