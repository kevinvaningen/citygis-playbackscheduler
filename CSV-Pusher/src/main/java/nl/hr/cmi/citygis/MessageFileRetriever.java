package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessageFileRetriever {

    private String fileName = FileNames.EVENTS.getName();

    public Stream<CityGisData> getDataFromCSV() {
        return getDataFromCSV(fileName);
    }

    public Stream<CityGisData> getDataFromCSV(String fileName) {
        System.out.println("Reverting file" + fileName);
        CsvConverter csvc = new CsvConverter("",fileName);
        Stream<CityGisData> data = csvc.getData();

        return data;
    }


    public enum FileNames{
        EVENTS("Events.csv"),
        MONITORING("Monitoring.csv"),
        POSITIONS("Positions.csv"),
        CONNECTIONS("Connections.csv");

        private final String name;
        private FileNames(String name){
            this.name = name;
        }
        String getName(){
            return name;
        }
    }
}
