import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Book {
    private String name;
    private String membershipType;
    private double price;
    private LocalDate validationDate1;
    private LocalDate validationDate2;

    public Book(String name, String membershipType, LocalDate validationDate1, LocalDate validationDate2, double price) {
        setName(name);
        setMembershipType(membershipType);
        setPrice(price);
        setValidationDate1(validationDate1);
        setValidationDate2(validationDate2);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setValidationDate1(LocalDate validationDate1) {
        this.validationDate1 = validationDate1;
    }

    public void setValidationDate2(LocalDate validationDate2) {
        this.validationDate2 = validationDate2;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getValidationDate1() {
        return validationDate1;
    }

    public LocalDate getValidationDate2() {
        return validationDate2;
    }

    public String getName() {
        return name;
    }
    public String getValidationDate1asSt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String stDate = formatter.format(validationDate1);
        return stDate;
    }

    public String getValidationDate2asSt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String stDate = formatter.format(validationDate2);
        return stDate;
    }
}
