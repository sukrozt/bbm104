import java.util.LinkedList;

public class baseConverter<T> {
    public static String baseConvert(int decimalNumber) {
        LinkedList<Integer> binary = new LinkedList<>();
        String binaryNumber = "";
        if (decimalNumber == 0) {
            binary.add(0);
        }

        while (decimalNumber > 0) {
            int remainder = decimalNumber % 2;
            binary.addFirst(remainder);
            decimalNumber /= 2;
        }
        for (int i : binary) {
            binaryNumber += String.valueOf(i);
        }
        return binaryNumber;
    }

}
