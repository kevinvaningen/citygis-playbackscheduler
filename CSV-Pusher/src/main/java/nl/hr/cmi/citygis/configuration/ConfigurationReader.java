package nl.hr.cmi.citygis.configuration;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cmi on 10-11-15.
 */
public class ConfigurationReader {
    Properties configurationProperties;
    ConfigurationPropertyValues properties = new ConfigurationPropertyValues();


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
    InputStream inputStream;

    public Properties getPropValues() throws IOException {
        Properties prop = new Properties();

        try {
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            return prop;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
            return prop;
        }
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


