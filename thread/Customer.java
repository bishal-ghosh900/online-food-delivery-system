package thread;

import resource.Order;
import resource.OrderQueue;
import resource.OrderStatus;

import java.util.List;
import java.util.UUID;

public class Customer implements Runnable{

    private final String customerId;
    private OrderQueue placedOrderQueue;
    private String restaurantName;
    private String address;
    private List<String> foodItems;

    public Customer(OrderQueue orderQueue, String restaurantName, String address, List<String> foodItems) {
        this.customerId = UUID.randomUUID().toString();
        this.placedOrderQueue = orderQueue;
        this.restaurantName = restaurantName;
        this.address = address;
        this.foodItems = foodItems;
    }

    @Override
    public void run() {
        Order order = new Order(customerId, restaurantName, foodItems, address);
        try {
            // simulating network latency during placing an order
            Thread.sleep(1000);
            placedOrderQueue.placeOrder(order);
            order.updateStatus(OrderStatus.PLACED);

            order.waitForStatus(OrderStatus.DELIVERED);
            System.out.println("Order successfully delivered, order id " + order.getOrderId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public OrderQueue getPlacedOrderQueue() {
        return placedOrderQueue;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getFoodItems() {
        return foodItems;
    }
}
