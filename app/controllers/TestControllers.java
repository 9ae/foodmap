package controllers;

import org.w3c.dom.Document;

import play.libs.WS;
import play.libs.XML;
import play.libs.WS.HttpResponse;
import play.mvc.*;
import services.WebpageValidators;
import services.YelpAPI;

public class TestControllers extends Controller {
	
    public static void testYelp(){
    	YelpAPI yelp = new YelpAPI();
    	String result = yelp.searchForBusinessesByLocation("ramen", "New York, NY");
    	renderJSON(result);
    }

    public static void yelpMenuValidator() {
		if(WebpageValidators.soupValidateYelpMenu("http://www.yelp.com/menu/totto-ramen-new-york")){
			renderText("true");
		} else {
			renderText("false");
		}
    }

}
