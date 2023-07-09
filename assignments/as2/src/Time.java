import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public interface Time {
    /**
     * Sets time when appropriate command has given. Method takes
     * the data which is given near command and converts it to a LocalTimeData.
     *
     * @param TimeAndDate Time and Date values as String
     * @param Time        current time to compare given time is before from initial time.
     * @return current time value set as given TimeAndDate argument.
     * @throws Exception if given time value is passed
     **/
    default LocalDateTime SetTime(String TimeAndDate, LocalDateTime Time) throws Exception {
        String[] dateAndHour = TimeAndDate.split("_");
        String[] yearMonthDay = dateAndHour[0].split("-");
        String[] hourMinute = dateAndHour[1].split(":");
        LocalDateTime currentTime = LocalDateTime.of(Integer.parseInt(yearMonthDay[0]),
                Integer.parseInt(yearMonthDay[1]), Integer.parseInt(yearMonthDay[2]), Integer.parseInt(hourMinute[0]),
                Integer.parseInt(hourMinute[1]), Integer.parseInt(hourMinute[2]));
        if (Time != null && currentTime.isBefore(Time))
            throw new MyException("ERROR: Time cannot be reversed!");
        else if (Time != null && TimeAndDate.equals(formatTime(Time)))
            throw new MyException("ERROR: There is nothing to change!");

        return currentTime;
    }

    /**
     * Sets initial time help with SetTime() when "SetInitialTime" command has given. Method takes
     * the data which is given near command and converts it to a LocalTimeData.
     *
     * @param TimeAndDate value which is intended to assign as current date and time
     * @return LocalDateTime value of current time
     * @throws MyException if initial time format is not suitable with LocalDateTime format
     */
    default LocalDateTime SetInitialTime(String TimeAndDate) throws MyException {
        try {
            LocalDateTime Time = null;
            LocalDateTime initialTime = SetTime(TimeAndDate, Time);
            return initialTime;
        }
        catch (Exception e) {
            throw new MyException("ERROR: Format of the initial date is wrong! Program is going to terminate!");
        }
    }

    /**
     * Passes time count of minutes which is given as argument
     */
    default void SkipMinutes() {

    }

    /**
     * Formats time value as appropriate form.
     *
     * @param switchTime LocalDateTime value given time which is not formatted
     * @return formatted form of given time as "yyyy-MM-dd_HH:mm:ss"
     */
    default String formatTime(LocalDateTime switchTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String switchTimeFormatted = formatter.format(switchTime);
        switchTimeFormatted = switchTimeFormatted.replace("T", "_");
        return switchTimeFormatted;
    }

    /**
     * Passes the time until a new smart device's switch time. Also sets switch on times to null if time of switch has
     * passed.
     *
     * @throws Exception if there is no device which is going to be set switched on in future
     */
    default void Nop() throws Exception {
        LocalDateTime nextSwitchTime = null;
        if (SmartDevice.SwitchTimes != null) {
            for (LocalDateTime switchTime : SmartDevice.getSwitchTimes()) {
                if (switchTime != null && switchTime.isAfter(CommandReader.Time)) {
                    nextSwitchTime = switchTime;
                    break;
                }
            }
        }
        if (nextSwitchTime == null) {
            throw new MyException("ERROR: There is nothing to switch!");
        }
        CommandReader.Time = nextSwitchTime;
        for (SmartDevice device : SmartDevice.SmartDevices) {
            if (device.getSwitchOnTime() != null && device.getSwitchOnTime().isBefore(CommandReader.Time)) {
                if (device.getInitialStatus().equals("On"))
                    device.setInitialStatus("Off");
                else
                    device.setInitialStatus("On");
                device.setSwitchOnTime(null);
            }
        }
    }

    /**
     * Sorts LocalDateTime elements of an array
     *
     * @param switchTimes LocalDateTime array which is intended to be sorted
     * @return minimum time value of array
     */
    default LocalDateTime sortTimes(ArrayList<LocalDateTime> switchTimes) {
        LocalDateTime minTime = switchTimes.get(0);
        for (int i = 1; i < switchTimes.size(); i++) {
            LocalDateTime current = switchTimes.get(i);
            if (current != null && current.isBefore(minTime)) {
                minTime = current;
            }
        }
        return minTime;
    }
}
