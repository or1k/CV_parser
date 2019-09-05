package Utils;

import View.Window;
import com.codeborne.selenide.WebDriverRunner;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Util {

    public static String reportFolderName;



    public static String lineToCSV(Map reportData){
        String linetoCSV;
        linetoCSV = reportData.get("URL").toString() + "," +
                reportData.get("Title").toString() + "," +
                reportData.get("Name").toString() + "," +
                reportData.get("City").toString() + "," +
                reportData.get("Age").toString() + "," +
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


    public static void createReportFolderIfNotExist(){
        File f = new File(System.getProperty("user.dir") + "\\Report");
        if (!f.exists() && !f.isDirectory()) {
            f.mkdir();

        }
    }


    public static void createActualReportFolder(String keyWords){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        File dirReport = new File(System.getProperty("user.dir") + "\\Report\\"+formatter.format(date) + "_" +keyWords + "\\");
        if (!dirReport.exists() && !dirReport.isDirectory()) {
            dirReport.mkdir();
            reportFolderName = formatter.format(date) + "_" + keyWords;

        }else {
            reportFolderName = formatter.format(date) + "_" + keyWords;
            System.out.println(reportFolderName);
        }

    }

    public static void closeTab() throws InterruptedException {
        Thread.sleep(1000);
        ArrayList<String> tabs2 = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        if (tabs2.size() != 1) {
            WebDriverRunner.getWebDriver().switchTo().window(tabs2.get(1));
            WebDriverRunner.getWebDriver().close();
            WebDriverRunner.getWebDriver().switchTo().window(tabs2.get(0));
            Thread.sleep(1000);
        }
    }

}
