package SpecificViews;

import JDBCController.Table;

import java.util.ArrayList;

public class AlumnoCalifasGetter {

    public ArrayList<ArrayList<String>> materias;
    public ALumnoOperator aluOperator;
    public ArrayList<String> evaluaciones;
    private ArrayList<CalifasRow> rows;

    public AlumnoCalifasGetter(String aluKey){
        evaluaciones = CalifasOperator.getEvaluaciones();
        aluOperator = new ALumnoOperator(aluKey);
        rows = new ArrayList<>();

    }

    public ArrayList<CalifasRow> getRegisters(){

        define();
        return rows;
    }

    private void define(){
        addRows("Numérica");
        addRows("A/NA");

    }

    private void addRows(String tipo){
        materias = aluOperator.grupoOperator.getMateriasList(tipo).getRegisters();

        ArrayList<CalifasRow> registers;

        if(tipo.equals("Numérica"))
            registers = getRegisters(getNumCols(),aluOperator.getNumBoleta());
        else
            registers = getRegisters(getBolCols(),aluOperator.getBolBoleta());

        rows.addAll(registers);

    }

    private String getMateria(String mat){
        for (ArrayList<String> row:materias)
            if (row.get(0).equals(mat))
                return row.get(1);

        return null;
    }

    private ArrayList<CalifasRow> getRegisters(OtherPropsGetter builder,ArrayList op) {
        ArrayList<AluMateriaOperator> boleta = op;
        ArrayList<CalifasRow> registers = new ArrayList<>();

        for (AluMateriaOperator operator : boleta) {

            CalifasRow row = new CalifasRow();
            row.materia = getMateria(operator.materia);
            row.cal = operator.getParCalif();
            row.faltas = operator.getFaltas();
            row.other = builder.getOt(operator);

            registers.add(row);

        }

        return registers;
    }


    private interface OtherPropsGetter{
        public ArrayList<String> getOt(AluMateriaOperator operator);
    }

    private OtherPropsGetter getBolCols(){
        return new OtherPropsGetter(){
            @Override
            public ArrayList<String> getOt(AluMateriaOperator operator) {
                AluMateriaBolOperator  bolOperator= (AluMateriaBolOperator)operator;
                ArrayList<String> register = new ArrayList<>();
                register.add("");
                register.add("");
                register.add(bolOperator.getPromFinal());

                return register;
            }
        };

    }

    private OtherPropsGetter getNumCols(){
        return new OtherPropsGetter(){
            @Override
            public ArrayList<String> getOt(AluMateriaOperator operator) {
                AluMateriaNumOperator numOperator = (AluMateriaNumOperator)operator;
                ArrayList<String> register = new ArrayList<>();
                    register.add(numOperator.getParProm());
                    register.add(numOperator.getCalifSemestral());
                    register.add(numOperator.getPromFinal());

                return register;
            }
        };

    }

    public ArrayList<String> getProms(){
        ArrayList<String> promedios = new ArrayList();
        ALumnoOperator.TodasMateriasOps materiasOperator =  aluOperator.getMateriaOperators();

            promedios.add("Promedios");
            promedios.addAll(mergeLists(materiasOperator.getPromUnidad(),materiasOperator.getSumatoriaFaltas()));
            promedios.add(materiasOperator.getPromUnidades());
            promedios.add(materiasOperator.getPromSemestrales());
            promedios.add(materiasOperator.getPromFinal());

        return promedios;
    }

    private ArrayList<String> mergeLists(ArrayList<String> cal,ArrayList<String> fal){
        ArrayList<String> par = new ArrayList<>();
        for(int i = 0; i < cal.size();i++){
            par.add(cal.get(i));
            par.add(fal.get(i));
        }

        return par;
    }

    public class CalifasRow {
        public String materia;
        public ArrayList<String> cal;
        public ArrayList<String> faltas;
        public ArrayList<String> other;

        public ArrayList<String> getRowArray(){
            ArrayList<String> row = new ArrayList<>();
            row.add(materia);
            row.addAll(mergeLists(cal,faltas));
                row.addAll(other);

            return  row;
        }

    }
}
