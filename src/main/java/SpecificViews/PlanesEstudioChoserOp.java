package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.FormResponseManager;
import sistemaceb.PlanEstudioMateriasUpdater;
import sistemaceb.RegisterDetailView;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.Global;
import sistemaceb.genericEvents;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
public class PlanesEstudioChoserOp extends Operation{
    PlanEstudioMateriasUpdater updater;

    public PlanesEstudioChoserOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Cambiar Plan";
    }

    @Override
    public void buildOperation() {
        updater = new PlanEstudioMateriasUpdater(keyValue);
        deployForm();
    }
    private void deployForm (){
        FormWindow formulario = new FormWindow("Cambiar Plan de Estudio");
            formulario.addDesplegableMenu("Nuevo Plan").setOptions(getPlanes());

        formulario.addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                FormDialogMessage dialog = new FormDialogMessage(
                    "Cambiar Plan del grupo",
                    getCoonfirmationMessage());
                dialog.addCloseButton();
                dialog.addAcceptButton();
                dialog.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent() {
                        dialog.closeForm();
                        formulario.getFrame().closeForm();

                        updatePlan(formulario.getData());
                        RegisterDetailView currentViewWindow = (RegisterDetailView)Global.view.currentWindow.thisWindow;
                            currentViewWindow.updateCurrentPill();

                    }
                });
            }
        });
    }

    private Table getPlanes(){
        DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio");

        String semestre = new GrupoOperator(keyValue).getRegisterValue("semestre");

        String[] colsToBring = new String[]{"descripcion","clave_plan"};

        String[] cond = new String[]{"semestre"};

        String[] val = new String[]{semestre};

        return consulter.bringTable(colsToBring,cond,val);
    }

    private String getCoonfirmationMessage(){
        return "<html><body>" +
                    "Al cambiar el plan de estudios de este grupo, todas las materias del plan anterior que no pertenezcan " +
                    "al nuevo plan tambi?n ser?n eliminadas de las asignaturas y el horario de ?ste grupo." +
                    "??sta completamente seguro de cambiar de plan?" +
                "</body></html>";
    }

    private void updatePlan(Map<String,String> data) {

        ArrayList<String> origCond = new ArrayList<>();
            origCond.add("grupo");

        ArrayList<String> originalValues = new ArrayList<>();
            originalValues.add(keyValue);

        ViewSpecs.Updater regUpdater = new ViewSpecs("plan_grupo").getUpdater();

        try {
            regUpdater.delete(origCond, originalValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(data);
        String nuevoPlan = data.get("clave_plan");
        ArrayList<String> keys = new ArrayList<>();
            keys.add("plan");
            keys.add("grupo");
        ArrayList<String> values = new ArrayList<>();
            values.add(nuevoPlan);
            values.add(keyValue);

        try {
            regUpdater.insert(keys,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        updater.makeUpdate();
    }

}
