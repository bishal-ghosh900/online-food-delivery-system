package thread;

import resource.Order;
import resource.OrderQueue;
import resource.OrderStatus;

public class Restaurant implements Runnable {

    private final OrderQueue orderQueue;

    public Restaurant(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        try {
            Order order = orderQueue.pickPlacedOrder();
            // simulating time for preparing the food.
            Thread.sleep(2000);
            orderQueue.placePreparedOrder(order);
            order.updateStatus(OrderStatus.PREPARED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
