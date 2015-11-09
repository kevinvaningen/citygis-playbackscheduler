package nl.hr.cmi.citygis;

/**
 * Created by cmi on 09-11-15.
 */
public interface Brokereable {

    public boolean publish(String topic, String message);
    public void disconnectFromBroker();
    public boolean isConnectedToServer();
    public void connect();
}
