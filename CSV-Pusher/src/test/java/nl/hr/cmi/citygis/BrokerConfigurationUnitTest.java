package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/***
 * Test class for brokers
 */
public class BrokerConfigurationUnitTest {

    @Test
    public void testCredentialsCheckTest() throws Exception {
        BrokerConfiguration bc = new BrokerConfiguration(new Properties());
        assertFalse(bc.usesCredentials());
    }

    @Test
    public void testCredentialsFilledInCheckTest() throws Exception {
        Properties p = new Properties();
        p.setProperty("MQTT_BROKER_PASSWORKD", "HOI");
        BrokerConfiguration bc = new BrokerConfiguration(p);
        assertTrue(bc.usesCredentials());
    }
}
