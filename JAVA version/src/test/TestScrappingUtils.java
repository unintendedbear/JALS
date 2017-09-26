/** This file is part of JALS ~ Just a little scraper.

    JALS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    JALS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JALS. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import parser.ScrappingUtils;

/**
 * @author Paloma de las Cuevas Delgado
 *
 */
public class TestScrappingUtils {

	static ScrappingUtils scraper = new ScrappingUtils();
	private static String urlString = "https://www.port-monitor.com/plans-and-pricing";

	@Test
	public void testWhenConnectThenResultContainsHTMLTags() {		
		String currentHTML = scraper.getHTMLFromURL(urlString).toString();
		currentHTML = currentHTML.substring(0, 30);
		
		assertTrue(currentHTML.matches("^<!?d?o?c?t?y?p?e?\\s?html>\n.*"));
	}
	
	@Test
	public void testWhenObtainingProductsElementsNotEmpty() {		
		Document URLDocument = scraper.getHTMLFromURL(urlString);
		Elements productList = scraper.getProducts(URLDocument);
		
		assertTrue(productList.size() > 0);
		
	}
	
	@Test
	public void testWhenParsingProductThenGetExpectedJSON() {
		String product = scraper.getProductsInJSONArray();
		//String expectedProductJSON = "{monitors: 10, check_rate: 60, history: 12, multiple_notifications: true, push_notifications: true, price: 4.54}";
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(product);
		} catch (ParseException e) {
			System.out.println("Failed to parse JSON.");
			e.printStackTrace();
		}
		JSONObject currentProductJSON = (JSONObject) obj;
		assertTrue(currentProductJSON.containsKey(("monitors")));
		assertTrue(currentProductJSON.containsKey(("check_rate")));
		assertTrue(currentProductJSON.containsKey(("history")));
		assertTrue(currentProductJSON.containsKey(("multiple_notifications")));
		assertTrue(currentProductJSON.containsKey(("push_notifications")));
		assertTrue(currentProductJSON.containsKey(("price")));
	}

}
