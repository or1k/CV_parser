package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class CsvFileWriter {
    //Delimiter used in CSV file
        private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header

    private static final String FILE_HEADER = "URL,Title,Name,City,Age,Email,Telephone";

    public static void writeHEADER (String fileName) throws IOException {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(System.getProperty("user.dir") + "\\Report\\" + Util.reportFolderName + "\\" + fileName, StandardCharsets.UTF_8, true);

        try {


            //Write the CSV file header
            fileWriter.append(FILE_HEADER);
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
        finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }




    public static void writeCsvFile(String fileName, ArrayList<String> data) {

        FileWriter fileWriter = null;
        File report = new File(System.getProperty("user.dir") + "\\Report\\" + Util.reportFolderName + "\\" + fileName);


        try {

//            fileWriter = new FileWriter(Util.reportFolder + fileName, StandardCharsets.UTF_8);
            fileWriter = new FileWriter(System.getProperty("user.dir") + "\\Report\\" + Util.reportFolderName + "\\" + fileName, StandardCharsets.UTF_8, true);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Object line : data) {
                fileWriter.append((CharSequence) line);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }


        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}
