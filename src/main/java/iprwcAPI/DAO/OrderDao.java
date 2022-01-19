package iprwcAPI.DAO;

import iprwcAPI.HTTPResponse;
import iprwcAPI.Models.Bestelling;
import iprwcAPI.Models.Product;
import iprwcAPI.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDao {
    @Autowired
    OrderRepository OrderRep;

    public HTTPResponse<Bestelling> AddOrder(Bestelling givenOrder){
        Bestelling order = new Bestelling(-1, "");
        order.setAmount(givenOrder.getAmount());
        order.setProductName(givenOrder.getProductName());
        order.setOrderId(givenOrder.getOrderId());
        if (!givenOrder.getOrderId().equals("")){
            order.setOrderId(givenOrder.getOrderId());
        }

        OrderRep.save(order);
        return HTTPResponse.returnSuccess(order);
    }

    public HTTPResponse changeOrder(Bestelling[] orders) {
        Bestelling old = orders[0];
        Bestelling newObject = orders[1];
        Optional<Bestelling> o = OrderRep.findById(old.getRowId());
        if (o.isEmpty()){
            return HTTPResponse.<Product[]>returnFailure("could not find order with id: " + old.getRowId());
        }
        newObject.setRowId(old.getRowId());

        OrderRep.save(newObject);
        return HTTPResponse.<Bestelling[]>returnSuccess(orders);
    }

    public HTTPResponse<Bestelling> deleteOrder(Bestelling order) {
        Optional<Bestelling> a = OrderRep.findById(order.getOrderId());
        if (a.isEmpty()) return HTTPResponse.<Bestelling>returnFailure("could not find order with id: " + order.getOrderId());
        OrderRep.deleteById(order.getOrderId());
        return HTTPResponse.<Bestelling>returnSuccess(order);
    }

    public HTTPResponse getNewOrderId(){
        String newId = UUID.randomUUID().toString();
        System.out.println(newId);
        return HTTPResponse.<String>returnSuccess(newId);
    }

    public HTTPResponse getAllOrders() {
        return HTTPResponse.<List<Bestelling>>returnSuccess(OrderRep.findAll());
    }

    public HTTPResponse getOrderbyId(String id){
        return HTTPResponse.<List<Bestelling>>returnSuccess(OrderRep.findAllById(Collections.singleton(id)));
    }
}
