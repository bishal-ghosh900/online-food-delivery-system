package thread;

import resource.DeliveryAgentManager;
import resource.Order;
import resource.OrderQueue;
import resource.OrderStatus;

public class DeliveryAgent implements Runnable{

    private final String agentName;
    private final DeliveryAgentManager agentManager;
    private final OrderQueue preparedOrderQueue;

    public DeliveryAgent(String agentName, DeliveryAgentManager agentManager, OrderQueue preparedOrderQueue) {
        this.agentName = agentName;
        this.agentManager = agentManager;
        this.preparedOrderQueue = preparedOrderQueue;
    }

    @Override
    public void run() {
        int activeAgents = agentManager.activeAgents();
        try{
            while (true) {
                Order order = preparedOrderQueue.nextOrder();
                if(agentManager.acquireAgent()) {
                    order = preparedOrderQueue.pickPreparedOrder();
                    // simulating delivery time
                    Thread.sleep(4000);
                    order.updateStatus(OrderStatus.DELIVERED);
                    agentManager.releaseAgent();
                    System.out.println("Delivery completed by " + agentName);
                } else if(order != null) {
                    System.out.println("Waiting for an agent for the order: " + order.getOrderId());
                    Thread.sleep(1000);
                }
            }

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
