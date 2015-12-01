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

    private String file;
    private String path;
    private FileMapping fileMapping;
    private Stream<CityGisData> data;

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    /***
     * @param fileName        use a full case sensitive filename.
     * @param filePath        use the path including final slash wich will be prefixed before the filename
     * @param fileTypeMapping use the Enumerated types in FileMapping
     */
    public App(String fileName, String filePath, FileMapping fileTypeMapping) {
        System.out.println("CSV pusher instantiated.");
        LOGGER.info("Started logging" + App.class.getSimpleName());
        LOGGER.info(String.format("With parameter: path: %s , file: %s ,  filetype: %s", filePath, fileName, fileTypeMapping.name()));

        this.file = fileName;
        this.path = filePath;
        this.fileMapping = fileTypeMapping;

        connection = new MqttBrokerClientConnector();
        csvc = new CsvConverter(filePath, fileTypeMapping);

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

        String file             = line.getOptionValue("file");
        String path             = line.getOptionValue("path");
        FileMapping fileMapping = FileMapping.valueOf(line.getOptionValue("type"));
        boolean usingRxJava     = line.hasOption("rx");

        App a = new App(file, path, fileMapping);
        a.startScheduler();
    }
}