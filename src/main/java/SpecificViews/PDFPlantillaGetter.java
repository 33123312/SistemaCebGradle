package SpecificViews;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import sistemaceb.form.Global;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
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



        addMembrete();

        add(title);
        add(subtitle);
        add(new Paragraph(""));

    }

    private void removeBorder(Table table)
    {
        for (IElement iElement : table.getChildren()) {
            ((Cell)iElement).setBorder(Border.NO_BORDER);
        }
    }

    private void addMembrete(){
        Table table = new Table(new float[]{1,1,1});
            table.setBorder(Border.NO_BORDER);
            table.setWidthPercent(100);

        table.addCell(getLogo());

        try {
            PdfFont cornerValue = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            table.addCell(getCell(Global.conectionData.loadedPeriodo, TextAlignment.RIGHT))
                    .setFont(cornerValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        removeBorder(table);
        add(table);
    }

    private Image getLogo(){
        String imgPath = "images/ceb-logo.png";
        URL a = this.getClass().getResource(imgPath);

        Image logo = new Image(ImageDataFactory.create(a));
            logo.scaleAbsolute(100,45);

        return logo;
    }

    private Cell getCell(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setTextAlignment(alignment);
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
