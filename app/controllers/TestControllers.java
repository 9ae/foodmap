package controllers;

import org.w3c.dom.Document;

import play.libs.WS;
import play.libs.XML;
import play.libs.WS.HttpResponse;
import play.mvc.*;
import services.YelpAPI;

public class TestControllers extends Controller {
	
    public static void testYelp(){
    	YelpAPI yelp = new YelpAPI();
    	String result = yelp.searchForBusinessesByLocation("ramen", "New York, NY");
    	renderJSON(result);
    }

}
