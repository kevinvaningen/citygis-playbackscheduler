package nl.hr.cmi.citygis;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Hello world!
 */
public class ApplicationReceiver implements MqttCallback {
    private String url = "tcp://145.24.222.132:8883";
    private String clientId = "Receiving";
    private String user = "";
    private String pass = "";
    private MqttClient client;
    private int connectionRetries = 5;
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationReceiver.class);

    private String[] topics = {"POSITIONS", "MONITORING", "EVENTS", "CONNECTIONS"};

    public static void main(String[] args) {
        new ApplicationReceiver().connectAndListen();
    }

    /***
     * Connect to the broker and start listening.
     */
    public void connectAndListen() {
        LOGGER.debug("Connect to message broker at." + url);

        try {
            client = new MqttClient(url, clientId);
            client.connect(createConnectionProperties(user, pass));
            client.setCallback(this);
            Arrays.stream(topics).forEach(t -> {
                try {
                    client.subscribe(t);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
            LOGGER.error("Connection failed." + e.getMessage());

        }
    }

    /***
     * Is called when connection is lost.
     *
     * @param cause
     */
    public void connectionLost(Throwable cause) {
        LOGGER.error("Connection lost." + cause.getMessage());

        connectionRetries--;
        if (connectionRetries != 0) {
            LOGGER.error("Retrying establishing a connection.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectAndListen();
        }
    }

    /***
     * Callback methods for each received method.
     *
     * @param topic   the topic where the message originated from.
     * @param message the contents of the actual message
     * @throws Exception
     */
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        System.out.println(message);
    }

    /***
     * Is not called in practice.
     *
     * @param token
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.debug("Delivery complete." + token.toString());
    }

    private MqttConnectOptions createConnectionProperties(String user, String password) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();

        connectOptions.setCleanSession(true);
        if (user != null && user.length() > 0) {
            connectOptions.setUserName(user);
            connectOptions.setPassword(password.toCharArray());
        }
        return connectOptions;
    }
}
