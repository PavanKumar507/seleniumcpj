package HomePage;

import CP.BasePageImpl;
import CP.Driver;
import org.openqa.selenium.By;

public class HomePageImpl extends BasePageImpl {

    @Override
    public String Url() {
        return "https://www.demoblaze.com/";
    }

    private static By userName = By.cssSelector("#loginusername");
    private static By passWord = By.cssSelector("#loginpassword");
    private static By loginBtn = By.cssSelector("button[onclick='logIn()']");
    private static By homePageLogin = By.cssSelector("#login2");

    public void login(String usr, String pwd){

        Driver.Instance.findElement(homePageLogin).click();
        Driver.Instance.findElement(userName).clear();
        Driver.Instance.findElement(userName).sendKeys(usr);
        Driver.Instance.findElement(passWord).clear();
        Driver.Instance.findElement(passWord).sendKeys(pwd);
        Driver.Instance.findElement(loginBtn).click();
    }

    public void waitLoadingCompleted() throws InterruptedException {
        Thread.sleep(10000);
    }
}
