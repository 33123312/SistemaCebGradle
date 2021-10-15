package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import RegisterDetailViewProps.RegisterDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfesorHorario extends Operation {

    public ProfesorHorario(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Horario";
    }

    @Override
    public void buildOperation() {
        buildTable();
    }

    private void buildTable(){
        ArrayList<ArrayList<ArrayList<String>>> registers = getHorarioContainer();
        Table asignaturas = getAsignaturas();

        for (TableRegister asignatura: asignaturas.getRegistersObject()){
            String materia = asignatura.get("materia");
            String grupo = asignatura.get("grupo");
            ArrayList<ArrayList<String>> horas = getAsignaturaHoras(materia,grupo);

            for (ArrayList<String> datosAsignatura:horas){
                String hora = datosAsignatura.get(0);
                String dia = datosAsignatura.get(1);

                int horaIndex = getHoraIndex(hora,registers);
                int diaIndex = getDiaIndex(dia);

                ArrayList<String> profInfoToPass = new ArrayList<>();
                    profInfoToPass.add(asignatura.get("nombre_abr"));
                    profInfoToPass.add(asignatura.get("grupo"));
                    profInfoToPass.add(asignatura.get("aula"));

                registers.get(horaIndex).set(diaIndex,profInfoToPass);
            }
        }

        deployPdf(registers);
    }

    private void deployPdf(ArrayList<ArrayList<ArrayList<String>>> registers){
        ProfesorHorarioPDFBuilder pdf = new ProfesorHorarioPDFBuilder();
            pdf.addParams(getParams());
        for (ArrayList<ArrayList<String>> register: registers)
            pdf.addRowHora(register);

        pdf.addTable();
        pdf.close();

    }

    private Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();
            params.put("Profesor",getNombreProfesor());

        return params;
    }

    private String getNombreProfesor(){
        DataBaseConsulter consulter = new DataBaseConsulter("profesores_visible_view");

        String[] bring = new String[]{"nombre_completo"};

        String[]cond = new String[]{"clave_profesor"};

        String[] values = new String[]{keyValue};

        return  consulter.bringTable(bring,cond,values).getUniqueValue();
    }
    private int getHoraIndex(String hora,ArrayList<ArrayList<ArrayList<String>>> registers){
        for (ArrayList<ArrayList<String>> register:registers){
            String currentHora = register.get(0).get(0);
            if (currentHora.equals(hora))
                return registers.indexOf(register);
        }

        return -1;
    }

    private ArrayList<ArrayList<ArrayList<String>>> getHorarioContainer(){
        ArrayList<ArrayList<ArrayList<String>>> horarioContainer = new ArrayList<>();
        ArrayList<String> horas = getHorasClase();
        ArrayList<ArrayList<String>> emptyArray = getEmptyArray();

        for (String hora: horas){
            ArrayList<ArrayList<String>> newRow = new ArrayList<>();
            ArrayList<String> horaArray = new ArrayList<>();
                horaArray.add(hora);
                newRow.add(horaArray);
                newRow.addAll(emptyArray);

            horarioContainer.add(newRow);
        }

        return horarioContainer;

    }

    private ArrayList<ArrayList<String>> getEmptyArray(){
        ArrayList<ArrayList<String>> empty = new ArrayList<>();
            empty.add(new ArrayList());
            empty.add(new ArrayList());
            empty.add(new ArrayList());
            empty.add(new ArrayList());
            empty.add(new ArrayList());

        return empty;
    }

    private ArrayList<String> getHorasClase(){
        return CalifasOperator.getHorasClase();
    }


    private int getDiaIndex(String dia){
        if (dia.equals("Lunes"))
            return 1;
        if (dia.equals("Martes"))
            return 2;
        if (dia.equals("Miércoles"))
            return 3;
        if (dia.equals("Jueves"))
            return 4;
        if (dia.equals("Viernes"))
            return 5;

        return -1;
    }

    private Table getAsignaturas() {
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_visible_view");

        String[] cond = new String[]{"profesor"};

        String[] value = new String[]{keyValue};

        Table response = consulter.bringTable(cond,value);

        return response;
    }

    private ArrayList<ArrayList<String>> getAsignaturaHoras(String materia,String grupo){
        DataBaseConsulter consulter = new DataBaseConsulter("horarios");

        String[] colsToBring = new String[]{"hora","dia"};


        String[] cond = new String[]{"materia","grupo"};

        String[] value = new String[]{materia,grupo};

        Table response = consulter.bringTable(colsToBring,cond,value);

        if(response.isEmpty())
            return new ArrayList<>();
        else
            return response.getRegisters();

    }

}
