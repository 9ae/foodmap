package jobs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Image;
import models.Seeker;
import models.SeekerResult;
import models.Venue;
import play.jobs.Job;
import services.YelpAPI;
import services.Providers;

public class SeekerJob extends Job {
	
	private Seeker seeker;
	
	public SeekerJob(Seeker seeker){
		this.seeker = seeker;
	}
	
	public void doJob(){
		// Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		
		YelpAPI yelp = new YelpAPI();
		int pageLimit = yelp.getPageSize();
		
    	String yelpVenueSearchResponse = yelp.searchForFoodByCoordinates(seeker.city, seeker.coords, seeker.tag);
    
    	JsonObject yelpVenuesObject = jsonParser.parse(yelpVenueSearchResponse).getAsJsonObject();
    	int totalYelpVenues = yelpVenuesObject.get("total").getAsInt();
    	
    	JsonArray yelpVenues = yelpVenuesObject.get("businesses").getAsJsonArray();
    	for(JsonElement e: yelpVenues){
    		JsonObject venueObject = e.getAsJsonObject();
    		String yelpId = venueObject.get("id").getAsString();
    		Venue venue = Venue.findByProviderId(Providers.YELP, yelpId);
    		if(venue==null){
    			String venueName = venueObject.get("name").getAsString();
    			Double lon=0.0;
    			Double lat=0.0;
    			if(venueObject.has("location")){
    				JsonObject location = venueObject.get("location").getAsJsonObject();
    				if(location.has("coordinate")){
		    			JsonObject coords = location.get("coordinate").getAsJsonObject();
		    			lat = coords.get("latitude").getAsDouble();
		    			lon = coords.get("longitude").getAsDouble();
    				}
    			}
    			
    			System.out.println("Found new venue: "+venueName);
    			new RegisterVenueJob(Providers.YELP, this.seeker, yelpId, venueName,
    					lat, lon).now();
    			
    		} else {
    			Image img = venue.getBestPictureOfTag(seeker.tag);
    			if(img!=null){
	    			SeekerResult sr = new SeekerResult(seeker, img);
	    			sr.save();
	    			System.out.println("Exisiting venue, has relevant photo");
    			} else {
    				System.out.println("Exisiting venue, NO relevant photo");
    			}
    		}
    	}
    	
	}

}
