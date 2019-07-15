package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class test7 {
	
	public WebDriver driver;
	
	@Parameters("browserName")
	@BeforeClass
	public void launchBrowser(String browserName) {
		
		if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver","C:\\Users\\hao10315\\Desktop\\Selenium\\geckodriver-v0.24.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\hao10315\\Desktop\\Selenium\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\IEDriverServer_x64_3.14.0\\IEDriverServer.exe");
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\edgedriver_win64\\msedgedriver.exe");
			driver = new ChromeDriver();
		}
	}

	@Test
	public void test1() {
		driver.manage().window().maximize();
		driver.get("https://google.com");
		driver.findElement(By.name("q")).sendKeys("Esri");
	}
}
