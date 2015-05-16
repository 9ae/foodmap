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
    
    public static void search(String city, String lat, String lon){ 	
    	YelpAPI yelp = new YelpAPI();
    	String result = yelp.searchForFoodByCoordinates(city, lat+","+lon);
    	renderJSON(result);
    }
    
    

}