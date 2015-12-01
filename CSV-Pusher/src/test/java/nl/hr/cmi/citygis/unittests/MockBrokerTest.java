package nl.hr.cmi.citygis.unittests;

import junit.framework.Assert;
import nl.hr.cmi.citygis.Publishable;
import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import nl.hr.cmi.citygis.mocks.BadMockClientBroker;
import nl.hr.cmi.citygis.mocks.MockClientBroker;
import org.junit.Before;

import java.util.Calendar;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class MockBrokerTest {

    Publishable bc;
    Calendar c;

    @Before
    public void setUp() throws Exception {
        BrokerConfiguration brokerConfiguration = new BrokerConfiguration(getTestProperties());
        bc = new MockClientBroker(brokerConfiguration);
        c = Calendar.getInstance();
    }

    @org.junit.Test
    public void testConnection(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        bc.disconnectFromBroker();
    }

    @org.junit.Test
    public void testBadBrokerage() throws Exception {
        Publishable badBroker = new BadMockClientBroker();

        Assert.assertFalse(badBroker.publish("testTopic","Hi ALl!"));
    }

    @org.junit.Test
    public void testSendOneMessage(){
        bc.connect();
        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(bc.publish("postions","TestMessage" + c.getTime()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(true);

        bc.disconnectFromBroker();
    }

    @org.junit.Test
    public void testSendMultipleMessages(){
        bc.connect();
        assertTrue(bc.isConnectedToServer());
        assertTrue(bc.publish("topic/", "TestMessage0" + c.getTime()));
        try {
            Thread.sleep(10);
            assertTrue(bc.isConnectedToServer());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(bc.isConnectedToServer());

        bc.disconnectFromBroker();
    }


    private Properties getTestProperties() {
        return null;
    }
}
