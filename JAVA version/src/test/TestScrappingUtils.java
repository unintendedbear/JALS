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
import org.jsoup.select.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Iterator;

import parser.ScrappingUtils;

/**
 * @author Paloma de las Cuevas Delgado
 *
 */
public class TestScrappingUtils {

	private static String urlString = "https://www.port-monitor.com/plans-and-pricing";
	static ScrappingUtils scraper = new ScrappingUtils(urlString);

	@Test
	public void testWhenConnectThenResultContainsHTMLTags() {		
		scraper.getHTMLFromURL();
		String currentHTML = scraper.getDocument().toString();
		currentHTML = currentHTML.substring(0, 30);
		
		assertTrue(currentHTML.matches("^<!?d?o?c?t?y?p?e?\\s?html>\n.*"));
	}
	
	@Test
	public void testWhenObtainingProductsElementsNotEmpty() {		
		scraper.getHTMLFromURL();
		scraper.getProducts();
		Elements productList = scraper.getList();
		
		assertTrue(productList.size() > 0);
		
	}
	
	@Test
	public void testWhenParsingProductThenGetExpectedJSON() {
		scraper.getHTMLFromURL();
		scraper.getProducts();
		String product = scraper.getProductsInJSONArray();
		//String expectedProductJSON = "{monitors: 10, check_rate: 60, history: 12, multiple_notifications: true, push_notifications: true, price: 4.54}";
		
		JSONParser parser = new JSONParser();
		try {
			Object tempProductList = parser.parse(product);
			JSONArray currentProductListJSON = (JSONArray) tempProductList;
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> JSONIterator = currentProductListJSON.iterator();
			while (JSONIterator.hasNext()) {
				JSONObject currentProduct = JSONIterator.next();
				assertTrue(currentProduct.containsKey("monitors"));
				assertTrue(currentProduct.containsKey("check_rate"));
				assertTrue(currentProduct.containsKey("history"));
				assertTrue(currentProduct.containsKey("multiple_notifications"));
				assertTrue(currentProduct.containsKey("push_notifications"));
				assertTrue(currentProduct.containsKey("price"));
			}
		} catch (ParseException e) {
			fail("Failed to parse JSON.");
			e.printStackTrace();
		}
	}

}
