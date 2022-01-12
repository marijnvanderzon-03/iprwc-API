package iprwcAPI.Repository;

import iprwcAPI.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>{
    Optional<Product> findById(String id);
    Optional<Product> findByProduct(String product);
}
