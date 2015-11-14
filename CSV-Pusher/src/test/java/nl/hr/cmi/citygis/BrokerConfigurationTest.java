package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.configuration.BrokerConfiguration;

import java.util.Properties;

/**
 * Created by cmi on 15-11-15.
 */
public class BrokerConfigurationTest extends TestCase {

    public void testCredentialsCheckTest() throws Exception {
        BrokerConfiguration bc = new BrokerConfiguration(new Properties());
        Assert.assertFalse(bc.usesCredentials());
    }

    public void testCredentialsFilledInCheckTest() throws Exception {
        Properties p = new Properties();
        p.setProperty("MQTT_BROKER_PASSWORKD", "HOI");
        BrokerConfiguration bc = new BrokerConfiguration(p);
        Assert.assertTrue(bc.usesCredentials());
    }
}
