package SpecificViews;

import JDBCController.Table;
import sistemaceb.form.HorizontalFormPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConcentradoTodasMateriasOp extends TableViewerPDFOp{

    GrupoOperator operator;
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
        deployPdf();
    }

    private HorizontalFormPanel getFormPanel(){
        HorizontalFormPanel form = new HorizontalFormPanel();

        form.addDesplegableMenu("Evaluacion").setRequired(true).setOptions(getEvaluaciones());
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
        materuiasNom = operator.getMaterias("Numérica").getColumn(0);
            materuiasNom.addAll(operator.getMaterias("A/NA").getColumn(0));

    }

    private void deployPdf(){
        ConcentradoTodasMateriasPDF doc = getDoc();

        ArrayList<ALumnoOperator> alumnosNum = operator.getAlumnosOpUsNum();
        ArrayList<ALumnoOperator> alumnosBol = operator.getAlumnosOpUsBol();

        for (int i = 0; i < alumnosBol.size();i++){
            doc.addRegister(getAlumnoRow(alumnosNum.get(i),alumnosBol.get(i)));
        }

        doc.addTable();
        doc.close();

    }

    private ArrayList<String> getAlumnoRow(ALumnoOperator alumnoNumInfo,ALumnoOperator alumnoBolInfo){

        ArrayList materiaOperators = alumnoNumInfo.getNumBoleta();
            materiaOperators.addAll(alumnoBolInfo.getBolBoleta());

        ArrayList<String> row = new ArrayList<>();
            //row.add(alumnoNumInfo.getRegisterValue("numero_control"));
            row.add(alumnoBolInfo.getRegisterValue("nombre_completo"));

        for (int i = 0; i < materiaOperators.size();i++){
            AluMateriaOperator materiaOperator = (AluMateriaOperator)materiaOperators.get(i);
            row.add(materiaOperator.getEvaluacionCalif(eva));
            row.add(materiaOperator.getEvaluacionFaltas(eva));
        }

        return row;
    }



}
