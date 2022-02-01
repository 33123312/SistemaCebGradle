package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;
import java.util.Arrays;

public class HorariosPlanosBuilder extends Operation{

    ArrayList<String> diasClase;
    ArrayList<String> grupos;
    private Table fullHorario;

    public HorariosPlanosBuilder(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation= "Plano De Horarios";
    }

    @Override
    public void buildOperation() {
        diasClase = getDias();
        grupos = getGrupos();
        pdf = new HorariosPlanosPdf(diasClase,grupos.size());
        fullHorario = getFullHorario();
        deployRows();
    }

    private ArrayList<String> getHoras(){

        return CalifasOperator.getHorasClase();
    }

    private ArrayList<String> getDias(){
        DataBaseConsulter consulter = new DataBaseConsulter("dias_clase_view");

        return consulter.bringTable().getColumn(0);
    }

    private ArrayList<String> getGrupos(){
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] colsToBring = new String[]{"grupo"};

        String[] cond = new String[]{"semestre"};

        String[] values = new String[]{keyValue};

        return consulter.bringTable(colsToBring,cond,values).getColumn(0);
    }

    HorariosPlanosPdf pdf;

    private void deployRows(){
        ArrayList<String> horas = getHoras();
        for (String hora: horas){
            pdf.addHoraCell(hora);
            addRegisters(hora);
        }

        pdf.addTable();
        pdf.close();
    }

    private void  addRegisters(String hora){

        for (String grupo:grupos){
            pdf.addGrupoCell(grupo);
            ArrayList<TableRegister> registers = getGrupoHoraDia(hora,grupo);
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            for (TableRegister register:registers){
                titles.add(register.get("nombre_abr"));
                values.add(register.get("nombre_completo"));
            }

            pdf.addMateriaCell(titles);
            pdf.addTitlesCell(values);
        }
    }

    private ArrayList<TableRegister>  getGrupoHoraDia(String hora,String grupo){
        ArrayList<TableRegister> register = new ArrayList();
        System.out.println(fullHorario.getRegisters());

        for (String dia:diasClase)
            register.add(getGrupoDiaHora(grupo,dia,hora));

        return register;
    }



    private Table getFullHorario(){
        DataBaseConsulter consulter = new DataBaseConsulter("grupo_asignaturas");

        Table register = consulter.bringTable();

        return register;
    }

    private TableRegister getGrupoDiaHora(String grupo, String dia, String hora){

        String[] cond = new String[]{"grupo","dia","hora"};

        String[] values = new String[]{grupo,dia,hora};

        System.out.println(Arrays.toString(values));

        Table register = fullHorario.getSubTable(cond,values);

        if(register.isEmpty()){
            ArrayList<ArrayList<String>> newRegisters = new ArrayList<>();
                ArrayList<String> emptyRegister = new ArrayList<>();
                for (String titles: register.getColumnTitles())
                    emptyRegister.add("");

            newRegisters.add(emptyRegister);
            register.setRegisters(newRegisters);
        }

            return register.getRegister(0);
    }

}
