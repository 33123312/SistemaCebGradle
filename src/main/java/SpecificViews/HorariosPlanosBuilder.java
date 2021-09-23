package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;

import java.util.ArrayList;
public class HorariosPlanosBuilder extends Operation{

    ArrayList<String> diasClase;
    ArrayList<String> grupos;

    public HorariosPlanosBuilder(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation= "Plano De Horarios";
    }

    @Override
    public void buildOperation() {
        diasClase = getDias();
        grupos = getGrupos();
        pdf = new HorariosPlanosPdf(diasClase,grupos.size());
        deployRows();
    }

    private ArrayList<String> getHoras(){
        DataBaseConsulter consulter = new DataBaseConsulter("horas_clase");

        return consulter.bringTable(new String[]{"hora"}).getColumn(0);

    }

    private ArrayList<String> getDias(){
        DataBaseConsulter consulter = new DataBaseConsulter("dias_clase");

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
                titles.add(register.get("nombre_materia"));
                values.add(register.get("nombre_completo"));
            }

            pdf.addMateriaCell(titles);
            pdf.addTitlesCell(values);
        }
    }

    private ArrayList<TableRegister>  getGrupoHoraDia(String hora,String grupo){
        ArrayList<TableRegister> register = new ArrayList();
        for (String dia:diasClase)
            register.add(getGrupoDiaHora(grupo,dia,hora));

        return register;
    }

    private TableRegister getGrupoDiaHora(String grupo, String dia, String hora){
        DataBaseConsulter consulter = new DataBaseConsulter("horarios_view");

        String[] colsToBring = new String[]{"nombre_materia","materia"};

        String[] cond = new String[]{"grupo","dia","hora"};

        String[] values = new String[]{grupo,dia,hora};

        Table register = consulter.bringTable(colsToBring,cond,values);

        ArrayList<String> newTitles = new ArrayList<>();
            newTitles.add("nombre_materia");
            newTitles.add("nombre_completo");

        register.setColumnTitles(newTitles);

        if(register.isEmpty()){
            ArrayList<ArrayList<String>> newRegiters = new ArrayList<>();
                ArrayList<String> registerA = new ArrayList<>();
                    registerA.add("");
                    registerA.add("");

            newRegiters.add(registerA);
            register.setRegisters(newRegiters);
        } else {


            ArrayList<ArrayList<String>> neweFullRegisters = new ArrayList<>();

            ArrayList<String> neweFirstRegisters = new ArrayList<>();
            neweFirstRegisters.add(register.getUniqueValue());
            String profesor = getProfesor(register.getColumn(1).get(0),grupo);

            if(profesor == null)
                profesor = "No asignado";

            neweFirstRegisters.add(profesor);

            neweFullRegisters.add(neweFirstRegisters);

            register = new Table(newTitles,neweFullRegisters);
        }



            return register.getRegister(0);
    }

    private String getProfesor(String materia, String grupo){
        DataBaseConsulter consulter = new DataBaseConsulter("asignaturas_visible_view");

        String[] colsToBring = new String[]{"nombre_completo"} ;

        String[]  cond = new String[]{"materia","grupo"} ;

        String[]  val = new String[]{materia,grupo} ;


        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();

    }
}
