import java.util.Arrays;
import java.util.Comparator;

public class TripController implements DepartureController, ArrivalController {
    protected TripSchedule trip_schedule;
    String pathOutput;

    public TripController(TripSchedule trip_schedule, String pathOutput) {
        this.trip_schedule = trip_schedule;
        this.pathOutput = pathOutput;
    }

    @Override
    public void DepartureSchedule(TripSchedule trip_schedule) {
        FileOutput.writeToFile(pathOutput, "Departure order:", false, true);
        Trip[] trips = trip_schedule.getTrip();
        Arrays.sort(trips, new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1 == null || t2 == null) {
                    return t1 == null ? -1 : 1;
                }
                if (t1.getDepartureTime().equals(t2.getDepartureTime())) {
                    t1.setState("DELAYED");
                    t2.setState("DELAYED");
                }
                return t1.getDepartureTime().compareTo(t2.getDepartureTime());
            }
        });
        for (int i = 0; i < trips.length; i++) {
            if (trips[i] != null) {
                FileOutput.writeToFile(pathOutput, trips[i].tripName +
                        " depart at " + trips[i].departureTime +
                        "\tTrip State:" + trips[i].state, true, true);
            }
        }
    }

    @Override
    public void ArrivalSchedule(TripSchedule trip_schedule) {
        FileOutput.writeToFile(pathOutput, "\nArrival order:", true, true);
        Trip[] trips = trip_schedule.getTrip();
        Arrays.sort(trips, new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1 == null || t2 == null) {
                    return t1 == null ? -1 : 1;
                }
                if (t1.getArrivalTime().equals(t2.getArrivalTime())) {
                    t1.setState("DELAYED");
                    t2.setState("DELAYED");
                }
                return t1.getArrivalTime().compareTo(t2.getArrivalTime());
            }
        });
        for (int i = 0; i < trips.length; i++) {
            if (trips[i] != null) {
                FileOutput.writeToFile(pathOutput, trips[i].tripName +
                        " arrive at " + trips[i].getArrivalTime() +
                        "\tTrip State:" + trips[i].state, true, true);
            }
        }
    }
}
