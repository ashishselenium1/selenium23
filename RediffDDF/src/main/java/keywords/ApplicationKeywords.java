package keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords{
	
	public ApplicationKeywords(ExtentTest test) {
		// init the prop object
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
		prop = new Properties();
		FileInputStream fs;
		try {
			fs = new FileInputStream(path);
			prop.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// init the rep obj
		this.test=test;
		softAssert = new SoftAssert();
	}
	
	public void login(String username, String password) {
	}
	
	public void sendEmail() {
		
	}

	public void defaultLogin() {
		type("user_email_id", prop.getProperty("global_username"));
		type("user_password_css", prop.getProperty("global_password"));
		click("login_button_id");
		// verification
		// reportFailure(null, false);
		
	}

	public void selectStock(String stockName) {
		type("stock_name_id", stockName.substring(0, 4));
		driver.findElement(By.xpath("//div[text()='"+stockName+"']")).click();
		
	}

}
