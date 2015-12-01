package nl.hr.cmi.citygis.mocks;

import nl.hr.cmi.citygis.Publishable;

/***
 * Mocked broker which returns faults.
 */
public class BadMockClientBroker implements Publishable {
    @Override
    public boolean publish(String topic, String message) {
        System.out.println("Did not publish");
        return false;
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
