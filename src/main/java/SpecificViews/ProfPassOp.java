package SpecificViews;

import JDBCController.BackendController;
import sistemaceb.AluPassGenerator;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.genericEvents;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProfPassOp extends Operation{

    public ProfPassOp(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Reestablecer Contraseña";
    }

    @Override
    public void buildOperation() {
        generate();
    }

    private void generate(){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = BackendController.getRequest("genPass/profesor/" + keyValue)
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
