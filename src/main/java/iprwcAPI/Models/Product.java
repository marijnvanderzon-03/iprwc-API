package iprwcAPI.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "image")
    private String image;

    @Column(name = "product")
    private String product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "amountInCart")
    private int amountInCart;


    public Product(){}

    public Product(String image, String product, int quantity, int price, int amountInCart) {
        this.image = image;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.amountInCart = amountInCart;
        this.id = UUID.randomUUID().toString();
    }

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
