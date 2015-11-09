package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisModel;
import nl.hr.cmi.citygis.models.Event;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by cmi on 09-11-15.
 */
public class MessageFileRetriever {

    private String fileName = "Events.csv";

    public LinkedHashMap<LocalDateTime,List<CityGisModel>> getDataFromCSV() {
        System.out.println("Reverting to default file");
        LinkedHashMap<LocalDateTime, List<CityGisModel>> data = CsvConverter.getGroupedModelsFromFile("",fileName, () -> new Event());
        return data;
    }
    public LinkedHashMap<LocalDateTime,List<CityGisModel>> getDataFromCSV(String fileName) {
        System.out.println("Reverting to default file");
        LinkedHashMap<LocalDateTime, List<CityGisModel>> data = CsvConverter.getGroupedModelsFromFile("",fileName, () -> new Event());
        return data;
    }

}
