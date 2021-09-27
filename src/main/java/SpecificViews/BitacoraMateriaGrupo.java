package SpecificViews;

import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.form.HorizontalFormPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BitacoraMateriaGrupo extends TableViewerPDFOp{

    GrupoOperator operator;
    String materia;
    String evaluacion;
    HorizontalFormPanel form;

    public BitacoraMateriaGrupo(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Ver Bitácora";

    }

    private void addParams( BitacoraMateriaGrupoPDF bitacora,AsignaturaOperatorr operatorr){
        Map<String,String> params = new HashMap<>();
            params.put("Semestre",operator.getGrupoInfo().get("semestre"));
            params.put("Grupo",operator.grupo);
            String materia = operatorr.getNombreMateria();
            params.put("Materia",materia);
            params.put("Evaluación",evaluacion);

        bitacora.addParams(params);
    }

    @Override
    public void buildOperation() {
        super.buildOperation();
        operator = new GrupoOperator(keyValue);
        form = getFormPanel();

    }

    protected void buildPDF() {
        Map<String,String> response = form.getData();
        materia = response.get("materias");
        evaluacion = response.get("Evaluacion");
        buidPdf();
    }

    private void buidPdf(){
        AsignaturaOperatorr asigOperator = operator.getAsignOperator(materia);
        TableRegister res = asigOperator.getProfesor();
        String nombreProfesor;
        if (res == null)
            nombreProfesor = "No asignado";
        else
            nombreProfesor = res.get("nombre_completo");

        BitacoraMateriaGrupoPDF bitacora = new BitacoraMateriaGrupoPDF(operator.getAlumnos().getRgistersCopy(),nombreProfesor);
        addParams(bitacora,asigOperator);
        bitacora.deployTable();
    }

    private HorizontalFormPanel getFormPanel(){
        HorizontalFormPanel form = new HorizontalFormPanel();

        form.addDesplegableMenu("Materia").setOptions(getMaterias());
        form.addDesplegableMenu("Evaluacion").setOptions(getEvaluaciones());
        form.showAll();

        thisWindow.addToHeader(form);

        return form;
    }

    public Table getMaterias(){
        operator.getMaterias();
        ArrayList<String> titles = new ArrayList<>();
            titles.add("Materias");
            titles.add("materias");

        Table materias = new Table(titles,getRegisers());
        return materias;
    }

    private ArrayList<ArrayList<String>> getRegisers(){
        Table materias = operator.getMaterias();

        ArrayList<ArrayList<String>> registers = new ArrayList<>();

        ArrayList<String> primary = materias.getColumn(0);
        ArrayList<String> human = materias.getColumn(1);

        for (int i = 0; i < primary.size();i++){
            ArrayList<String> newRegister = new ArrayList<>();
                newRegister.add(human.get(i));
                newRegister.add(primary.get(i));

            registers.add(newRegister);
        }

        return registers;

    }

    public ArrayList<String> getEvaluaciones() {

        return CalifasOperator.getEvaluaciones();
    }
}
