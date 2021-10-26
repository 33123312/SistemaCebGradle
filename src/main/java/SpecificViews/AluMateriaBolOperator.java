package SpecificViews;

import java.util.ArrayList;

public class AluMateriaBolOperator extends  AluMateriaOperator{

    AluMateriaBolOperator(String materia,String type,String periodo,ALumnoOperator aLumnoOperator) {
        super(materia,type,periodo,aLumnoOperator);
    }

    public String getPromFinal(){
        ArrayList<String> validValues = PromsOperations.getVaildCal(parcialesCal);

        if(validValues.isEmpty())
            return "";

        int prom = 1;
        for (String calif:parcialesCal){
            if(calif != null && !calif.isEmpty()){
                int currenaCalifa = Integer.parseInt(calif);
                prom*= currenaCalifa;
            }
        }
        String res = Integer.toString(prom);
        return convertToString(res);
    }

    @Override
    public String getEvaluacionCalif(String eva) {
        String califBol = super.getEvaluacionCalif(eva);
        return convertToString(califBol);
    }

    @Override
    public ArrayList<String> getParCalif() {
        return convertBolsToString(super.getParCalif());
    }

    private ArrayList<String> convertBolsToString(ArrayList<String> calificacion){
        ArrayList<String> converted = new ArrayList<>();
        for (String bol: calificacion)
            converted.add(convertToString(bol));

        return converted;
    }

    private String convertToString(String calificacionString){
        if (calificacionString == null || calificacionString.equals(""))
            return "";
        else{
            int intCali = (int) Double.parseDouble(calificacionString);

            if(intCali == 0)
                return "NA";
            else
                return "A";
        }

    }
}
