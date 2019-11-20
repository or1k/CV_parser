package Pages;

import View.Window;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Login {

    private SelenideElement email = $(By.xpath("//input[@id='email']"));
    private SelenideElement password = $(By.xpath("//input[@id='password']"));
    private SelenideElement profileName = $(By.xpath("//span[@class='hidden-xs nav-name']"));
    private SelenideElement submitButton = $(By.xpath("//button[@type='submit']"));


    //tari@smart-mail.top
    //1q2w3e4r


    //kegoyijox@mail-lab.net
    //1q2w3e4r
    public BrowseSummary loginInAccount() {
        getEmail().val(Window.userText.getText());
        getPassword().val(String.valueOf(Window.passwordText.getPassword()));
        getSubmitButton().click();
        profileName.waitUntil(Condition.visible, 999999);
        return page(BrowseSummary.class);
    }



    public SelenideElement getEmail() {
        return email;
    }

    public SelenideElement getPassword() {
        return password;
    }

    public SelenideElement getSubmitButton() {
        return submitButton;
    }
}
