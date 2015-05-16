package services;
/*
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Document; */

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.select.Elements;

public class WebpageValidators {

	/*
	public static boolean validateYelpMenu(Document doc){
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			XPathExpression expr =
			        xpath.compile("//img[@class='photo-box-img']");
			double images = (double) expr.evaluate(doc, XPathConstants.NUMBER);
			
			return images > 0;
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	} */
	
	public static boolean soupValidateYelpMenu(String url){
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements photos = doc.select("img.photo-box-img");
			return photos.size() > 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
