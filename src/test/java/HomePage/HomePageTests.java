package HomePage;

import CP.TestFixture;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class HomePageTests extends TestFixture {

    @Test
    public void test1() {

        HomePage.GoTo();
        HomePage.Login()
                .WithUserName("Admin")
                .WithPassword("admin123")
                .Execute(false);

        VerificationError.New();
        SoftAssert(()->Assert.assertEquals("a","a","not expected"));
        //SoftAssert(()->Assert.assertEquals("b","a","not expected"));
        VerificationError.AssertAll();
    }
}
