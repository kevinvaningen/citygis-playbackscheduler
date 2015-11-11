package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import rx.Observable;

import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessageFileRetriever {
    private String fileName = FileMapping.EVENTS.getFileName();


    public Observable<CityGisData> getObservableDataFromCSV(String path, FileMapping fileMapping) {
        System.out.println("Reverting file" + fileMapping.getFileName());

        CsvConverter csvc = new CsvConverter(fileMapping);
        csvc.setPath(path);
        return csvc.getObservableData();
    }

    public Stream<CityGisData> getDataFromCSV(String path, FileMapping fileMapping) {
        System.out.println("Reverting file" + fileMapping.getFileName());

        CsvConverter csvc = new CsvConverter(fileMapping);
        csvc.setPath(path);
        Stream<CityGisData> data = csvc.getData();

        return data;
    }

    /**
     * Do what the methods name says it does, with de default path.
     * @param fileMapping
     * @return
     */
    public Stream<CityGisData> getDataFromCSV(FileMapping fileMapping) {
        System.out.println("Reverting file" + fileMapping.getFileName());

        CsvConverter csvc = new CsvConverter(fileMapping);
        Stream<CityGisData> data = csvc.getData();

        return data;
    }


}
