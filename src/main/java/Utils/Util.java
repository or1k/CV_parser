package Utils;

import View.Window;

import java.awt.*;
import java.io.File;
import java.util.Map;

public class Util {

    public static String lineToCSV(Map reportData){
        String linetoCSV;
        linetoCSV = reportData.get("URL").toString() + "," +
                reportData.get("Title").toString() + "," +
                reportData.get("Name").toString() + "," +
                reportData.get("Email").toString() + "," +
                reportData.get("Telephone").toString();
        return  linetoCSV;
    }

    public static String getTimeforTest(long startTime) {
        long currentTime = (System.currentTimeMillis() - startTime)/1000;
        if(currentTime == 0 )
        {
            currentTime = 1;
        }
        return  Long.toString(currentTime);
    }

    public  static void openReport(){
        File a = new File(System.getProperty("user.dir") + "\\CV.csv");
        if(a.exists() && a.isFile()) {
            try {
                Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\CV.csv"));
            } catch (Exception ex) {

            }
        }
    }

    public static String fileName(){
        return "CV_" + Window.keyWordsText.getText() + ".csv";
    }

}
