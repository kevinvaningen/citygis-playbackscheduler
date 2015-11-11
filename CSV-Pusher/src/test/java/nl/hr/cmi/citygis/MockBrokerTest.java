package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import nl.hr.cmi.citygis.mocks.MockClientBroker;

import java.util.Calendar;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class MockBrokerTest extends TestCase {

    Publishable bc;
    Calendar c;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MockBrokerTest(String testName) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( MockBrokerTest.class );
    }

    public void setUp() throws Exception {
        BrokerConfiguration brokerConfiguration = new BrokerConfiguration(getTestProperties());
        bc = new MockClientBroker(brokerConfiguration);
        c = Calendar.getInstance();
        super.setUp();
    }


    public void testConnection(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        bc.disconnectFromBroker();
    }

    public void testSendOneMessage(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(bc.publish("postions","TestMessage" + c.getTime()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(true);

        bc.disconnectFromBroker();
    }

    public void testSendMultipleMessages(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/", "TestMessage0" + c.getTime()));
        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        try {
            Thread.sleep(10);
            Assert.assertTrue(bc.isConnectedToServer());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(bc.isConnectedToServer());

        bc.disconnectFromBroker();
    }


    private Properties getTestProperties() {
        return null;
    }
}
