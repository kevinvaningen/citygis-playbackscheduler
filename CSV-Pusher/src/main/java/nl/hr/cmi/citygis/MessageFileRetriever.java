package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;

import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessageFileRetriever {

    private String fileName = FileMapping.EVENTS.getFileName();

    public Stream<CityGisData> getDataFromCSV(FileMapping fileMapping) {
        System.out.println("Reverting file" + fileMapping.getFileName());

        CsvConverter csvc = new CsvConverter(fileMapping);
        Stream<CityGisData> data = csvc.getData();

        return data;
    }

}
