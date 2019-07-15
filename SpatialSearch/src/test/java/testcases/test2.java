/**
 * Test case 2
 * 
 * This test case include updating an item's extent in the user's local content folder
 * The originial extent was set to Shanghai, updated extent is Redlands
 * Layer is located in Sapporo but both pre and post extents are incorrect, testing whether if elasticsearch is working correctly
 *
 * Result: Item showed up with new extent search
 */

package testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import SpatialSearch.SpatialSearch.supportFunctions;

public class test2 {

	@BeforeTest (alwaysRun = true)
	public void openPage() throws InterruptedException {
		supportFunctions.browserSetup();
		supportFunctions.login("haoyan_dev","TESTtest1");
		supportFunctions.openContent();
		supportFunctions.openFolder("Test");
		Thread.sleep(3000);
		supportFunctions.locationSearch("Redlands");
		Thread.sleep(2000);
		supportFunctions.screenshotSearchResults("test2_pre_edit");
	}
	
	@Test (priority=1, description="Set new extent for item & search (In groups)")
	public void editExtent() throws InterruptedException {
		supportFunctions.openContent();
		supportFunctions.openFolder("Test");
		supportFunctions.openItemExtent("Japan_false");
		supportFunctions.editExtent("Redlands");
		Thread.sleep(3000);
		supportFunctions.screenshotExtent("Japan_false");
	}
	
	@Test (priority=2, description="Check if search result has been updated")
	public void checkSearchResult() throws InterruptedException {
		supportFunctions.openContent();
		Thread.sleep(3000);
		
		// Change here for different search results
		supportFunctions.locationSearch("Redlands");
		Thread.sleep(2000);
		supportFunctions.screenshotSearchResultsWithHighlight("test2_post_edit", "Japan_false");
	}
	
	@Test (priority=3, description="Change extent back to original")
	public void restoreExtent() throws InterruptedException {
		supportFunctions.openItemExtent("Japan_false");
		supportFunctions.editExtent("Shanghai");
		Thread.sleep(3000);
		supportFunctions.screenshotExtent("Japan_false");
	}
	
	@AfterTest
	public void teardown() {
		supportFunctions.driver.quit();
	}
}