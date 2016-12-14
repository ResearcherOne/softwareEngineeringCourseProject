import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkHandler {
	public static String USER_AGENT = "Mozilla/5.0";
	public static String GET_URL = "https://google.com";
	public static String POST_URL = "https://google.com";
	public static String USER_PARAMS = "userName=Umut";
	public static String SCORE_PARAMS = "gameScore=500";
	public static String CLIENT_PARAMS = "clientId=1234";

	// Argument GET_URL added for test purpose.
	static String sendGET(String GET_URL) throws IOException, JSONException {
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
					// Convert to json object and get specified data from it. Then you can print if the response is json type.
					String list = getList(response.toString());
					System.out.println(list);
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

	private static String getList(String responseString) throws JSONException {
		//Get specific field from response string.
		JSONObject array = new JSONObject(responseString);
		return array.getString("url");
	}

	// Argument POST_URL added for test purpose.
	static String sendPOST(String POST_URL) throws IOException {
		// Check URL is valid or not.
		if (isValidURL(POST_URL)) {
			try {
				//Initial connection
				URL obj = new URL(POST_URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", USER_AGENT);

				// For POST only - START
				con.setDoOutput(true);
				OutputStream os = con.getOutputStream();
				JSONArray array = new JSONArray();
				
				//Get Mac Adress.
				InetAddress ip = InetAddress.getLocalHost();
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				byte[] mac = network.getHardwareAddress();
				StringBuilder macString = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					macString.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				//Send mac address as parameter.
				CLIENT_PARAMS = macString.toString();
				
				//Send Params.
				array.put(USER_PARAMS);
				array.put(SCORE_PARAMS);
				array.put(CLIENT_PARAMS);
				os.write(array.toString().getBytes());

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
