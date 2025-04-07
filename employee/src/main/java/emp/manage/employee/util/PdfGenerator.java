package emp.manage.employee.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import emp.manage.employee.entity.Employee;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfGenerator {
    public static ByteArrayInputStream generate(List<Employee> employees) {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 3, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            table.addCell(new PdfPCell(new Phrase("ID", headFont)));
            table.addCell(new PdfPCell(new Phrase("Name", headFont)));
            table.addCell(new PdfPCell(new Phrase("Department", headFont)));
            table.addCell(new PdfPCell(new Phrase("Role", headFont)));
            table.addCell(new PdfPCell(new Phrase("Salary", headFont)));

            for (Employee emp : employees) {
                table.addCell(String.valueOf(emp.getId()));
                table.addCell(emp.getUsername());
                table.addCell(emp.getDepartment());
                table.addCell(emp.getRole());
                table.addCell(String.valueOf(emp.getSalary()));
            }

            doc.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
