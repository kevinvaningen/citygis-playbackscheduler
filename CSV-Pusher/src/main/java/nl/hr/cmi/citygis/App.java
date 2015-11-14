package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.configuration.CliBuilder;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;
/**
 * CityGis CSV pusher
 */
public class App {
    Publishable connection;
    PlaybackScheduler scheduler;
    CsvConverter csvc;

    String file;
    String path;
    FileMapping fileMapping;
    boolean usingRxJava;

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    public App(String file, String path, FileMapping fileMapping, boolean usingRxJava) {
        System.out.println("Started logging on console: " + App.class.getSimpleName());
        LOGGER.info("Started logging" + App.class.getSimpleName());
        LOGGER.info(String.format("With parameter: path: %s , file: %s ,  filetype: %s , rx: %s", path, file, fileMapping.name(), usingRxJava));

        this.file = file;
        this.path = path;
        this.fileMapping = fileMapping;
        this.usingRxJava = usingRxJava;

        connection = new MqttBrokerClientConnector();
        csvc       = new CsvConverter(path, fileMapping);

        scheduler = new PlaybackScheduler(csvc.getFileStartTime(), connection, fileMapping);
    }

    public void run(){
        Stream<CityGisData> data = csvc.getData();

        scheduler.startPlayback(data);
    }

    public static void main(String[] args){
        CommandLine line = CliBuilder.parse(args);

        String file             = line.getOptionValue("file");
        String path             = line.getOptionValue("path");
        FileMapping fileMapping = FileMapping.valueOf(line.getOptionValue("type"));
        boolean usingRxJava     = line.hasOption("rx");

        App a = new App(file, path, fileMapping, usingRxJava);
        a.run();
    }
}