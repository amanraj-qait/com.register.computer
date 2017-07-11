package com.register.computer;

import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class ExecuteComputersDatabaseUiTest {

	static WebDriver driver = null;
	static String browser;
	static String name;
	static String introduce_date;
	static String discontinued_date;
	static String company;
	static String url;
	Properties property = new Properties();
	Properties property2 = new Properties();
	InputStream testDataInput = null;
	InputStream browserInput = null;
	public static UIMap data = new UIMap();

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	@BeforeTest
	public void intializeValues() throws Exception {

		testDataInput = getClass().getClassLoader().getResourceAsStream("RegisterData.properties");
		browserInput = getClass().getClassLoader().getResourceAsStream("config.properties");
		property.load(testDataInput);
		property2.load(browserInput);
		// Initialize value to strings
		name = property.getProperty("Name");
		introduce_date = property.getProperty("IntroduceDate");
		discontinued_date = property.getProperty("DiscontinuedDate");
		company = property.getProperty("Company");
		url = property.getProperty("Url");
		browser = property2.getProperty("Browser");
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\amanraj\\Downloads\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.marionette", "Downloads\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		/*
		 * else if(browser.equalsIgnoreCase("Edge")){
		 * System.setProperty("webdriver.edge.driver",
		 * ".\\MicrosoftWebDriver.exe"); driver = new EdgeDriver(); }
		 */
		else {
			// If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Obtaining locator value
	}

	/**
	 * register a new computer by add a new computer name
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1)
	public static void registerNameOnWebsite() throws Exception {
		driver.get(url);
		driver.findElement(data.getLocator("Add_computer")).click();
		driver.findElement(data.getLocator("Register_name")).sendKeys(name);
		driver.findElement(data.getLocator("Introduce_date")).sendKeys(introduce_date);
		driver.findElement(data.getLocator("Discontinued_date")).sendKeys(discontinued_date);
		Select dropdown = new Select(driver.findElement(data.getLocator("Company")));
		dropdown.selectByVisibleText(company);
		driver.findElement(data.getLocator("Submit")).click();
	}

	/**
	 * check the values of registered computer name
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2)
	public static void checkForRegisteredName() throws Exception {
		driver.navigate().to(url);
		driver.findElement(data.getLocator("Search_filter")).sendKeys(name);
		driver.findElement(data.getLocator("Search_filter")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(data.getLocator("Select_name")).click();
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(data.getLocator("Register_name")).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(data.getLocator("Introduce_date")).getAttribute("value"),
				introduce_date);
		Assert.assertEquals(driver.findElement(data.getLocator("Discontinued_date")).getAttribute("value"),
				discontinued_date);
		String selectedOption = new Select(driver.findElement(data.getLocator("Company"))).getFirstSelectedOption()
				.getText();
		Assert.assertEquals(selectedOption, company);
	}

	/**
	 * close the Webdriver
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority=3)
	public void endTest() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
		driver.quit();
	}
}
