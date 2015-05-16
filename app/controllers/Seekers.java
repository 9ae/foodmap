package controllers;

import models.Seeker;
import models.UserApp;

import com.google.gson.JsonObject;

import play.mvc.*;
import services.YelpAPI;

public class Seekers extends JsonController {
	
	private static UserApp authenticate(){
		String auth_token;
		
		if(request.method=="GET"|| request.method=="DELETE"){
			auth_token = request.params.get("auth_token");
		} else {
			JsonObject jBody = getJsonBody().getAsJsonObject();
			auth_token = jBody.get("auth_token").getAsString();
		}
		if (auth_token==null){
			return null;
		} else {
			return UserApp.find("authToken=?", auth_token).first();
		}
	}

    public static void start(String city, String lat, String lon) {
    	UserApp user = authenticate();
    	if(user==null){
    		renderText("Invalid user!");
    		return;
    	}
    	
    	//TODO: check invalid parameters
    	
    	Seeker seeker = new Seeker(user, city, lat, lon);
    	seeker.save();
    	
		seeker.proccess();
    	
    	JsonObject jay = new JsonObject();
		jay.addProperty("id", seeker.id);
		renderElement(jay);
    	/*
    	YelpAPI yelp = new YelpAPI();
    	String result = yelp.searchForFoodByCoordinates(city, lat+","+lon);
    	renderJSON(result); */
    }

}
