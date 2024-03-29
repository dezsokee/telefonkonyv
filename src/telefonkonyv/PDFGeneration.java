package telefonkonyv;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

public class PDFGeneration {
    
    public void PDFGeneration (String fileName, ObservableList <Person> data)
    {
        Document document = new Document();
        
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            
            document.open();
            
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(400, 172);
            image1.setAbsolutePosition(170f, 650f);
            document.add(image1);
            
            document.add(new Paragraph("\n\n\n\n\n\n\n"));
            
            float[] columnWidths = {3, 3, 4};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell (new Phrase("Kontaktlista"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("E-mail");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int counter = 1; counter <= data.size(); counter++)
            {
                Person actualPerson = data.get(counter-1);
                
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getEmail());
            }
            
            document.add(table);
            
            
            Chunk signature = new Chunk("\n\n Generálva a telefonkönyv segítségével.");
            
            Paragraph base = new Paragraph(signature);
            
            document.add(base);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        document.close();
    }
}
