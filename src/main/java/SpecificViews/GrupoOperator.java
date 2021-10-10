package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;

import java.util.ArrayList;

public class GrupoOperator {

    public String grupo;
    public String plan;
    public int numAlumnos;
    private TableRegister grupoInfo;

    public GrupoOperator(String grupo){
        this.grupo = grupo;
        plan = getPlan();
        numAlumnos = getNumAlumnos();
        grupoInfo = getRegisterInfo();
    }

    public TableRegister getGrupoInfo() {
        return grupoInfo;
    }

    private int getNumAlumnos(){
        return getAlumnos().rowCount();
    }

    public Table getMateriasList(String materias){
        if(materias.equals("Numérica"))
            return getMaterias("Numérica");
        else
            return
                    getMaterias("Boleana");
    }


    public Table getMaterias(){
        if(plan != null) {
            DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio_materias_view");
            String[] columnsToBring = new String[]{"materia","nombre_materia"};
            String[] cond = new String[]{"clave_plan"};
            String[] value = new String[]{plan};
            Table materias = consulter.bringTable(columnsToBring, cond, value);

            return materias;

        } else
            return new Table(new ArrayList<>(),new ArrayList<>());
    }

    public Table getMaterias(String tipo){
        if(plan != null) {
            DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio_materias_view");

            String[] columnsToBring = new String[]{"materia","nombre_materia"};

            String[] cond = new String[]{"clave_plan","tipo_calificacion"};

            String[] value = new String[]{plan,tipo};

            Table materias = consulter.bringTable(columnsToBring, cond, value);

            return materias;

        } else
            return new Table(new ArrayList<>(),new ArrayList<>());
    }

    private TableRegister getRegisterInfo(){
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] cond = new String[]{"grupo"};

        String[] value = new String[]{grupo};

        return consulter.bringTable(cond,value).getRegister(0);

    }

    public String getPlan(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] columnsToBring = new String[]{"plan"};

        String[] cond = new String[]{"grupo"};

        String[] value = new String[]{grupo};

        String plan = consulter.bringTable(columnsToBring,cond,value).getUniqueValue();

        return plan;
    }


    Table alumnos;
    public Table getAlumnos(){
        if (alumnos == null) {
            DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");

            String[] colsToBring = new String[]{"numero_control", "nombre_completo"};

            String[] cond = new String[]{"grupo"};

            String[] values = new String[]{grupo};

            Table materias = consulter.bringTable(colsToBring, cond, values);

            alumnos = materias;

            return materias;
        }

        return alumnos;

    }

    public GrupoMateriaOperator getGrupoMateriaOp(String materia){
        return new GrupoMateriaOperator(materia);
    }

    public AsignaturaOperatorr getAsignOperator(String materia){
        return new AsignaturaOperatorr(grupo,materia);
    }

    public class GrupoMateriaOperator{

        ArrayList<AluMateriaNumOperator> operators;


        public GrupoMateriaOperator(String materia){
            operators = new ArrayList<>();
            defineOperators(materia);

        }

        private void defineOperators(String materia){
            ArrayList<String> alumnos = getAlumnos().getColumn(0);
            for (String alumno: alumnos){
                ALumnoOperator operator = new ALumnoOperator(alumno);
                operators.add((AluMateriaNumOperator)operator.getMateriaState(materia));
            }
        }

        private ArrayList<ArrayList<String>> getEmptyProms(){
            ArrayList<ArrayList<String>> proms = new ArrayList<>();
                proms.add(new ArrayList<>());
                proms.add(new ArrayList<>());
                proms.add(new ArrayList<>());
                proms.add(new ArrayList<>());

            return proms;
        }

        public String getPromedioFinal(){
            ArrayList<String> promediosFinales = new ArrayList<>();

            for (AluMateriaNumOperator op: operators){
                String currentPromedio = op.getPromFinal();
                promediosFinales.add(currentPromedio);

            }

            String promedio = PromsOperations.getProm(promediosFinales);

            return promedio;
        }

        public String getSumatoriaFinal(){
            ArrayList<String> sumatoriasFinales = new ArrayList<>();

            for (AluMateriaNumOperator op: operators){
                String currentPromedio = op.getPromFinal();
                sumatoriasFinales.add(currentPromedio);

            }

            String sumatorias = PromsOperations.getSumaFaltas(sumatoriasFinales);

            return sumatorias;
        }

        public ArrayList<String> getPromedioUnidades(){
            ArrayList<ArrayList<String>> proms = getEmptyProms();

            for (AluMateriaNumOperator op: operators){
                ArrayList<String> parCalif = op.getParCalif();
                for (int i = 0; i < 4;i++)
                    proms.get(i).add(parCalif.get(i));
             }

            ArrayList<String> finalProms = new ArrayList<>();

            for (ArrayList<String> unidadVals: proms)
                finalProms.add(PromsOperations.getProm(unidadVals));

            return finalProms;
        }

        public String getPromedioUnidad(int i){
            return getPromedioUnidades().get(i);
        }

        private String getPercentage(int reprobado){
            if(numAlumnos == 0)
                return 0.0 + "";
            else
                return 100*reprobado/numAlumnos + "";
        }

        public ArrayList<String> getFaltasXAlumno(){
            ArrayList<String> sumatoriaFaltas =  getSumatoriaFaltasXUnidad();
            ArrayList<String> faltasPorUnidad = new ArrayList<>();

            for (String faltasUnidad:sumatoriaFaltas)
                if(!faltasPorUnidad.equals("")){
                    int faltasInt = Integer.parseInt(faltasUnidad);
                    if (numAlumnos == 0)
                        faltasPorUnidad.add(0.0 + "");
                    else
                        faltasPorUnidad.add(Double.toString(faltasInt/numAlumnos));
            }

            return faltasPorUnidad;
        }

        private ArrayList<String> getPorcenReprobadosXUnidad(int[] reprobados){
            ArrayList<String> reprobadosPerc = new ArrayList<>();
            for (int reprobado:reprobados )
                reprobadosPerc.add(getPercentage(reprobado));

            return reprobadosPerc;
        }

        public ArrayList<String> getPorcentajeReprobacionXUnidad(){

            return getPorcenReprobadosXUnidad(getReprobadosXUnidad());
        }

        public String getPorcentajeReprobadosEnUnidad(int unidad){
            return getPorcentajeReprobacionXUnidad().get(unidad);
        }

        public int[] getReprobadosXUnidad(){
            int[] reprobados = new int[4];

            for (AluMateriaNumOperator op: operators){
                ArrayList<String> parCalif = op.getParCalif();
                for (int i = 0; i < 4;i++){
                    String cal = parCalif.get(i);
                    if (!cal.isEmpty()){
                        double caliInt = Double.parseDouble(parCalif.get(i));
                        if(caliInt < 6)
                            reprobados[i] ++;
                    }
                }
            }
            return reprobados;
        }

        public int getReprobadosEnUnidad(int unidad){
            return getReprobadosXUnidad()[unidad];
        }

        public ArrayList<String> getSumatoriaFaltasXUnidad(){

            ArrayList<ArrayList<String>> proms = getEmptyProms();

            for (AluMateriaNumOperator op: operators){
                ArrayList<String> parCalif = op.getFaltas();
                for (int i = 0; i < 4;i++)
                    proms.get(i).add(parCalif.get(i));

            }

            ArrayList<String> finalProms = new ArrayList<>();

            for (ArrayList<String> unidadValues: proms)
                finalProms.add(PromsOperations.getSumaFaltas(unidadValues));

            return finalProms;

        }
    }

}
