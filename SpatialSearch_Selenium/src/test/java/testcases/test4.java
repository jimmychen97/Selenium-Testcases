/**
 * Test case 4
 * 
 * This test cases will have a second user upload an item with the same extent
 * To check if the new item show up in the location search results along with the previously added ones
 */

package testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.assertthat.selenium_shutterbug.core.Shutterbug;

import SpatialSearch.SpatialSearch.supportFunctions;

public class test4 {
	@BeforeTest (alwaysRun = true)
	public void openPage() throws InterruptedException {
		supportFunctions.browserSetup();
		supportFunctions.login("haoyan_dev2","TESTtest1");
		supportFunctions.openContent();
		supportFunctions.openGroup("location_test");
		Thread.sleep(3000);
		supportFunctions.locationSearch("Sapporo");
		Thread.sleep(2000);
		Shutterbug.shootPage(supportFunctions.driver, true).withName("SearchResult_test4_group_user").save("C:\\Users\\hao10315\\eclipse-workspace\\SpatialSearch\\SearchScreenshots");	
	}
	
	@Test (priority=1, description= "Delete old item & upload new item from computer")
	public void test1() throws InterruptedException {
		supportFunctions.openContent();
		supportFunctions.searchForItem("climate_Sapporo");
		supportFunctions.deleteItem();
		Thread.sleep(15000);
		supportFunctions.uploadItem("\\\\esri.com\\dev\\agol\\TestFiles\\SHP\\polygon\\climate.zip","climate_Sapporo", "test");
		Thread.sleep(25000);
	}
	
	@Test (priority=2, description= "Set extent to Sapporo and perform location search")
	public void test2() throws InterruptedException {
		supportFunctions.openContent();
		supportFunctions.openItemExtent("climate_Sapporo");
		supportFunctions.editExtent("Sapporo");
		Thread.sleep(3000);
		supportFunctions.screenshotExtent("climate_Sapporo");
	}
	
	@Test (priority=3, description="Check if search result has been updated")
	public void test3() throws InterruptedException {
		supportFunctions.openContent();
		Thread.sleep(3000);
		
		// Change here for different search results
		supportFunctions.locationSearch("Sapporo");
		Thread.sleep(2000);
		supportFunctions.screenshotSearchResultsWithHighlight("test4_climate_Sapporo", "climate_Sapporo");
	}
	
	@Test (priority=4, description="Share to location_test group")
	public void test4() throws InterruptedException {
		supportFunctions.openContent();
		Thread.sleep(5000);
		supportFunctions.openContent();
		supportFunctions.shareToGroup("climate_Sapporo", "Energy");
		supportFunctions.openContent();
		supportFunctions.openGroup("location_test");
		supportFunctions.locationSearch("Sapporo");
		Thread.sleep(2000);
		supportFunctions.screenshotSearchResultsWithHighlight("test4_climate_Sapporo", "climate_Sapporo");
	}
	
	@AfterTest
	public void teardown() {
		supportFunctions.driver.quit();
	}
}
