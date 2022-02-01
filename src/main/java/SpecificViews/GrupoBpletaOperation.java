package SpecificViews;

import JDBCController.Table;

import sistemaceb.BoletaPdfBuilder;

import java.util.ArrayList;

public class GrupoBpletaOperation extends Operation{
    public GrupoBpletaOperation(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Ver Boletas";
    }

    @Override
    public void buildOperation() {
        GrupoOperator operator = new GrupoOperator(keyValue);

        ArrayList<ALumnoOperator> num = operator.getAlumnosOpUsNum();
        ArrayList<ALumnoOperator> bol = operator.getAlumnosOpUsBol();

        Table order = operator.getMaterias();

        BoletaPDFBuilder pdfBuilder = BoletaPdfBuilder.createPdf(getEvaluaciones());

        for (int i = 0;i < num.size();i++){
            BoletaPdfBuilder builder = new BoletaPdfBuilder(num.get(i),bol.get(i),order);
                builder.setPdf(pdfBuilder);
                builder.getModPdf();

        };

        pdfBuilder.close();

    }


    private ArrayList<String> getEvaluaciones(){

        return CalifasOperator.getEvaluaciones();
    }
}
