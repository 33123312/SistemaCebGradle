package sistemaceb.form;

import DInamicPanels.DinamicPanel;
import JDBCController.DBSTate;
import JDBCController.DataBaseConector;
import JDBCController.DataBaseResManager;
import sistemaceb.DInamicWindow;
import sistemaceb.MainWindow;

import java.io.IOException;

public class Global {
    public static DInamicWindow view;
    public static MainWindow currentmainWindow;
    public static DataBaseConector SQLConector;
    public static DBSTate conectionData;

    public static void initGlobal() {
        bootSystem();
    }

     private static void bootSystem(){
         try {
             pauseSystem();

             view = new DInamicWindow();
             SQLConector = new DataBaseConector();
             conectionData = new DBSTate();
             currentmainWindow = new MainWindow();

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public static void pauseSystem(){
         if(SQLConector != null)
            SQLConector.endConection();
         if(currentmainWindow != null)
            currentmainWindow.closeForm();
     }

     public static void closeSystem(){
        pauseSystem();
         System.exit(0);
     }
}
