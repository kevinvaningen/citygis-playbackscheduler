package nl.hr.cmi.citygis.configuration;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Command line interface interpreter
 */
public class CliBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(CliBuilder.class);

    public static CommandLine parseCommandLineArguments(String[] args) {
        Options options = createOptions();
        showHelpMessage(args, options);

        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;

        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {
            LOGGER.error("Parsing command line arguments failed.  Reason: " + ex.getMessage());
            System.exit(1);
        }

        return line;
    }


    private static Options createOptions(){
        Option oFile = Option.builder("f").argName("file").longOpt("file").required().hasArg().desc("The file (including path).").build();
        Option oType = Option.builder("t").argName("type").longOpt("type").required().hasArg().desc("Here you can set the file type <CONNECTIONS || EVENTS || MONITORING || POSITIONS>.").build();
        Option oMqttHostname = Option.builder("h").argName("host").longOpt("host").hasArg().desc("Here you can set the URI (including port) for the message broker.").build();
        Option oMqttUsername = Option.builder("u").argName("username").longOpt("username").hasArg().desc("Here you can set the user for the message broker.").build();
        Option oMqttPassword = Option.builder("p").argName("password").longOpt("password").hasArg().desc("Here you can set the password for the message broker.").build();
        Option oMqttClientId = Option.builder("c").argName("clientid").longOpt("clientid").hasArg().desc("Here you can set the clientid for the message broker.").build();
        Option oMqttQos = Option.builder("q").argName("qos").longOpt("qos").hasArg().desc("Here you can set the qos for the message broker.").build();

        Options options = new Options();
        options.addOption(oFile);
        options.addOption(oType);
        options.addOption(oMqttHostname);
        options.addOption(oMqttUsername);
        options.addOption(oMqttPassword);
        options.addOption(oMqttClientId);
        options.addOption(oMqttQos);

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
                formatter.printHelp("java -jar Applicationfilename.jar -f /path/filename.csv -t CONNECTIONS", options);
                System.exit(0);
            }
        } catch (ParseException ex) {
            System.err.println("Parsing failed.  Reason: " + ex.getMessage());
            System.exit(1);
        }
    }
}
