package stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.krish.pageObjects.LoginPage;
import com.krish.pageObjects.LoginPage_Prac;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDef extends BaseClass_Prac{


	public void setup() throws IOException {
		
		//Logger (Logger not working need to check)
		//logger=Logger.getLogger("nopCommerce"); //Added logger
		PropertyConfigurator.configure("Log4j.properties"); //Added logger

		//Reading the properties
		configProp = new Properties();
		//Load the File
		FileInputStream configPropfile = new FileInputStream("config.properties");
		configProp.load(configPropfile);

		String br=configProp.getProperty("browser");

		if(br.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
			driver = new ChromeDriver();
		}
		else if(br.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
			driver = new FirefoxDriver();
		}
		else if(br.equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver", configProp.getProperty("iepath"));
		}
	}
	
	//------------------------------------------------------
	
	@Given("User Launch Chrome browser")
	public void user_Launch_Chrome_browser() {
		
		lp=new LoginPage_Prac(driver);
	
	}

	@When("^User Opens URL \"(.*?)\"$")
	public void user_Opens_URL(String url) {

		driver.get(url);
		driver.manage().window().maximize();
	}

	////Given("^\"(.*?)\" is elder to \"(.*?)\" and \"(.*?)\"$")
	@When("^User enters Email as \"(.*?)\" and Password as \"(.*?)\"$")
	public void user_enters_Email_as_and_Password_as(String email, String password) {
		
		lp.setUserName(email);
		lp.setPassword(password);
	
	}

	@When("Click on Login")
	public void click_on_Login() throws InterruptedException {
	
		lp.clickLogin();
	}

	@Then("Page Title should be \"(.*?)\"$")
	public void page_Title_should_be(String title) throws InterruptedException {

	if(driver.getPageSource().contains("Login was unsuccessful.")) {
		driver.close();
		Assert.assertTrue(false);
	}else {
		Assert.assertEquals(title, driver.getTitle());
	}

	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException {
		
		lp.clickLogout();
	}

	@Then("close browser")
	public void close_browser() {
	
		driver.quit();
	}


}
