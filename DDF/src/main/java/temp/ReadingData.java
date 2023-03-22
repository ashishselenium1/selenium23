package temp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadingData {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir")+"//data/login_test.json";
		JSONParser parser = new JSONParser();
		Reader reader;
		try {
			reader = new FileReader(path);
			JSONObject data = (JSONObject)parser.parse(reader);
			System.out.println(data);
			JSONArray testData = (JSONArray)data.get("test_data");
			System.out.println(testData);
			Object[][] obj = new Object[testData.size()][1];
			for(int i=0;i<testData.size();i++) {
				JSONObject testDetails =(JSONObject) testData.get(i);
				System.out.println(testDetails);
				obj[i][0]=testDetails;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
