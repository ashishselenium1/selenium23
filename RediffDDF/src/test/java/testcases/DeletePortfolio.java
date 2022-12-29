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

public class DeletePortfolio {
	ExtentReports rep;
	ExtentTest test;
	ApplicationKeywords app;
	@BeforeMethod
	public void init() {
		System.out.println("---before test----");
		rep = ExtentManager.getReport(System.getProperty("user.dir")+"\\reports\\");
		test = rep.createTest("Delete Portfolio Test");
		app = new ApplicationKeywords(test);// prop init+init report obj
	}
	
	@AfterMethod
	public void quit() {
		System.out.println("---after test---");
		rep.flush();// mandatory
		app.quit();
	}
	
	@Test(dataProvider = "getData")
	public void deletePortfolio(JSONObject testDetails) {
		test.log(Status.INFO, "Starting delete portfolio test");
		test.log(Status.INFO, testDetails.toString());
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
