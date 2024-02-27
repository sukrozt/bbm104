import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Member {
    private String name;
    private String membershipType;
    private LocalDate date;

    Member(String name, String membership, LocalDate date) {
        setLocalDate(date);
        setName(name);
        setMembershipType(membership);
    }

    public void setLocalDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getName() {
        return name;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public LocalDate getDate() {
        return date;
    }
    public String getDateasSt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(date);
    }
}
