package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.CliBuilder;
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
        System.out.println("CSV pusher instantiated.");
        LOGGER.info("Started logging" + App.class.getSimpleName());
        LOGGER.info(String.format("With parameter: fileAndPath: %s , fileType: %s", fileAndPathName, fileTypeMapping.name()));

        this.fileAndPath = fileAndPathName;
        this.fileMapping = fileTypeMapping;

        connection = new MqttBrokerClientConnector();
        csvc = new CsvConverter(fileAndPath, fileTypeMapping);

        scheduler = new PlaybackScheduler(csvc.getFileStartTime(), connection, fileTypeMapping.name());
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
        CommandLine line = CliBuilder.parse(args);

        String fileAndPath = line.getOptionValue("file");
        FileMapping fileMapping = FileMapping.valueOf(line.getOptionValue("type"));

        App a = new App(fileAndPath, fileMapping);
        a.startScheduler();
    }
}