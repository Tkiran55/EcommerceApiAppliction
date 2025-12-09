package ecommerce.service;

import ecommerce.entity.CustomerOrder;
import ecommerce.repository.CustomerOrderRepository;
import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;


    public OrderService(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.productRepository = productRepository;
    }

    @Transactional //Important! If any step fails, roll back everything.
    public CustomerOrder placeOrder(Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));

        //check stock available
        if(product.getStock() < quantity){
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock());
        }

        product.setStock(product.getStock()-quantity);
        productRepository.save(product);

        //calculate total price
        double totalAmount = product.getPrice()*quantity;

        CustomerOrder order = new CustomerOrder();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(totalAmount);

        return customerOrderRepository.save(order);
    }

    public List<CustomerOrder> getAllOrders(){
        return customerOrderRepository.findAll();
    }

}
