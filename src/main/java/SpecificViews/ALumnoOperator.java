package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import sistemaceb.form.Global;

import java.awt.desktop.PreferencesEvent;
import java.util.ArrayList;

public class  ALumnoOperator extends TableOperator{
    public GrupoOperator grupoOperator;

    public ALumnoOperator(String aluMatr){
        super(aluMatr,"alumnos");
        grupoOperator =
                new GrupoOperator(getTableInfo().get("grupo"));
    }

    public ALumnoOperator(TableRegister aluInfo,Table boleta,GrupoOperator op){
        super(aluInfo.get("numero_control"),"alumnos",aluInfo);
        this.boleta = boleta;
        grupoOperator = op;

    }
    public ArrayList<AluMateriaBolOperator> getBolBoleta(){
        ArrayList<AluMateriaBolOperator> op = new ArrayList<>();
        ArrayList<String> materiasBol = grupoOperator.getMaterias("A/NA").getColumn("materia");


        for (String key: materiasBol)
            if(boleta == null){
                Table fullResB = getboleta("alumno_bol_califa_charge_view");
                op.add(new AluMateriaBolOperator(key,Global.conectionData.loadedPeriodo,getTableInfo(),fullResB));
            } else
                op.add(new AluMateriaBolOperator(key, Global.conectionData.loadedPeriodo,getTableInfo(),boleta));

        return op;
    }

    public ArrayList<AluMateriaNumOperator> getNumBoleta(){
        ArrayList<AluMateriaNumOperator> op = new ArrayList<>();
        ArrayList<String> materiasNum = grupoOperator.getMaterias("Numérica").getColumn("materia");


        for (String key: materiasNum)
            if(boleta == null){
                Table fullResN = getboleta("alumno_num_califa_charge_view");
                op.add(new AluMateriaNumOperator(key,Global.conectionData.loadedPeriodo,getTableInfo(),fullResN));
            } else
                op.add(new AluMateriaNumOperator(key, Global.conectionData.loadedPeriodo,getTableInfo(),boleta));

        return op;
    }

    public Table boleta;

    private Table getboleta(String view){

        if (boleta == null){
            DataBaseConsulter consulter = new DataBaseConsulter(view);

            String[] colsToBring = new String[]{"numero_control","materia","nombre_abr","calificacion","faltas","evaluacion"};

            String[] cond = new String[]{"numero_control","periodo"};

            String[] val = new String[]{getTableRegister(),Global.conectionData.loadedPeriodo};

            return consulter.bringTable(colsToBring,cond,val);
        } else return boleta;

    }

    public static String getMateriaType(String materia){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"tipo_calificacion"};

        String[] cond = new String[]{"clave_materia"};

        String[] val = new String[]{materia};

        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();
    }

    public AluMateriaBolOperator getBolMatState(String materia){
        String periodo = Global.conectionData.loadedPeriodo;
        if (boleta == null)
            return new AluMateriaBolOperator(materia, periodo,getTableInfo());
        else
            return new AluMateriaBolOperator(materia,periodo,getTableInfo(),boleta);
    }

    public AluMateriaNumOperator getNumMatState(String materia){
        String periodo = Global.conectionData.loadedPeriodo;
        if (boleta == null)
            return new AluMateriaNumOperator(materia, periodo,getTableInfo());
        else
            return new AluMateriaNumOperator(materia,periodo,getTableInfo(),boleta);
    }

    public AluMateriaOperator getMateriaState(String materia){

        String materiaType = getMateriaType(materia);
        if (materiaType.equals("A/NA"))
            return getNumMatState(materia);
        else
            return getBolMatState(materia);
    }

    public TodasMateriasOps getMateriaOperators(){
        return new TodasMateriasOps();
    }

    public class TodasMateriasOps {
        ArrayList<AluMateriaNumOperator> operators;
        ArrayList<String> materias;

        public TodasMateriasOps(){
            operators = new ArrayList<>();
            materias = grupoOperator.getMaterias("Numérica").getColumn(0);
            for (String materia : materias)
                operators.add((AluMateriaNumOperator) getMateriaState(materia));
        }

        public ArrayList<String> getSumatoriaFaltas() {
            ArrayList<ArrayList<String>> faltasTotales = new ArrayList<>();
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());

            for (AluMateriaNumOperator materiaOp : operators) {
                ArrayList<String> faltas = materiaOp.getFaltas();
                for (int i = 0; i < 4; i++)
                    faltasTotales.get(i).add(faltas.get(i));

            }

            ArrayList<String> sumatorias = new ArrayList<>();
            for (ArrayList<String> faltas : faltasTotales)
                sumatorias.add(PromsOperations.getSumaFaltas(faltas));

            return sumatorias;

        }

        public ArrayList<String> getPromUnidad() {
            ArrayList<ArrayList<String>> faltasTotales = new ArrayList<>();
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());
            faltasTotales.add(new ArrayList<>());

            for (AluMateriaNumOperator materiaOp : operators) {
                ArrayList<String> faltas = materiaOp.getParCalif();
                for (int i = 0; i < 4; i++)
                    faltasTotales.get(i).add(faltas.get(i));

            }

            ArrayList<String> sumatorias = new ArrayList<>();
            for (ArrayList<String> faltas : faltasTotales)
                sumatorias.add(PromsOperations.getProm(faltas));

            return sumatorias;

        }

        public String getPromSemestrales() {
            ArrayList<String> semestrales = new ArrayList<>();

            for (AluMateriaNumOperator materiaOp : operators)
                semestrales.add(materiaOp.getCalifSemestral());

            return PromsOperations.getProm(semestrales);

        }

        public String getPromUnidades() {
            ArrayList<String> semestrales = new ArrayList<>();

            for (AluMateriaNumOperator materiaOp : operators)
                semestrales.add(materiaOp.getParProm());

            return PromsOperations.getProm(semestrales);

        }

        public String getSumFinalFaltas() {
            ArrayList<String> semestrales = new ArrayList<>();

            for (AluMateriaNumOperator materiaOp : operators)
                semestrales.add(materiaOp.getSumaFaltas());

            return PromsOperations.getProm(semestrales);

        }

        public String getPromFinal() {
            ArrayList<String> semestrales = new ArrayList<>();

            for (AluMateriaNumOperator materiaOp : operators)
                semestrales.add(materiaOp.getPromFinal());

            return PromsOperations.getProm(semestrales);

        }

    }

}
