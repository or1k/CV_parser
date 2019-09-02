package Pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;

import javax.swing.*;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.Thread.sleep;


public class BrowseSummary {
    private int counterOnPage = 0;

    private SelenideElement searchField = $(By.xpath("//input[@id='search']"));
    private SelenideElement name = $(By.xpath("//h1[@class='add-top-xs']"));
    private SelenideElement openContactInfoButton = $(By.xpath("//a[@id='showContacts']"));
    private SelenideElement emailField = $(By.xpath("//a[contains(@href, 'mailto')]"));
    private SelenideElement telephoneFiled = $(By.xpath("//div[@class='card card-indent wordwrap']/dl/dd/span/.."));
    private SelenideElement telephoneConfirmationFiled = $(By.xpath("//div[@class='card card-indent wordwrap']/dl/dd/span"));
    private SelenideElement titleField = $(By.xpath("//div[@class='card card-indent wordwrap']/div//h2"));

    public void findSummary(String request) {
        getSearchField().val(request).pressEnter();

    }

    public void grabCards() throws InterruptedException {
        if(counterOnPage > 13) {
            //ul[@class='pagination hidden-xs']/li[7]/a
            executeJavaScript("document.querySelector('.pagination li:last-of-type a').click();");
            counterOnPage =0;
        }
        try {
        sleep(1000);
        executeJavaScript("document.getElementsByClassName('card-hover')[" + counterOnPage + "].click();");
            counterOnPage++;
        }catch (JavascriptException ex){
            JOptionPane.showMessageDialog(null, "Нет резюме по вашему запросу.");
            WebDriverRunner.getWebDriver().quit();
        }

    }

    public void backPage(){
        back();
    }

    public  String getEmail() throws InterruptedException {
        sleep(500);
        if(getOpenContactInfoButton().exists()) {

                executeJavaScript("document.getElementsByClassName('btn btn-default hidden-print')[0].click();");
                sleep(500);

        }
        if(getEmailField().isDisplayed()) {
            return getEmailField().getText();
        }
        return "-";
    }

    public String getTelephone(){
        if(getTelephoneConfirmationFiled().exists()) {
            return getTelephoneFiled().getText();
        }
        return "-";
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

    public SelenideElement getTelephoneFiled() {
        return telephoneFiled;
    }


    public SelenideElement getTitleField() {
        return titleField;
    }

    public SelenideElement getTelephoneConfirmationFiled() {
        return telephoneConfirmationFiled;
    }
}
