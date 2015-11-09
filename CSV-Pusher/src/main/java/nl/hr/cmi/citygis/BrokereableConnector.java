package nl.hr.cmi.citygis;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by cmi on 09-11-15.
 */
public class BrokereableConnector implements Brokereable {
    MqttClient mqttConnectedClient;
    String topic        = "";
    String content      = "";
    int qos             = 0;
    String broker       = "tcp://localhost:1883";
    String clientId     = "CityGis csv pusher";

    public BrokereableConnector(){
        System.out.println("Setting up "+BrokereableConnector.class.getSimpleName()+ " using default configuration.");
    }

    public BrokereableConnector(String brokerUrl, String clientId, int qos) {
        //TODO pull up connection vars to ne object
        this.broker = brokerUrl;
        this.clientId = clientId;
        this.qos = qos;
    }

    public void connect(){
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            //TDOO create configuration file for message broker settings
            mqttConnectedClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            mqttConnectedClient.connect(connOpts);
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
        if(mqttConnectedClient==null){
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