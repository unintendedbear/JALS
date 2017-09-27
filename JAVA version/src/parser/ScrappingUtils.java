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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrappingUtils {
	
	String url;
	Document document;
	Elements list;
	
	public ScrappingUtils(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Elements getList() {
		return list;
	}

	public void setList(Elements list) {
		this.list = list;
	}
	
	public void getHTMLFromURL() {
		Document URLDocument = null;
		try {
			URLDocument = Jsoup.connect(this.url).get();
		} catch (IOException e) {
			System.out.println("Failed to retrieve the webpage.");
			e.printStackTrace();
		}
		
		this.document = URLDocument;
	}

	public void getProducts() {
		Elements productList = this.document.select("div.product");
		
		this.list = productList;
	}

	@SuppressWarnings("unchecked")
	public String getProductsInJSONArray() {
		Elements productElements = this.list;
		JSONObject productJSON = new JSONObject();
		JSONArray productListJSON = new JSONArray();
		String numberInTextRegExp = "\\w*\\s?(\\d{2})\\s\\w+";
		String yesOrNoRegExp = "(Yes|No)";
		String priceRegExp = "\\$([\\d\\.]+)[\\s/\\w]+";
		Pattern numberInTextPattern = Pattern.compile(numberInTextRegExp);
		Pattern yesOrNoPattern = Pattern.compile(yesOrNoRegExp);
		Pattern pricePattern = Pattern.compile(priceRegExp);
		
		for (Element product: productElements) {
			
			productJSON.put("monitors", product.select("h2").text());

			Elements ddElements = product.getElementsByTag("dd");
			Element ddCurrent = ddElements.first();
			
			getCheckRate(productJSON, numberInTextPattern, ddCurrent);
			
			ddCurrent = ddCurrent.nextElementSibling().nextElementSibling();
			getHistory(productJSON, numberInTextPattern, ddCurrent);
			
			ddCurrent = ddCurrent.nextElementSibling().nextElementSibling();
			getMultipleNotifications(productJSON, yesOrNoPattern, ddCurrent);
			
			ddCurrent = ddCurrent.nextElementSibling().nextElementSibling();
			getPushNotifications(productJSON, yesOrNoPattern, ddCurrent);
			
			getPrice(productJSON, pricePattern, product);
			
			productListJSON.add(productJSON);
		}
		
		return productListJSON.toJSONString();
	}

	@SuppressWarnings("unchecked")
	private JSONObject getPrice(JSONObject productJSON, Pattern pricePattern, Element product) {
		Matcher priceMatcher = pricePattern.matcher(product.getElementsByAttributeValueContaining("class", "btn").text());
		if (priceMatcher.find()) {
			productJSON.put("price", Float.parseFloat(priceMatcher.group(1)));
		} else {
			productJSON.put("price", "-");
		}
		
		return productJSON;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getPushNotifications(JSONObject productJSON, Pattern yesOrNoPattern, Element ddCurrent) {
		Matcher yesOrNoMatcher = yesOrNoPattern.matcher(ddCurrent.text());
		if (yesOrNoMatcher.find()) {
			productJSON.put("push_notifications", yesOrNoMatcher.group(1));
		} else {
			productJSON.put("push_notifications", "-");
		}
		
		return productJSON;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getMultipleNotifications(JSONObject productJSON, Pattern yesOrNoPattern, Element ddCurrent) {
		Matcher yesOrNoMatcher = yesOrNoPattern.matcher(ddCurrent.text());
		if (yesOrNoMatcher.find()) {
			productJSON.put("multiple_notifications", yesOrNoMatcher.group(1));
		} else {
			productJSON.put("multiple_notifications", "-");
		}
		
		return productJSON;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getHistory(JSONObject productJSON, Pattern numberInTextPattern, Element ddCurrent) {
		Matcher numberInTextMatcher;
		numberInTextMatcher = numberInTextPattern.matcher(ddCurrent.text());
		if (numberInTextMatcher.find()) {
			productJSON.put("history", Integer.parseInt(numberInTextMatcher.group(1)));
		} else {
			productJSON.put("history", "-");
		}
		
		return productJSON;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getCheckRate(JSONObject productJSON, Pattern numberInTextPattern, Element ddCurrent) {
		Matcher numberInTextMatcher = numberInTextPattern.matcher(ddCurrent.text());
		if (numberInTextMatcher.find()) {
			productJSON.put("check_rate", Integer.parseInt(numberInTextMatcher.group(1)));
		} else {
			productJSON.put("check_rate", "-");
		}
		
		return productJSON;
	}

}
