package nl.hr.cmi.citygis.configuration;

import java.util.Properties;

/**
 * Created by cmi on 10-11-15.
 */
public class BrokerConfiguration {
    Properties props;

    public BrokerConfiguration(Properties p){
        this.props = p;
    }

    public String getBrokerUrl(){
        return (String) props.get("MQTT_BROKER_URL");
    }
    public String getBrokerUsername(){
        return (String)props.get("MQTT_BROKER_USERNAME");
    }
    public String getBrokerPassword(){
        return (String)props.get("MQTT_BROKER_PASSWORKD");
    }
    public int getBrokerQos(){
        return Integer.parseInt((String)props.get("MQTT_QOS"));
    }
    public String getClientId(){
        return (String) props.get("MQTT_CLIENT_ID");
    }
    public boolean usesCredentials(){
        if(getBrokerPassword()==null || getBrokerPassword().trim().equals("")){
            return false;
        }else{
            return true;
        }
    }
}

