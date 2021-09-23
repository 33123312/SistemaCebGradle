package SpecificViews;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import java.io.IOException;
import java.util.ArrayList;

public class HorariosPlanosPdf extends PDFPlantillaTable{

    int gruposCount;


    public HorariosPlanosPdf(ArrayList<String> titles,int gruposCount) {
        super("Plano de Horario");
        this.gruposCount = gruposCount;
        ArrayList<String> newTitles = new ArrayList<>(titles);
        newTitles.add(0,"");
        newTitles.add(0,"");
        setTable(getSizes());
        addTitles(newTitles);
    }

    private void addTitles(ArrayList<String> titles){
        for (String title:titles){
            Cell cell = getDefCell(title)
                    .setFontSize(10);
            table.addCell(addTitleStyle(cell));

        }
    }

    private float[] getSizes(){
        float[] sizes = new float[]{1,1,1,1,1,1,1};

        return sizes;
    }

    public void addHoraCell(String dia){
        Cell diaCell = getCellDeftyle(dia,new Cell(gruposCount*2,0));
            diaCell = addTitleStyle(diaCell);

        table.addCell(diaCell);
    }

    public void addGrupoCell(String dia){
        Cell diaCell = getCellDeftyle(dia,new Cell(2,0));
        diaCell = addTitleStyle(diaCell);

        table.addCell(diaCell);
    }


    public Cell getInterCell(String title){
        SolidBorder border = new SolidBorder(1);
        Cell diaCell = getCellDeftyle(title,new Cell(0,0));
            diaCell.setBorder(Border.NO_BORDER).
                    setBorderLeft(border).
                    setBorderRight(border).
                    setFontSize(8).
                    setHorizontalAlignment(HorizontalAlignment.CENTER);

        return diaCell;
    }

    private Cell getMateriaCell(String txt){
        Cell currentCell = getInterCell(txt);
        try {
            currentCell.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentCell;
    }

    private Cell getTitleCell(String txt){

        Cell currentCell = getInterCell(txt);
            currentCell.setBorderBottom(new SolidBorder(1));
        return currentCell;
    }

    public void addTitlesCell(ArrayList<String> titles){
        for (String title: titles)
            table.addCell(getTitleCell(title));
    }

    public void addMateriaCell(ArrayList<String> titles){
        for (String title: titles)
            table.addCell(getMateriaCell(title));
    }



}
