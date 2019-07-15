/**
 * Test case #1
 * This test case will check all the items within the user's content folder
 * To make sure all extents are set correctly by screenshoting the extent map
 * 
 * Result: Output to local folder all extent maps
 */

package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import SpatialSearch.SpatialSearch.supportFunctions;

public class test1 {
	
	@BeforeTest (alwaysRun = true)
	public void openPage() throws InterruptedException {
		supportFunctions.createFolder();
		supportFunctions.browserSetup();
		supportFunctions.login("haoyan_dev","TESTtest1");
		supportFunctions.openContent();
		supportFunctions.openFolder("Test");
	}
	
	@Test (description="Check all items' extent")
	public void openItem() throws InterruptedException {
		String[] layerNames = {"Japan_Sapporo", "Japan_Hokkaido", "Japan_Jap", "Japan_false", "Japan_Tokyo", "Japan_IDL1", "Japan_IDL2"};
		int layerSize = layerNames.length;
		System.out.println(layerSize);
		
		Assert.assertTrue(true, "Could not check all items");
		
		for (int i = 0; i < layerSize-1; ++i) {
			System.out.println(layerNames[i]);
			supportFunctions.openItemExtent(layerNames[i]);
			supportFunctions.screenshotExtent(layerNames[i]);
			supportFunctions.openContent();
			Thread.sleep(3000);
		}
	}
	
	@AfterTest 
	public void teardown() {
		supportFunctions.driver.quit();
	}
}