package iprwcAPI.DAO;

import iprwcAPI.HTTPResponse;
import iprwcAPI.Model.Product;
import iprwcAPI.Repository.ProductRepository;
import iprwcAPI.RequestObjects.ProductRequestObject;
import iprwcAPI.jwt.JwtTokenUtil;
import iprwcAPI.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.util.Optional;



@Component
public class ProductDao {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private ProductRepository productRepository;

    public HTTPResponse addProduct(Product product){
        productRepository.save(product);
        return HTTPResponse.returnSuccess("product is saved");
    }

    public HTTPResponse<ProductRequestObject> getProductByName(String name){
        Optional<Product> product = productRepository.findByProduct(name);
        if (product.isEmpty()){
            return null;
        }
        ProductRequestObject obj = new ProductRequestObject(product.get().getId(),
                                                            product.get().getProduct(),
                                                            product.get().getQuantity(),
                                                            product.get().getImage(),
                                                            product.get().getPrice());
        return HTTPResponse.<ProductRequestObject>returnSuccess(obj);

    }

    public HTTPResponse addProduct(String product, Integer quantity, Blob image, Integer price){
        if (product == "" || quantity.equals(null) || image.equals(null)){
            return HTTPResponse.<Product>returnInvalidCredentials("one or more fields are empty");
        } else{
            Product prod = new Product(product, quantity, image, price);
            return HTTPResponse.<Product>returnSuccess(prod);
        }

    }

}
