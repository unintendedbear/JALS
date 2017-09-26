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
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import parser.ScrappingUtils;

/**
 * @author Paloma de las Cuevas Delgado
 *
 */
public class TestScrappingUtils {

	@Test
	public void testWhenConnectThenResultContainsHTMLTags() {
		ScrappingUtils scraper = new ScrappingUtils();
		String urlString = "https://www.port-monitor.com/plans-and-pricing";
		String currentHTML = scraper.getHTMLFromURL(urlString);
		currentHTML = currentHTML.substring(0, 30);
		
		assertTrue(currentHTML.matches("^<!?d?o?c?t?y?p?e?\\s?html>\n.*"));
	}

}
