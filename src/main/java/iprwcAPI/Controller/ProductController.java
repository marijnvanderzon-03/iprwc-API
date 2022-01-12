package iprwcAPI.Controller;

import iprwcAPI.DAO.ProductDao;
import iprwcAPI.HTTPResponse;
import iprwcAPI.RequestObjects.ProductRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @GetMapping("/product")
    public HTTPResponse getProduct(@RequestParam(name = "product", defaultValue = "")String product){
        if (product.equals("")){
            return HTTPResponse.returnFailure("noProduct was given");
        } else {
            return productDao.getProductByName(product);
        }
    }

    @PostMapping("/product")
    public HTTPResponse saveProduct(@RequestBody ProductRequestObject product){
        return productDao.addProduct(product.getProduct(), product.getPrice(), product.getImage(), product.getQuantity());

    }
}
