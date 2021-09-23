package SpecificViews;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PromsOperations {

    public static ArrayList<String> getVaildCal(ArrayList<String> vallues){
        ArrayList<String> validCal = new ArrayList<>();
        for (String calif:vallues)
            if(calif != null && !calif.equals(""))
                validCal.add(calif);

        return validCal;

    }



    public static String getSumaFaltas(ArrayList<String> faltas){
        faltas = getVaildCal(faltas);
        int sumatoriaFaltas = 0;
        for (String falta:faltas){
            int faltaInt = Integer.parseInt(falta);
            sumatoriaFaltas+=faltaInt;
        }

        return  Integer.toString(sumatoriaFaltas);
    }

    public static String getProm(ArrayList<String> cali) {
        cali = getVaildCal(cali);
        if (cali.isEmpty())
            return "";
        double prom = 0.0;

        for (String cal:cali)
            prom += Double.parseDouble(cal);

        double calsNum = cali.size();
        prom = prom/calsNum;

        DecimalFormat formato1 = new DecimalFormat("#.00");

        return formato1.format(prom);

    }


}
