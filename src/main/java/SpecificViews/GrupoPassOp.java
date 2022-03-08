package SpecificViews;

import JDBCController.BackendController;
import JDBCController.DataBaseConsulter;
import JDBCController.ViewSpecs;
import sistemaceb.AluPassGenerator;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.genericEvents;

import javax.swing.text.View;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrupoPassOp extends Operation{

    public GrupoPassOp(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Reestablecer Contraseña";
    }

    @Override
    public void buildOperation() {
        generate();
    }

    private void generate(){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = BackendController.getRequest("genPass/alumno/" + keyValue)
            .header("Content-Type", "application/json")
            .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
            showDiaog("El proceso ha iniciado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            showDiaog("Error desconocido en el servidor");
        }

    }

    private void showDiaog(String message){
        FormDialogMessage mes = new FormDialogMessage("",message);
        mes.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                mes.closeForm();
            }
        });

    }


}
