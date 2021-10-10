package SpecificViews;

import JDBCController.DBSTate;
import JDBCController.DataBaseResManager;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import sistemaceb.form.Global;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class PDFPlantillaGetter extends Document{

    private String title;
    private static String pdfPath = "C:/Users/" + System.getProperty("user.name") + "/tempPdfSysCeb.pdf";

    public PDFPlantillaGetter(String title) {
        super(getNewDocumnt());
        this.title = title;
        try {
            addTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void addTitle() throws IOException {

        PdfFontFactory.createFont();

        PdfFont fontTitle = PdfFontFactory.createFont(FontConstants.HELVETICA);
        Paragraph title = new Paragraph("CENTRO DE ESTUDIOS DE BACHILLERATO 6/4").
                setFont(fontTitle).
                setTextAlignment(TextAlignment.CENTER).
                setFontSize(12);


        PdfFont fontSubTitle = PdfFontFactory.createFont(FontConstants.COURIER);
        Paragraph subtitle = new Paragraph(this.title).
                setFont(fontSubTitle).
                setTextAlignment(TextAlignment.CENTER).
                setFontSize(12);

        PdfFont cornerValue = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Paragraph corner = new Paragraph(Global.conectionData.loadedPeriodo).
                setFont(cornerValue).
                setTextAlignment(TextAlignment.RIGHT).
                setFontSize(12);

        add(corner);
        add(title);
        add(subtitle);
        add(new Paragraph(""));


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
