package iprwcAPI.Model;

import javax.persistence.*;
import java.sql.Blob;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "product")
    private String product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "image")
    private Blob image;

    @Column(name = "price")
    private Integer price;

    public Product(String product, Integer quantity, Blob image, Integer price){
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
