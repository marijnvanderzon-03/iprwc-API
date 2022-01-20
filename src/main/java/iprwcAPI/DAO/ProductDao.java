package iprwcAPI.DAO;

import iprwcAPI.HTTPResponse;
import iprwcAPI.Models.Product;
import iprwcAPI.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {
    @Autowired
    ProductRepository productRep;

    public HTTPResponse<Product>AddProduct(Product givenProduct){
        Product product = new Product("", "", 1, -1);
        product.setProduct(givenProduct.getProduct());
        product.setPrice(givenProduct.getPrice());
        product.setImage(givenProduct.getImage());
        product.setQuantity(givenProduct.getQuantity());
        productRep.save(product);
        return HTTPResponse.returnSuccess(product);
    }

    public HTTPResponse changeProduct(Product[] products) {
        Product old = products[0];
        Product newObject = products[1];
        Optional<Product> p = productRep.findById(old.getId());
        if (p.isEmpty()){
            return HTTPResponse.<Product[]>returnFailure("could not find ticket with id: " + old.getId());
        }
        newObject.setId(old.getId());

        productRep.save(newObject);
        return HTTPResponse.<Product[]>returnSuccess(products);
    }

    public HTTPResponse<Product> deleteProduct(Product product) {
            Optional<Product> a = productRep.findById(product.getId());
            if (a.isEmpty()) return HTTPResponse.<Product>returnFailure("could not find ticket with id: " + product.getId());
            productRep.deleteById(product.getId());
        return HTTPResponse.<Product>returnSuccess(product);
    }

    public HTTPResponse getAllProducts() {
        return HTTPResponse.<List<Product>>returnSuccess(productRep.findAll());
    }
}
