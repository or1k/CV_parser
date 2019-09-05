package Pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static java.lang.Thread.sleep;


public class BossAzCV {

    private SelenideElement emailFieldBossAz = $(By.xpath("//*[@class='email params-i-val']/a"));
    private SelenideElement telephoneFiledBossAz = $(By.xpath("//*[@class='phone params-i-val']/a"));
    private SelenideElement cityFieldBossAz = $(By.xpath("//*[@class='region params-i-val']"));
    private SelenideElement ageFieldBossAz = $(By.xpath("//*[@class='age params-i-val']"));
    private SelenideElement titleFieldBossAz = $(By.xpath("//h1[@class='post-title']"));
    private SelenideElement fullNameFiled = $(By.xpath("//div[@class='post-seeker']"));


    public String getTitle(){
        return "\"" + getTitleFieldBossAz().getText() + "\"";
    }

    public String getFullName(){
        return getFullNameFiled().getText();
    }

    public  String getEmail() throws InterruptedException {
        switchTo().window(1);
            sleep(500);
            if (getEmailFieldBossAz().isDisplayed()) {
                return getEmailFieldBossAz().getText();
            }
        return "-";
    }



    public String getCity() {
        if (getCityFieldBossAz().exists()) {
            return getCityFieldBossAz().getText();
        }

        return "-";
    }


    public String getAge() {
        if (getAgeFieldBossAz().exists()) {
            return getAgeFieldBossAz().getText();
        }
        return "-";
    }

    public String getTelephone() {
        if (getTelephoneFiledBossAz().exists()) {
            return getTelephoneFiledBossAz().getText();
        }
        WebDriverRunner.getWebDriver().close();
        return "-";
    }


    public SelenideElement getEmailFieldBossAz() {
        return emailFieldBossAz;
    }

    public SelenideElement getTelephoneFiledBossAz() {
        return telephoneFiledBossAz;
    }

    public SelenideElement getCityFieldBossAz() {
        return cityFieldBossAz;
    }

    public SelenideElement getAgeFieldBossAz() {
        return ageFieldBossAz;
    }

    public SelenideElement getTitleFieldBossAz() {
        return titleFieldBossAz;
    }

    public SelenideElement getFullNameFiled() {
        return fullNameFiled;
    }
}
