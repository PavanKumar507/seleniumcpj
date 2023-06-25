package CP;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.devtools.v112.profiler.model.Profile;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.IReporter;
import org.testng.*;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.util.Date;

public class TestFixture
{
    private static int screenshotCounter;
    boolean isTakeScreenshot=true;

    private static ExtentReports extent;
    private static ExtentTest test;
    private static ITestResult testResult;

    @BeforeSuite
    public void BeforeSuite(){
        extent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("./report.html");
        extent.attachReporter(htmlReporter);
    }
    @BeforeClass
    public void OneTimeSetUp()
    {

        DriverInitializeOptions options = GetDefaultOptions();
        Driver.Initialize(options);
        var size = Driver.Instance.manage().window().getSize();

        Driver.Instance.manage().window().maximize();
    }

    @BeforeMethod
    protected void SetUp()
    {
        screenshotCounter = 0;
    }


    @AfterMethod
    protected void TearDown(ITestResult testResult)
    {
        this.testResult = testResult;
        test = extent.createTest("ID:"+testResult.getTestContext().getCurrentXmlTest()+" "+testResult.getName());
        if (Driver.Instance != null && testResult.getStatus() !=  ITestResult.SUCCESS)
        {
            File fileInfo = Driver.takeSnapshot(null,null);
            test.addScreenCaptureFromPath(fileInfo.getPath());
        }

        if(testResult.getStatus() ==  ITestResult.FAILURE){

            test.log(Status.INFO,testResult.getThrowable().getMessage());
            test.log(Status.FAIL, "Test Failed");
        }

        if(testResult.getStatus() ==  ITestResult.SUCCESS){
            test.log(Status.PASS, "Test Passed");
        }


    }

    @AfterClass
    protected void OneTimeTearDown()
    {
        Driver.Instance.quit();

    }
    @AfterSuite
    public void Aftersuie(){
        extent.flush();
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
            //VerificationError.Append("\r\n Exception Message: " + exception.getMessage());
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
                var fileInfo = Driver.takeSnapshot(testResult.getName() +"_"+ screenshotCounter++, null);
                test.addScreenCaptureFromPath(fileInfo.getPath());
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
