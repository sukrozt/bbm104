import java.text.ParseException;

public class TripSchedule {
    Trip[] trips = new Trip[100];

    TripSchedule(String inputPath) throws ParseException {
        String[] inputs = FileInput.readFile(inputPath, true, true);
        for (String input : inputs) {
            String[] allTripData = input.split("\t");
            Trip trip = new Trip(allTripData[0], allTripData[1], allTripData[2]);
            for (int i = 0; i < trips.length; i++) {
                if (trips[i] == null) {
                    this.trips[i] = trip;
                    break;
                }
            }
        }
    }

    public Trip[] getTrip() {
        return trips;
    }
}
