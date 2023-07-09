import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Reads the input and takes the necessary action for the command, calls another methods from other classes or interfaces.
 */
public class CommandReader implements Time, Operations {
    protected String pathOutput;
    private boolean isInitTimeSet = false;
    protected ArrayList<SmartDevice> smartDevices = new ArrayList<>();

    public static LocalDateTime Time = null;

    /**
     * Constructor of CommandReader.
     * Output path is taken by object of class CommandReader.
     *
     * @param pathOutput output path
     */
    CommandReader(String pathOutput) {
        this.pathOutput = pathOutput;
    }

    /**
     * ArrayList of SmartDevices is got by getter of SmartDevices.
     *
     * @return ArrayList of SmartDevices
     */
    public ArrayList<SmartDevice> getSmartDevices() {
        return smartDevices;
    }

    /**
     * The current time in program is getting returned by accessor of Time variable.
     *
     * @return the current time
     */

    public static LocalDateTime getTime() {
        return Time;
    }

    /**
     * Array which is given has been split from tabs and be assigned to an object which instance of given subclass.
     * This method implemented from Operations interface.
     *
     * @param inputArray is the String[] which returns all input information line by line. First element of inputArray is command, second
     *                   * element of inputArray the type of device, third element of inputArray is the name of device. Other elements are
     *                   * different
     */
    @Override
    public void Add(String[] inputArray) {
        switch (inputArray[1]) {
            case "SmartPlug":
                SmartPlug plug = new SmartPlug(pathOutput, inputArray[2]);
                try {
                    if (inputArray.length >= 4)
                        plug.setInitialStatus(inputArray[3]);
                    if (inputArray.length >= 5)
                        plug.setAmpere(Double.parseDouble(inputArray[4]));
                    smartDevices.add(plug);
                }
                catch (Exception e) {
                }
                break;
            case "SmartCamera":
                try {
                    SmartCamera camera = new SmartCamera(pathOutput, (inputArray[2]), Double.parseDouble(inputArray[3]));
                    if (inputArray.length >= 5)
                        camera.setInitialStatus(inputArray[4]);
                    smartDevices.add(camera);
                }
                catch (MyException e) {
                    FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                }
                catch (Exception e) {
                    FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                }
                break;
            case "SmartLamp":
                SmartLamp lamp = new SmartLamp(pathOutput, inputArray[2]);
                try {
                    smartDevices.add(lamp);
                    if (inputArray.length >= 4)
                        lamp.setInitialStatus(inputArray[3]);
                    if (inputArray.length >= 5)
                        lamp.setKelvin(Integer.parseInt(inputArray[4]));
                    if (inputArray.length >= 6)
                        lamp.setBrightness(Integer.parseInt(inputArray[5]));
                }
                catch (Exception e) {
                    smartDevices.remove(lamp);
                }
                break;
            case "SmartColorLamp":
                SmartColorLamp clamp = new SmartColorLamp(pathOutput, inputArray[2]);
                try {
                    if (inputArray.length >= 4)
                        clamp.setInitialStatus(inputArray[3]);
                    if (inputArray.length >= 5)
                        if (inputArray[4].startsWith("0x"))
                            clamp.setColorCode(Integer.parseInt(inputArray[4].substring(2), 16));
                        else
                            clamp.setKelvin(Integer.parseInt(inputArray[4]));
                    if (inputArray.length >= 6)
                        clamp.setBrightness(Integer.parseInt(inputArray[5]));
                    smartDevices.add(clamp);
                }
                catch (NumberFormatException e) {
                    FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                }
                catch (Exception e) {
                    smartDevices.remove(clamp);
                }
                break;
            default:
                FileOutput.writeToFile(pathOutput, "ERROR: Device type is wrong!", true, true);
        }
    }

    /**
     * The given SmartDevice object be removed from main smartDevices array list.
     * This method implemented from Operations interface.
     *
     * @param device is going to be removed from smartDevices array list.
     */
    @Override
    public void Remove(SmartDevice device) {
        smartDevices.remove(device);
    }

    /**
     * smartDevices arraylist be sorted by the switch times of objects inside. Returns nothing.
     */
    public void sortBySwitchTimes() {
        Collections.sort(smartDevices, new Comparator<SmartDevice>() {
            @Override
            public int compare(SmartDevice sd1, SmartDevice sd2) {
                LocalDateTime switchTime1 = sd1.getSwitchOnTime();
                LocalDateTime switchTime2 = sd2.getSwitchOnTime();
                if (switchTime1 == null && switchTime2 == null) {
                    return 0;
                } else if (switchTime1 == null) {
                    return 1;
                } else if (switchTime2 == null) {
                    return -1;
                } else {
                    return switchTime1.compareTo(switchTime2);
                }
            }
        });
    }

