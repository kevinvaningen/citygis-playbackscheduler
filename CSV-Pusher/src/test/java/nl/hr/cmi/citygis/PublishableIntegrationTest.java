package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class PublishableIntegrationTest {

    private MqttBrokerClientConnector bc;

    @Before
    public void setUp() throws Exception {
        BrokerConfiguration brokerConfiguration = new BrokerConfiguration(getTestProperties());
        bc = new MqttBrokerClientConnector(brokerConfiguration);
    }

    @org.junit.Test
    public void testConnection(){
        bc.connect();
        assertTrue(bc.isConnectedToServer());
        bc.disconnectFromBroker();
    }

    public void testSendOneMessage(){
        bc.connect();
        assertTrue(bc.isConnectedToServer());
        assertTrue(bc.publish("postions", "TestMessage" + LocalDateTime.now()));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(bc.isConnectedToServer());

        bc.disconnectFromBroker();
    }

    public void testSendMultipleMessages(){
        bc.connect();
        assertTrue(bc.isConnectedToServer());
        assertTrue(bc.publish("topic/", "TestMessage0" + LocalDateTime.now()));
        assertTrue(bc.publish("topic/", "TestMessage0" + LocalDateTime.now()));
        try {
            Thread.sleep(10);
            assertTrue(bc.isConnectedToServer());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            bc.disconnectFromBroker();
        }
        assertFalse(bc.isConnectedToServer());
    }


    private Properties getTestProperties(){
        Properties p = new Properties();
        p.setProperty("MQTT_BROKER_URL","tcp://test.mosquitto.org:1883");
        p.setProperty("MQTT_CLIENT_ID","CityGis csv pusher");
        p.setProperty("MQTT_BROKER_USERNAME","");
        p.setProperty("MQTT_BROKER_PASSWORKD","");
        p.setProperty("MQTT_QOS","0");
        return p;
    }
}
