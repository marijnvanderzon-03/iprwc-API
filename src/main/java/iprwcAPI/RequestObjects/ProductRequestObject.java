package iprwcAPI.RequestObjects;

import java.sql.Blob;

public class ProductRequestObject {

    String id;
    String product;
    Integer quantity;
    Blob image;
    Integer price;

    public ProductRequestObject(String id, String product, Integer quantity, Blob image, Integer price) {
        this.id = id;
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

    public void setProduct(String procuct) {
        this.product = procuct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
