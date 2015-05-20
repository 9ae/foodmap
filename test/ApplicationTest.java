import org.junit.*;
import org.w3c.dom.Document;

import play.test.*;
import play.libs.XML;
import play.mvc.*;
import play.mvc.Http.*;
import services.WebpageValidators;
import models.*;

public class ApplicationTest extends FunctionalTest {

	/*
    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }*/

    @Test
    public void testYelpMenuValidation(){
    	Response response = GET("http://www.yelp.com/menu/totto-ramen-new-york");
    	assertIsOk(response);
    	
    	String string_response = FunctionalTest.getContent(response);
  
    	// Document menuDoc = XML.getDocument(string_response);
    	
    	// boolean test = WebpageValidators.validateYelpMenu(menuDoc);
    	//this.assertTrue(test);
    }
    
}