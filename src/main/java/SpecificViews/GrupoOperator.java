package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import sistemaceb.form.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrupoOperator extends TableOperator{

    String plan;
    public GrupoOperator(String grupo){
        super(grupo,"grupos");
        plan = getPlan();
        materias = new HashMap();
    }

    public Table getMateriasList(String materias){
        if(materias.equals("Numérica"))
            return getMaterias("Numérica");
        else
            return
                    getMaterias("A/NA");
    }

    public int getNumAlu(){
        String numAlumnos = getRegisterValue("alumnos");
        return Integer.parseInt(numAlumnos);

    }

    public Table getMaterias(){
        if(plan != null) {
            DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio_materias_view");
            String[] columnsToBring = new String[]{"materia","nombre_abr"};
            String[] cond = new String[]{"clave_plan"};
            String[] value = new String[]{plan};
            Table materias = consulter.bringTable(columnsToBring, cond, value);

            return materias;

        } else
            return new Table(new ArrayList<>(),new ArrayList<>());
    }

    Map<String,Table> materias;

    public Table getMaterias(String tipo){
        if(plan != null) {
            if (!materias.containsKey(tipo)) {
                DataBaseConsulter consulter = new DataBaseConsulter("planes_estudio_materias_view");

                String[] columnsToBring = new String[]{"materia", "nombre_abr"};

                String[] cond = new String[]{"clave_plan", "tipo_calificacion"};

                String[] value = new String[]{plan, tipo};

                Table materias = consulter.bringTable(columnsToBring, cond, value);

                this.materias.put(tipo, materias);

                return materias;
            }  else return materias.get(tipo);

        } else
            return new Table(new ArrayList<>(),new ArrayList<>());
    }

    public ArrayList<ALumnoOperator> getAlumnosOpUsBol(){
        ArrayList<ALumnoOperator> op = new ArrayList<>();
        Table alumnos = getFullAlumnos();
        Table boleta = getboleta("alumno_bol_califa_charge_view");

        for (int i = 0; i < alumnos.rowCount();i++){
            TableRegister aluReg = alumnos.getRegisterObject(i);
            op.add(new ALumnoOperator(aluReg,boleta,this));
        }

        return op;
    }

    public ArrayList<ALumnoOperator> getAlumnosOpUsNum(){

        ArrayList<ALumnoOperator> op = new ArrayList<>();
        Table alumnos = getFullAlumnos();
        Table boleta = getboleta("alumno_num_califa_charge_view");
        Table boletaSemestral = getSemestralesboleta();

        for (int i = 0; i < alumnos.rowCount();i++){
            TableRegister aluReg = alumnos.getRegisterObject(i);
            op.add(new ALumnoOperator(aluReg,boleta,boletaSemestral,this));
        }

        return op;
    }


    private Table getboleta(String view){
        DataBaseConsulter consulter = new DataBaseConsulter(view);

        String[] colsToBring = new String[]{"numero_control","materia","nombre_abr","calificacion","faltas","evaluacion"};

        String[] cond = new String[]{"grupo","periodo"};

        String[] val = new String[]{getTableRegister(), Global.conectionData.loadedPeriodo};

        return consulter.bringTable(colsToBring,cond,val);

    }

    private Table getSemestralesboleta(){
        DataBaseConsulter consulter = new DataBaseConsulter("alumno_sem_califa_charge_view");

        String[] colsToBring = new String[]{"numero_control","materia","nombre_abr","calificacion"};

        String[] cond = new String[]{"grupo","periodo"};

        String[] val = new String[]{getTableRegister(), Global.conectionData.loadedPeriodo};

        return consulter.bringTable(colsToBring,cond,val);

    }

    public String getPlan(){
        DataBaseConsulter consulter = new DataBaseConsulter("plan_grupo");

        String[] columnsToBring = new String[]{"plan"};

        String[] cond = new String[]{"grupo"};

        String[] value = new String[]{tableRegister};

        String plan = consulter.bringTable(columnsToBring,cond,value).getUniqueValue();

        return plan;
    }



    public Table getAlumnos(){
            DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");

            String[] colsB = new String[]{"numero_control","nombre_completo"};

            String[] cond = new String[]{"grupo"};

            String[] values = new String[]{tableRegister};

            Table materias = consulter.bringTable(colsB,cond, values);

            return materias;

    }

    public Table getFullAlumnos(){
            DataBaseConsulter consulter = new DataBaseConsulter("alumnos_visible_view");
            String[] cond = new String[]{"grupo"};

            String[] values = new String[]{tableRegister};

            Table materias = consulter.bringTable(cond, values);

            return materias;

    }

    public GrupoMateriaOperator getGrupoMateriaOp(String materia){
        return new GrupoMateriaOperator(materia);
    }

    public AsignaturaOperator getAsignOperator(String materia){
        return new AsignaturaOperator(tableRegister,materia);
    }

    public GrupoBoletaOperator getGrupoBoletaOperator(){return new GrupoBoletaOperator();}

    public class GrupoBoletaOperator{

        private ArrayList<ALumnoOperator.TodasMateriasOps> alumnos;
        private ArrayList<ALumnoOperator> operators;
        public String grupo = getRegisterValue("grupo");

        public GrupoBoletaOperator(){
            alumnos = getAlumnos();
        }

        public GrupoMateriaOperator getGrupoMateriaOp(String materia){
            return new GrupoMateriaOperator(materia,operators);
        }

        private ArrayList<ALumnoOperator.TodasMateriasOps> getAlumnos(){
            operators = getAlumnosOpUsNum();

            ArrayList<ALumnoOperator.TodasMateriasOps> alumnos = new ArrayList<>();

            for(ALumnoOperator alumno:operators)
                alumnos.add(alumno.getMateriaOperators());

            return alumnos;
        }

        public String[] getTopAlumnoSemestral(){
            double biggerProm = 0;
            String nombreAlumno = "";

            for (ALumnoOperator.TodasMateriasOps alumno: alumnos){
                try{
                    double prom = Double.parseDouble(alumno.getPromSemestrales());
                    if (biggerProm < prom){
                        biggerProm = prom;
                        nombreAlumno = alumno.operator.getRegisterValue("nombre_completo");
                    }
                } catch (Exception e){

                }


            }

            return new String[]{nombreAlumno,""+biggerProm};
        }

        public String[] getTopAlumnoFinal(){
            double biggerProm = 0;
            String nombreAlumno = "";

            for (ALumnoOperator.TodasMateriasOps alumno: alumnos){
                try{
                    double prom = Double.parseDouble(alumno.getPromFinal());
                    if (biggerProm < prom){
                        biggerProm = prom;
                        nombreAlumno = alumno.operator.getRegisterValue("nombre_completo");
                    }
                } catch (Exception e){

                }

            }

            return new String[]{nombreAlumno,""+biggerProm};
        }

        public String[] getTopAlumno(int unidad){
            double biggerProm = 0;
            String nombreAlumno = "";

            for (ALumnoOperator.TodasMateriasOps alumno: alumnos){
                try{
                    double prom = Double.parseDouble(alumno.getPromUnidad().get(unidad));
                    if (biggerProm < prom){
                        biggerProm = prom;
                        nombreAlumno = alumno.operator.getRegisterValue("nombre_completo");
                    }
                } catch (Exception e){

                }

            }

            return new String[]{nombreAlumno,""+biggerProm};
        }

    }


    public class GrupoMateriaOperator{

        private ArrayList<AluMateriaNumOperator> operators;

        public GrupoMateriaOperator(String materia,  ArrayList<ALumnoOperator> operators){
            this.operators = getOperators(materia,operators);

        }

        public GrupoMateriaOperator(String materia){
            operators = getOperators(materia);

        }

        private ArrayList<AluMateriaNumOperator> getOperators(String materiaKey){
            ArrayList<ALumnoOperator> aluOp = getAlumnosOpUsNum();

            ArrayList<AluMateriaNumOperator> numOP = new ArrayList();
            for (ALumnoOperator op : aluOp)
                numOP.add(op.getNumMatState(materiaKey));

            return numOP;
        }

        private ArrayList<AluMateriaNumOperator> getOperators(String materiaKey,ArrayList<ALumnoOperator> aluOp){

            ArrayList<AluMateriaNumOperator> numOP = new ArrayList();
            for (ALumnoOperator op : aluOp)
                numOP.add(op.getNumMatState(materiaKey));

            return numOP;
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
                for (int i = 0; i < 3;i++)
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
            int intAlu = getNumAlu();
            if(intAlu == 0)
                return 0.0 + "";
            else
                return 100*reprobado/intAlu + "";
        }

        public ArrayList<String> getFaltasXAlumno(){
            ArrayList<String> sumatoriaFaltas =  getSumatoriaFaltasXUnidad();
            ArrayList<String> faltasPorUnidad = new ArrayList<>();
            int intAlu = getNumAlu();

            for (String faltasUnidad:sumatoriaFaltas)
                if(!faltasPorUnidad.equals("")){
                    int faltasInt = Integer.parseInt(faltasUnidad);
                    if (intAlu == 0)
                        faltasPorUnidad.add(0.0 + "");
                    else
                        faltasPorUnidad.add(Double.toString(faltasInt/intAlu));
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
            int[] reprobados = new int[3];

            for (AluMateriaNumOperator op: operators){
                ArrayList<String> parCalif = op.getParCalif();
                for (int i = 0; i < 3;i++){
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
