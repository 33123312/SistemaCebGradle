package JDBCController;

import sistemaceb.form.Global;

import java.util.ArrayList;

public class DBSTate {
    public  String loadedPeriodo;
    public  boolean periodIsPair;
    private Table mainTables;

    public static String currentDatabase;
    public static String user;
    public static String password;


    public DBSTate() {
        user = "remote";
        password = "Kinareth41ñ$";

        useMainDatabase();

    }

    public static boolean usingMainDatabase(){
        return currentDatabase.equals("cebdatabase");
    }

    public Table getMainTables(){
        if( mainTables == null)
            mainTables = new DataBaseConsulter("viewsspecs.mainviewsview").bringTable();

        return mainTables;
    }


    public void useMainDatabase(){
        setDataBase( "cebdatabase");
    }

    public void useResDatabase(){ setDataBase( "backDatabase");
    }

    public void setCurrentPeriodo(){
        DataBaseConsulter consulter = new DataBaseConsulter("currentperiodo");

        String[] period = new String[]{"periodo"};

        Table reponse = consulter.bringTable(period);

        loadedPeriodo = reponse.getUniqueValue();
        getParidad();
    }

    private  void getParidad(){
        DataBaseConsulter consulter = new DataBaseConsulter("periodos");

        String[] cond = new String[]{"periodo"};

        String[] val = new String[]{loadedPeriodo};

        Table reponse = consulter.bringTable(cond,val);

        String pairString = reponse.getColumn("paridad").get(0);

        if (pairString.equals("0") )
            periodIsPair = true;
        else
            periodIsPair = false;
    }

    public  ArrayList<String> getSemestres(){
        ArrayList<String> semestres = new ArrayList<>();
        if (periodIsPair){
            semestres.add("2");
            semestres.add("4");
            semestres.add("6");
        } else{
            semestres.add("1");
            semestres.add("3");
            semestres.add("5");
        }

        return semestres;
    }

    public void setDataBase(String base){
        currentDatabase = base;
        Global.SQLConector.reConect();
        setCurrentPeriodo();
    }



}
