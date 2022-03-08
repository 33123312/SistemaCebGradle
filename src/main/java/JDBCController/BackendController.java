package JDBCController;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class BackendController {

    //public static final String url = "http://localhost:3000/";
    public static final String url = DBSTate.serverURL + ":3000/";
    private static final String backUser = "admin";
    private static final String password = "pocheto";
    private static String accesToken;


    public static HttpRequest.Builder getRequest (String endpoint){
        try {

            String backUrl = url + endpoint;
            System.out.println(backUrl);
            return HttpRequest.newBuilder()
                    .uri(URI.create(backUrl))
                    .header("Content-Type", "application/json")
                    .header("x-acces-token",getAccesToken());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getAccesToken() throws IOException, InterruptedException {
        if (accesToken == null){
            login();
        }

        return accesToken;
    }

    private static void login() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url + "login"))
                .POST(HttpRequest.BodyPublishers.ofString(getCredentialsAsJson()))
                .build();

        HttpResponse<String> res =  client.send(request, HttpResponse.BodyHandlers.ofString());
        accesToken = res.body();

    }

    private static String getCredentialsAsJson(){
        Map<String,String> credentials = new HashMap<>();
            credentials.put("user",backUser);
            credentials.put("password",password);

        return stringify(credentials);
    }

    public static String stringify(Map map){
        Gson gson = new Gson();
        return gson.toJson(map,Map.class);
    }


}
