package Parser;

import BrowserConfig.MyChromeBrowserClass;
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
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;


@Listeners({TextReport.class, BrowserPerClass.class})
public  class BaseTest {

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
        Configuration.holdBrowserOpen = true;
        /**
         *
         */

        Settings.saveLogin();
        Login login = open("https://www.work.ua/employer/login/", Login.class);
        login.loginInAccount();
        BrowseSummary browseSummary = open("https://www.work.ua/resumes/", BrowseSummary.class);
        browseSummary.findSummary(Window.keyWordsText.getText());

        long startRegistrationTime = System.currentTimeMillis();
        for(int i = 0; i <= Integer.parseInt(Window.quantityText.getText())-1; i++) {
            browseSummary.grabCards();
            //write in report
            reportData.put("URL", WebDriverRunner.getWebDriver().getCurrentUrl());
            System.out.println(reportData.get("URL"));
            reportData.put("Title", browseSummary.getTitle());
            System.out.println(reportData.get("Title"));
            reportData.put("Name", browseSummary.getFullName());
            System.out.println(reportData.get("Name"));
            reportData.put("Email", browseSummary.getEmail());
            System.out.println(reportData.get("Email"));
            reportData.put("Telephone", browseSummary.getTelephone());
            System.out.println(reportData.get("Telephone"));

            browseSummary.backPage();
            list.add(Util.lineToCSV(reportData));
            reportData.clear();
        }

        CsvFileWriter.writeCsvFile(Util.fileName(), list, "workUA");
        Util.openReport();


    }

//    @AfterClass
//    public void tearDown(){
//        CsvFileWriter.writeCsvFile("CV.csv", list, "workUA");
//
//    }



}
