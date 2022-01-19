package iprwcAPI.Repository;

import iprwcAPI.Models.Bestelling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Bestelling, String> {
    Optional<Bestelling> findByOrderId(String id);


}
