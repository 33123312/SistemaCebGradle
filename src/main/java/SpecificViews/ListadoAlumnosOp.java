package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListadoAlumnosOp extends  Operation {

    GrupoListaGenerator generator;

    public ListadoAlumnosOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Ver Directorio";
    }

    private void buildTables() {
        ArrayList<String> alus = getAluKeys();
        for (String alu : alus)
            generator.addAluInfoTable(getAlumnooinfo(alu));

        generator.close();

    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("Grupo", keyValue);

        return params;
    }

    private ArrayList<String> getPropierty() {
        ArrayList<String> props = new ArrayList<>();
        props.add("Matricula");
        props.add("Nombre");
        props.add("Fecha Nac.");
        props.add("CURP");
        props.add("Domicilio");
        props.add("Num. tel");
        props.add("Tutor");
        props.add("Ocupacion");
        props.add("Num. tel");

        return props;
    }

    private ArrayList<String> getAluKeys() {
        DataBaseConsulter consulter = new DataBaseConsulter("alumnos");

        String[] colsToBring = new  String[]{"numero_control"};

        String[] conditions = new  String[]{"grupo"};

        String[] values = new  String[]{keyValue};

        return consulter.bringTable(colsToBring, conditions, values).getColumn(0);
    }

    private ArrayList<String> getAlumnooinfo(String alumno){
        ArrayList info = getAlumnoReg(alumno);
            info.addAll(getContactoInfo(alumno));
            info.addAll(getTutor(alumno));

        return info;
    }

    private ArrayList<String> getAlumnoReg(String matricula){
        DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");

        String[] bring = new String[]{"numero_control","nombre_completo","fecha_nacimiento","CURP"};

        String[] cond = new String[]{"numero_control"};

        String[] values = new String[]{matricula};

        Table response = consulter.bringTable(bring,cond,values);

        return response.getRegister(0).getValues();

    }

    private ArrayList<String> getTutor(String matricula){
        DataBaseConsulter consulter = new DataBaseConsulter("tutores");

        String[] bring = new String[]{"nombres","ocupacion","telefono"};

        String[] cond = new String[]{"numero_control"};

        String[] values = new String[]{matricula};

        Table response = consulter.bringTable(bring,cond,values);

        if (response.isEmpty())
            return  getEmptyArray(3);
        else
            return response.getRegister(0).getValues();

    }

    private ArrayList<String> getContactoInfo(String matricula){
        DataBaseConsulter consulter = new DataBaseConsulter("alumnos_contacto");

        String[] bring = new String[]{"domicilio","num_telefonico"};

        String[] cond = new String[]{"numero_control"};

        String[] values = new String[]{matricula};

        Table response = consulter.bringTable(bring,cond,values);

        if (response.isEmpty())
            return  getEmptyArray(2);
        else
            return response.getRegister(0).getValues();

    }

    private ArrayList<String> getEmptyArray(int cont){
        ArrayList<String> emptyArray = new ArrayList<>();

        for (int i = 0; i < cont;i++)
            emptyArray.add("");

        return emptyArray;
    }

    @Override
    public void buildOperation() {

        generator =new GrupoListaGenerator(getPropierty(),getParams());
        buildTables();


    }

}
