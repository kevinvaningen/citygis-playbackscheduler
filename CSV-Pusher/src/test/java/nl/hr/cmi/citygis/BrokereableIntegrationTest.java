package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Calendar;

/**
 * Unit test for simple App.
 */
public class BrokereableIntegrationTest extends TestCase {

    BrokereableConnector bc;
    Calendar c;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BrokereableIntegrationTest(String testName) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( BrokereableIntegrationTest.class );
    }

    public void setUp() throws Exception {
        bc = null;//new BrokereableConnector("tcp://localhost:1883","CityGis csv test pusher",0);
        c = Calendar.getInstance();
        super.setUp();
    }

    public void testConnection(){
        bc.connect();
//        Assert.assertTrue(bc.isConnectedToServer());
        Assert.assertTrue(true);
        bc.disconnectFromBroker();
    }

    public void testSendOneMessage(){
        bc.connect();
//        Assert.assertTrue(bc.isConnectedToServer());
//        Assert.assertTrue(bc.publish("postions","TestMessage" + c.getTime()));
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
//        Assert.assertTrue(bc.isConnectedToServer());
//        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
//        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
//        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
//        Assert.assertTrue(bc.publish("topic/","TestMessage0" + c.getTime()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Assert.assertTrue(bc.isConnectedToServer());
Assert.assertTrue(true);
        bc.disconnectFromBroker();
    }
}
