package reports;



import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	static ExtentReports report;
	public static String screenshotFolderPath;
// dynamically generates the report folder name	
	
	public static ExtentReports getReport(String basePath) {
		if(report==null) {
			// dynamically generates the report folder name	
			Date d = new Date();
			String reportFolder=d.toString().replaceAll(":","-");
			screenshotFolderPath = basePath+reportFolder+"\\screenshots\\";
			// make the directories
			File file  = new File(screenshotFolderPath);
			file.mkdirs();
			// finalize the report config
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(basePath+reportFolder);
			sparkReporter.config().setReportName("Production Regression Testing");
			sparkReporter.config().setDocumentTitle("Automation Reports");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setEncoding("utf-8");
			// init the report
			report = new ExtentReports();
			report.attachReporter(sparkReporter);
		}
		
		return report;
		
	}
	
	
}
