package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import sistemaceb.AluPassGenerator;
import sistemaceb.form.Global;

import java.awt.desktop.PreferencesEvent;
import java.util.ArrayList;

public class  ALumnoOperator extends TableOperator{
    public GrupoOperator grupoOperator;
    private int evasSize;
    public Table boleta;
    public Table semestralesBoleta;
    private String periodo;
    protected ALumnoOperator thisOp;

    public ALumnoOperator(String aluMatr){
        super(aluMatr,"alumnos");
        thisOp = this;
        periodo = Global.conectionData.loadedPeriodo;
        grupoOperator =
                new GrupoOperator(getTableInfo().get("grupo"));
        evasSize  = 0;
    }

    public ALumnoOperator(TableRegister aluInfo,Table grupoBoleta,GrupoOperator op){
        super(aluInfo.get("numero_control"),"alumnos",aluInfo);
        thisOp = this;
        periodo = Global.conectionData.loadedPeriodo;
        this.boleta = extractBoleta(grupoBoleta);
        grupoOperator = op;
        evasSize = 0;
    }

    public ALumnoOperator(TableRegister aluInfo,Table grupoBoleta,Table grupoSemestralesBoleta,GrupoOperator op){
        super(aluInfo.get("numero_control"),"alumnos",aluInfo);
        thisOp = this;
        periodo = Global.conectionData.loadedPeriodo;
        this.boleta = extractBoleta(grupoBoleta);
        this.semestralesBoleta = extractBoleta(grupoSemestralesBoleta);
        grupoOperator = op;
        evasSize = 0;
    }

    private Table extractBoleta(Table grupoBoleta){
        return grupoBoleta.getSubTable(
                new String[]{"numero_control"},
                new String[]{getRegisterValue("numero_control")});
    }


    private int getEvasSize(){
        if (evasSize == 0)
            evasSize = CalifasOperator.getEvaluaciones().size();

        return evasSize;
    }

    private ArrayList<ArrayList<String>> getEmptySizerArr() {
        ArrayList<ArrayList<String>> e = new ArrayList<>();

        int size = getEvasSize();

        for (int i = 0;i < size;i++)
            e.add(new ArrayList<>());


        return e;
    }

    public ArrayList<AluMateriaBolOperator> getBolBoleta(){


        return getBolBoleta(grupoOperator.getMaterias("A/NA").getColumn("materia"));
    }

    public ArrayList<AluMateriaBolOperator> getBolBoleta(ArrayList<String> materiasBol){
        ArrayList<AluMateriaBolOperator> op = new ArrayList<>();

        for (String key: materiasBol){
            Table boleta = getBoleta("alumno_num_califa_charge_view");
            op.add(new AluMateriaBolOperator(key, periodo,getTableInfo(),boleta));
        }

        return op;
    }

    public ArrayList<AluMateriaNumOperator> getNumBoleta(){


        return getNumBoleta(grupoOperator.getMaterias("Numérica").getColumn("materia"));
    }

    public ArrayList<AluMateriaNumOperator> getNumBoleta(ArrayList<String> materiasNum){
        ArrayList<AluMateriaNumOperator> op = new ArrayList<>();
        getBoleta("alumno_num_califa_charge_view");
        getSemBoleta();

        for (String key: materiasNum)
            op.add(getNumMatState(key));


        return op;
    }


    private Table getBoleta(String view){
        if (boleta == null){
            DataBaseConsulter consulter = new DataBaseConsulter(view);

            String[] colsToBring = new String[]{"numero_control","materia","nombre_abr","calificacion","faltas","evaluacion"};

            String[] cond = new String[]{"numero_control","periodo"};

            String[] val = new String[]{getTableRegister(),periodo};

            boleta = consulter.bringTable(colsToBring,cond,val);

        }

        return boleta;

    }

    private Table getSemBoleta(){
        if (semestralesBoleta == null){
            DataBaseConsulter consulter = new DataBaseConsulter("calificaciones_semestrales_view");

            String[] cond = new String[]{"clave_alumno","periodo"};

            String[] val = new String[]{getTableRegister(),periodo};

            semestralesBoleta = consulter.bringTable(cond,val);

        }

        return semestralesBoleta;

    }

    public static String getMateriaType(String materia){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"tipo_calificacion"};

        String[] cond = new String[]{"clave_materia"};

        String[] val = new String[]{materia};

        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();
    }

    public AluMateriaBolOperator getBolMatState(String materia){

        if (boleta == null)
            return new AluMateriaBolOperator(materia, periodo,getTableInfo());
        else
            return new AluMateriaBolOperator(materia,periodo,getTableInfo(),boleta);
    }

    public AluMateriaNumOperator getNumMatState(String materia){
        if (boleta == null)
            return new AluMateriaNumOperator(materia, periodo,getTableInfo());
        else
            if (semestralesBoleta == null)
                return new AluMateriaNumOperator(materia,periodo,getTableInfo(),boleta);
            else
                return new AluMateriaNumOperator(materia,periodo,getTableInfo(),boleta,semestralesBoleta);

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
        public ALumnoOperator operator = thisOp;
        private ArrayList<AluMateriaNumOperator> operators;

        public TodasMateriasOps(){
            operators = getNumBoleta();

        }

        public ArrayList<String> getSumatoriaFaltas() {
            ArrayList<ArrayList<String>> faltasTotales = getEmptySizerArr();

            for (AluMateriaNumOperator materiaOp : operators) {
                ArrayList<String> faltas = materiaOp.getFaltas();
                for (int i = 0; i < faltasTotales.size(); i++)
                    faltasTotales.get(i).add(faltas.get(i));

            }

            ArrayList<String> sumatorias = new ArrayList<>();
            for (ArrayList<String> faltas : faltasTotales)
                sumatorias.add(PromsOperations.getSumaFaltas(faltas));

            return sumatorias;

        }

        public ArrayList<String> getPromUnidad() {
            ArrayList<ArrayList<String>> faltasTotales = getEmptySizerArr();

            for (AluMateriaNumOperator materiaOp : operators) {
                ArrayList<String> faltas = materiaOp.getParCalif();
                for (int i = 0; i < faltasTotales.size(); i++)
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

            return PromsOperations.getSumaFaltas(semestrales);

        }

        public String getPromFinal() {
            ArrayList<String> semestrales = new ArrayList<>();

            for (AluMateriaNumOperator materiaOp : operators)
                semestrales.add(materiaOp.getPromFinal());

            return PromsOperations.getProm(semestrales);

        }

    }

}
