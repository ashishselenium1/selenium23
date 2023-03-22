package keywords;

public class ValidationKeywords extends GenericKeywords{
	
	// critical failure or not
	public void validateTitle(String expectedTitleKey) {
		String expectedTitle=prop.getProperty(expectedTitleKey);
		String actualTitle=driver.getTitle();
		if(!expectedTitle.equals(actualTitle)){
			// report a failure
			reportFailure("Titles did not match", false);
		}
	}
	
	public void validateText(String xpathExp,String expectedText) {
		// critical failure or not
	}

}
