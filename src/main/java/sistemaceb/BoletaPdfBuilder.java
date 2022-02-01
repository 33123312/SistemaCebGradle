package sistemaceb;

import JDBCController.Table;
import SpecificViews.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoletaPdfBuilder {

    private ALumnoOperator aluNumOperator;
    private ALumnoOperator aluBolOperator;

    BoletaPDFBuilder pdf;

    private Table order;

    public BoletaPdfBuilder(String aluMatr){
        aluNumOperator = new ALumnoOperator(aluMatr);
        aluBolOperator = new ALumnoOperator(aluMatr);

        order = aluBolOperator.grupoOperator.getMaterias();

        pdf = createPdf(getEvaluaciones());

        buildPDF();
    }

    public static BoletaPDFBuilder createPdf(ArrayList<String> evaluaciones){
        BoletaPDFBuilder pdf = new BoletaPDFBuilder(evaluaciones);

        return pdf;
    }

    public BoletaPdfBuilder(ALumnoOperator aluNumOperator,ALumnoOperator aluBolOperator,Table order){
        this.aluNumOperator = aluNumOperator;
        this.aluBolOperator = aluBolOperator;

        this.order = order;


    }

    public void setPdf(BoletaPDFBuilder pdf) {
        this.pdf = pdf;
    }

    public BoletaPDFBuilder getModPdf(){
        pdf.setTable();

        Table boleta = getBoleta();

        ArrayList<String> titles = boleta.getColumnTitles();
        ArrayList<ArrayList<String>> registers = boleta.getRegisters();

        int i = 0;
        for (ArrayList<String> register: registers){
            ArrayList<String> newReg = new ArrayList<>();
            newReg.add(titles.get(i));
            newReg.addAll(register);

            pdf.addCalifasReg(newReg);
            i++;
        }
        ArrayList<String> proms = new ArrayList<>();
        proms.add("promedios");
        proms.addAll(getProms());

        pdf.addPromediosRegister(getProms());

        pdf.addParams(getParams());
        pdf.addTable();
        pdf.addFirmaProfesor();

        return pdf;
    }

    public void buildPDF(){
        getModPdf();
        pdf.close();

    }

    private Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();
        params.put("Semestre",aluNumOperator.getRegisterValue("semestre"));
        params.put("Alumno",aluNumOperator.getRegisterValue("nombre_completo"));

        return params;
    }

    private Table getBoleta(){
        return new BoletaSorter(
                        defineNunBoleta(),
                        defineBolBoleta(),
                        order
        ).getSortedAndNamed();
    }

    private ArrayList<String> mixArr(ArrayList<String> f,ArrayList<String> s){
        ArrayList<String> newArray = new ArrayList<>();
        for (int i = 0;i < f.size();i++){
            newArray.add(f.get(i));
            newArray.add(s.get(i));
        }

        return newArray;
    }

    private ArrayList<String> getProms(){
        ALumnoOperator.TodasMateriasOps op = aluNumOperator.getMateriaOperators();
        ArrayList<String> proms = new ArrayList<>();
            proms.add("");
            proms.addAll(mixArr(op.getPromUnidad(),op.getSumatoriaFaltas()));
            proms.add(op.getPromUnidades());
            proms.add(op.getPromSemestrales());
            proms.add(op.getPromFinal());
            proms.add(op.getSumFinalFaltas());

        return proms;
    }

    private Table defineNunBoleta(){
        ArrayList<ArrayList<String>> parsedBoleta  = new ArrayList<>();
        ArrayList<AluMateriaNumOperator> operators = aluNumOperator.getNumBoleta();
        ArrayList<String> materias = new ArrayList<>();

        for (AluMateriaNumOperator materia:operators){
            ArrayList<String> register = new ArrayList<>();
                register.addAll(mixArr(materia.getParCalif(),materia.getFaltas()));
                register.add(materia.getParProm());
                register.add(materia.getCalifSemestral());
                register.add(materia.getPromFinal());
                register.add(materia.getSumaFaltas());

            parsedBoleta.add(register);
            materias.add(materia.materia);
        }


        return new Table(materias,parsedBoleta);
    }

    private Table defineBolBoleta(){
        ArrayList<ArrayList<String>> parsedBoleta  = new ArrayList<>();
        ArrayList<AluMateriaBolOperator> operators = aluNumOperator.getBolBoleta();
        ArrayList<String> materias = new ArrayList<>();

        for (AluMateriaBolOperator materia:operators){
            ArrayList<String> register = new ArrayList<>();
                register.addAll(mixArr(materia.getParCalif(),materia.getFaltas()));
                register.add(materia.getPromFinal());
                register.add("");
                register.add(materia.getPromFinal());
                register.add(materia.getSumaFaltas());

            parsedBoleta.add(register);
            materias.add(materia.materia);

        }

        return new Table(materias,parsedBoleta);
    }

    private ArrayList<String> getEvaluaciones(){

        return CalifasOperator.getEvaluaciones();
    }

}
