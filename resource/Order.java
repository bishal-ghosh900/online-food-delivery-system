package resource;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Order {

    private final String orderId;
    private final String customerId;
    private String restaurantName;
    private List<String> foodItems;
    private String address;
    private OrderStatus status;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public Order(String customerId, String restaurantName, List<String> foodItems, String address) {
        this.orderId = "Order_" + UUID.randomUUID().toString();
        this.customerId = customerId;
        this.restaurantName = restaurantName;
        this.foodItems = foodItems;
        this.address = address;
    }

    public void waitForStatus(OrderStatus expectedStatus) throws InterruptedException {
        lock.lock();
        try {
            while(!expectedStatus.equals(this.status)) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void updateStatus(OrderStatus status) {
        lock.lock();
        try {
            this.status = status;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<String> getFoodItems() {
        return foodItems;
    }

    public String getAddress() {
        return address;
    }
}
