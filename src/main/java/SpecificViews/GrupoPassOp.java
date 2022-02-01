package SpecificViews;

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
        operation = "Reestablecer Contraseñas";
    }

    @Override
    public void buildOperation() {
        ArrayList<String> cond = new ArrayList<>();
            cond.add("numero_control");
        ArrayList<String> val = new ArrayList<>();
            val.add(keyValue);

        try {
            new ViewSpecs("webUsers").getUpdater().delete(cond,val);
            generate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showDiaog("Error desconocido en la base de datos");
        }
    }

    private void generate(){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(AluPassGenerator.serverURL + "genAluPass/" + keyValue))
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
