package testcases;
//Create Portfolio
//Delete Portfolio
//Buy stock
//Sell Stock

// implement selenium grid
// jenkins and github

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
import util.DataUtil;

public class CreatePortfolio{
	ExtentReports rep;
	ExtentTest test;
	ApplicationKeywords app;
	
	@BeforeMethod
	public void init() {
		System.out.println("---before test----");
		rep = ExtentManager.getReport(System.getProperty("user.dir")+"\\reports\\");
		test = rep.createTest("Create Portfolio Test");
		app = new ApplicationKeywords(test);// prop init+init report obj
	}
	
	@AfterMethod
	public void quit() {
		System.out.println("---after test---");
		rep.flush();// mandatory
		app.quit();
	}
	
	@Test(dataProvider ="getData")
	public void createPortFolio(JSONObject testDetails) {
		test.log(Status.INFO, "Starting create portfolio test");
		test.log(Status.INFO, testDetails.toString());
		
		if(((String)testDetails.get("runmode")).equals("N"))
				app.skipTest();				
		app.openBrowser("Chrome");
		app.navigate("app_url");
		app.defaultLogin();
		app.click("create_portfolio_button_xpath");
		app.clear("portfolio_name_css");
		app.type("portfolio_name_css", (String)testDetails.get("portfolio_name"));
		app.click("portfolio_creation_button_xpath");
		// verify - you
		app.assertAll();// non critical failures
	}
	
	@DataProvider
	public Object[][] getData(){
		String path = System.getProperty("user.dir")+"//data/create_portfolio.json";
		return new DataUtil().getData(path);
	}
	
	

}
