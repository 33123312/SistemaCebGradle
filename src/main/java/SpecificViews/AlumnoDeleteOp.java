package SpecificViews;

import JDBCController.DataBaseUpdater;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.genericEvents;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoDeleteOp extends Operation{

    public AlumnoDeleteOp(OperationInfoPanel infoPanlel){
        super(infoPanlel);
        operation = "Dar de Baja";
    }

    @Override
    public void buildOperation() {
        showConfMessage();
    }

    private void showConfMessage(){
        String confMessageTxt = getDialogMsg();
        FormDialogMessage message = new FormDialogMessage("Dar alumno de baja",confMessageTxt);
        message.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                darDeBaja();
                message.closeForm();
            }
        });

    }

    private void darDeBaja(){
        ArrayList<String> colstoChange = new ArrayList<>();
            colstoChange.add("grupo");

        ArrayList<String> valuesToChange = new ArrayList<>();
            valuesToChange.add("NULL");

        ArrayList<String> cond = new ArrayList<>();
            cond.add("numero_control");

        ArrayList<String> values = new ArrayList<>();
            values.add(keyValue);

        try {
            new ViewSpecs("alumnos").getUpdater().update(colstoChange,valuesToChange,cond,values);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getDialogMsg(){
        return
                "Atención: al dar de baja a éste alumno, su información no será borrada del sistema, simplemente se cambirá su " +
                "grupo a un valor nulo, de manera que sus calificaciones y record se conservarán en la base de datos," +
                "¿Desea dar de baja a éste alumno?" ;

    }
}
