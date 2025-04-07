package emp.manage.employee.util;

import emp.manage.employee.entity.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    public static byte[] generate(List<Employee> employees) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Department", "Role", "Salary"};

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Employee emp : employees) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(emp.getId());
                row.createCell(1).setCellValue(emp.getUsername());
                row.createCell(2).setCellValue(emp.getDepartment());
                row.createCell(3).setCellValue(emp.getRole());
                row.createCell(4).setCellValue(emp.getSalary());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}