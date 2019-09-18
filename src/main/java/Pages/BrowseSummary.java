package Pages;

import Utils.CitiesBossAz;
import Utils.Util;
import View.Window;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;

import javax.swing.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.Thread.sleep;


public class BrowseSummary {
    /**
     * BA- BossAZ
     * WU - Work Ua
     */






    private int counterOnPage = 0;
    private ArrayList idCards = new ArrayList();

    private SelenideElement dropDownSearchFilter = $(By.xpath("//a[@class=' search-toggle']"));
    private SelenideElement searchField = $(By.xpath("//input[@id='search']"));
    private SelenideElement searchButton = $(By.xpath("//input[@type='submit']"));
    private SelenideElement name = $(By.xpath("//h1[@class='add-top-xs']"));
    private SelenideElement openContactInfoButton = $(By.xpath("//a[@id='showContacts']"));
    private SelenideElement emailField = $(By.xpath("//a[contains(@href, 'mailto')]"));
    private SelenideElement telephoneFiled = $(By.xpath("//div[@class='card card-indent wordwrap']/dl/dd/span/.."));
    private SelenideElement telephoneConfirmationFiled = $(By.xpath("//div[@class='card card-indent wordwrap']/dl/dd/span"));
    private SelenideElement titleField = $(By.xpath("//div[@class='card card-indent wordwrap']/div//h2"));
    private SelenideElement city = $(By.xpath("//*[@class='row']//dl[@class='dl-horizontal']/dd[2]"));
    private SelenideElement cityIfInfoClosed = $(By.xpath("//*[@class='dl-horizontal']/dd"));
    private SelenideElement inputCity = $(By.xpath("//input[@class='js-main-region form-control']"));
    private SelenideElement ageField = $(By.xpath("//*[@class='row']//dl[@class='dl-horizontal']/dd[1]"));
    private ElementsCollection ids = $$(By.xpath("//div[@id='pjax-resume-list']/a"));
    private ElementsCollection idsBA = $$(By.xpath("//a[@class='results-i-link']"));
    private boolean cardsFlag;


    private SelenideElement inputCityBossAz = $(By.xpath("//select[@id='search_region_id']"));
    private SelenideElement searchFieldBossAZ = $(By.xpath("//input[@id='search_keyword']"));
    private SelenideElement salary = $(By.xpath("//div[@class='results-i']"));
    private SelenideElement emailFieldBossAz = $(By.xpath("//*[@class='email params-i-val']/a"));
    private SelenideElement telephoneFiledBossAz = $(By.xpath("//*[@class='phone params-i-val']/a"));
    private SelenideElement cityFieldBossAz = $(By.xpath("//*[@class='region params-i-val']"));
    private SelenideElement ageFieldBossAz = $(By.xpath("//*[@class='age params-i-val']"));
    private SelenideElement nextList = $(By.xpath("//span[@class='next']/a"));
    private SelenideElement resultNothingBossAZ = $(By.xpath("//*[@class='results-nothing']"));

    public void findSummary(String request, String city) {
        if(WebDriverRunner.getWebDriver().getCurrentUrl().contains("work.ua")) {
            getSearchField().val(request);
            getInputCity().val(city).pressEnter().pressEnter();
        }else if(WebDriverRunner.getWebDriver().getCurrentUrl().contains("boss.az")){
            getDropDownSearchFilter().click();
            getSearchFieldBossAZ().val(request);
            System.out.println(CitiesBossAz.getCitiesCode(city));
            if(city.equals("Все города")) {
                getSearchButton().click();
            }else {
                getInputCityBossAz().selectOptionByValue(CitiesBossAz.getCitiesCode(city));
                getSearchButton().click();
            }
        }
    }




    public boolean grabCards() throws InterruptedException, IOException {
        if(counterOnPage > 13) {
            System.out.println(counterOnPage);
            //ul[@class='pagination hidden-xs']/li[7]/a
            executeJavaScript("document.querySelector('.pagination li:last-of-type a').click();");
            counterOnPage =0;
//            ids.clear();
        }

        if(counterOnPage == 0){
            getIds();
        }

        if(counterOnPage == getIds().size()){
            JOptionPane.showMessageDialog(null, "Резюме закончились.");
            WebDriverRunner.getWebDriver().quit();
            cardsFlag = false;
            return true;
        }

        if(!Util.readFromFileWU(getIds().get(counterOnPage).getAttribute("name"))) {
            try {
                Util.writeInFileWU(getIds().get(counterOnPage).getAttribute("name"));
                sleep(1000);
                executeJavaScript("document.getElementsByClassName('card-hover')[" + counterOnPage + "].click();");
                counterOnPage++;
                cardsFlag = true;
                return true;
            } catch (JavascriptException ex) {
                JOptionPane.showMessageDialog(null, "Резюме закончились.");
                WebDriverRunner.getWebDriver().quit();
                cardsFlag = false;

            }
        }else {
            counterOnPage++;
            System.out.println("Duplicate");
            System.out.println("============================");
        }
        return false;
    }


