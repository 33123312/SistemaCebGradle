package SpecificViews;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.form.HorizontalFormPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConcentradoTodasMateriasOp extends TableViewerPDFOp{

    GrupoOperator operator;
    ArrayList<String> materiasKeys;
    ArrayList<String> materuiasNom;
    String eva;
    HorizontalFormPanel form;

    public ConcentradoTodasMateriasOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
        operation = "Concentrado x Unidad";
    }

    @Override
    public void buildOperation(){
        super.buildOperation();
        form = getFormPanel();
        operator = new GrupoOperator(keyValue);
        defineMaterias();

    }

    @Override
    protected void buildPDF() {
        eva = form.getData().get("Evaluacion");
        deployPdf(operator.getAlumnos());
    }

    private HorizontalFormPanel getFormPanel(){
        HorizontalFormPanel form = new HorizontalFormPanel();

        form.addDesplegableMenu("Evaluacion").setRequired(true).setOptions(getEvaluaciones());
        form.showAll();
        thisWindow.addToHeader(form);
        return form;
    }

    public ArrayList<String> getEvaluaciones() {
        return CalifasOperator.getEvaluaciones();
    }

    private ConcentradoTodasMateriasPDF getDoc(){
        ConcentradoTodasMateriasPDF doc = new ConcentradoTodasMateriasPDF(materuiasNom);
        Map<String,String> params = new HashMap<>();

            params.put("Semestre",operator.getRegisterValue("semestre"));
            params.put("Grupo",operator.getTableRegister());
            params.put("Evaluacion",eva);
            doc.addParams(params);
            doc.deploy();

        return doc;

    }

    private void defineMaterias(){
        Table materias = operator.getMaterias();
        materiasKeys = materias.getColumn(0);
        materuiasNom = materias.getColumn(1);
    }

    private void deployPdf(Table alumnos){
        ConcentradoTodasMateriasPDF doc = getDoc();
        ArrayList<ArrayList<String>> alumnosRe = alumnos.getRgistersCopy();
        for (ArrayList<String> aluInfo:alumnosRe){
            doc.addRegister(getAlumnoRow(aluInfo));
        }

        doc.addTable();
        doc.close();

    }

    private ArrayList<String> getAlumnoRow(ArrayList<String> alumnoInfo){
        String aluMatricula = alumnoInfo.get(0);
        ALumnoOperator operator = new ALumnoOperator(aluMatricula);

        for (String materia:materiasKeys){
            AluMateriaOperator materiaOperator = operator.getMateriaState(materia);
            alumnoInfo.add(materiaOperator.getEvaluacionCalif(eva));
            alumnoInfo.add(materiaOperator.getEvaluacionFaltas(eva));
        }

        return alumnoInfo;
    }



}
