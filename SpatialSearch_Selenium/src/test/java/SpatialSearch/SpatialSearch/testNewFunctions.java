package SpatialSearch.SpatialSearch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testNewFunctions {
	
	@BeforeTest (alwaysRun = true)
	public void openPage() throws InterruptedException {
		supportFunctions.browserSetup();
		supportFunctions.login("haoyan_dev","TESTtest1");
		supportFunctions.openContent();
		supportFunctions.openFolder("Test");
	}
	
	@Test
	public void editExtent() throws InterruptedException {
		Thread.sleep(3000);
		List<WebElement> elementsRoot = supportFunctions.driver.findElements(By.xpath("//div[@class=\'table-select-rows js-table-rows js-view\']/div"));
		int itemSize = elementsRoot.size();
		System.out.println("Item size is: "+itemSize);
		for (int i = 0; i < itemSize; ++i) {
			WebElement item = elementsRoot.get(i).findElement(By.xpath("//div[starts-with(@id, 'dijit__TemplatedMixin')]/span[1]/span/span/a"));
			item.click();
			Thread.sleep(3000);
			supportFunctions.openItemExtent_Setting();
			supportFunctions.screenshotExtent_Setting();
			supportFunctions.openContent();
			supportFunctions.openFolder("Test");
			Thread.sleep(3000);
		}
	}
}
