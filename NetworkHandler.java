import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkHandler {
	public static String USER_AGENT = "Mozilla/5.0";
	public static String GET_URL = "https://google.com";
	public static String POST_URL = "https://google.com";
	public static String POST_PARAMS = "userName=Umut";

	// Argument GET_URL added for test purpose.
	static String sendGET(String GET_URL) throws IOException {
		// Check URL is valid or not.
		if (isValidURL(GET_URL)) {
			try {
				URL obj = new URL(GET_URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + responseCode);
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// print result
					System.out.println(response.toString());
					return "GET request worked"; // Added for testing.
				} else {
					System.out.println("GET request not worked");
					return "GET request not worked";
				}
			} catch (MalformedURLException e) {
				return "URL is valid but connection could not be established.";
			} catch (IOException e) {
				return "URL is valid but connection could not be established.";
			}
		} else {
			return "URL is not valid.";
		}
	}

	// Argument POST_URL added for test purpose.
	static String sendPOST(String POST_URL) throws IOException {
		// Check URL is valid or not.
		if (isValidURL(POST_URL)) {
			try {
				URL obj = new URL(POST_URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", USER_AGENT);

				// For POST only - START
				con.setDoOutput(true);
				OutputStream os = con.getOutputStream();
				os.write(POST_PARAMS.getBytes());
				os.flush();
				os.close();
				// For POST only - END

				int responseCode = con.getResponseCode();
				System.out.println("POST Response Code :: " + responseCode);

				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// print result
					System.out.println(response.toString());
					return "POST request worked"; // Added for testing.
				} else {
					System.out.println("POST request not worked");
					return "POST request not worked";
				}
			} catch (MalformedURLException e) {
				return "URL is valid but connection could not be established.";
			} catch (IOException e) {
				return "URL is valid but connection could not be established.";
			}

		} else {
			return "URL is not valid.";
		}
	}

	public static boolean isValidURL(String url) {
		try {
			new URL(url);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
