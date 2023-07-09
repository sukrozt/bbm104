import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The main class of all smart devices. Smart plug, smart lamp, smart camera classes are subclass of it. The field of
 * class contains common functions off all devices.
 */

public class SmartDevice extends CommandReader implements Time {
    protected String nameOfDevice;
    protected static ArrayList<SmartDevice> SmartDevices;
    protected static ArrayList<LocalDateTime> SwitchTimes;
    protected String initialStatus = "Off";
    protected LocalDateTime switchOnTime = null;
    protected LocalDateTime switchOffTime = null;
    private double duration = 0.00;

    /**
     * Constructor of SmartDevice. Creates a new smartDevices arraylist to keep all devices created. Also creates a
     * switchTimes arraylist to organise switch times of devices.
     *
     * @param pathOutput the path to be written the output data.
     */
    SmartDevice(String pathOutput) {
        super(pathOutput);
        SmartDevices = new ArrayList<>();
        SwitchTimes = new ArrayList<>();
    }

    /**
     * Accessor method of SmartDevices arraylist.
     *
     * @return ArrayList<SmartDevice> SmartDevices if it exists.
     */
    public ArrayList<SmartDevice> getSmartDevices() {
        return SmartDevices;
    }

    /**
     * Accessor method of SwitchTimes arraylist.
     *
     * @return ArrayList<LocalDateTime> SwitchTimes if it exists.
     */
    public static ArrayList<LocalDateTime> getSwitchTimes() {
        return SwitchTimes;
    }

    /**
     * Returns name of device to assign an SmartDevice object or compare it with others.
     *
     * @return String name of device object
     */
    public String getNameOfDevice() {
        return nameOfDevice;
    }

    /**
     * Returns initial status of SmartDevice object to learn if it's off or on.
     *
     * @return String initial status of device object
     */
    public String getInitialStatus() {
        return initialStatus;
    }

    /**
     * Returns switch on time of SmartDevice object to calculate working duration or to inform user.
     *
     * @return LocalDateTime switch on time of device object
     */
    public LocalDateTime getSwitchOnTime() {
        if (switchOnTime != null && switchOnTime.isAfter(Time))
            return switchOnTime;
        else
            return null;
    }

    /**
     * Formats the switch time of object to write it as in String format to file.
     *
     * @return String formatted version of switch time as "yyyy-mm-dd_hh:mm:ss"
     */
    public String formatSwitchTime(LocalDateTime switchOnTime) {
        if (switchOnTime == null)
            return "null";
        return formatTime(switchOnTime);
    }

    /**
     * Returns the total working time of device
     *
     * @return double duration of on time
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Accesses the switch time off device
     *
     * @return switchOffTime
     */
    public LocalDateTime getSwitchOffTime() {
        return switchOffTime;

    }

    /**
     * Mutator of duration of on time of device
     *
     * @param duration time interval which device is open
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Sets initial status of device by the given status
     *
     * @param initialStatus the current status of device
     * @throws Exception if the given status is different from "On" or "Off"
     */
    public void setInitialStatus(String initialStatus) throws Exception {
        if (initialStatus.equals("On")) {
            this.initialStatus = initialStatus;
            setSwitchOnTime(Time);

        } else if (initialStatus.equals("Off")) {
            this.initialStatus = initialStatus;
            setSwitchOnTime(null);

        } else {
            FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
            throw new Exception();
        }
    }

    /**
     * Assigns a name to the smart device, returns nothing.
     *
     * @param newName name of device
     */
    public void setNameOfDevice(String newName) {
        this.nameOfDevice = newName;
    }

    /**
     * Sets the given time to switchOffTime when switch off command has been given
     *
     * @param time the current time when device is switched on
     */
    public void setSwitchOnTime(LocalDateTime time) {
        this.switchOnTime = time;
        SwitchTimes.add(switchOnTime);
    }
}
