
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;

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
            FoodTruckDAO dao = new FoodTruckDAO();                          // Send query to the DAO
            LinkedList<FoodTruck> foodTrucks = dao.fetchNext10(query);

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

}
// to run:
// $ javac FoodTruckFinder.java && java FoodTruckFinder
