package SpecificViews;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import java.util.ArrayList;
import java.util.Map;

public class GrupoListaGenerator extends PDFPlantillaGetter{
    ArrayList<String> props;
    float[] sizes;

    GrupoListaGenerator(ArrayList<String> props, Map<String,String> pdfParams){
        super("Directorio de alumnos");
        addParams(pdfParams);
        this.props = props;
        sizes = new float[]{1,2,1,2,1,2};
    }



    public void addAluInfoTable(ArrayList<String> values){
        Table newTable = new Table(sizes).setWidthPercent(100)
                .setMarginBottom(4);
            for (int i = 0; i < values.size();i++){
                newTable.addCell(getTitleCell(props.get(i)));
                newTable.addCell(getCell(values.get(i)));
            }

        add(newTable);

    }

    private Cell getTitleCell(String text){
        return getCell(text).setBackgroundColor(Color.LIGHT_GRAY);
    }

    private Cell getCell(String text){
        Cell newcell= new Cell();
            newcell.add(text).
                    setPadding(0).
                    setPaddingLeft(1)
                    .setPaddingRight(1)
                    .setFontSize(9);

        return newcell;
    }


}
