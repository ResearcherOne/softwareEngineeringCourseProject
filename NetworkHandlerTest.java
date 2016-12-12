import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class NetworkHandlerTest {

	@Test
	public void testGetRequest() throws IOException {
		assertEquals("GET request worked", NetworkHandler.sendGET("https://google.com")); //URL form is valid, request is accepted. Https connection.
		assertEquals("GET request worked", NetworkHandler.sendGET("http://google.com")); // URL form is valid, request is accepted. Http connection.	
	}
	
	@Test
	public void testGetRequest1() throws IOException {
		assertEquals("GET request not worked", NetworkHandler.sendGET("https://httpbin.org/post")); //URL form is valid, but request is denied. Https connection.
		assertEquals("GET request not worked", NetworkHandler.sendGET("http://httpbin.org/post"));  //URL form is valid, but request is denied. Http connection.
	}
	
	@Test
	public void testGetRequest2() throws IOException {
		assertEquals("URL is valid but connection could not be established.", NetworkHandler.sendGET("http://weqwrwrqweqwe.com/")); //URL form is valid, but does not exist. Https connection.
		assertEquals("URL is valid but connection could not be established.", NetworkHandler.sendGET("http://weqwrwrqweqwe.com/")); //URL form is valid, but does not exist. Http connection.
	}
	
	@Test
	public void testGetRequest3() throws IOException {
		assertEquals("URL is not valid.", NetworkHandler.sendGET("nothing")); //URL form is not valid.
	}

	@Test
	public void testPostRequest() throws IOException {
		assertEquals("POST request worked", NetworkHandler.sendPOST("https://httpbin.org/post")); //URL form is valid, request is accepted. Https connection.
		assertEquals("POST request worked", NetworkHandler.sendPOST("http://httpbin.org/post")); //URL form is valid, request is accepted. Http connection.
	}
	
	@Test
	public void testPostRequest1() throws IOException {
		assertEquals("POST request not worked", NetworkHandler.sendPOST("https://httpbin.org/get")); //URL form is valid, but request is denied. Https connection.
		assertEquals("POST request not worked", NetworkHandler.sendPOST("http://httpbin.org/get")); //URL form is valid, but request is denied. Http connection.
	}
	
	@Test
	public void testPostRequest2() throws IOException {
		assertEquals("URL is valid but connection could not be established.", NetworkHandler.sendPOST("https://weqwrwrqweqwe.com/")); //URL form is valid, but does not exist. Https connection.
		assertEquals("URL is valid but connection could not be established.", NetworkHandler.sendPOST("http://weqwrwrqweqwe.com/")); //URL form is valid, but does not exist. Http connection.
	}
	
	@Test
	public void testPostRequest3() throws IOException {
		assertEquals("URL is not valid.", NetworkHandler.sendPOST("nothing")); //URL form is not valid.
	}
}
