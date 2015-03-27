package controllers;

import play.mvc.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonController extends Controller {
	
	protected static JsonElement getJsonBody(){
		String body_string = request.params.get("body");
		return (new JsonParser()).parse(body_string);
	}

	protected static void renderElement(JsonElement element){
		Gson gson = new Gson();
		renderJSON(gson.toJson(element));
	}

}
