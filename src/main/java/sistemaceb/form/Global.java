package sistemaceb.form;

import JDBCController.DBSTate;
import JDBCController.DataBaseConector;
import sistemaceb.DInamicWindow;
import sistemaceb.MainWindow;
import sistemaceb.RespaldosManager;

import java.io.IOException;

public class Global {
    public static DInamicWindow view;
    public static MainWindow currentmainWindow;
    public static DataBaseConector SQLConector;
    public static DBSTate conectionData;

    public static void initGlobal() {
        SQLConector = new DataBaseConector();
        conectionData = new DBSTate();
        createView();
    }

     public static void resetPriodo(){
        conectionData.setCurrentPeriodo();
        reStartView();
     }

     private static void createView(){
         try {
             view = new DInamicWindow();
             currentmainWindow = new MainWindow();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public static void chargeBackup(String file){
        try {
            new RespaldosManager().chargeBackup(file);
            conectionData.useResDatabase();
            reStartView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void chargePeriodoRes(String periodo){
        try {
            new RespaldosManager().chargePeriodoBackup(periodo);
            conectionData.useResDatabase();
            reStartView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void chargeMainDatabase(){
        conectionData.useMainDatabase();
        reStartView();
    }

     public static void reStartView(){
         currentmainWindow.closeForm();
         createView();
         SQLConector.reConect();

     }

     public static void closeView(){
         if(SQLConector != null)
            SQLConector.endConection();
         if(currentmainWindow != null)
            currentmainWindow.closeForm();
     }

     public static void closeSystem(){
            closeView();
         System.exit(0);
     }
}
