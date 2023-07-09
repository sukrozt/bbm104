import java.time.Duration;

public class SmartCamera extends SmartDevice {
    private double MBperSecond = 0;
    private double totalMb = 0;

    /**
     * Constructor of smartCamera class which creates new smart camera objects
     *
     * @param cameraName  name of smart camera
     * @param MBperSecond Maximum storage-able MB capacity per second of smart camera
     */
    SmartCamera(String pathOutput, String cameraName, double MBperSecond) throws Exception {
        super(pathOutput);
        if (MBperSecond > 0) {
            this.MBperSecond = MBperSecond;
            this.nameOfDevice = cameraName;
        } else {
            throw new MyException.generalError("ERROR: Megabyte value must be a positive number!");
        }
    }

    /**
     * Accesses total MB capacity of camera
     *
     * @return total MB capacity of camera
     */
    public double getTotalMb() {
        return totalMb;
    }

    /**
     * Assigns MB capacity of camera and checks if it is given as positive number
     *
     * @return MB capacity of camera per second
     * @throws Exception if the given MB value is non-positive
     */
    public double setMBperSecond() throws Exception {
        if (MBperSecond > 0)
            return MBperSecond;
        else {
            throw new NegativeArraySizeException();
        }
    }

    /**
     * Calculates MB usage for a certain time interval and adds it to total MB usage.
     *
     * @return total MB usage of camera
     */
    public double calculateMB() {
        if (switchOnTime != null) {
            Duration workingDuration = Duration.between(switchOnTime, Time);
            setDuration(workingDuration.toMinutes());
        }
        double mbUsed = getDuration() * MBperSecond;
        return totalMb += mbUsed;
    }

    /**
     * New initial status of device is assigned as "On" or "Off", also sets switch on times as current time.
     *
     * @param initialStatus string value of the current status of device
     * @throws Exception if given initial status is different from "On" or "Off"
     */
    @Override
    public void setInitialStatus(String initialStatus) throws Exception {
        super.setInitialStatus(initialStatus);
        if (initialStatus.equals("On")) {
            switchOnTime = Time;
        } else if (initialStatus.equals("Off")) {
            calculateMB();
            switchOnTime = null;
        }
    }
}
