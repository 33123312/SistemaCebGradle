package sistemaceb;

import sistemaceb.form.FormDialogMessage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AluPassGenerator {

    //private  String serverURL = "http://147.182.129.199:3000/";
    public static String serverURL = "http://localhost:3002/";

    public AluPassGenerator(){
        FormDialogMessage mes = new FormDialogMessage(
                "",
                "Se generarán claves para todos los alumnos que no tengan ya una," +
                " dado que nuestro servicio de email sólo permite enviar 90 mails diarios, éste proceso tardará varios días " +
                "en concretarse. También tenga en cuenta que es un proceso experimental y puede fallar, en ese caso reinicie el proceso" +
                " o contacte al administrador");
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
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(serverURL + "genAluPass"))
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
