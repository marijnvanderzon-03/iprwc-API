package iprwcAPI.Repository;

import iprwcAPI.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findById(String id);

    void deleteById(String id);
}
