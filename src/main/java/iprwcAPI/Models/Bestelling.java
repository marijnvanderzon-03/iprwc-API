package iprwcAPI.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Entity
public class Bestelling {
    @Id
    @Column(name = "RowId")
    private String RowId;

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "Amount")
    private int Amount;

    @Column(name = "productName")
    private String productName;


    public Bestelling(){}

    public Bestelling(int amount, String productName) {
        this.RowId = UUID.randomUUID().toString();
        this.orderId =UUID.randomUUID().toString();
        this.Amount = amount;
        this.productName = productName;
    }

    public String getRowId() {
        return RowId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setRowId(String rowId) {
        RowId = rowId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderlineId) {
        this.orderId = orderlineId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
