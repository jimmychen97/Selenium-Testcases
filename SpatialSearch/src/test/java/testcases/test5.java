package testcases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.BrowserUpProxyServer;
import com.browserup.bup.client.ClientUtil;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.harreader.model.Har;

import SpatialSearch.SpatialSearch.supportFunctions;

public class test5 {

	public WebDriver driver = null;
	public String url = driver.getCurrentUrl();

	@Parameters("browserName")
	@BeforeTest
	public void setUp(String browserName) {

		System.out.println("Browser name is: " + browserName);
		System.out.println("Thread id is: " + Thread.currentThread().getId());

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					"C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\iedriver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
		}
	}

	@Test
	public void test1() throws InterruptedException, IOException {

		BrowserUpProxy proxy = new BrowserUpProxyServer();
		proxy.start();
		int port = proxy.getPort();
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
	    proxy.newHar("arcgis.com");
	    
	    
	    
		
		// change here for dexext, QA and prod testing
		driver.get("https://devext.arcgis.com/index.html");

		// internal login
		driver.findElement(By.id("j_username")).sendKeys("sharing");
		driver.findElement(By.id("j_password")).sendKeys("ago.test");
		Thread.sleep(1000);
		driver.findElement(By.id("submit")).click();

		// AGOL devext login
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Sign In")).click();
		//supportFunctions.waitForElementLinkText("Sign In");
		//supportFunctions.waitForElementID("user_username");
		driver.findElement(By.id("user_username")).sendKeys("haoyan_dev");
		driver.findElement(By.id("user_password")).sendKeys("TESTtest1");
		driver.findElement(By.id("signIn")).click();
		
		Har har = proxy.getHar();
		File harFile = new File("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\HAR files\\login.har");
	    harFile.createNewFile();
	    //har.writeTo(harFile);
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}
}
