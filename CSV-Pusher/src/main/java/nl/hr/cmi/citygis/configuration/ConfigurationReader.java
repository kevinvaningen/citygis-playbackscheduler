package nl.hr.cmi.citygis.configuration;


import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigurationReader {
    public BrokerConfiguration getBrokerConfiguration() {
        return new BrokerConfiguration(tryReadingSystemProperties());
    }

    public BrokerConfiguration getCommandlineBrokerConfiguration(String host, String user, String pass, String clientId, String qos) {
        return new BrokerConfiguration(ConfigurationPropertyValues.createProperties(host, user, pass, clientId, qos));
    }


    private Properties tryReadingSystemProperties() {
        ConfigurationPropertyValues properties = new ConfigurationPropertyValues();

        Properties configurationProperties;

        try {
            configurationProperties = properties.getDefaultPropertyFileValues();
        } catch (IOException e) {
            e.printStackTrace();
            configurationProperties = properties.getDefaultProperties();
        }
        return configurationProperties;
    }
}


class ConfigurationPropertyValues {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigurationPropertyValues.class);

    private InputStream inputStream;
    private static final String PROPERTY_FILE_FILE_NAME = "config.properties";

    public Properties getDefaultPropertyFileValues() throws IOException {
        Properties prop = new Properties();
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_FILE_NAME);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTY_FILE_FILE_NAME + "' not found in the classpath");
            }

        } catch (Exception e) {
            LOGGER.error("Exception in loading config.properties.: " + e);
        } finally {
            inputStream.close();
        }
        return prop;
    }

    public static Properties getDefaultProperties() {
        Properties prop = new Properties();
        prop.setProperty("MQTT_BROKER_URL","tcp://localhost:1883");
        prop.setProperty("MQTT_CLIENT_ID","CityGis csv pusher");
        prop.setProperty("MQTT_QOS","0");
        LOGGER.info("Defaulting to localhost configuration. Configuration:" + prop.toString());
        return prop;
    }

    public static Properties createProperties(String hostName, String userName, String password, String clientId, String qos) {
        Properties prop = new Properties();
        prop.setProperty("MQTT_BROKER_URL", hostName);
        prop.setProperty("MQTT_CLIENT_ID", clientId);
        prop.setProperty("MQTT_QOS", qos);

        if (userName != null) {
            prop.setProperty("MQTT_BROKER_USERNAME", userName);
            prop.setProperty("MQTT_BROKER_PASSWORKD", password);
        }
        return prop;
    }
}


