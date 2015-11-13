package nl.hr.cmi.citygis.mocks;

import nl.hr.cmi.citygis.Publishable;

/**
 * Created by cmi on 12-11-15.
 */
public class BadMockClientBroker implements Publishable {
    @Override
    public boolean publish(String topic, String message) {
        System.out.println("Did not publish");
        return true;
    }

    @Override
    public void disconnectFromBroker() {
        System.out.println("Disconnected from broker.");
    }

    @Override
    public boolean isConnectedToServer() {
        System.out.println("Is not connected.");
        return false;
    }

    @Override
    public void connect() {
        System.out.println("Connection failed");

    }
}
