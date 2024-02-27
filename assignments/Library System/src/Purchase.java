
import java.time.LocalDate;

public class Purchase extends Member{
    private String purchaseName;
    private int quantity;
    private double bill;
    public Purchase(String name, String membership, LocalDate date,String purchaseName, int quantity){
        super(name, membership, date);
        setPurchaseName(purchaseName);
        setQuantity(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

}
