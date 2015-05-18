package jobs;

import org.w3c.dom.Document;

import models.Image;
import models.Provider;
import models.Seeker;
import models.SeekerResult;
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
		boolean isValid = this.venue.makeSoupFromMenu();
		if(!isValid){
			System.out.println("Menu validation failed");
			String photosPageURL = this.venue.getPhotosUrl();
		
		}
		Image img = venue.getBestPictureOfTag(seeker.tag);
		if(img!=null){
			SeekerResult sr = new SeekerResult(seeker, img);
			sr.save();
		}
	}
	
}
