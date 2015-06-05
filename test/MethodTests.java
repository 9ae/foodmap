import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.libs.WS;
import play.libs.WS.HttpResponse;

public class MethodTests {
	
	public static void testValidateYelpMenu(){
		HttpResponse response = WS.url("http://www.yelp.com/menu/totto-ramen-new-york").get();
    	System.out.println(response.toString());
	}
	
	@SuppressWarnings("unused")
	public static void testYelpImageUrlParser(){
    	String sizeSuffix = "60s";
    	String imageUrl = "http://s3-media3.fl.yelpcdn.com/bphoto/gWURGi6nZvmY9yNnwD6_Ew/60s.jpg";
    	Pattern p = Pattern.compile("^(http.*\\/bphoto)\\/(.*)\\/"+sizeSuffix+"\\.(jpg|png|gif)$");
		Matcher m = p.matcher(imageUrl);
		m.matches();
		String prefix = m.group(1);
		String providerPictureId = m.group(2);
		String suffix = m.group(3);
    
    }
	
	public static void strip(){
		String input = "my lunch:  dry cappuccino & veggie lentil soup  both delicious";
		input = input.replaceAll("[^a-zA-Z0-9\\s]", "");
		input = input.replaceAll("\\s{2,}", " ");
		System.out.println(input);
	}
	
	public static void main(String[] args) {
		strip();
	}

}
