package controllers;

import jobs.SeekerJob;
import models.Seeker;
import models.UserApp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.mvc.*;
import services.YelpAPI;

public class Seekers extends JsonController {
	
	private static UserApp checkUser(){
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

    public static void start(String city, String lat, String lon, String tag) {
    	UserApp user = checkUser();
    	if(user==null){
    		renderText("Invalid user!");
    		return;
    	}
    	
    	//TODO: check invalid parameters
    	
    	Seeker seeker = new Seeker(user, city, lat, lon);
    	if(tag!=null && !tag.isEmpty()){
    		seeker.tag = tag;
    	}
    	seeker.save();
    	
    	new SeekerJob(seeker).now();
    	
    	JsonObject jay = new JsonObject();
		jay.addProperty("id", seeker.id);
		renderElement(jay);
    }
    
    public static void check(String seeker_id){
    	UserApp user = checkUser();
    	if(user==null){
    		renderText("Invalid user!");
    		return;
    	}
    	
    	Seeker seeker = Seeker.findById(Long.parseLong(seeker_id));
    	if (seeker.created_by!=user){
    		renderText("You don't have permission to this request!");
    		return;
    	}
    	
    	JsonObject obj = new JsonObject();
    	obj.add("results", seeker.getResults());
    	renderElement(obj);
    }

}
