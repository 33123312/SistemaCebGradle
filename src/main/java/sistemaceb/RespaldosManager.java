package sistemaceb;

import JDBCController.BackendController;
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

    private String getJsonOrderBackup(String type){
        Map jsonToSend = new HashMap();
            jsonToSend.put("periodo", Global.conectionData.loadedPeriodo);
            jsonToSend.put("type",type);

        return BackendController.stringify(jsonToSend);
    }


    public void orderPeriodoRes() throws IOException, InterruptedException {
        orderBackup("periodoBackup");
    }

    public void orderRes() throws IOException, InterruptedException {
        orderBackup("backup");
    }

    private  void orderBackup(String type) throws IOException, InterruptedException {
        String parsedJSON = getJsonOrderBackup(type);

        HttpRequest request = BackendController.getRequest("orderBackup")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(parsedJSON))
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<InputStream> res =  client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        createLocalRes(res);

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


    public ArrayList<String> getBackUps(String periodo) throws IOException, InterruptedException {
        return consultListaFromServer("getBack/" + periodo);
    }

    public ArrayList<String> getPriodosBackUps() throws IOException, InterruptedException {
        ArrayList<String> periodos = consultListaFromServer("getPeriodosBack");
            periodos.remove(Global.conectionData.loadedPeriodo);
        return
                periodos;
    }

    private ArrayList<String> consultListaFromServer(String endpoint) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = BackendController.getRequest(endpoint)
            .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        String object =  response.body();
            System.out.println(object);
            Gson gson = new Gson();
            Map map = gson.fromJson(object,Map.class);

            return (ArrayList<String>) map.get("list");

    }

    public void chargeBackup(String file) throws IOException, InterruptedException {
        Map jsonToSend = new HashMap();
            jsonToSend.put("file", file);
            jsonToSend.put("periodo", Global.conectionData.loadedPeriodo);
            jsonToSend.put("type", "backup");

            charge(jsonToSend, "chargeBackup");
    }



    public void chargePeriodoBackup(String periodo) throws IOException, InterruptedException {
        Map jsonToSend = new HashMap();
            jsonToSend.put("periodo", periodo);
            jsonToSend.put("type", "periodoBackup");


        charge(jsonToSend, "chargeBackup");
    }

    public void chargeBackupAsMainDatabase(String file) throws IOException, InterruptedException {
        orderRes();
        Map json = new HashMap();
            json.put("type","backup");
            json.put("file",file);

        chargeAsMainDatabase(Global.conectionData.loadedPeriodo,json);

    }

    public void chargePeriodoAsMainDatabase(String periodo) throws IOException, InterruptedException {
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

    private void chargeAsMainDatabase(String periodo,Map json) throws IOException, InterruptedException {
        Map specs = new HashMap();
            specs.putAll(json);
            specs.put("periodo",periodo);

        String endPoint = "useAsMainDatabase";
        charge(specs,endPoint);
        Global.resetPriodo();

    }

    private void charge(Map specs,String endPoint) throws IOException, InterruptedException {
        String parsedJSON = BackendController.stringify(specs);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = BackendController.getRequest(endPoint)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(parsedJSON))
            .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());

    }

    public void deleteResDir(String periodo) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = BackendController.getRequest("deletePeriodoBackDir/" + periodo)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());

    }

    public void createResDir(String periodo) throws IOException, InterruptedException {
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
