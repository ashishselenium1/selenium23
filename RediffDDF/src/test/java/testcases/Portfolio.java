package testcases;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;
import util.DataUtil;

public class Portfolio {
	
	ExtentReports rep;
	ExtentTest test;
	
	@BeforeMethod
	public void init() {
		System.out.println("---before test----");
		rep = ExtentManager.getReport(System.getProperty("user.dir")+"\\reports\\");
	}
	
	@AfterMethod
	public void quit() {
		System.out.println("---after test---");
		rep.flush();// mandatory
	}
	
	@Test(dataProvider ="getData",priority = 1)
	public void createPortfolio(JSONObject testDetails) {
		test = rep.createTest("Create Portfolio Test");
		test.log(Status.INFO, "Starting create portfolio test");
		test.log(Status.INFO, testDetails.toString());
		ApplicationKeywords app = new ApplicationKeywords(test);// prop init+init report obj
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
	
	@Test(dataProvider ="getData",priority = 2)
	public void deletePortfolio(JSONObject testDetails) {
		test = rep.createTest("Delete Portfolio Test");
		test.log(Status.INFO, "Starting delete portfolio test");
		test.log(Status.INFO, testDetails.toString());
		ApplicationKeywords app = new ApplicationKeywords(test);// prop init+init report obj
		if(((String)testDetails.get("runmode")).equals("N"))
				app.skipTest();				
		app.openBrowser("Chrome");
		app.navigate("app_url");
		app.defaultLogin();
		app.select("portfolio_selection_dropdown_id", (String)testDetails.get("portfolio_name"));
		app.click("delete_portfolio_button_id");
		app.acceptAlert();
		// verify - you
		app.assertAll();// non critical failures	
	}
	
	@DataProvider
	public Object[][] getData(){
		String path = System.getProperty("user.dir")+"//data/create_portfolio.json";
		return new DataUtil().getData(path);
	}

}
