import java.util.LinkedList;

public class countInBinary extends baseConverter{
    public static LinkedList<String> countBinary(int decimalNumber) {
        LinkedList<String> binaryLine = new LinkedList<String>();
        for(int i = 1; i <= decimalNumber; i++)
            binaryLine.add(baseConverter.baseConvert(i));
        return binaryLine;
    }
}
