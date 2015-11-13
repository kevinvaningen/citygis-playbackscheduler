package nl.hr.cmi.citygis.configuration;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by youritjang on 12-11-15.
 */
public class CliBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(CliBuilder.class);


    public static CommandLine parse(String[] args){
        Options options = createOptions();
        showHelpMessage(args, options);

        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;

        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {
            LOGGER.error("Parsing failed.  Reason: " + ex.getMessage());
            System.exit(1);
        }

        return line;
    }


    private static Options createOptions(){
        Option ofile = Option.builder("f").argName("file").longOpt("file").required().hasArg().desc("The csv data file name").build();
        Option opath = Option.builder("p").argName("path").longOpt("path").hasArg().desc("The path, default is the source root.").build();
        Option otype = Option.builder("t").argName("type").longOpt("type").required().hasArg().desc("Here you can set the file type <CONNECTIONS || EVENTS || MONITORING || POSITIONS>.").build();
        Option rtype = Option.builder("rx").argName("rx").longOpt("rx").desc("Want to use RXJava with observable?").build();

        Options options = new Options();
        options.addOption(ofile);
        options.addOption(opath);
        options.addOption(otype);
        options.addOption(rtype);

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
