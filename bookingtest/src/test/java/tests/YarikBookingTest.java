package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import utils.DriverSetup;



/**
 * @author y.pernerovskyy
 *
 */
public class YarikBookingTest extends DriverSetup {

    WebDriver driver;
   
    @BeforeClass
    public void init() throws IOException {
	driver = setUp();
	}

    @Test
    @Features("Demo")
    @Stories("Demo test")
    public void testDemo() throws Exception {
	
    	driver.findElement(By.id("dfd"));
    }

   
    @AfterClass
    public void shutDown() {
	driver.quit();
    }

}
