package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfesorHorario extends Operation {

    private Table horario;

    private String keycol;
    private String proptoGet;

    public ProfesorHorario(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Horario";
    }

    private void initvar(){
        if(viewSpecs.getTable().equals("grupos")){
            keycol = "grupo";
            proptoGet = "nombre_completo";
        } else {
            keycol = "profesor";
            proptoGet = "grupo";
        }
    }

    @Override
    public void buildOperation() {
        initvar();
        horario = getFullHorario();
        buildTable();

    }

    private void buildTable(){
        getHorasClase();

        ArrayList<HorarioRowInfo> rows = new ArrayList<>();

        for (String hora: horasClase){
            HorarioRowInfo newInfo = geteRow(hora);
            if (newInfo.hora != null)
                rows.add(newInfo);
        }

        deployPdf(rows);
    }

    private HorarioRowInfo geteRow(String hora){
        HorarioRowInfo row = new HorarioRowInfo();
        row.row = new ArrayList<>();

        Table subtable = horario.getSubTable(new String[]{"hora"},new String[]{hora});

        ArrayList<String> diasClase = getDiasClase();

        for (String dia:diasClase){
            Table reg = subtable.getSubTable(new String[]{"dia"},new String[]{dia});
            ArrayList<String> horaData = new ArrayList<>();

            if (!reg.isEmpty()){
                TableRegister regi = reg.getRegisterObject(0);
                horaData.add(regi.get("nombre_abr"));
                horaData.add(regi.get(proptoGet));
                horaData.add(regi.get("aula"));

                if(row.hora == null)
                    row.hora = regi.get("hora_c");
            }

            row.row.add(horaData);
        }
        return row;
    }

    public class HorarioRowInfo{
        public String hora;
        public ArrayList<ArrayList<String>> row;

    }

    private void deployPdf(ArrayList<HorarioRowInfo> rows){
        ProfesorHorarioPDFBuilder pdf = new ProfesorHorarioPDFBuilder();
            if (viewSpecs.getTable().equals("profesores"))
            pdf.addParams(getParams());

        for (HorarioRowInfo register: rows)
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

    private ArrayList<String> horasClase;
    private ArrayList<String> getHorasClase(){
        if(horasClase == null)
            horasClase = CalifasOperator.getHorasClase();

        return horasClase;
    }


    private ArrayList<String> getDiasClase(){
        ArrayList<String> dias = new ArrayList<>();
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miércoles");
            dias.add("Jueves");
            dias.add("Viernes");

        return dias;
    }

    private Table getFullHorario(){
        DataBaseConsulter consulter = new DataBaseConsulter("");

        return consulter.bringTable("SELECT * FROM cebdatabase.asignaturas_horario_view where " + keycol +" = '" + keyValue + "'  order by hora;");
    }




}
