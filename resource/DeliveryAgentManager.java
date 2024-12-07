package resource;

import java.util.concurrent.Semaphore;

public class DeliveryAgentManager {
    private final Semaphore agentManager;

    public DeliveryAgentManager(int numberOfAgents) {
        this.agentManager = new Semaphore(numberOfAgents);
    }

    public boolean acquireAgent() {
        try {
            return agentManager.tryAcquire();
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void releaseAgent() {
        agentManager.release();
    }

    public int activeAgents() {
        return agentManager.availablePermits();
    }
}
