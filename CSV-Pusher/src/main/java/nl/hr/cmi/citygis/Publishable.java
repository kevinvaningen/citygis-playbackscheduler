package nl.hr.cmi.citygis;

/***
 * Interface for broker-type connectors.
 */
public interface Publishable {

    /**
     * Method that makes a connection and publishes the message to the server topic.
     *
     * @param topic   this is the mqtt channel where messages are published
     * @param message a textual String that is publisheable to MQTT
     * @return returns the status of succesfull publication. True means the message is succesfully published.
     */
    boolean publish(String topic, String message);


    /**
     * Active disconnect
     */
    void disconnectFromBroker();

    /**
     * @return returns the connection status. True means an open socket connection.
     */
    boolean isConnectedToServer();

    void connect();
}
