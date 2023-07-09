import java.time.Duration;
import java.time.LocalDateTime;

public class SmartPlug extends SmartDevice {
    /**
     * The subclass of SmartDevice for smart plug devices.
     */
    private int voltage = 220;
    private double ampere = 0;
    private boolean isPluggedIn = false;
    private LocalDateTime plugOutTime = null;
    private LocalDateTime plugInTime = null;
    private LocalDateTime currentTime = null;
    private double energyConsumed = 0;

    /**
     * Constructor of SmartPlug. Creates a new SmartPlug object and assigns a new name to it.
     *
     * @param pathOutput path of output file for report what is done or what error is occured.
     * @param plugName   name which is given to new smart plug.
     */
    SmartPlug(String pathOutput, String plugName) {
        super(pathOutput);
        this.nameOfDevice = plugName;
    }

    /**
     * Calculates the total energy consumption in a time interval for a smart plug device. Uses currentTime, Time and
     * the information of if device plugged in or not.
     */
    public void calculateEnergy() {
        if (currentTime != null && isPluggedIn) {
            Duration workingDuration = Duration.between(currentTime, Time);
            setDuration(workingDuration.toMinutes() / 60.00);
        }
        double timePassed = getDuration();
        setEnergyConsumed(getAmpere() * getVoltage() * timePassed);
    }

    /**
     * Assigns the given voltage value to device.
     *
     * @param voltage value which is given with Add command.
     */
    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    /**
     * Adds every energy consumption given to static EnergyConsumed variable.
     *
     * @param energyConsumed calculation of value of wasted energy in total.
     */
    public void setEnergyConsumed(double energyConsumed) {
        this.energyConsumed += energyConsumed;
    }

    /**
     * Accesses the energy consumption value.
     *
     * @return energyConsumed in total for a plug.
     */
    public double getEnergyConsumed() {
        return energyConsumed;
    }

    /**
     * Assigns current time as plug out time. Returns nothing.
     */
    public void setPlugOutTime() {
        this.plugOutTime = Time;
    }

    /**
     * Assigns current time as plug in time. Returns nothing.
     */
    public void setPlugInTime() {
        this.plugInTime = Time;
    }


    /**
     * Assigns new ampere value for smart plug
     *
     * @param ampere the new value of ampere
     * @throws Exception if ampere value is 0 or non-positive.
     */
    public void setAmpere(double ampere) throws Exception {
        if (ampere > 0) {
            this.ampere = ampere;
        } else {
            FileOutput.writeToFile(pathOutput, "ERROR: Ampere value must be a positive number!", true, true);
            throw new Exception();
        }
    }

    /**
     * Accesses the voltage value of plug
     *
     * @return voltage value
     */
    public int getVoltage() {
        return voltage;
    }

    /**
     * Accesses the ampere value of plug
     *
     * @return ampere value
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     * Checks if device is plugged in or not
     *
     * @return true if device is plugged in, else false.
     */
    public boolean getIsPluggedin() {
        return isPluggedIn;
    }

    /**
     * Sets isPluggedIn value of device as true
     */
    public void setPluggedIn() {
        isPluggedIn = true;
        plugInTime = Time;
    }

    /**
     * Sets isPluggedOut value of device as false. Prints error if device is already plugged out.
     */
    public void setPluggedOut() {
        if (isPluggedIn) {
            isPluggedIn = false;
            setPlugOutTime();
        } else
            FileOutput.writeToFile(pathOutput, "ERROR: This plug has no item to plug out from that plug!", true, true);
    }

    /**
     * Accesses plug out time of device
     *
     * @return plugOutTime of device
     */
    public LocalDateTime getPlugOutTime() {
        return plugOutTime;
    }

    /**
     * Assigns new initial status of the plug. Energy consumption is being calculated if the given status is off.
     *
     * @param initialStatus the current status of device
     * @throws Exception if given initial status is different from "On" or "Off"
     */
    @Override
    public void setInitialStatus(String initialStatus) throws Exception {
        super.setInitialStatus(initialStatus);
        if (initialStatus.equals("On")) {
            currentTime = Time;
            if (ampere != 0)
                isPluggedIn = true;
        } else if (initialStatus.equals("Off")) {
            calculateEnergy();
            currentTime = null;
        }
    }
}