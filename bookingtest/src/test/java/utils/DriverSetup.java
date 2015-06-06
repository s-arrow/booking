package utils;

import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.allure.annotations.Attachment;

public class DriverSetup extends TestListenerAdapter {

    private static final Object propertiesLock = new Object();
    private static String url;
    private static Client client;
    private static WebDriver driver;

    private enum Client {
	FF, GC, IE
    }

    public WebDriver setUp() throws IOException {

	Properties props;

	synchronized (propertiesLock) {
	    props = readConfig();
	    setClient(props.getProperty("client", "gc"));
	    url = props.getProperty("url");
	}

	return launchDriver();
    }

    private WebDriver launchDriver() {

	switch (client) {
	case GC:
	    ChromeDriverManager.setup(Architecture.x64);
	    driver = new ChromeDriver();
	    break;
	case FF:
	    driver = new FirefoxDriver();
	    break;
	case IE:
		InternetExplorerDriverManager.setup();
		driver = new InternetExplorerDriver();
	    break;
	default:
	    driver = null;
	    break;
	}

	driver.manage().window().maximize();
	driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
	driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	driver.get(url);

	return driver;

    }

    private Properties readConfig() throws IOException {

	Properties props = new Properties();

	try {
	    props.load(new FileInputStream("config.properties"));
	} catch (IOException ioe) {
	    System.err.println("Not able to read property file");
	    throw ioe;
	}

	return props;
    }

    private Client setClient(String clientName) {
	try {
	    client = Client.valueOf(clientName.toUpperCase());
	} catch (Exception e) {
	    System.out
		    .println("Unknown browser in config file. Google Chrome will be used instead.");
	    client = Client.GC;
	}
	return client;
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    protected byte[] saveAllureScreenshot() {
	return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
	System.out.println("Error. Capturing Screenshot...");
	saveAllureScreenshot();
    }

}
