/**
 * Test case 3
 * 
 * This test case include updating an item's extent and check if the spatial search results differ.
 * It is done with in a group, so other user's modifications might alter the search results
 * The original extent was set to Japan, updated extent is Sapporo
 * 
 * Result: No difference in search result order, output screenshots of search results with item highlighted
 */

package testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import SpatialSearch.SpatialSearch.supportFunctions;

public class test3 {
	
	@BeforeTest (alwaysRun = true)
	public void openPage() throws InterruptedException {
		supportFunctions.browserSetup();
		supportFunctions.login("haoyan_dev","TESTtest1");
		supportFunctions.openContent();
		supportFunctions.openFolder("Test");
		supportFunctions.openGroup("location_test");
		Thread.sleep(3000);
		supportFunctions.locationSearch("Sapporo");
		Thread.sleep(2000);
		supportFunctions.screenshotSearchResultsWithHighlight("test3_pre_edit_group","Japan_Jap");
	}
	
	@Test (priority=1, description="Change a larger extent to a smaller one")
	public void editExtent() throws InterruptedException {
		supportFunctions.openItemExtent("Japan_Jap");
		supportFunctions.editExtent("Sapporo");
		Thread.sleep(3000);
		supportFunctions.screenshotExtent("Japan_Jap");
	}
	
	
	@Test (priority=2, description="Check if search result has been updated")
	public void checkSearchResult() throws InterruptedException {
		  supportFunctions.openContent(); 
		  supportFunctions.openGroup("location_test");
		  Thread.sleep(3000);
		  
		  // Change here for different search results
		  supportFunctions.locationSearch("Sapporo"); 
		  Thread.sleep(2000);
		  supportFunctions.screenshotSearchResultsWithHighlight("test3_post_edit_group","Japan_Jap"); 
	}
	 
	
	@Test (priority=2, description="Change extent back to original")
	public void restoreExtent() throws InterruptedException {
		supportFunctions.openItemExtent("Japan_Jap");
		supportFunctions.editExtent("Japan");
		Thread.sleep(3000);
		supportFunctions.screenshotExtent("Japan_Jap");
	}
	
	@AfterTest
	public void teardown() {
		supportFunctions.driver.quit();
	}
}