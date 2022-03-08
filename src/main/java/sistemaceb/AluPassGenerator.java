package sistemaceb;

import JDBCController.BackendController;
import sistemaceb.form.FormDialogMessage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AluPassGenerator {

    public static String serverURL = "http://147.182.129.199:3000/";
    //public static String serverURL = "http://localhost:3000/";

    public AluPassGenerator(){
        FormDialogMessage mes = new FormDialogMessage(
                "",
                "Se generarán claves para todos los alumnos que no tengan ya una,");
        mes.addCloseButton();
        mes.addAcceptButton();
        mes.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                mes.closeForm();
                generate();
            }
        });

    }


    private void generate(){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = BackendController.getRequest("genPass/alumno")
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
