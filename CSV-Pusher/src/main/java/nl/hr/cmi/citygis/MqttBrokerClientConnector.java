package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import nl.hr.cmi.citygis.configuration.ConfigurationReader;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by cmi on 09-11-15.
 */
public class MqttBrokerClientConnector implements Publishable {
    MqttClient mqttConnectedClient;
    MqttConnectOptions connectionOptions = new MqttConnectOptions();

    private int qos;
    private String broker;
    private String clientId;

    public MqttBrokerClientConnector(){
        System.out.println("Setting up "+MqttBrokerClientConnector.class.getSimpleName()+ ", defaulting to config.properties configuration.");
        ConfigurationReader configurationReader = new ConfigurationReader();
        BrokerConfiguration brokerConfiguration = configurationReader.getBrokerConfiguration();
        setConnectionProperties(brokerConfiguration);
        }

    public MqttBrokerClientConnector(BrokerConfiguration brokerConfiguration){
        System.out.println("Setting up "+MqttBrokerClientConnector.class.getSimpleName()+ " using broker argument configuration.");
        setConnectionProperties(brokerConfiguration);
    }

    public MqttBrokerClientConnector(String brokerUrl, String clientId, int qos, BrokerConfiguration brokerConfiguration) {
        System.out.println("Setting up "+MqttBrokerClientConnector.class.getSimpleName()+ " using argument configuration.");
        this.broker = brokerUrl;
        this.clientId = clientId;
        this.qos = qos;
        connectionOptions.setPassword(brokerConfiguration.getBrokerPassword().toCharArray());
        connectionOptions.setUserName(brokerConfiguration.getBrokerUsername());

    }
    private void setConnectionProperties(BrokerConfiguration brokerConfiguration){
        this.qos = brokerConfiguration.getBrokerQos();
        this.broker = brokerConfiguration.getBrokerUrl();
        this.clientId = brokerConfiguration.getClientId();
        connectionOptions.setCleanSession(true);
        if(brokerConfiguration.usesCredentials()) {
            connectionOptions.setUserName(brokerConfiguration.getBrokerUsername());
            connectionOptions.setPassword(brokerConfiguration.getBrokerPassword().toCharArray());
        }
    }

    public void connect(){
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            //TDOO create configuration file for message broker settings
            mqttConnectedClient = new MqttClient(broker, clientId, persistence);
            System.out.println("Connecting to broker: " + broker);
            mqttConnectedClient.connect(connectionOptions);
            System.out.println("Connected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public boolean isConnectedToServer(){
        if(mqttConnectedClient== null){
            return false;
        }
        return mqttConnectedClient.isConnected();
    }

    public void disconnectFromBroker(){
        try {
            mqttConnectedClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    @Override
    public boolean publish(String topic, String message) {
        if(isConnectedToServer()) {
            try {
                System.out.println("Publishing message: " + message);

                MqttMessage Mqttmessage = new MqttMessage(message.getBytes());
                Mqttmessage.setQos(qos);

                mqttConnectedClient.publish(topic, Mqttmessage);
                System.out.println("Message published");
                return true;
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            }
        }
        else{
            connect();
            publish(topic,message);
        }
        return false;
    }
}