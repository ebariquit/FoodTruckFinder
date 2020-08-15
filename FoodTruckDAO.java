import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class FoodTruckDAO {

    // To be called by controller.
    public LinkedList<FoodTruck> fetchNext10(String query) {

        String httpResponse = makeRequest(query);
        return parseJSON(httpResponse);

    }


    // Make the API call using the query string we created.
    private String makeRequest(String query) {
        StringBuilder result = new StringBuilder();

        // Handle any HTTP errors that may arise.
        try {
            // Open connection.
            URL url = new URL("http://data.sfgov.org/resource/bbb8-hzi6.json" + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response.
            InputStream inStream = conn.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream);
            BufferedReader rd = new BufferedReader(inStreamReader);

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result.toString();
    }

    // Parse the json response into FoodTruck objects.
    private LinkedList<FoodTruck> parseJSON(String response) {
        LinkedList<FoodTruck> foodTrucks = new LinkedList<>();

        // Handle any JSON parsing errors that may arise.
        try {
            // Parse the response string.
            JSONParser parser = new JSONParser();
            JSONArray data = (JSONArray) parser.parse(response);

            // Create the foodtruck objects from our extracted JSON data.
            for (Object object : data) {
                JSONObject obj = (JSONObject) object;
                FoodTruck truck = new FoodTruck(obj);
                foodTrucks.add(truck);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return foodTrucks;
    }

}