    public boolean grabCardsBossAz() throws InterruptedException, IOException {

        if (counterOnPage > 19) {
            executeJavaScript("document.querySelector('.next a[rel=next]').click();");
//            getNextList().click();
            counterOnPage = 0;
        }

        if(counterOnPage == 0){
            getIdsBA();
        }

        if(counterOnPage == getIdsBA().size()){
            JOptionPane.showMessageDialog(null, "Резюме закончились.");
            WebDriverRunner.getWebDriver().quit();
            cardsFlag = false;
            return true;
        }


        try {
            if (!Util.readFromFileBA(getIDformCV(idsBA, counterOnPage))) {
                    Util.writeInFileBA(getIDformCV(idsBA, counterOnPage));
                    sleep(1000);

                    if (resultNothingBossAZ.isDisplayed()) {
                        JOptionPane.showMessageDialog(null, "Нет резюме по вашему запросу.");
                        WebDriverRunner.getWebDriver().quit();
                        return false;
                    }
                    executeJavaScript("document.querySelectorAll('.results-i')[" + counterOnPage + "].click();");
                    counterOnPage++;
                    cardsFlag = true;
                    return true;
            } else {
                counterOnPage++;
                System.out.println("Duplicate");
                System.out.println("============================");
            }
        }catch (JavascriptException | IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Резюме закончились.");
            WebDriverRunner.getWebDriver().quit();
            cardsFlag = false;
        }
        return false;
    }


    public void backPage(){
        back();
    }

    public  String getEmail() throws InterruptedException {
        if(Window.siteMenu.getSelectedIndex() == 0) {
            sleep(500);
            if (getOpenContactInfoButton().exists()) {

                executeJavaScript("document.getElementsByClassName('btn btn-default hidden-print')[0].click();");
                sleep(500);

            }
            if (getEmailField().isDisplayed()) {
                return getEmailField().getText();
            }

        }else if(Window.siteMenu.getSelectedIndex() == 1){
            sleep(500);

            if (getEmailFieldBossAz().isDisplayed()) {
                return getEmailFieldBossAz().getText();
            }

        }
        return "-";
    }

    public String getTelephone(){
        if(Window.siteMenu.getSelectedIndex() == 0) {
            if (getTelephoneConfirmationFiled().exists()) {
                return getTelephoneFiled().getText();
            }
        }else if(Window.siteMenu.getSelectedIndex() == 1){
            if (getTelephoneFiledBossAz().exists()) {
                return getTelephoneFiledBossAz().getText();
            }
        }
        return "-";
    }

    public String getCity(){
        if(Window.siteMenu.getSelectedIndex() == 0) {
            if (getFullName().equals("Личные данные скрыты")) {
                if (getCityIfInfoClosed().exists()) {
                    return getCityIfInfoClosed().getText();
                }
            } else {
                if (getCityField().exists()) {
                    return getCityField().getText();
                }
            }
        }else if (Window.siteMenu.getSelectedIndex() == 1){
            if (getCityFieldBossAz().exists()) {
                return getCityFieldBossAz().getText();
            }
        }
        return "-";
    }


        public String getAge() {
            if(Window.siteMenu.getSelectedIndex() == 0) {
                if (!getFullName().equals("Личные данные скрыты")) {
                    if (getAgeField().exists()) {
                        return getAgeField().getText();
                    }
                }
            }else if(Window.siteMenu.getSelectedIndex() == 1){
                    if (getAgeFieldBossAz().exists()) {
                        return getAgeFieldBossAz().getText();
                    }
            }
            return "-";
        }

public String getIDformCV(ElementsCollection ids, int number){
    String[] id = ids.get(number).getAttribute("href").split("/");

    return id[4];
}




    private SelenideElement getSearchField() {
        return searchField;
    }


    public  String getFullName(){
        return getSurName().getText();
    }

    public String getTitle(){
       return "\"" + getTitleField().getText() + "\"";
    }


    private  SelenideElement getSurName() {
        return name;
    }

    private SelenideElement getOpenContactInfoButton() {
        return openContactInfoButton;
    }

    private SelenideElement getEmailField() {
        return emailField;
    }

    private SelenideElement getTelephoneFiled() {
        return telephoneFiled;
    }


    private SelenideElement getTitleField() {
        return titleField;
    }

    private SelenideElement getTelephoneConfirmationFiled() {
        return telephoneConfirmationFiled;
    }

    private SelenideElement getCityField() {
        return city;
    }

    private SelenideElement getInputCity() {
        return inputCity;
    }

    private SelenideElement getCityIfInfoClosed() {
        return cityIfInfoClosed;
    }

    private SelenideElement getAgeField() {
        return ageField;
    }

    private SelenideElement getDropDownSearchFilter() {
        return dropDownSearchFilter;
    }

    private SelenideElement getSearchFieldBossAZ() {
        return searchFieldBossAZ;
    }

    private SelenideElement getSearchButton() {
        return searchButton;
    }

    private SelenideElement getInputCityBossAz() {
        return inputCityBossAz;
    }

    public boolean isCardsFlag() {
        return cardsFlag;
    }

    public SelenideElement getSalary() {
        return salary;
    }

    private SelenideElement getEmailFieldBossAz() {
        return emailFieldBossAz;
    }

    private SelenideElement getTelephoneFiledBossAz() {
        return telephoneFiledBossAz;
    }

    private SelenideElement getCityFieldBossAz() {
        return cityFieldBossAz;
    }

    private SelenideElement getAgeFieldBossAz() {
        return ageFieldBossAz;
    }

    public ElementsCollection getIds() {
        return ids;
    }

    public ElementsCollection getIdsBA() {
        return idsBA;
    }
}
