package JDBCController;

import java.util.ArrayList;

public class DBSTate {
    public  String loadedPeriodo;
    public  String currentDatabase;
    public  boolean periodIsPair;

    public DBSTate(){
        currentDatabase = "cebdatabase";
        resetPeriodo();
    }

    public  Table getMainTables(){
        DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.mainviewsview");

        return  consulter.bringTable();
    }


    public void resetPeriodo(){
        DataBaseConsulter consulter = new DataBaseConsulter("currentperiodo");

        String[] period = new String[]{"periodo"};

        Table reponse = consulter.bringTable(period);

        loadedPeriodo = reponse.getColumn("periodo").get(0);
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

    public  void setDataBase(String base){
        currentDatabase = base;
        resetPeriodo();
    }


}
