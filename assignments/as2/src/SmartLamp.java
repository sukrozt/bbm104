public class SmartLamp extends SmartDevice {
    /**
     * Subclass of SmartDevice for smart lamps.
     */
    int brightness = 100;
    int kelvin = 4000;

    /**
     * Constructor of SmartLamp. Creates new smart lamps and assigns their names.
     *
     * @param pathOutput path out output file for reports of actions and error which possible to occur
     * @param lampName   name of lamp which will be assigned
     */
    SmartLamp(String pathOutput, String lampName) {
        super(pathOutput);
        this.nameOfDevice = lampName;
    }

    /**
     * Assigns the brightness of smart lamp
     *
     * @param brightness value of brightness of smart lamp.
     * @throws Exception if brightness value is under 0 or above 100.
     */
    void setBrightness(int brightness) throws Exception {
        if (brightness >= 0 && brightness <= 100)
            this.brightness = brightness;
        else {
            FileOutput.writeToFile(pathOutput, "ERROR: Brightness must be in range of 0%-100%!", true, true);
            throw new Exception();
        }
    }

    /**
     * Assigns kelvin value which is given.
     *
     * @param kelvin value for temperature and whiteness setting of smart lamp
     * @throws Exception if kelvin value is not in the interval 2000K-6500K
     */
    void setKelvin(int kelvin) throws Exception {
        if (kelvin >= 2000 && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            FileOutput.writeToFile(pathOutput, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
            throw new Exception();
        }
    }

    /**
     * Accesses brightness value and returns it.
     *
     * @return returns brightness value as String with "%" symbol
     */
    public String getBrightness() {
        return String.valueOf(brightness) + "%";
    }

    /**
     * Accesses kelvin value
     *
     * @return kelvin value of lamp
     */
    public int getKelvin() {
        return kelvin;
    }
}