package com.register.computer;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;

public class ExecuteComputersDatabaseApiTest {
	Properties property = new Properties();
	Properties property2 = new Properties();
	InputStream testDataInput = null;
	InputStream testDataInput2 = null;
	static String url;
	static String url2;
	URL url3 = null;
	HttpURLConnection connection;
	/**
	 * @throws IOException 
	 * 
	 */
/*	private void _connectToServiceURL(String urlToConnect) throws IOException {

		url3 = new URL(urlToConnect);
		connection = (HttpURLConnection) url3.openConnection();
		}*/
	@BeforeTest
	public void initialize_values() throws IOException{
		testDataInput = getClass().getClassLoader().getResourceAsStream("RegisterData.properties"); 
		testDataInput2 = getClass().getClassLoader().getResourceAsStream("PageElementAttribute.properties"); 
		property2.load(testDataInput2);
		property.load(testDataInput);
		url=property.getProperty("Url");
	}
	/**
	 * 
	 */
/*	@Test
	public String getHeaderFieldValueFromTheService(String urlToConnect,
			String header) {
			String headerFiledValue = null;
			try {
			_connectToServiceURL(urlToConnect);
			headerFiledValue = connection.getHeaderField(header);
			} catch (IOException ex) {
			headerFiledValue = null;
			ex.printStackTrace();
			}
			return headerFiledValue;
			}*/
	@Test
	public void basic_Test() {
		given().when().get(url).then().assertThat().statusCode(200)
				.and().contentType(ContentType.HTML);
	}

	/**
	 * 
	 */
	@Test(priority=2)
	public void test_Data() {
             url=url.concat(url2);
             System.out.println(url);
		String Name = get(url).body().htmlPath()
				.getString(property2.getProperty("Test_Name"));
		Assert.assertEquals(Name, property.getProperty("Name"));
		String Intro_Date = get(url).body().htmlPath()
				.getString(property2.getProperty("Test_IDate"));
		Assert.assertEquals(Intro_Date, property.getProperty("IntroduceDate"));
		String Discontinued_Date = get(url).body().htmlPath()
				.getString(property2.getProperty("Test_DDate"));
		Assert.assertEquals(Discontinued_Date, property.getProperty("DiscontinuedDate"));
		String Company = get(url).body().htmlPath()
				.getString(property2.getProperty("Test_Company"));
		Assert.assertEquals(Company, property.getProperty("Company"));
	}

	/**
	 * 
	 */
	@Test(priority=1)
	public void test_Response() {
	url2=get("http://computer-database.gatling.io/computers?f=APEXCS").body().htmlPath().getString("html.body.section.table.tbody.tr.td[0].a.@href");
	String ap= url2.split("/")[2];
	url2="/"+ap;
	}
}
