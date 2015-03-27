package controllers;

import play.*;
import play.mvc.*;
import services.YelpAPI;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void testYelp(){
    	YelpAPI yelp = new YelpAPI();
    	String result = yelp.searchForBusinessesByLocation("ramen", "New York, NY");
    	renderJSON(result);
    }

}