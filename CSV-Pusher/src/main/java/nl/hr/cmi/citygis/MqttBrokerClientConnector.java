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

/**
 * Created by cmi on 09-11-15.
 */
public class MqttBrokerClientConnector implements Publishable {
    private MqttClient mqttConnectedClient;
    private MqttConnectOptions connectionOptions = new MqttConnectOptions();

    private int qos;
    private String broker;
    private String clientId;
    private final static Logger LOGGER = LoggerFactory.getLogger(MqttBrokerClientConnector.class);


    public MqttBrokerClientConnector() {
        LOGGER.debug("Setting up " + MqttBrokerClientConnector.class.getSimpleName() + ", defaulting to config.properties configuration.");
        ConfigurationReader configurationReader = new ConfigurationReader();
        BrokerConfiguration brokerConfiguration = configurationReader.getBrokerConfiguration();
        setConnectionProperties(brokerConfiguration);
    }

    public MqttBrokerClientConnector(BrokerConfiguration brokerConfiguration) {
        LOGGER.debug("Setting up " + MqttBrokerClientConnector.class.getSimpleName() + " using broker argument configuration.");
        setConnectionProperties(brokerConfiguration);
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

    public void connect() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            //TDOO create configuration file for message broker settings
            mqttConnectedClient = new MqttClient(broker, clientId, persistence);
            mqttConnectedClient.connect(connectionOptions);
            LOGGER.info("Connected to broker at:" + mqttConnectedClient.getServerURI());
        } catch (MqttException me) {
            LOGGER.error("Failed to connect to broker" + broker + " at " + mqttConnectedClient.getServerURI());

            printException(me);
            me.printStackTrace();
        }
    }

    public boolean isConnectedToServer() {
        if (mqttConnectedClient == null) {
            return false;
        }
        return mqttConnectedClient.isConnected();
    }

    public void disconnectFromBroker() {
        try {
            mqttConnectedClient.disconnect();
            LOGGER.debug("Broker disconnected from:");
        } catch (MqttException me) {
            printException(me);
            me.printStackTrace();
        }
    }

    @Override
    public boolean publish(String topic, String message) {
        if (isConnectedToServer()) {
            try {
                LOGGER.debug("Publishing message: " + message);
                MqttMessage Mqttmessage = new MqttMessage(message.getBytes());
                Mqttmessage.setQos(qos);

                mqttConnectedClient.publish(topic, Mqttmessage);

                return true;
            } catch (MqttException me) {
                printException(me);
                me.printStackTrace();
            }
        } else {
            connect();
            publish(topic, message);
        }
        return false;
    }

    private void printException(MqttException me) {
        LOGGER.error("reason " + me.getReasonCode() +
                " msg " + me.getMessage() +
                " loc " + me.getLocalizedMessage() +
                " cause " + me.getCause() +
                " excep " + me.toString());
    }
}