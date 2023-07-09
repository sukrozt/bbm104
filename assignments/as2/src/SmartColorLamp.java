public class SmartColorLamp extends SmartLamp {
    /**
     * Subclass of SmartLamp for colored smart lamps.
     */
    int colorCode;

    /**
     * @param pathOutput
     * @param lampName
     */
    SmartColorLamp(String pathOutput, String lampName) {
        super(pathOutput, lampName);
    }

    /**
     * @param colorCode
     * @throws Exception
     */
    void setColorCode(int colorCode) throws Exception {
        if (colorCode <= 0x00FFFFFF && colorCode >= 0)
            this.colorCode = colorCode;
        else {
            FileOutput.writeToFile(pathOutput, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
            throw new Exception();
        }
    }

    /**
     * @return
     */
    public String getColorCode() {
        if (colorCode == 0)
            return Integer.toString(kelvin) + "K";
        else
            return String.format("0x%06X", colorCode, 16);
    }
}
