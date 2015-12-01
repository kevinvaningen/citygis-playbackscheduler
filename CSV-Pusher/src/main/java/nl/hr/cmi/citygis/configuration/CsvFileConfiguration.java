package nl.hr.cmi.citygis.configuration;

import java.util.Properties;

public class CsvFileConfiguration {
    private Properties props;
    public String getEventsFileName(){
        return (String) props.get("CSVFILE_EVENTS");
    }
    public String getPositionsFileName(){
        return (String) props.get("CSVFILE_POSITIONS");
    }
    public String getMonitoringFileName(){
        return (String) props.get("CSVFILE_MONITORING");
    }
    public String getConnectionsFileName(){
        return (String) props.get("CSVFILE_CONNECTIONS");
    }
}
