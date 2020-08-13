// The JAR containing the following can be found here: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
// Please be sure the JAR is included in your classpath.
import org.json.simple.JSONObject;

public class FoodTruck implements Comparable<FoodTruck>{

    private final String applicant;
    private final String location;

    // Constructor. Takes a JSON object.
    public FoodTruck(JSONObject obj) {
        this.applicant = obj.get("applicant").toString();
        this.location = obj.get("location").toString();
    }

    // Getters

    public String getLocation() {
        return location;
    }

    public String getApplicant() {
        return applicant;
    }

    // For alphabetizing.
    @Override
    public int compareTo(FoodTruck other) {
        return this.applicant.compareTo(other.getApplicant());
    }

    @Override
    public String toString() {
        return applicant + ", Location: " + location;
    }
}
