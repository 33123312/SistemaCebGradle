package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.DataBaseUpdater;
import JDBCController.Table;

import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.*;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.FormWindow;
import sistemaceb.form.Formulario;
import sistemaceb.form.Global;

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
                FormDialogMessage dialog = new FormDialogMessage("Cambiar Plan del grupo",getCoonfirmationMessage());
                dialog.addOnAcceptEvent(new genericEvents() {
                    @Override
                    public void genericEvent() {
                        updatePlan(formulario.getData());
                        dialog.closeForm();
                        formulario.getFrame().closeForm();
                    }
                });
            }
        });
        formulario.showAll();
    }

    private Table getPlanes(){
        DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio");
        String[] colsToBring = new String[]{"nombre_plan","clave_plan"};

        return consulter.bringTable(colsToBring);
    }

    private String getCoonfirmationMessage(){
        return "<html><body>" +
                    "Al cambiar el plan de estudios de este grupo, todas las materias del plan anterior que no pertenezcan " +
                    "al nuevo plan también serán eliminadas de las asignaturas y el horario de éste grupo." +
                    "¿Ésta completamente seguro de cambiar de plan?" +
                "</body></html>";
    }

    private void updatePlan(Map<String,String> data) {

        ArrayList<String> origCond = new ArrayList<>();
            origCond.add("grupo");

        ArrayList<String> originalValues = new ArrayList<>();
            originalValues.add(keyValue);

        try {
            new DataBaseUpdater("plan_grupo").delete(origCond, originalValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String nuevoPlan = data.get("clave_plan");
        if(nuevoPlan != null){
            ArrayList<String> keys = new ArrayList<>();
            keys.add("plan");
            keys.add("grupo");
            ArrayList<String> values = new ArrayList<>();
            values.add(nuevoPlan);
            values.add(keyValue);

            try {
                new DataBaseUpdater("plan_grupo").insert(keys,values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        updater.makeUpdate();
    }



}
