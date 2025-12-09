package ecommerce.controller;

import ecommerce.entity.CustomerOrder;
import ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CustomerOrder placeOrder(@RequestBody OrderRequest request){
        return orderService.placeOrder(request.productId, request.quantity);

    }

    @GetMapping
    public List<CustomerOrder> getAllOrders(){
        return orderService.getAllOrders();
    }

    public static class OrderRequest {
        public Long productId;
        public int quantity;
    }
}
