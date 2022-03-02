package sistemaceb;

import JDBCController.DBSTate;
import JDBCController.ViewSpecs;
import com.google.gson.Gson;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Global;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RespaldosManager {

    private String serverURL = DBSTate.serverURL + ":3000/";

    private Map getDefJson(){
        Map jsonToSend = new HashMap();
        jsonToSend.put("password",Global.conectionData.password);

            jsonToSend.put("user",Global.conectionData.user);

        return jsonToSend;
    }

    private  String getJsonOrderBackup(String type){
        Map jsonToSend = getDefJson();
            jsonToSend.put("periodo", Global.conectionData.loadedPeriodo);
            jsonToSend.put("type",type);

        return stringify(jsonToSend);
    }

    private String stringify(Map map){
        Gson gson = new Gson();
        return gson.toJson(map,Map.class);
    }

    public void orderPeriodoRes()  {
        orderBackup("periodoBackup");
    }

    public void orderRes(){
        orderBackup("backup");
    }

    private  void orderBackup(String type){
        String parsedJSON = getJsonOrderBackup(type);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + DBSTate.backAuthKey)
                .uri(URI.create(serverURL + "orderBackup"))
                .POST(HttpRequest.BodyPublishers.ofString(parsedJSON))
                .build();

        try {
            HttpResponse<InputStream> res =  client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            createLocalRes(res);

            showDiaog("Se ha respaldado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            showDiaog("Error desconocido en el servidor al hacer el respaldo, no se ha podido realizar");
        }
    }

    private void createLocalRes(HttpResponse<InputStream> res) {
        InputStream resB= res.body();
        try {
            checkIfResDirExists();
            FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/res/" + getLocalResName() + ".sql"));
            resB.transferTo(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDiaog(String message){
        FormDialogMessage mes = new FormDialogMessage("",message);
            mes.addAcceptButton();
            mes.addOnAcceptEvent(new genericEvents() {
                @Override
                public void genericEvent() {
                    mes.closeForm();
                }
            });

    }


    public ArrayList<String> getBackUps(String periodo){
        return consultListaFromServer("getBack/" + periodo);
    }

    public ArrayList<String> getPriodosBackUps(){
        ArrayList<String> periodos = consultListaFromServer("getPeriodosBack");
            periodos.remove(Global.conectionData.loadedPeriodo);
        return
                periodos;
    }

    private ArrayList<String> consultListaFromServer(String endpoint){
        String url = serverURL + endpoint;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String object =  response.body();
            Gson gson = new Gson();
            Map map = gson.fromJson(object,Map.class);

            return (ArrayList<String>) map.get("list");

    }

    public void chargeBackup(String file) {
        Map jsonToSend = getDefJson();
            jsonToSend.put("file", file);
            jsonToSend.put("periodo", Global.conectionData.loadedPeriodo);
            jsonToSend.put("type", "backup");

            charge(jsonToSend, "chargeBackup");
    }



    public void chargePeriodoBackup(String periodo) {
        Map jsonToSend = getDefJson();
            jsonToSend.put("periodo", periodo);
            jsonToSend.put("type", "periodoBackup");

        System.out.println(periodo);

        charge(jsonToSend, "chargeBackup");
    }

    public void chargeBackupAsMainDatabase(String file){
        orderRes();
        Map json = new HashMap();
            json.put("type","backup");
            json.put("file",file);

        chargeAsMainDatabase(Global.conectionData.loadedPeriodo,json);

    }

    public void chargePeriodoAsMainDatabase(String periodo){
        orderPeriodoRes();
        Map json = new HashMap();
            json.put("type","periodoBackup");

        chargeAsMainDatabase(periodo,json);
        truncateBajas("bajas");

    }


    private void truncateBajas(String bajas){
        try {
            Global.SQLConector.getMyStatment().executeUpdate("truncate cebdatabase." + bajas);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void chargeAsMainDatabase(String periodo,Map json){
        Map specs = getDefJson();
            specs.putAll(json);
            specs.put("periodo",periodo);

        String endPoint = "useAsMainDatabase";
        charge(specs,endPoint);
        Global.resetPriodo();

    }

    private void charge(Map specs,String endPoint){
        String parsedJSON = stringify(specs);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")

                .uri(URI.create(serverURL + endPoint))
                .POST(HttpRequest.BodyPublishers.ofString(parsedJSON))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());

        } catch (IOException e) {
            e.printStackTrace();
            showDiaog("Error desconocido al traer la lista, no se ha podido realizar");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showDiaog("Error desconocido al traer la lista, no se ha podido realizar");
        }
    }

    public void deleteResDir(String periodo){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "deletePeriodoBackDir/" + periodo))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());

        } catch (IOException e) {
            e.printStackTrace();
            showDiaog("Error desconocido al borrar el respaldo, no se ha podido realizar");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showDiaog("Error desconocido al borrar el respaldo, no se ha podido realizar");
        }
    }

    public void createResDir(String periodo){
        Map json = new HashMap();
            json.put("periodo",periodo);

        charge(json,"createPeriodoBackupsDir");
    }



    private String getLocalResName(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    private void checkIfResDirExists(){
        File file = new File(System.getProperty("user.dir") + "/res" );
        if(file.exists()){

        } else
            file.mkdirs();
    }


}
