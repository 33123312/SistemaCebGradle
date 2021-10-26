package SpecificViews;

import java.util.HashMap;
import java.util.Map;

public class PdfCuadroHonorBuilder extends PDFPlantillaTable{

    public PdfCuadroHonorBuilder(String smestre,String eva) {
        super("Cuadro de Honor");
        setTable(new float[]{1,1,1});
        addParams(smestre,eva);
        deployTable();

    }

    private void deployTable(){
        table.addCell(addTitleStyle(getDefCell("Grupo")));
        table.addCell(addTitleStyle(getDefCell("Nombre")));
        table.addCell(addTitleStyle(getDefCell("Calificación")));

    }

    private void addParams(String semestre,String eva){
        Map<String,String> params = new HashMap<>();
            params.put("Semestre",semestre);
            params.put("Evaluación",eva);

        addParams(params);
    }

    public void addRow(String gpo, String nombre,String calif){

        table.addCell(getDefCell(gpo));
        table.addCell(getDefCell(nombre));
        table.addCell(getDefCell(calif));
    }

}
