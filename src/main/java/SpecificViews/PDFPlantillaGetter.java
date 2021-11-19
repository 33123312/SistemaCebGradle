package SpecificViews;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import sistemaceb.form.Global;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class PDFPlantillaGetter extends Document{

    private String title;
    private static String pdfPath = "C:/Users/" + System.getProperty("user.name") + "/tempPdfSysCeb.pdf";

    public PDFPlantillaGetter(String title) {
        super(getNewDocumnt());
        this.title = title;
    }

    private static PdfDocument getNewDocumnt(){
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument documentPdf = new PdfDocument(writer);

            return documentPdf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void adddMembreteCorto(){
        add(new Paragraph("CENTRO DE ESTUDIOS DE BACHILLERATO 6/4").setTextAlignment(TextAlignment.CENTER));
    }

    public void addMembreteLargo(){
        Table table = new Table(UnitValue.createPercentArray(new float[]{1,3,1}));
            table.setWidthPercent(100);

        table.addCell(getLogo());

        table.addCell(
                getCell("CENTRO DE ESTUDIOS DE BACHILLERATO 6/4",0,0));

        PdfFont cornerValue = null;
        PdfFont fontSubTitle = null;
        try {
             cornerValue = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
             fontSubTitle = PdfFontFactory.createFont(FontConstants.COURIER);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int fontSize = 11;

        table.addCell(
            getCell(Global.conectionData.loadedPeriodo,3,0)
                .setFont(cornerValue)
                .setFontSize(11));

        table.addCell(
                getCell("Privada de Guadalupe 108, Delicias, Chih.",0,0)
                    .setFont(fontSubTitle)
                    .setFontSize(11));

        table.addCell(
                getCell(title,0,0)
                    .setFont(fontSubTitle)
                    .setFontSize(11));

        add(table);
        add(new Paragraph(""));
    }

    private Cell getLogo(){
        String imgPath = "images/ceb-logo.png";
        URL a = this.getClass().getResource(imgPath);

        Image logo = new Image(ImageDataFactory.create(a));
            logo.scaleAbsolute(100,45);

        Cell cell = getCell(3,0);
            cell.add(logo);

        return cell;
    }

    private Cell getCell(String text,int row,int col) {

        return getCell(row,col).add(new Paragraph(text));
    }

    private Cell getCell(int row,int col){
        Cell cell = new Cell(row,col);
        cell.setPadding(0);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public void addParams(Map<String,String> params){
        float[] sizes = getSizes(params);
        Table table = new Table(sizes);
        for (Map.Entry<String,String> param:params.entrySet()) {
            try {
                addParam(param.getKey(),param.getValue(),table);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        add(table);

    }

    private float[] getSizes(Map<String,String> params){
        float[] sizes = new float[params.size()*2];
        for (int i = 0; i < sizes.length;i++)
            sizes[i] = 1;
        return sizes;
    }

    private void addParam(String title,String value,Table table) throws IOException {
        title +=":";

        Cell titleCell = new Cell(0,0).
            add(title ).
            setFont(PdfFontFactory.createFont(FontConstants.COURIER_BOLD)).
            setFontSize(13).
                setBorder(Border.NO_BORDER);

        Cell valueCell = new Cell(0,0).
                add(value).
                setFont(PdfFontFactory.createFont(FontConstants.COURIER)).
                setFontSize(13).
                setPaddingLeft(2).
                setBorder(Border.NO_BORDER).setPaddingRight(10);


        table.addCell(titleCell);
        table.addCell(valueCell);

    }


    @Override
    public void close() {
        super.close();
        openPdf();
    }

    public void openPdf(){
        try {
            File myFile = new File(pdfPath);
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
        }
    }

}
