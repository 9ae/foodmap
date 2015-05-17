package services;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.select.Elements;

public class WebpageValidators {
	
	public static boolean soupValidateYelpMenu(String url){
		String selector = "div.menu-section > div.menu-item:not(.menu-item-placeholder-photo)";
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements photos = doc.select(selector);
			return photos.size() > 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
