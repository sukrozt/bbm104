import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        String purchaseOrder = args[0];
        String priceCatalog = args[1];
        FileOutput.writeToFile("output.txt", "", false, false);
        ArrayList<String> memberData = Purchaser.dataLister(purchaseOrder);
        ArrayList<String> bookData = Purchaser.dataLister(priceCatalog);
        for(String line : memberData){
            String[] lineSplited = line.split("\t");
            ArrayList<String> purchaseDetails = new ArrayList<String>(Arrays.asList(lineSplited));
        ArrayList<Purchase>purchases = Purchaser.writeMemberData("output.txt", purchaseDetails);
        Purchaser.writeShoppingData("output.txt", bookData, purchases);
        }
    }
}

//Ozgun Serergun Koca 2220765042