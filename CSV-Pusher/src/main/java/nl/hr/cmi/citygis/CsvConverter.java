package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import nl.hr.cmi.citygis.models.iCityGisModel;
import rx.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class CsvConverter {
    private String path = "";
    private String file;
    private Stream<CityGisData> data;
    private Supplier<iCityGisModel> supplier;

    public CsvConverter(String path, FileMapping fileMapping) {
        this(fileMapping);
        setPath(path);
    }

    public CsvConverter(FileMapping fileMapping) {
        supplier = fileMapping.getSupplier();
        file     = fileMapping.getFileName();
    }

    /**
     * Default path is project root. You can override it here
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public Observable<CityGisData> getObservableData() {
        return Observable.from(getData()::iterator);
    }

    public Stream<CityGisData> getData() {
        Stream<String> lines = getLinesFromCsv();
        setData(getCityGisModelsFromLinesAsStream(lines, supplier));
        return data;
    }

    public void setData(Stream<CityGisData> data) {
        this.data = data;
    }

    public static Stream<CityGisData> getCityGisModelsFromLinesAsStream(Stream<String> lines, Supplier<iCityGisModel> cgm) {
        return lines
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(";")))
                    .map(list -> cgm.get().create(list));
    }

    public Stream<String> getLinesFromCsv() {
        BufferedReader breader = null;
        try{
            Path path = Paths.get(this.path, this.file);
            System.out.println("Trying to read file: " + path.toAbsolutePath());
            breader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
        }catch(IOException exception){
            System.out.println("Error occurred while trying to read the file: " + exception);
            System.exit(0);
        }

        return breader.lines();
    }
}