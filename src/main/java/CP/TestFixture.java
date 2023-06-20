package CP;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.devtools.v112.profiler.model.Profile;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.IReporter;
import org.testng.*;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;

public class TestFixture
{
    private static int screenshotCounter;
    boolean isTakeScreenshot=true;

    @BeforeClass
    public void OneTimeSetUp()
    {

        DriverInitializeOptions options = GetDefaultOptions();
        Driver.Initialize(options);
        var size = Driver.Instance.manage().window().getSize();

        Driver.Instance.manage().window().maximize();
        VerificationError.New();
    }

    @BeforeMethod
    protected void SetUp()
    {
        screenshotCounter = 0;
    }

    @AfterMethod
    protected void TearDown()
    {
        if (Driver.Instance != null && Reporter.getCurrentTestResult().getStatus() !=  ITestResult.SUCCESS)
        {
            File fileInfo = Driver.takeSnapshot(null,null);
        }
    }

    @AfterSuite
    protected void OneTimeTearDown()
    {
        Driver.Instance.quit();
    }

    protected DriverInitializeOptions GetDefaultOptions()
    {
        Dimension dimension = new Dimension(1366,1080);
        DriverInitializeOptions options = new DriverInitializeOptions();
        options.WindowSize=dimension;
        options.BrowserType=Browser.Chrome;

        /*FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", Driver.DOWNLOADS_DIR);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.show_plugins_in_list", false);
        profile.setPreference("browser.download.panel.shown", true);
        profile.setPreference("security.enterprise_roots.enabled", true);

        options.BrowserOptions = new FirefoxOptions().setProfile(profile);*/

        return options;
    }

    protected static void SoftAssert(Runnable method, boolean... isTakeScreenshot)
    {
        boolean _isTakeScreenshot = (isTakeScreenshot.length>0) ? isTakeScreenshot[0] : true;
        try
        {
          method.run();
        }
        catch (AssertionError exception)
        {
            VerificationError.Append("\r\n Exception Message: " + exception.getMessage());
            if (exception.getMessage() != null)
            {
                VerificationError.Append(exception.getMessage());
            }
            else if (exception.getStackTrace() != null)
            {
                System.out.println("STACKTRACE: " + exception.getStackTrace());
            }

            if (_isTakeScreenshot)
            {
                var fileInfo = Driver.takeSnapshot(Reporter.getCurrentTestResult().getName() +"_"+ screenshotCounter++, null);
            }
        }
    }

    public static class VerificationError
    {
        static StringBuilder _sVerificationErrors;

        public static StringBuilder Get()
        {
            return _sVerificationErrors;
        }

        public static void Append(String sError)
        {
            _sVerificationErrors.append(sError);
        }

        public static void New()
        {
            _sVerificationErrors = new StringBuilder();
        }

        public static void AssertAll()
        {
            Assert.assertEquals(new StringBuilder().length(), VerificationError.Get().length(), VerificationError.Get().toString());
        }
    }

    protected static class WarningMessage
    {
        static StringBuilder _sWarningMessages;

        public static void New()
        {
            _sWarningMessages = new StringBuilder();
        }

        public static StringBuilder Get()
        {
            return _sWarningMessages;
        }

        public static void Append(String warning)
        {
            _sWarningMessages.append(warning + "<br>");
        }

        public static void Publish()
        {
            if (Get().toString()!=null)
            {
                Reporter.log(Get().toString());
            }
        }
    }
}
