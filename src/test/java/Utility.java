import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class Utility {

    private static Logger log = Logger.getLogger(Utility.class.getName());

    /**
     * Invoking api call to server
     */
    public static void invokeApiCalls() {
        baseURI(staticData.baseURI);
        basePath("/Categories/6327/Details.json?catalogue=false");
        jSonContent();

    }

    /**
     * Setting the URI by getting input from String
     * @param uRIAddress This obtain the URI from User
     * @return String This returns the baseURI given by the User.
     */
    //Create common method for setting URI
    public static String baseURI(String uRIAddress) {

        return (RestAssured.baseURI = uRIAddress);

    }

    /**
     * Setting the Basepath by adding to URI input from String
     * @param basePath This obtain the URI from User
     * @return String This returns the basePath followed by the URI
     */
    //Set the basic path
    public static String basePath(String basePath) {

        return (RestAssured.basePath = basePath);

    }

    /**
     * This methid is for wetting the content type as Json
     * should with a api request
     *
     */
    public static void jSonContent() {

        given().contentType(ContentType.JSON);
    }

    /**
     * Setting the content type XML
     * should use with api request
     */
    public static void xmlContent() {
        //Setting the content type XML
        given().contentType(ContentType.XML);
    }

    /**
     * This method is used to send the api get() request
     * and receive the response verify status code,
     * get response time and return the response for other
     * data validations and commands
     * @return Response
     */
    public static Response basicNavigation() {
        Response response = RestAssured.get();
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);
        long responseTime = response.getTime();
        log.info("Response is : " + code);
        log.info("Response time is : " + responseTime + " ms");
        return response;

    }
    /**
     * This method purpose to read the data from excel sheet
     * both .xlsx and .xls file compatible for reading
     * checking the file reading compatibility
     * using file extentsion's name
     * @param filePath
     *
     * */

    public static void readExcel(String filePath) {

        try {
            Path filePaths = Paths.get(filePath);
            String fileName = filePaths.getFileName().toString();
            String fileDirectory = filePaths.getParent().toString();

            //Create an object of File class to open xlsx file
            File file = new File(filePath);
            //Create an object of FileInputStream class to read excel file
            FileInputStream inputStream = new FileInputStream(file);
            Workbook excelSWorkbook = null;
            XSSFWorkbook excelXWorkbook = null;
            //Find the file extension by splitting file name in substring  and getting only extension name
            String fileExtensionName = fileName.substring(fileName.toLowerCase().indexOf("."));

            if (!fileExtensionName.equalsIgnoreCase(".xlsx") && !fileExtensionName.equalsIgnoreCase(".xls")) {
                log.warning("given file extention niether xlsx nor xls " + fileExtensionName + " can not be read, reading process ended");
                throw new IllegalArgumentException();
            }
            //Check condition if the file is xlsx file
            else if (fileExtensionName.equalsIgnoreCase(".xlsx")) {
                excelXWorkbook = new XSSFWorkbook(inputStream);
                XSSFSheet xssfSheet = excelXWorkbook.getSheetAt(0);
                xlsxReader(xssfSheet);
            }
            //Check condition if the file is xls file
            else {
                //If it is xls file then create object of XSSFWorkbook class
                excelSWorkbook = new HSSFWorkbook(inputStream);
                //Read sheet inside the workbook by its name
                Sheet excelSheet = excelSWorkbook.getSheetAt(0);
                //Find number of rows in excel file
                iterationExcelData(excelSheet);

            }

        } catch (FileNotFoundException FileNotFound) {
            log.severe(
                    "Given file is not able to find!!! Please verify the directory and file name " + filePath + "\n");
            System.out.println(FileNotFound);
        } catch (IOException IOex) {
            System.out.println(IOex);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /***
     * This read the excel sheets top to bottom untill where data is there.
     * Then jups to the next column and proceed the same
     * @param xssfSheet
     */
    private static Cell xlsxReader(XSSFSheet xssfSheet) {
        //If it is xlsx file then create object of XSSFWorkbook class
        /**
         * This implementation read the rows first and go through the column by column
         */
        Iterator<Row> rowIterator = xssfSheet.iterator();
        Cell cell = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            //  System.out.print("\n");
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                // System.out.print(cell.getStringCellValue() + "\t");
            }
        }
        return cell;
    }


    private static Row iterationExcelData(Sheet excelSheet) {
        /**
         * This code is not built for retrieve a single element of data from excel sheeet
         * Assumed single cell data is not entered. for MVP.If one want to tackel this issue , address row count exception
         */
        int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
        if (rowCount == 0) {
            throw new NullPointerException("There is no data seems to be found in given spread sheet");
        }
        //Create a loop over all the rows of excel file to read it
        Row row = null;
        for (int i = 0; i < rowCount + 1; i++) {
            row = excelSheet.getRow(i);
            //Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++) {
                //Print Excel data in console
                // System.out.print(row.getCell(j).getStringCellValue() + "|| ");
            }
        }
        return row;
    }
}
