// 代码生成时间: 2025-09-06 00:55:11
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ExcelGeneratorService {

    @Inject
    @ConfigProperty(name = "excel.file.name")
    String excelFileName;

    public void generateExcelFile(List<List<String>> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data Sheet");
            int rowCount = 0;

            for (List<String> rowData : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                for (String cellData : rowData) {
                    row.createCell(columnCount++).setCellValue(cellData);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new IOException("Error generating Excel file", e);
        }
    }

    /*
     * Method to handle the generation of Excel file with error handling.
     */
    public void generateExcelFileWithExceptionHandling(List<List<String>> data) throws Exception {
        try {
            generateExcelFile(data);
        } catch (IOException e) {
            // Log and handle the exception appropriately
            throw new Exception("Failed to generate Excel file", e);
        }
    }
}
