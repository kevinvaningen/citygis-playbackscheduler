package nl.hr.cmi.citygis.mocks;

import nl.hr.cmi.citygis.Publishable;
import nl.hr.cmi.citygis.configuration.BrokerConfiguration;

/**
 * Created by cmi on 09-11-15.
 */
public class MockClientBroker implements Publishable {

    public MockClientBroker() {
    }
    public MockClientBroker(BrokerConfiguration brokerConfiguration) {
    }



    @Override
    public boolean publish(String topic, String message) {
        System.out.println("Mock succesfull event sended at: "+ topic + " Message is: "+message);
        return true;
    }

    @Override
    public void disconnectFromBroker() {
        System.out.println("Mock disconnect.");
    }

    @Override
    public boolean isConnectedToServer() {
        System.out.println("Returned isConnectedTrue");
        return true;
    }

    @Override
    public void connect() {
        System.out.println("Mock created connection.");
    }
}
