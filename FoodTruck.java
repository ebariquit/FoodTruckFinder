// The JAR containing the following can be found here: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
import org.json.simple.JSONObject;

public class FoodTruck implements Comparable<FoodTruck>{

    private String applicant;
    private String location;

    // Constructor. Takes a JSON object.
    public FoodTruck(JSONObject obj) {
        this.applicant = obj.get("applicant").toString();
        this.location = obj.get("location").toString();
    }

    // Getter methods.

    public String getLocation() {
        return location;
    }

    public String getApplicant() {
        return applicant;
    }

    @Override
    public int compareTo(FoodTruck other) {
        return this.applicant.compareTo(other.getApplicant());
    }

    //
    public void print() {
        System.out.println(applicant + ", " + location);
    }
}