    /**
     * The String is to be read to process by the first String of the given array.
     *
     * @param inputArray the input line which is going to be processed by the first String of it. The first String is
     *                   always command, second String is optional or necessary up to command. Array is going to be
     *                   processed if the command requires more than one argument to avoid from NullPointerException
     *                   errors. Returns nothing.
     * @throws Exception if the first String of the array is not a valid command.
     */
    public void ReadCommand(String[] inputArray) throws Exception {
        String command = inputArray[0];
        if (inputArray.length == 1) {
            switch (command) {
                case "Nop":
                    try {
                        Nop();
                    }
                    catch (MyException e) {
                        FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                    }
                    break;
                case "ZReport":
                    FileOutput.writeToFile(pathOutput, "Time is: " + formatTime(Time), true, true);
                    sortBySwitchTimes();
                    for (SmartDevice device : smartDevices) {
                        reportObject(device);
                    }
                    break;
                default:
                    FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    break;
            }
        } else {
            switch (command) {
                case "SetInitialTime":
                    if (isInitTimeSet) {
                        FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    } else {
                        try {
                            Time = SetTime(inputArray[1], Time);
                        }
                        catch (Exception e) {
                            throw new MyException("ERROR: Format of the initial date is wrong! Program is going to terminate!");
                        }
                        FileOutput.writeToFile(pathOutput, "SUCCESS: Time has been set to " + formatTime(Time) + "!", true, true);
                        isInitTimeSet = true;
                        break;
                    }
                    break;
                case "Add":
                    try {
                        if (smartDevices.size() == 0) {
                            Add(inputArray);
                        } else {
                            for (SmartDevice existingDevice : smartDevices) {
                                if (existingDevice.getNameOfDevice().equals(inputArray[2]) && !inputArray[1].equals("SmartCamera")) {
                                    throw new MyException("ERROR: There is already a smart device with same name!");
                                } else if (existingDevice.getNameOfDevice().equals(inputArray[2]) && inputArray[1].equals("SmartCamera")) {
                                    if (inputArray.length >= 4)
                                        throw new MyException("ERROR: There is already a smart device with same name!");
                                }
                            }
                            Add(inputArray);
                        }
                    }
                    catch (MyException e) {
                        FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                    }
                    catch (Exception e) {
                        FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    }
                    break;
                case "Remove":
                    for (SmartDevice existingDevice : smartDevices) {
                        if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                            smartDevices.remove(existingDevice);
                            existingDevice.setInitialStatus("Off");
                            FileOutput.writeToFile(pathOutput, "SUCCESS: Information about removed smart device is as follows:", true, true);
                            reportObject(existingDevice);
                            break;
                        }
                    }
                    break;
                case "Switch":
                    boolean isDeviceExist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                            if (existingDevice.getInitialStatus().equals(inputArray[2]))
                                FileOutput.writeToFile(pathOutput, String.format("ERROR: This device is already switched %s!", inputArray[2].toLowerCase()), true, true);
                            else
                                existingDevice.setInitialStatus(inputArray[2]);
                            isDeviceExist = true;
                            break;
                        }
                    }
                    if (!isDeviceExist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "PlugIn":
                    boolean isdeviceExist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                isdeviceExist = true;
                                if (existingDevice instanceof SmartPlug) {
                                    if (((SmartPlug) existingDevice).getIsPluggedin())
                                        throw new MyException("ERROR: There is already an item plugged in to that plug!");
                                    if (inputArray.length >= 3)
                                        ((SmartPlug) existingDevice).setAmpere(Integer.parseInt(inputArray[2]));
                                    ((SmartPlug) existingDevice).setPluggedIn();
                                    ((SmartPlug) existingDevice).setPlugInTime();
                                    break;
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart plug!", true, true);
                                    break;
                                }
                            }
                        }
                        catch (MyException e) {
                            FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                            break;
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!isdeviceExist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "PlugOut":
                    boolean isdeviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                isdeviceexist = true;
                                if (existingDevice instanceof SmartPlug) {
                                    if (inputArray.length >= 3)
                                        ((SmartPlug) existingDevice).setAmpere(Integer.parseInt(inputArray[2]));
                                    ((SmartPlug) existingDevice).setPluggedOut();
                                    ((SmartPlug) existingDevice).setPlugOutTime();
                                    break;
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart plug!", true, true);
                                    break;
                                }
                            }
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!isdeviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "SkipMinutes":
                    try {
                        int minutes = Integer.parseInt(inputArray[1]);
                        if (inputArray.length > 2)
                            throw new MyException.generalError("ERROR: Erroneous command!");
                        if (minutes < 0)
                            throw new MyException.minutesError("ERROR: Time cannot be reversed!");
                        if (minutes == 0)
                            throw new MyException.minutesError("ERROR: There is nothing to skip!");
                        Time = Time.plusMinutes(Integer.parseInt(inputArray[1]));
                    }
                    catch (MyException e) {
                        FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                    }
                    catch (Exception e) {
                        FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    }
                    break;
                case "SetTime":
                    try {
                        Time = SetTime(inputArray[1], Time);
                    }
                    catch (MyException e) {
                        FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                    }
                    catch (Exception e) {
                        FileOutput.writeToFile(pathOutput, "ERROR: Time format is not correct!", true, true);
                    }
                    break;
                case "SetSwitchTime":
                    isdeviceExist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                            existingDevice.setSwitchOnTime(SetTime(inputArray[2], Time));
                            SmartDevice.SwitchTimes.add(existingDevice.getSwitchOnTime());
                            isdeviceExist = true;
                            break;
                        }
                    }
                    if (!isdeviceExist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "SetKelvin":
                    boolean deviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                deviceexist = true;
                                if (existingDevice instanceof SmartLamp) {
                                    if (inputArray.length >= 3)
                                        ((SmartLamp) existingDevice).setKelvin(Integer.parseInt(inputArray[2]));
                                    else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a Kelvin value!", true, true);
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart lamp!", true, true);
                                    break;
                                }

                            }
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!deviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "SetBrightness":
                    deviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                deviceexist = true;
                                if (existingDevice instanceof SmartLamp) {
                                    if (inputArray.length >= 3)
                                        ((SmartLamp) existingDevice).setBrightness(Integer.parseInt(inputArray[2]));
                                    else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a Kelvin value!", true, true);
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart lamp!", true, true);
                                    break;
                                }

                            }
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!deviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "SetColorCode":
                    deviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                deviceexist = true;
                                if (existingDevice instanceof SmartColorLamp) {
                                    if (inputArray.length >= 3)
                                        ((SmartColorLamp) existingDevice).setColorCode(Integer.parseInt(inputArray[2]));
                                    else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a Kelvin value!", true, true);
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart color lamp!", true, true);
                                    break;
                                }

                            }
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!deviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "SetWhite":
                    deviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                deviceexist = true;
                                if (existingDevice instanceof SmartLamp) {
                                    if (inputArray.length >= 3)
                                        ((SmartLamp) existingDevice).setKelvin(Integer.parseInt(inputArray[2]));
                                    else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a Kelvin value!", true, true);
                                    if (inputArray.length >= 4)
                                        ((SmartLamp) existingDevice).setBrightness(Integer.parseInt(inputArray[3]));
                                    else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a brightness value!", true, true);
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart lamp!", true, true);
                                    break;
                                }

                            }
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!deviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                case "ChangeName":
                    deviceexist = false;
                    try {
                        if (inputArray[1].equals(inputArray[2])) {
                            throw new MyException.DuplicateDevices("ERROR: Both of the names are the same, nothing changed!");
                        }
                        SmartDevice device = null;
                        for (SmartDevice existingDevice : smartDevices) {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                device = existingDevice;
                                deviceexist = true;
                                break;
                            }
                        }
                        if (!deviceexist) {
                            throw new MyException.DeviceNotExist("ERROR: There is no such a device!");
                        }

                        for (SmartDevice existingDevice : smartDevices) {
                            if (existingDevice.getNameOfDevice().equals(inputArray[2])) {
                                throw new MyException("ERROR: There is already a smart device with the same name!");
                            }
                        }
                        device.setNameOfDevice(inputArray[2]);
                    }
                    catch (MyException e) {
                        FileOutput.writeToFile(pathOutput, e.getMessage(), true, true);
                    }
                    catch (Exception e) {
                        FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    }
                    break;
                case "SetColor":
                    deviceexist = false;
                    for (SmartDevice existingDevice : smartDevices) {
                        try {
                            if (existingDevice.getNameOfDevice().equals(inputArray[1])) {
                                deviceexist = true;
                                if (existingDevice instanceof SmartColorLamp) {
                                    if (inputArray.length < 3)
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a color code!", true, true);
                                    else if (inputArray.length == 3)
                                        ((SmartColorLamp) existingDevice).setColorCode(Integer.parseInt(inputArray[2].substring(2), 16));

                                    else if (inputArray.length == 4) {
                                        ((SmartColorLamp) existingDevice).setBrightness(Integer.parseInt(inputArray[3]));
                                        ((SmartColorLamp) existingDevice).setColorCode(Integer.parseInt(inputArray[2].substring(2), 16));
                                    } else
                                        FileOutput.writeToFile(pathOutput, "ERROR: There should be a brightness value!", true, true);
                                } else {
                                    FileOutput.writeToFile(pathOutput, "ERROR: This device is not a smart color lamp!", true, true);
                                    break;
                                }

                            }
                        }
                        catch (NumberFormatException e) {
                            FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                            break;
                        }
                        catch (Exception e) {
                            break;
                        }
                    }
                    if (!deviceexist)
                        FileOutput.writeToFile(pathOutput, "ERROR: There is not such a device!", true, true);
                    break;
                default:
                    FileOutput.writeToFile(pathOutput, "ERROR: Erroneous command!", true, true);
                    break;
            }
        }

    }
}
