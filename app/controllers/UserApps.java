package controllers;

import models.UserApp;

import com.google.gson.JsonObject;

import play.mvc.*;

public class UserApps extends JsonController {

    public static void register() {
    	JsonObject jBody = getJsonBody().getAsJsonObject();
    	String aii = jBody.get("aii").getAsString();
    	
    	//TODO: check is unique
    	UserApp app = new UserApp(aii);
    	app.save();
    	
    	
    	JsonObject responseBody = new JsonObject();
    	responseBody.addProperty("auth_token", app.authToken);
    	
        renderElement(responseBody);
    }

}
