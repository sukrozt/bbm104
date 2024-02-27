import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Purchaser {
    static int i = 0;
    static Member[] members = new Member[40];

    public static ArrayList<String> dataLister(String pathInput) {
        String[] purcArray = FileInput.readFile(pathInput, true, false);
        ArrayList<String> purchArrayList = new ArrayList<>(Arrays.asList(purcArray));
        return purchArrayList;
    }

    public static ArrayList<Purchase> writeMemberData(String pathOutput, ArrayList<String> memberData) {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        Member member = new Member(memberData.get(0), memberData.get(1), convertDate(memberData.get(2)));
        members[i] = member;
        Purchase purchase = new Purchase(memberData.get(0), memberData.get(1), convertDate(memberData.get(2)),
                memberData.get(3), Integer.parseInt(memberData.get(4)));
        purchases.add(purchase);
        if(memberData.size() > 5 && memberData.get(5) != null && (memberData.get(6)) != null){
        Purchase purchase2 = new Purchase(memberData.get(0), memberData.get(1), convertDate(memberData.get(2)),
                memberData.get(5), Integer.parseInt(memberData.get(6)));
            purchases.add(purchase2);}
        i++;
        FileOutput.writeToFile(pathOutput, String.format("------------Bill for Customer %d-------------", i), true, true);
        FileOutput.writeToFile(pathOutput, "Customer: " + member.getName() + "\nMembership Type: " +
                member.getMembershipType() + "\nDate: " + member.getDateasSt(), true, true);
        return purchases;
    }

    public static void writeShoppingData(String pathOutput, ArrayList<String> books, ArrayList<Purchase> purchases) {
        double bill;
        Book[] bookList = new Book[40];
        int i = 0;
        for (String line : books) {
            String[] bookArray = line.split("\t");
            ArrayList<String> bookData = new ArrayList<>(Arrays.asList(bookArray));
            Book book = new Book(bookData.get(0), bookData.get(1), convertDate(bookData.get(2)), convertDate(bookData.get(3)), Double.parseDouble(bookData.get(4)));
            bookList[i] = book;
            i++;
        }
        FileOutput.writeToFile(pathOutput, "\nItems Purchased:", true, true);
        double totalBill = 0;
        for (Purchase purchase : purchases) {
            for (Book book : bookList) {
                if(book != null && purchase != null){
                    if (purchase.getMembershipType().equals(book.getMembershipType()) && purchase.getDate().isAfter(book.getValidationDate1())
                            && purchase.getDate().isBefore(book.getValidationDate2()) && purchase.getPurchaseName().equals(book.getName())) {
                        int quantity = purchase.getQuantity();
                        double price = book.getPrice();
                        bill = quantity * price;
                        totalBill += bill;
                        purchase.setBill(bill);
                        FileOutput.writeToFile(pathOutput, book.getName() + String.format(" (Qty: %d) - %.2f each\n(Valid from %s to %s)\nSubtotal: %.2f",
                                quantity, price, book.getValidationDate1asSt(), book.getValidationDate2asSt(), bill), true, true);
                    }
                }
            }
        }
        FileOutput.writeToFile(pathOutput, String.format("\nTotal Cost: %.2f\n", totalBill), true, true);
    }

    public static LocalDate convertDate(String stDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(stDate, formatter);
    }
}
