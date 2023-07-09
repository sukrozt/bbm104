public class MyException extends Exception {
    /**
     * Throws exception for obligated situations which is determined from device program
     *
     * @param message error message which is going to be written on output file if error is occurred.
     */
    public MyException(String message) {
        super(message);
    }

    /**
     * Returns the message which is given as argument. Used to check if the device is exists or not.
     */
    public static class DeviceNotExist extends MyException {
        public DeviceNotExist(String message) {
            super(message);
        }
    }

    /**
     * Returns the message which is given as argument. Used to check if the device is used twice.
     */
    public static class DuplicateDevices extends MyException {
        public DuplicateDevices(String message) {
            super(message);
        }
    }

    /**
     * Returns the message which is given as argument. General error for all erroneous commands.
     */
    public static class generalError extends MyException {
        public generalError(String message) {
            super(message);
        }
    }

    /**
     * Returns the message which is given as argument. Used to check if the minutes are positive or not.
     */
    public static class minutesError extends MyException {
        public minutesError(String message) {
            super(message);
        }
    }
}
