package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import nl.hr.cmi.citygis.configuration.ConfigurationReader;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/***
 * MqttBrokerClientConnector is an implemenation of Publishable. It connects to a MQTT broker using a socket connection.
 */
public class MqttBrokerClientConnector implements Publishable {
    private MqttClient mqttConnectedClient;
    private MqttConnectOptions connectionOptions = new MqttConnectOptions();

    private int qos;
    private String broker;
    private String clientId;
    private final static Logger LOGGER = LoggerFactory.getLogger(MqttBrokerClientConnector.class);


    /***
     * Instantiate the broker connector using config.properties set configuration
     */
    public MqttBrokerClientConnector() {
        LOGGER.debug("Setting up " + MqttBrokerClientConnector.class.getSimpleName() + "without arguments, defaulting to config.properties file-based configuration.");
        ConfigurationReader configurationReader = new ConfigurationReader();
        //read and set file-based broker configuration
        setConnectionProperties(configurationReader.getBrokerConfiguration());
    }

    /***
     * Instantiate the broker connector using overloaded arguments
     *
     * @param brokerConfiguration provide the configuration arguments of an MQTT compatible broker.
     */
    public MqttBrokerClientConnector(BrokerConfiguration brokerConfiguration) {
        LOGGER.debug("Setting up " + MqttBrokerClientConnector.class.getSimpleName() + " using broker argument configuration.");
        setConnectionProperties(brokerConfiguration);
    }

    /***
     * Create an active socket connection to the broker without sending data.
     */
    public void connect() {
        try {
            mqttConnectedClient = new MqttClient(broker, clientId, new MemoryPersistence());
            mqttConnectedClient.connect(connectionOptions);
            LOGGER.info("Connected to broker at:" + mqttConnectedClient.getServerURI());
        } catch (MqttException me) {
            LOGGER.error("Failed to connect to broker" + broker + " at " + mqttConnectedClient.getServerURI());
            me.printStackTrace();
        }
    }

    /***
     * Helper method to diagnose connection status.
     * @return true for a connected status
     */
    public boolean isConnectedToServer() {
        if (mqttConnectedClient == null) {
            return false;
        }
        return mqttConnectedClient.isConnected();
    }

    /***
     * Actively close the connectino to the broger.
     */
    public void disconnectFromBroker() {
        try {
            mqttConnectedClient.disconnect();
            LOGGER.debug("Broker disconnected from:");
        } catch (NullPointerException n) {
            LOGGER.debug("Broker didnt existed.");
        } catch (MqttException me) {
            logMqttException(me);
            me.printStackTrace();
        }
    }

    /***
     * Checks for a connections (makes whan when not active) and pushes a message to the topic.
     * @param topic   this is the mqtt channel where messages are published
     * @param message a textual String that is publisheable to MQTT
     * @return the status of the publication. True when not exceptions occured.
     */
    @Override
    public boolean publish(String topic, String message) {
        if (isConnectedToServer()) {
            try {
                LOGGER.debug("Publishing message: " + message);
                MqttMessage Mqttmessage = new MqttMessage(message.getBytes("UTF8"));
                Mqttmessage.setQos(qos);

                mqttConnectedClient.publish(topic, Mqttmessage);

                return true;
            } catch (MqttException me) {
                logMqttException(me);
                return false;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Unsupported UTF8 encoding exception.");
                return false;
            }
        } else {
            connect();
            publish(topic, message);
        }
        return false;
    }

    private void setConnectionProperties(BrokerConfiguration brokerConfiguration) {
        this.qos = brokerConfiguration.getBrokerQos();
        this.broker = brokerConfiguration.getBrokerUrl();
        this.clientId = brokerConfiguration.getClientId();
        connectionOptions.setCleanSession(true);
        if (brokerConfiguration.usesCredentials()) {
            connectionOptions.setUserName(brokerConfiguration.getBrokerUsername());
            connectionOptions.setPassword(brokerConfiguration.getBrokerPassword().get().toCharArray());
        }
    }

    private void logMqttException(MqttException me) {
        LOGGER.error("reason " + me.getReasonCode() +
                " msg " + me.getMessage() +
                " loc " + me.getLocalizedMessage() +
                " cause " + me.getCause() +
                " excep " + me.toString());
    }
}