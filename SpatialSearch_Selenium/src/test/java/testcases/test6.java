package testcases;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.browserup.harreader.model.HarEntry;

import SpatialSearch.SpatialSearch.supportFunctions;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

public class test6 {

	@Test
	public static void setup() throws IOException, InterruptedException {
		
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start();
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		
		System.setProperty("webdriver.gecko.driver","C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver(firefoxOptions);
		
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
	
		proxy.newHar("agol");

		driver.get("https://devext.arcgis.com/index.html");

		// internal login
		driver.findElement(By.id("j_username")).sendKeys("sharing");
		driver.findElement(By.id("j_password")).sendKeys("ago.test");
		Thread.sleep(1000);
		driver.findElement(By.id("submit")).click();
		Thread.sleep(5000);
		
		Har har1 = proxy.getHar();
	    File harFile1 = new File("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\HAR files\\login.har");
	    harFile1.createNewFile();
	    har1.writeTo(harFile1);

		// AGOL devext login
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Sign In")).click();
		//supportFunctions.waitForElementLinkText("Sign In");
		//supportFunctions.waitForElementID("user_username");
		driver.findElement(By.id("user_username")).sendKeys("haoyan_dev");
		driver.findElement(By.id("user_password")).sendKeys("TESTtest1");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(10000);
		
	    Har har2 = proxy.getHar();
	    File harFile2 = new File("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\HAR files\\agol_login.har");
	    harFile2.createNewFile();
	    har2.writeTo(harFile2);
	}
}
