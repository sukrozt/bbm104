public interface Operations {
    /**
     * Adds given object to provided arraylist
     *
     * @param inputArray the line which contains add command and device information
     */
    default void Add(String[] inputArray) {
    }

    /**
     * Removes given object from provided arraylist
     *
     * @param device the device which is going to be removed
     */
    default void Remove(SmartDevice device) {
    }

    /**
     * Writes to output file all information of the given device as a sentence.
     *
     * @param device given device which is going to be written to output file
     */
    default void reportObject(SmartDevice device) {
        if (device instanceof SmartPlug) {
            FileOutput.writeToFile(device.pathOutput, String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device), and its time to switch its status is %s.", device.getNameOfDevice(), device.getInitialStatus().toLowerCase(), ((SmartPlug) device).getEnergyConsumed(), device.formatSwitchTime(device.getSwitchOnTime())), true, true);
        }
        if (device instanceof SmartCamera) {
            FileOutput.writeToFile(device.pathOutput, String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.", device.getNameOfDevice(), device.getInitialStatus().toLowerCase(), ((SmartCamera) device).getTotalMb(), device.formatSwitchTime(device.getSwitchOnTime())), true, true);
        }
        if (device instanceof SmartLamp) {
            if (device instanceof SmartColorLamp) {
                FileOutput.writeToFile(device.pathOutput, String.format("Smart Color Lamp %s is %s and its color value is %s with %s brightness, and its time to switch its status is %s.", device.getNameOfDevice(), device.getInitialStatus().toLowerCase(), ((SmartColorLamp) device).getColorCode(), ((SmartColorLamp) device).getBrightness(), device.formatSwitchTime(device.getSwitchOnTime())), true, true);
            } else {
                FileOutput.writeToFile(device.pathOutput, String.format("Smart Lamp %s is %s and its kelvin value is %dK with %s brightness, and its time to switch its status is %s.", device.getNameOfDevice(), device.getInitialStatus().toLowerCase(), ((SmartLamp) device).getKelvin(), ((SmartLamp) device).getBrightness(), device.formatSwitchTime(device.getSwitchOnTime())), true, true);
            }
        }
    }
}
