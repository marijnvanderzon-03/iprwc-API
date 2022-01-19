package iprwcAPI.Controller;


import iprwcAPI.DAO.ProductDao;
import iprwcAPI.HTTPResponse;
import iprwcAPI.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {
    @Autowired
    ProductDao dao;

    @PostMapping("/product")
    public HTTPResponse createProduct(@RequestBody Product product){
        return dao.AddProduct(product);
    }

    @GetMapping("/product")
    public HTTPResponse getAllProducts(){
        return dao.getAllProducts();
    }

    @PutMapping("/product")
    public HTTPResponse changeProduct(@RequestBody Product[] products) {
        if (products.length == 2) {
            return dao.changeProduct(products);
        }
        return HTTPResponse.returnFailure("input length is not 2");
    }

    @DeleteMapping("/product")
    public  HTTPResponse deleteProduct(@RequestBody Product product){
        return dao.deleteProduct(product);
    }
}
