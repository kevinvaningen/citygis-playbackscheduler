package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import org.apache.commons.cli.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;


/**
 * CityGis CSV pusher
 */
public class App {
    Publishable connection;
    StreamPlaybackScheduler scheduler;
    MessageFileRetriever mr;

    String file;
    String path;
    FileMapping fileMapping;

    public App(String file, String path, FileMapping fileMapping) {
        this(fileMapping);
        this.file = file;
        this.path = path;
        this.fileMapping = fileMapping;
    }

    public App(FileMapping fileMapping){
        System.out.println("Started" + App.class.getSimpleName()+ " started. ");

        connection = new MqttBrokerClientConnector();
        scheduler = new StreamPlaybackScheduler(LocalDateTime.now(), connection);
        mr = new MessageFileRetriever();
    }


    public void run(){
        Stream<CityGisData> data = mr.getDataFromCSV(fileMapping);

        System.out.println("Starting message scheduler.");
        scheduler.startPlayback( data );
    }


    public static void main(String[] args){
        Options options = createOptions();
        showHelpMessage(args, options);


        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;

        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("Parsing failed.  Reason: " + ex.getMessage());
            System.exit(1);
        }

        String file             = line.getOptionValue("file");
        String path             = line.getOptionValue("path");
        FileMapping fileMapping = FileMapping.valueOf(line.getOptionValue("type"));

        App a = new App(file, path, fileMapping);
        a.run();
    }

    private static Options createOptions(){
        Option ofile = Option.builder("f").argName("file").longOpt("file").required().hasArg().desc("The csv data file name").build();
        Option opath = Option.builder("p").argName("path").longOpt("path").hasArg().desc("The path, default is the source root.").build();
        Option otype = Option.builder("t").argName("type").longOpt("type").required().hasArg().desc("Here you can set the file type <CONNECTIONS || EVENTS || MONITORING || POSITIONS>.").build();

        Options options = new Options();
        options.addOption(ofile);
        options.addOption(opath);
        options.addOption(otype);

        return options;
    }

    private static void showHelpMessage(String[] args, Options options) {
        Options helpOptions = new Options();
        helpOptions.addOption(Option.builder("h").longOpt("help")
                .desc("print this message").build());
        try {
            CommandLine helpLine = new DefaultParser().parse(helpOptions, args, true);
            if (helpLine.hasOption("help") || args.length == 0) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("App -file Connections.csv -type CONNECTIONS", options);
                System.exit(0);
            }
        } catch (ParseException ex) {
            System.err.println("Parsing failed.  Reason: " + ex.getMessage());
            System.exit(1);
        }
    }
}