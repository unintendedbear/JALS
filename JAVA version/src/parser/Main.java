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

/**
 * @author Paloma de las Cuevas Delgado
 *
 */

package parser;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import parser.ScrappingUtils;

public class Main {

	public static void main(String[] args) {
		String urlString = "https://www.port-monitor.com/plans-and-pricing";
		ScrappingUtils scraper = new ScrappingUtils();
		
		System.out.println("JALS is now parsing https://www.port-monitor.com/plans-and-pricing");
		Document URLDoc = scraper.getHTMLFromURL(urlString);
		Elements URLElements = scraper.getProducts(URLDoc);
		System.out.println("Your results:");
		printJSONArray(scraper.getProductsInJSONArray(URLElements));
		System.out.println("Thanks for using JALS!");
		
	}
	
	public static void printJSONArray (String JSONArray) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(JSONArray);
			JSONArray products = (JSONArray) obj;
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> JSONIterator = products.iterator();
			System.out.println("[");
			while (JSONIterator.hasNext()) {
				JSONObject currentObject = JSONIterator.next();
				System.out.println(currentObject.toString());
			}
			System.out.println("]");
		} catch (ParseException e) {
			System.out.println("Failed to parse JSON.");
			e.printStackTrace();
		}
	}

}