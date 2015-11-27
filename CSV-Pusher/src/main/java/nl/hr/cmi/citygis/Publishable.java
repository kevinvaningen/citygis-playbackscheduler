package nl.hr.cmi.citygis;

/**
 * Created by cmi on 09-11-15.
 */
public interface Publishable {

    /**
     * Method that makes a connection and publishes the message to the server topic.
     *
     * @param topic
     * @param message
     * @return
     */
    public boolean publish(String topic, String message);

    /**
     * Active disconnect
     */
    public void disconnectFromBroker();

    /**
     * returns the connnection status
     * @return
     */
    public boolean isConnectedToServer();
    public void connect();
}
