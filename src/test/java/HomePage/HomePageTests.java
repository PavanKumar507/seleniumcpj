package HomePage;

import CP.Driver;
import CP.TestFixture;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Test;

public class HomePageTests extends TestFixture {

    @Test (testName = "PK-01")
    public void HomePageLoginTest() {

        HomePage.GoTo();
        HomePage.Login()
                .WithUserName("Admin")
                .WithPassword("admin123")
                .Execute(false);

        VerificationError.New();
        SoftAssert(()->Assert.assertEquals("a","a","not expected"));
        SoftAssert(()->Assert.assertTrue(false,"HomePageLoginFailed"));
        VerificationError.AssertAll();
    }

    @Test(testName = "PK-02")
    public void HomePageCanBeOpened() {

        Driver.Instance.get("https://chat.openai.com/");

        VerificationError.New();
        SoftAssert(()->Assert.assertEquals("a","a","not expected"));
        VerificationError.AssertAll();
    }
}
