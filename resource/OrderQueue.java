package resource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OrderQueue {
    private final BlockingQueue<Order> placedOrderQueue;
    private final BlockingQueue<Order> preparedOrderQueue;

    public OrderQueue(int size) {
        this.placedOrderQueue = new ArrayBlockingQueue<>(size);
        this.preparedOrderQueue = new ArrayBlockingQueue<>(size);
    }

    public void placeOrder(Order order) throws InterruptedException {
        placedOrderQueue.put(order);
        System.out.println("Order placed: " + order.getOrderId());
    }

    public void placePreparedOrder(Order order) throws InterruptedException {
        preparedOrderQueue.put(order);
        System.out.println("Order is prepared: " + order.getOrderId());
    }

    public Order pickPlacedOrder() throws InterruptedException {
        Order order = placedOrderQueue.take();
        order.waitForStatus(OrderStatus.PLACED);
        System.out.println("Placed order is picked by restaurant, order id:  " + order.getOrderId());
        return order;
    }

    public Order pickPreparedOrder() throws InterruptedException {
        Order order = preparedOrderQueue.take();
        order.waitForStatus(OrderStatus.PREPARED);
        System.out.println("Order is picked by delivery agent, order id: " + order.getOrderId());
        return order;
    }

    public Order nextOrder() {
        return preparedOrderQueue.peek();
    }

    public int availablePlacedOrder() {
        return placedOrderQueue.size();
    }

    public int availablePreparedOrder() {
        return preparedOrderQueue.size();
    }
}
