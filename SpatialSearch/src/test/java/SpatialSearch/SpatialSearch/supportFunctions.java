package SpatialSearch.SpatialSearch;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class supportFunctions {

	public static WebDriver driver;
	static String[] baseUrl = { "https://devext.arcgis.com/index.html", "http://qaext.arcgis.com",
			"http://arcgis.com" };

	// print_date gets current date and time
	static DateFormat dateFormat = new SimpleDateFormat("MMddyyhhmmss");
	static DateFormat folderDateFormat = new SimpleDateFormat("MMdd");
	// static Date date = new Date();
	static String print_date = dateFormat.format(new Date());
	static String folderDate = folderDateFormat.format(new Date());

	
	// Import webdriver and open browser
	public static void browserSetup() {
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	// Setting up proxy to monitor network traffic
	public static void proxyBrowserSetup() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start();
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability(CapabilityType.PROXY, seleniumProxy);

		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\hao10315\\Desktop\\Selenium\\webdrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver(firefoxOptions);

		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);

		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

		proxy.newHar("agol");
	}
	
	// Get proxy server running
	public BrowserMobProxy getProxyServer() {
	     BrowserMobProxy proxy = new BrowserMobProxyServer();
	     proxy.setTrustAllServers(true); 
	     proxy.start();
	     return proxy;
	}

	public static void createFolder() {
		File dir = new File(folderDate + "_screenshots");
		dir.mkdir();
	}

	// Wait for an element to load (with ID)
	public static void waitForElementID(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator))).click();
	}

	// Wait for an element to load (with LinkText)
	public static void waitForElementLinkText(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locator))).click();
	}

	// Login with given username and password (Different orgs)
	public static void login(String username, String password) throws InterruptedException {

		// change here for dexext, QA and prod testing
		driver.get("https://devext.arcgis.com/index.html");

		// internal login
		driver.findElement(By.id("j_username")).sendKeys("sharing");
		driver.findElement(By.id("j_password")).sendKeys("ago.test");
		driver.findElement(By.id("submit")).click();

		// AGOL devext login
		// driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		waitForElementLinkText("Sign In");
		waitForElementID("user_username");
		driver.findElement(By.id("user_username")).sendKeys(username);
		driver.findElement(By.id("user_password")).sendKeys(password);
		driver.findElement(By.id("signIn")).click();
	}

	public static void checkItemNumber() {
		List<WebElement> elementsRoot = driver
				.findElements(By.xpath("//div[@class='table-select-rows js-table-rows js-view']"));

		for (int i = 0; i < elementsRoot.size(); ++i) {

		}
	}

	// Open content page (Anywhere 'Content' tab is available)
	public static void openContent() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.linkText("Content")).click();
		Thread.sleep(1000);
	}

	// Open folder with given folder name
	public static void openFolder(String folderName) {
		driver.findElement(By.linkText(folderName)).click();
	}

	// Open group with given group name
	public static void openGroup(String groupName) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.linkText("My Groups")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText(groupName)).click();
		Thread.sleep(1000);
	}

	public static void searchForItem(String itemName) {
		driver.findElement(By.id("dijit__TemplatedMixin_2")).sendKeys(itemName, Keys.ENTER);
	}

	public static void deleteItem() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath(
				"/html/body/div[3]/div/div[2]/main/div/section/div/main/div[3]/div[1]/div[1]/div/span[1]/label/input"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/main/div/section/div/main/div[2]/nav/button[5]"))
				.click();
		Thread.sleep(3000);
		driver.findElement(By.id("button_delete-warning-submit_label")).click();
	}

	// Share to group with given name
	public static void shareToGroup(String itemName, String groupName) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.linkText(itemName)).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/div/main/aside/div/button[11]")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("group_2_div")).click();
		driver.findElement(By.id("dijit_form_Button_0_label")).click();
	}

	// Add new item from the computer
	public static void uploadItem(String filePath, String title, String tag) throws InterruptedException {
		// Select file from computer
		driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/main/div/div/div/div/div[1]/div/div[1]/button"))
				.click();
		driver.findElement(
				By.xpath("/html/body/div[3]/div/div[2]/main/div/div/div/div/div[1]/div/div[1]/nav/button[1]")).click();

		Thread.sleep(5000);
		WebElement choose_file = driver.findElement(By.id("file"));
		choose_file.sendKeys(filePath);

		// Add title
		WebElement titleElem = driver.findElement(By.id("title"));
		titleElem.clear();
		titleElem.click();
		titleElem.sendKeys(title);
		Thread.sleep(2000);

		// Add tag
		WebElement tags = driver.findElement(By.id("tags"));
		tags.click();
		Actions action = new Actions(driver);
		action.sendKeys(tag).build().perform();
		action.sendKeys(Keys.RETURN).build().perform();
		driver.findElement(By.id("dijit_Dialog_0")).click();
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		driver.findElement(By.id("addItem-btn_label")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Open item's extent page with given item name
	// If the there's a location search bar open, close it then find the item
	public static void openItemExtent(String itemName) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.linkText(itemName)).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Settings")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("setExtent")).click();
		Thread.sleep(10000);
		int i = driver.findElements(By.xpath("//iframe")).size();
		driver.switchTo().frame(i - 1);
	}

	public static void openItemExtent_Setting() throws InterruptedException {
		driver.findElement(By.linkText("Settings")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("setExtent")).click();
		Thread.sleep(10000);
		int i = driver.findElements(By.xpath("//iframe")).size();
		driver.switchTo().frame(i - 1);
	}

	// Update/edit current extent with given new extent
	public static void editExtent(String newExtent) throws InterruptedException {
		driver.findElement(By.xpath("/html/body/div[2]/aside/main/div[1]/section[2]/div/div/div[3]/form/input"))
				.sendKeys(newExtent);
		Actions action = new Actions(driver);
		action.sendKeys(Keys.RETURN).build().perform();
		Thread.sleep(2000);
	}

	// use coordinates to update map's extent
	public static void editExtentWithCoord(double top_latitude, double bot_latitude, double left_longitude,
			double right_longitude) throws InterruptedException {
		driver.findElement(By.id("advancedHeader")).click();
		Thread.sleep(1000);

		WebElement topLatitude = driver.findElement(By.id("extentPickerNorth"));
		WebElement botLatitude = driver.findElement(By.id("extentPickerSouth"));
		WebElement leftLongitude = driver.findElement(By.id("extentPickerWest"));
		WebElement rightLongitude = driver.findElement(By.id("extentPickerEast"));

		topLatitude.clear();
		topLatitude.sendKeys(String.valueOf(top_latitude), Keys.TAB);
		Thread.sleep(1000);
		botLatitude.sendKeys(String.valueOf(bot_latitude), Keys.TAB);
		Thread.sleep(1000);
		leftLongitude.sendKeys(String.valueOf(left_longitude), Keys.TAB);
		Thread.sleep(1000);
		rightLongitude.sendKeys(String.valueOf(right_longitude));

		Actions action = new Actions(driver);
		// action.sendKeys(Keys.TAB).build().perform();
		// action.sendKeys(Keys.TAB).build().perform();
		action.sendKeys(Keys.RETURN).build().perform();
		Thread.sleep(2000);

		// driver.findElement(By.linkText("Save")).click();
	}

	// Spatial elasticsearch with given location
	public static void locationSearch(String location) throws InterruptedException {
		Actions action = new Actions(supportFunctions.driver);
		supportFunctions.driver.findElement(By
				.xpath("/html/body/div[3]/div/div[2]/main/div/section/div/div/div[2]/div[2]/div/div/div/section[3]/h4"))
				.click();
		Thread.sleep(2000);
		supportFunctions.driver.findElement(By.id("filterBBox_input")).click();

		action.sendKeys(location);
		action.sendKeys(Keys.RETURN).build().perform();
		supportFunctions.driver.findElement(By.id("main-content-area")).click();
		Thread.sleep(2000);
		action.sendKeys(Keys.PAGE_UP).build().perform();
	}

	// Take a screenshot if the extent map
	public static void screenshotExtent(String itemName) {
		WebElement extent = driver.findElement(By.id("map-container"));
		Shutterbug.shootElement(driver, extent, true).withName(itemName + "_extent_" + dateFormat.format(new Date()))
				.save("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\" + folderDate + "_screenshots");
		driver.findElement(By.xpath("/html/body/div[2]/aside/main/div[3]/div/button[1]")).click();
		driver.switchTo().defaultContent();
	}

	public static void screenshotExtent_Setting() {
		WebElement extent = driver.findElement(By.id("map-container"));
		Shutterbug.shootElement(driver, extent, true).withName("extent_" + dateFormat.format(new Date()))
				.save("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\" + folderDate + "_screenshots");
		driver.findElement(By.xpath("/html/body/div[2]/aside/main/div[3]/div/button[1]")).click();
		driver.switchTo().defaultContent();
	}

	// Take a screenshot of the search results with certain element highlighted
	public static void screenshotSearchResultsWithHighlight(String screenshotName, String searchElement) {
		WebElement searchResult = driver.findElement(By.linkText(searchElement));
		Shutterbug.shootPage(driver, true).highlight(searchResult)
				.withName("SearchResult_" + screenshotName + "_" + dateFormat.format(new Date()))
				.save("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\SearchScreenshots");
	}

	// Take a screenshot of the search results with no highlight
	public static void screenshotSearchResults(String screenshotName) {
		Shutterbug.shootPage(driver, true)
				.withName("SearchResult_" + screenshotName + "_" + dateFormat.format(new Date()))
				.save("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\SearchScreenshots");
	}
}