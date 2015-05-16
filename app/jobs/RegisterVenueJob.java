package jobs;

import org.w3c.dom.Document;

import models.Provider;
import models.Seeker;
import models.Venue;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XML;
import services.WebpageValidators;

public class RegisterVenueJob extends Job {

	private Seeker seeker;
	private Venue venue;
	
	public RegisterVenueJob(Provider provider, Seeker seeker, String providerId, String name, double lat, double lon){
		this.seeker = seeker;
		this.venue = new Venue(providerId, provider, name, lat, lon);
		venue.save();
	}
	
	public void doJob(){
		//TODO: try Menu
		String menuURL = this.venue.getMenuUrl();
		boolean isValid = WebpageValidators.soupValidateYelpMenu(menuURL);
		if(isValid){
			//TODO: alert menu validation failed
			System.out.println("Menu validation failed");
		}	
		 else {
			//TODO: look at photos
			String photosPageURL = this.venue.getPhotosUrl();
		
		}
		
	}
	
}
