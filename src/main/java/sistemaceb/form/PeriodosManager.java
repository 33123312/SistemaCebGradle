package sistemaceb.form;

import JDBCController.DBSTate;
import JDBCController.DataBaseResManager;

public class PeriodosManager {

    public static void changeToBackup(){
        setDataBase("cebdatabase_backup");
    }

    public static void changeToMain(){
        setDataBase("cebdatabase");
    }

    private static void setDataBase(String base){
        Global.conectionData.setDataBase(base);
    }

    public static void chargeDatabaseToBack(String periodo){
        loadBackDatabase(periodo);
        changeToBackup();
        Global.conectionData.resetPeriodo();
    }

    public static void createPriodoFinalBackup(){
        DataBaseResManager.createBackUp(Global.conectionData.loadedPeriodo,Global.conectionData.currentDatabase);
    }

    public static void createPriodoBackup(){
        String periodoName = Global.conectionData.loadedPeriodo;
        periodoName = periodoName + "" + getPeriodosNumber();
        DataBaseResManager.createBackUp(periodoName,Global.conectionData.currentDatabase);

    }

    private static String getPeriodosNumber(){
        return null;
    }

    private static void loadBackDatabase(String periodo){
        DataBaseResManager.chargeBackup(periodo,"cebdatabase_backup");


    }
}
