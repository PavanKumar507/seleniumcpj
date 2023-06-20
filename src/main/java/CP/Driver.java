package CP;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class Driver {

    public static WebDriver Instance;
    public static JavascriptExecutor JSInstance;
    public static Actions ActionsInstance;
    private static int webDrivePID = -1;
    private ITestContext testContext;

    public static final String DOWNLOADS_DIR = "C:\\Temp\\";
    public static final String SCREENSHOTS_DIR = "C:\\Temp\\Screenshots";

    public static void Initialize(DriverInitializeOptions options)
    {
        switch (options.BrowserType)
        {
            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                Instance = new FirefoxDriver();
                break;
            case Edge:
                WebDriverManager.edgedriver().setup();
                Instance = new EdgeDriver();
                break;
            case Safari:
                WebDriverManager.safaridriver().setup();
                Instance = new SafariDriver();
                break;
            case Chrome:
                WebDriverManager.chromedriver().setup();
                Instance = new ChromeDriver();
                break;
        }

        if (options.WindowSize != null)
        {
            Instance.manage().window().setSize(options.WindowSize);
        }

        Instance.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

        ActionsInstance = new Actions(Instance);
        JSInstance = (JavascriptExecutor) Instance;
    }

    public static File takeSnapshot(String snapshotName, String snapshotPath) {
        snapshotName = snapshotName != null ? snapshotName : Reporter.getCurrentTestResult().getName();
        snapshotPath = snapshotPath != null ? snapshotPath : SCREENSHOTS_DIR;

        String filePath = Paths.get(snapshotPath, snapshotName + ".png").toString();
        String directory = Paths.get(filePath).getParent().toString();
        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        File screenshotFile = ((TakesScreenshot)Instance).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshotFile.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }

    public static void GoToUrl(String url)
    {
        Instance.get(url);
    }

    public static void Refresh()
    {
        Instance.navigate().refresh();
    }

}

