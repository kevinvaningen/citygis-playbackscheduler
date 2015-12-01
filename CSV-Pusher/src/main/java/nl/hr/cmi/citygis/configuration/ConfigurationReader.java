package nl.hr.cmi.citygis.configuration;


import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigurationReader {
    private Properties configurationProperties;
    private ConfigurationPropertyValues properties = new ConfigurationPropertyValues();


    public ConfigurationReader() {
        try {
            configurationProperties = properties.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
            configurationProperties = properties.getDefaultProperties();
        }
    }

    public BrokerConfiguration getBrokerConfiguration(){
        return new BrokerConfiguration(configurationProperties);
    }
}


class ConfigurationPropertyValues {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigurationPropertyValues.class);

    private InputStream inputStream;
    private static final String PROPERTY_FILE_FILE_NAME = "config.properties";

    public Properties getPropValues() throws IOException {
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
    public Properties getDefaultProperties(){
        Properties prop = new Properties();
        prop.setProperty("CSV_FILE_EVENTS","Events.csv");
        prop.setProperty("CSV_FILE_POSITIONS", "Positions.csv");
        prop.setProperty("CSV_FILE_MONITORING", "Monitoring.csv");
        prop.setProperty("CSV_FILE_CONNECTIONS", "Connections.csv");

        prop.setProperty("MQTT_BROKER_URL","tcp://localhost:1883");
        prop.setProperty("MQTT_BROKER_USERNAME","=dqpjlyct");
        prop.setProperty("MQTT_BROKER_PASSWORKD","=EseKDkIvaT1h");
        prop.setProperty("MQTT_CLIENT_ID","CityGis csv pusher");
        prop.setProperty("MQTT_QOS","0");
        return prop;
    }
}


