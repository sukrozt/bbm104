import java.text.ParseException;
import java.time.LocalTime;

public class Trip {

    LocalTime arrivalTime;
    LocalTime departureTime;
    String tripName;
    int duration;
    String state;

    public Trip(String tripName, String departureTime, String duration) throws ParseException {
        this.tripName = tripName;
        String[] depTimeSep = departureTime.split(":");
        int hour = Integer.parseInt(depTimeSep[0]);
        int minute = Integer.parseInt(depTimeSep[1]);
        this.departureTime = LocalTime.of(hour, minute);
        this.duration = Integer.parseInt(duration);
        this.state = "IDLE";
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void calculateArrival() {
        departureTime = getDepartureTime();
        arrivalTime = departureTime.plusMinutes(duration);
    }

    public LocalTime getArrivalTime() {
        calculateArrival();
        return this.arrivalTime;
    }
}
