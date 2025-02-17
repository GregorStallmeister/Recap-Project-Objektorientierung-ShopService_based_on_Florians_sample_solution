import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus (OrderStatus orderStatus) {
       return orderRepo.getOrders()
               .stream()
               .filter(order -> order.orderStatus().equals(orderStatus))
               .toList();
    }

    public void updateOrder(String orderID, OrderStatus newStatus) {
        Order order = orderRepo.getOrderById(orderID);

        Order modifiedOrder = order.withOrderStatus(newStatus);
        orderRepo.removeOrder(orderID);
        orderRepo.addOrder(modifiedOrder);
    }
}
