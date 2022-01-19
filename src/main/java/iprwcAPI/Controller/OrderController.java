package iprwcAPI.Controller;

import iprwcAPI.DAO.OrderDao;
import iprwcAPI.HTTPResponse;
import iprwcAPI.Models.Bestelling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class OrderController {
    @Autowired
    OrderDao dao;

    @PostMapping("/order")
    public HTTPResponse createOrder(@RequestBody Bestelling order){
        return dao.AddOrder(order);
    }

    @GetMapping("/order")
    public HTTPResponse getAllOrders(){
        return dao.getAllOrders();
    }

    @GetMapping("/orderbyId")
    public HTTPResponse getOrderById(@RequestParam(name= "id", defaultValue="")String id){
        return dao.getOrderbyId(id);
    }
    @GetMapping("/orderId")
    public HTTPResponse getOrderId(){
        return dao.getNewOrderId();
    }

    @PutMapping("/order")
    public HTTPResponse changeProduct(@RequestBody Bestelling[] orders) {
        if (orders.length == 2) {
            return dao.changeOrder(orders);
        }
        return HTTPResponse.returnFailure("input length is not 2");
    }

    @DeleteMapping("/order")
    public  HTTPResponse deleteOrder(@RequestBody Bestelling order){
        return dao.deleteOrder(order);
    }
}
