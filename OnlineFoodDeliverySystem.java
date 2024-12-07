import resource.DeliveryAgentManager;
import resource.OrderQueue;
import thread.Customer;
import thread.DeliveryAgent;
import thread.Restaurant;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OnlineFoodDeliverySystem {
    public static void main(String[] args) {
        // TODO create the ExecutorService
        ExecutorService ex = Executors.newFixedThreadPool(8);
        try(ex) {
            // TODO create the OrderQueue
            OrderQueue orderQueue = new OrderQueue(3);

            // TODO create the Customer threads
            Customer customer1 = new Customer(
                    orderQueue,
                    "ABC",
                    "Addrress1",
                    List.of("Biriyani", "Fried Rice"));

            Customer customer2 = new Customer(
                    orderQueue,
                    "ABC",
                    "Addrress1",
                    List.of("Biriyani", "Fried Rice"));

            Customer customer3 = new Customer(
                    orderQueue,
                    "ABC",
                    "Addrress1",
                    List.of("Biriyani", "Fried Rice"));

            // TODO create the Restaurant threads
            Restaurant restaurant1 = new Restaurant(orderQueue);
            Restaurant restaurant2 = new Restaurant(orderQueue);
            Restaurant restaurant3 = new Restaurant(orderQueue);

            // TODO create the DeliveryAgentManagaer
            DeliveryAgentManager agentManager = new DeliveryAgentManager(1);

            // TODO create the DeliveryAgent threads
            DeliveryAgent agent1 = new DeliveryAgent("agent1", agentManager, orderQueue);
            DeliveryAgent agent2 = new DeliveryAgent("agent2", agentManager, orderQueue);

            // TODO execute the Customer threads from ExecutorService
            ex.execute(customer1);
            ex.execute(customer2);
            ex.execute(customer3);

            // TODO execute the Restaurant threads from ExecutorService
            ex.execute(restaurant1);
            ex.execute(restaurant2);
            ex.execute(restaurant3);

            // TODO execute the DeliveryAgent threads from ExecutorService
            ex.execute(agent1);
            ex.execute(agent2);

            // TODO shutdown the ExecutorService
            ex.shutdown();
        }
    }
}
