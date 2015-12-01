package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.configuration.BrokerConfiguration;

import java.util.Calendar;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class PublishableIntegrationTest extends TestCase {

    MqttBrokerClientConnector bc;
    Calendar c;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PublishableIntegrationTest(String testName) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( PublishableIntegrationTest.class );
    }

    public void setUp() throws Exception {
        BrokerConfiguration brokerConfiguration = new BrokerConfiguration(getTestProperties());
        bc = new MqttBrokerClientConnector(brokerConfiguration);
        c = Calendar.getInstance();
        super.setUp();
    }

    public void testConnection(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        bc.disconnectFromBroker();
    }

    @org.junit.Test
    public void testSendOneMessage(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(bc.publish("postions", "TestMessage" + c.getTime()));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(bc.isConnectedToServer());

        bc.disconnectFromBroker();
    }

    public void testSendMultipleMessages(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/", "TestMessage0" + c.getTime()));
        try {
            Thread.sleep(10);
            Assert.assertTrue(bc.isConnectedToServer());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            bc.disconnectFromBroker();
        }
        Assert.assertFalse(bc.isConnectedToServer());
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
