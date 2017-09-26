package parser;
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

import java.io.IOException;

import org.jsoup.Jsoup;

/**
 * @author Paloma de las Cuevas Delgado
 *
 */

import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ScrappingUtils {

	public Document getHTMLFromURL(String url) {
		Document URLDocument = null;
		try {
			URLDocument = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Failed to retrieve the webpage.");
			e.printStackTrace();
		}
		
		return URLDocument;
	}

	public Elements getProducts(Document document) {
		Elements productList = document.select("div.product");
		
		return productList;
	}

	public String getProductsInJSONArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
