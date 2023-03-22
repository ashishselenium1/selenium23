package keywords;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class GenericKeywords {
	
	public WebDriver driver;
	public Properties prop;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	public void openBrowser(String browserName) {
		test.log(Status.INFO, "Opening browser "+browserName);
		System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "D:\\drivers\\geckodriver.exe");
		System.setProperty("webdriver.edge.driver", "D:\\drivers\\msedgedriver.exe");

		String bName="Chrome";
		
		if(bName.equals("Chrome")) {
			driver = new ChromeDriver();
		}else if(bName.equals("Mozilla")) {
			driver = new FirefoxDriver();
		}else if(bName.equals("Edge")) {
			driver = new EdgeDriver();
		}
		// implicit wait - presence of the element, no visibility
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	

	}
	
	public void navigate(String urlKey) {
		test.log(Status.INFO, "Navigating to "+urlKey);
		driver.get(prop.getProperty(urlKey));
	}
	
	public void click(String locatorKey) {
		test.log(Status.INFO, "Clicking on "+locatorKey);
		getElement(locatorKey).click();
	}
	
	public void type(String locatorKey,String data) {
		test.log(Status.INFO, "Typing in "+locatorKey+". Data "+data);
		getElement(locatorKey).sendKeys(data);
	}
	
	public void select(String locatorKey,String value) {
		
	}
   
	// central function to extract elements
	public WebElement getElement(String locatorKey) {
		// make sure element is present and visible otherwise - report critical failure
		if(isElementPresent(locatorKey)) {
			WebElement e = driver.findElement(getLocator(locatorKey));
			return e;
		}else {
			// report a critical failure and stop test
			reportFailure("Element not found "+locatorKey, true);
			return null;
		}
	}
	
	// true - present and visible
	// false - not present or visible
	public boolean  isElementPresent(String locatorKey) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
		}catch(Exception e) {
			return false;
		}	
		return true;
	}
	
	// decide the locator strat
	public By getLocator(String locatorKey) {
		By by=null;
		if(locatorKey.endsWith("_id"))
			by = By.id(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_xpath"))
			by = By.xpath(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_name"))
			by = By.name(prop.getProperty(locatorKey));
		else
			by = By.cssSelector(prop.getProperty(locatorKey));
		return by;
	}
	// reporting functions
	public void reportFailure(String failureMessage, boolean stopFlag) {
		// fail in extent reports
		test.log(Status.FAIL, failureMessage);
		// fail in testng
		softAssert.fail(failureMessage);
		// screenshot
		
		if(stopFlag)
			assertAll();
	}
	
	public void skipTest() {
		//skip in extent reports
		test.log(Status.SKIP, "Skipping the test");
		// skip the test
		throw new SkipException("skipping the test");
	}
	
	public void assertAll() {
		softAssert.assertAll();
	}
}
