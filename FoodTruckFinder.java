import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;

// The JAR containing the following can be found here: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
// Please be sure the JAR is included in your classpath.
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FoodTruckFinder {

    public static void main(String[] args) {
        int offset = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Showing food trucks currently open in San Francisco, CA ***\n");

        // Each loop makes an API call. Each call is paginated
        // and picks up where the previous call left off.
        while (true) {
            String[] currentDayAndTime = currentDayAndTime();               // Fetch needed params for API call.
            String query = buildQueryString(currentDayAndTime, offset);     // Create and encode the query string.
            String jsonResponse = makeRequest(query);                       // Make the call.
            LinkedList<FoodTruck> foodTrucks = parseJSON(jsonResponse);     // Parse the response into FoodTruck objects.

            // Break loop if there are no results on this page.
            if (foodTrucks.size() == 0)
                break;

            // Print the results for this page.
            for (FoodTruck truck : foodTrucks) {
                System.out.println(" - " + truck);
            }

            // For pagination.
            offset += 10;

            System.out.println("\n*** Enter any value to see more results. ***");
            scanner.next();
        }

        System.out.println("End of results.");
        scanner.close();

    }

    // Returns array containing day and current time as string in HH:MM format.
    private static String[] currentDayAndTime() {
        String[] data = new String[2];

        // Utilize java calendar class.
        Calendar cal = Calendar.getInstance();
        int dayOrder = cal.get(Calendar.DAY_OF_WEEK) - 1;           // API: Sunday=0    |    java.Calendar: Sunday=1
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        // Hour MUST be two digits (ie: 07:00 vs 7:00); follows format of API.
        String time;
        if (hour < 10)
            time = "0" + hour + ":" + minute;
        else
            time = hour + ":" + minute;

        data[0] = String.valueOf(dayOrder);     // Day as integer
        data[1] = time;                         // Time in HH:MM format.
        return data;
    }

    // Put the query string together and encode it so it can be passed to the URI.
    private static String buildQueryString(String[] timeData, int offset) {
        String query = "SELECT applicant,location" +
                " WHERE dayOrder = '" + timeData[0] + "'" +
                " AND start24 <= '" + timeData[1] + "'" +
                " AND end24 >= '" + timeData[1] + "'" +
                " ORDER BY applicant ASC" +
                " LIMIT 10" +
                " OFFSET " + offset;

        // Handle unsupported encodings.
        try {
            query = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }

        return "?$query=" + query;
    }

    // Make the API call using the query string we created.
    private static String makeRequest(String query) {
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
    private static LinkedList<FoodTruck> parseJSON(String response) {
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
// to run:
// $ javac FoodTruckFinder.java && java FoodTruckFinder
