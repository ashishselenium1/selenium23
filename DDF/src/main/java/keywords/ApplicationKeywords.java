package keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

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

}
