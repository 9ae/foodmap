package services;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.select.Elements;

public class WebpageReaders {

	public static Elements soupGetYelpMenuItems(String url){
		String selector = "div.menu-section > div.menu-item:not(.menu-item-placeholder-photo)";
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements menuItems = doc.select(selector);
			if(menuItems.size() > 0){
				return null;
			} else {
				return menuItems;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
}
