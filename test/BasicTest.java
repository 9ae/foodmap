import org.junit.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.test.*;
import services.Providers;
import services.Words;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }
    
    @Test
    public void findPrepositionWords(){
    	assertTrue(Words.isPreposition("the"));
    	assertFalse(Words.isPreposition("aaaaah"));
    	assertFalse(Words.isPreposition("zelda"));
    	assertTrue(Words.isPreposition("about"));
    	assertTrue(Words.isPreposition("without"));
    }
    
    @Test
    public void testYelpImageUrlParser(){
    	Venue starbucks = new Venue("starbucks", Providers.YELP, "starbucks", 0, 0);
    	Image img = new Image(starbucks);
    	img.yelpImageUrlParser("http://s3-media3.fl.yelpcdn.com/bphoto/gWURGi6nZvmY9yNnwD6_Ew/60s.jpg", "60s");
    	assertEquals(img.prefix, "http://s3-media3.fl.yelpcdn.com");
    	assertEquals(img.providerPictureId, "gWURGi6nZvmY9yNnwD6_Ew");
    	assertEquals(img.suffix, "jpg");
    	
    	assertEquals(img.getImageUrl(), "http://s3-media3.fl.yelpcdn.com/bphoto/gWURGi6nZvmY9yNnwD6_Ew/l.jpg");
    }

}
