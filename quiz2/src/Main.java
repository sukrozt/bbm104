import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        String inputPath = args[0];
        String outputPath = args[1];
        TripSchedule trip_schedule = new TripSchedule(inputPath);
        FileInput.readFile(inputPath, true, true);
        TripController controller = new TripController(trip_schedule, outputPath);
        controller.DepartureSchedule(trip_schedule);
        controller.ArrivalSchedule(trip_schedule);
    }
}
//Sukriye Ozturk 2210356110