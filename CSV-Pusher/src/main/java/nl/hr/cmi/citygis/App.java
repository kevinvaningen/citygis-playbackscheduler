package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.BrokerConfiguration;
import nl.hr.cmi.citygis.configuration.CliBuilder;
import nl.hr.cmi.citygis.configuration.ConfigurationReader;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/***
 * CityGis CSV pusher's main entry point. Use it for setting the propper program arguments and initiating the playback scheduler.
 */
public class App {
    private Publishable connection;
    private PlaybackScheduler scheduler;
    private CsvConverter csvc;

    private String fileAndPath;
    private FileMapping fileMapping;
    private Stream<CityGisData> data;

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    /***
     * @param fileAndPathName        use a full case sensitive filename.
     * @param fileTypeMapping use the Enumerated types in FileMapping
     */
    public App(String fileAndPathName, FileMapping fileTypeMapping) {
        this(fileAndPathName, fileTypeMapping, new MqttBrokerClientConnector());
        LOGGER.info("No commandline arguments for the broker dectected. Defaulting to default broker arguments.");
    }


    public App(String fileAndPathName, FileMapping fileTypeMapping, MqttBrokerClientConnector brokerConnection) {
        System.out.println("CSV pusher instantiated.");
        LOGGER.info("Started logging" + App.class.getSimpleName());
        LOGGER.info(String.format("With parameter: fileAndPath: %s , fileType: %s", fileAndPathName, fileTypeMapping.name()));

        this.fileAndPath = fileAndPathName;
        this.fileMapping = fileTypeMapping;

        csvc = new CsvConverter(fileAndPath, fileTypeMapping);

        scheduler = new PlaybackScheduler(csvc.getFileStartTime(), brokerConnection, fileTypeMapping.name());
        data = csvc.getCityGisDataFromFile();
    }

    /***
     * Call for starting the scheduler based on a previously entered dataset.
     */
    public void startScheduler() {
        scheduler.startPlayback(data);
    }


    /***
     * Main starting point for the runnable Jar application. Without args it will default to a helper instruction providing guidelines for arguments.
     * @param args provide arguments for filename (String), path (String), type (Enum), and use Reactive(boolean)
     */
    public static void main(String[] args){
        App application;
        CommandLine line = CliBuilder.parseCommandLineArguments(args);

        String fileAndPath = line.getOptionValue("file");
        FileMapping fileMapping = FileMapping.valueOf(line.getOptionValue("type"));
        String hostName = line.getOptionValue("host");
        String userName = line.getOptionValue("username");
        String password = line.getOptionValue("password");
        String clientId = line.getOptionValue("clientid");
        String qos = line.getOptionValue("qos");

        if (hostName != null && hostName.length() > 0) {
            ConfigurationReader configurationReader = new ConfigurationReader();
            BrokerConfiguration brokerConfiguration = configurationReader.getCommandlineBrokerConfiguration(hostName, userName, password, clientId, qos);
            application = new App(fileAndPath, fileMapping, new MqttBrokerClientConnector(brokerConfiguration));
        } else {
            //this option defaults to configuration using config.properties.
            application = new App(fileAndPath, fileMapping);
        }
        application.startScheduler();
    }
}