package testcases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
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

public class BuyStock {
	ExtentReports rep;
	ExtentTest test;
	ApplicationKeywords app;

	@BeforeMethod
	public void init() {
		System.out.println("---before test----");
		rep = ExtentManager.getReport(System.getProperty("user.dir")+"\\reports\\");
		test = rep.createTest("Buy Stock Test");
		app = new ApplicationKeywords(test);// prop init+init report obj
	}
	
	@AfterMethod
	public void quit() {
		System.out.println("---after test---");
		rep.flush();// mandatory
		app.quit();
	}

	@Test(dataProvider ="getData")
	public void buyStock(JSONObject testDetails) throws ParseException {
			String stockName=(String)testDetails.get("stock_name");
			String date=(String)testDetails.get("purchase_date");
			String qty=(String)testDetails.get("qty");
			String price=(String)testDetails.get("price");			
			test.log(Status.INFO, "Starting Buy Stock test");
			test.log(Status.INFO, testDetails.toString());
			test.log(Status.INFO, testDetails.toString());
			if(((String)testDetails.get("runmode")).equals("N"))
					app.skipTest();				
			app.openBrowser("Chrome");
			app.navigate("app_url");
			app.defaultLogin();
			app.click("add_stock_button_id");
			app.selectStock(stockName);
			app.click("date_icon_id");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = sdf.parse(date);
			
			sdf = new SimpleDateFormat("d");
			String day = sdf.format(d1);
			System.out.println(day);
			
			sdf = new SimpleDateFormat("MMMM");
			String month = sdf.format(d1);
			System.out.println(month);
			
			sdf = new SimpleDateFormat("yyyy");
			String year = sdf.format(d1);
			System.out.println(year);
			
			String monthYearToBeSelected=month+" "+year;
			String monthYearDisplayed = app.getText("month_year_text_xpath");
			System.out.println(monthYearToBeSelected +" ---- "+monthYearDisplayed );
			Date currDate = new Date();
			while(!monthYearToBeSelected.equals(monthYearDisplayed)) {
				if(d1.compareTo(currDate) == 1) {
					// forward
				}else {
					// back
					app.click("back_button_xpath");
				}
				monthYearDisplayed = app.getText("month_year_text_xpath");
				System.out.println(monthYearDisplayed);
			}
			
			app.driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
			app.type("stock_qty_name", qty);
			app.type("stock_price_name", price);
			app.click("addstock_btn_id");
			
			
	}
	
	
	@DataProvider
	public Object[][] getData(){
		String path = System.getProperty("user.dir")+"//data/buy_stock.json";
		return new DataUtil().getData(path);
	}
}
