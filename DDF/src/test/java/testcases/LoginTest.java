package testcases;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;

public class LoginTest{
	ExtentReports rep;
	ExtentTest test;
	
	@BeforeMethod
	public void init() {
		System.out.println("---before test----");
		rep = ExtentManager.getReport(System.getProperty("user.dir")+"\\reports\\");
		test = rep.createTest("Login Test");
	}
	
	@AfterMethod
	public void quit() {
		System.out.println("---after test---");
		rep.flush();// mandatory
	}
	
	@Test(dataProvider ="getData")
	public void doLogin(JSONObject testDetails) {
		System.out.println("---login---");
		test.log(Status.INFO, "Starting login test");
		test.log(Status.INFO, testDetails.toString());
		ApplicationKeywords app = new ApplicationKeywords(test);// prop init+init report obj
		if(((String)testDetails.get("runmode")).equals("N"))
				app.skipTest();				
		app.openBrowser("Chrome");
		app.navigate("app_url");
		app.validateTitle("login_page_title");
		app.click("login_link_xpath");
		app.type("username_id", (String)testDetails.get("username"));
		app.click("login_button_css");
		test.log(Status.INFO, "Username submited");
		app.assertAll();// non critical failures
	/*	
	 openBrowser("Chrome");	
	 driver.get("http://zoho.com");
	 driver.findElement(By.xpath("//a[@class='zh-login']")).click();
	 driver.findElement(By.xpath("//input[@id='login_id']")).sendKeys("abc");
	 driver.findElement(By.xpath("//button[@id='nextbtn']")).click();
	*/		 
	}
	
	@DataProvider
	public Object[][] getData(){
		String path = System.getProperty("user.dir")+"//data/login_test.json";
		JSONParser parser = new JSONParser();
		Reader reader;
		Object[][] obj=null;
		try {
			reader = new FileReader(path);
			JSONObject data = (JSONObject)parser.parse(reader);
			//System.out.println(data);
			JSONArray testData = (JSONArray)data.get("test_data");
			//System.out.println(testData);
			obj = new Object[testData.size()][1];
			for(int i=0;i<testData.size();i++) {
				JSONObject testDetails =(JSONObject) testData.get(i);
				System.out.println(testDetails);
				obj[i][0]=testDetails;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	

}
