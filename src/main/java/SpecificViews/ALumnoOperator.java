package SpecificViews;

import JDBCController.DataBaseConsulter;
import sistemaceb.form.Global;

import java.util.ArrayList;

public class  ALumnoOperator extends TableOperator{
    public GrupoOperator grupoOperator;

    public ALumnoOperator(String aluMatr){
        super(aluMatr,"alumnos");
        grupoOperator = new GrupoOperator(tableRegister);
    }

    public static String getMateriaType(String materia){
        DataBaseConsulter consulter = new DataBaseConsulter("materias");

        String[] colsToBring = new String[]{"tipo_calificacion"};

        String[] cond = new String[]{"clave_materia"};

        String[] val = new String[]{materia};

        return consulter.bringTable(colsToBring,cond,val).getUniqueValue();
    }

    public AluMateriaOperator getMateriaState(String materia){
        return getMateriaState(materia, Global.conectionData.loadedPeriodo);
    }

    public AluMateriaOperator getMateriaState(String materia,String periodo){
        String materiaType = getMateriaType(materia);
        if (materiaType.equals("A/NA"))
            return new AluMateriaBolOperator(materia,materiaType, periodo,this);
        else
            return new AluMateriaNumOperator(materia,materiaType, periodo,this);
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
