package JDBCController;

import java.io.*;

public class DataBaseResManager {




    public static void chargeBackup(String base,String backup){
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("mysql -u root -pkinareth41 " + base);
            OutputStream os = p.getOutputStream(); //Pedimos la salida
            FileInputStream fis = new FileInputStream(backup + ".sql"); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer

            int leido = fis.read(buffer);//Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                os.write(buffer, 0, leido); //Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = fis.read(buffer);
            }

            os.flush(); //vacía los buffers de salida
            os.close(); //Cierra
            fis.close(); //Cierra respaldo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createBackUp(String backName, String baseToBack){
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("mysqldump -u root -pkinareth41 " + baseToBack);
            InputStream is = p.getInputStream();//Pedimos la entrada
            FileOutputStream fos = new FileOutputStream(backName + ".sql"); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer

            int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = is.read(buffer);
            }

            fos.close();//Cierra respaldo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
