package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

@Entity
public class Venue extends Model {
    
	public String providerVenueId;
	
	@ManyToOne
	public Provider provider;
	
	public String name;
	
	public Double lat;
	public Double lon;
	
	public Calendar lastImagesUpdate;
	
	public Venue(String providerVenueId, Provider provider, String name, double lat, double lon){
		this.providerVenueId = providerVenueId;
		this.provider = provider;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		
		lastImagesUpdate = Calendar.getInstance();
	}
	
	public static Venue findByProviderId(String providerShortname, String id){
		return Venue.find("provider.shortname=? AND providerVenueId=?", providerShortname, id).first();
	}

	public Image getBestPictureOfTag(String string) {
		String queryString = "SELECT img FROM ImageTag tag"
				+ " JOIN tag.image img"
				+ " WHERE"
				+ " img.venue.id = :venue_id"
				+ " AND"
				+ " tag.name LIKE :tag_name"
				+ " ORDER BY tag.confidence DESC";
		Query query = JPA.em().createQuery(queryString);
		query.setParameter("venue_id", this.id);
		query.setParameter("tag_name", string);
		query.setMaxResults(1);
		List<Image> images = query.getResultList();
		if (images.isEmpty()){
			return null;
		} else {
			return images.get(0);
		}
	}
	
	public String getURL(){
		return "http://www.yelp.com/biz/"+this.providerVenueId;
	}
	
	public String getMenuUrl(){
		if (this.provider.shortname.equals("yelp")){
			return "http://www.yelp.com/menu/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
	public String getPhotosUrl(){
		if (this.provider.shortname.equals("yelp")){
			return "http://www.yelp.com/biz_photos/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
	public boolean makeSoupFromMenu(){
		String url = getMenuUrl();
		System.out.println("going to :"+url);
		String selector = "div.menu-section > div.menu-item:not(.menu-item-placeholder-photo)";
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements menuItems = doc.select(selector);
			if(menuItems.size()==0){
				return false;
			} else {
				System.out.println("Going through "+menuItems.size()+" menu items");
				Iterator<Element> eleIt = menuItems.iterator();
				while(eleIt.hasNext()){
					Element item = eleIt.next();
					String imgSrc;
					String description;
					Elements itemImages = item.select("div.biz-photo-box img.photo-box-img");
					if(itemImages.isEmpty()){
						System.out.println("no image for this item");
						continue;
					} else {
						Element img = itemImages.get(0);
						imgSrc = img.attr("src");
					}
					Elements itemTitles = item.select("div.menu-item-details h3");
					Elements itemDescriptions = item.select("div.menu-item-details div.menu-item-details-description");
					if(itemTitles.isEmpty() && itemDescriptions.isEmpty()){
						System.out.println("no description for this item");
						continue;
					}
					description = itemTitles.text().trim().toLowerCase();
					System.out.println("\t "+imgSrc+" : "+description);
					Image img = new Image(this);
					img.description = description;
					img.yelpImageUrlParser(imgSrc, "60s");
					img.save();
					img.makeTags(itemDescriptions.text().trim().toLowerCase());
					
				}
				System.out.println("Complete menu photos registration");
				lastImagesUpdate = Calendar.getInstance();
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
