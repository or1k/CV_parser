package Parser;

import BrowserConfig.MyChromeBrowserClass;
import Pages.BossAzCV;
import Pages.BrowseSummary;
import Pages.Login;
import Utils.CsvFileWriter;
import Utils.Settings;
import Utils.Util;
import View.Window;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.BrowserPerClass;
import com.codeborne.selenide.testng.TextReport;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;


@Listeners({TextReport.class, BrowserPerClass.class})
public  class BossAz {

    Map reportData = new HashMap();
    ArrayList<String> list = new ArrayList<>();

    @Test
    public  void parser() throws IOException, InterruptedException {
        /**
         * Setup
         */
        Configuration.browser = MyChromeBrowserClass.class.getName();
        Configuration.startMaximized = true;
        Configuration.timeout = 60000;
//        Configuration.holdBrowserOpen = true;
        /**
         *
         */

        /**
         * Settings
         */
        Settings.saveLogin();
        Util.createReportFolderIfNotExist();
        Util.createUtilsIfNotExistBA();
        Util.createActualReportFolder(Window.keyWordsText.getText());
        /**
         *
         */

        BrowseSummary browseSummary = open("https://ru.boss.az/categories/resumes", BrowseSummary.class);
        browseSummary.findSummary(Window.keyWordsText.getText(), Window.cityText.getText());
        BossAzCV bossAzCV = new BossAzCV();

        CsvFileWriter.writeHEADER(Util.fileName());
        for(int i = 0; i <= Integer.parseInt(Window.quantityText.getText())-1;) {

            if(browseSummary.grabCardsBossAz()) {
                if (browseSummary.isCardsFlag()) {
                    switchTo().window(1);
                    System.out.println();
                    //write in report
                    reportData.put("URL", WebDriverRunner.getWebDriver().getCurrentUrl());
                    System.out.println(reportData.get("URL"));
                    reportData.put("Title", bossAzCV.getTitle());
                    System.out.println(reportData.get("Title"));
                    reportData.put("Name", bossAzCV.getFullName());
                    System.out.println(reportData.get("Name"));
                    reportData.put("City", bossAzCV.getCity());
                    System.out.println(reportData.get("City"));
                    reportData.put("Age", bossAzCV.getAge());
                    System.out.println(reportData.get("Age"));
                    reportData.put("Email", bossAzCV.getEmail());
                    System.out.println(reportData.get("Email"));
                    reportData.put("Telephone", bossAzCV.getTelephone());
                    System.out.println(reportData.get("Telephone"));
                    Util.closeTab();
                    list.add(Util.lineToCSV(reportData));
                    reportData.clear();
                    i++;
                }else {
                    break;
                }
                if (i % 10 == 0 & i != 0) {
                    CsvFileWriter.writeCsvFile(Util.fileName(), list);
                    list.clear();
                }
            }
        }

        CsvFileWriter.writeCsvFile(Util.fileName(), list);
        Util.openReport();
        JOptionPane.showMessageDialog(null,  "Process DONE!");

    }

    @AfterClass
    public void tearDown(){
        CsvFileWriter.writeCsvFile("CV.csv", list);

    }



}
