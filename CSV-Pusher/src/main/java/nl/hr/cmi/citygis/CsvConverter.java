package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import nl.hr.cmi.citygis.models.iCityGisModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class CsvConverter {
    private String path = "";
    private String file;
    private Stream<CityGisData> data;
    private Supplier<iCityGisModel> supplier;

    private final static Logger LOGGER = LoggerFactory.getLogger(CsvConverter.class);


    public CsvConverter(String path, FileMapping fileMapping) {
        this.path = path;

        supplier = fileMapping.getSupplier();
        file     = fileMapping.getFileName();
    }

    public Stream<CityGisData> getData() {
        Stream<String> lines = getLinesFromCsv();
        setData(getCityGisModelsFromLinesAsStream(lines));
        return data;
    }

    public void setData(Stream<CityGisData> data) {
        this.data = data;
    }

    public Stream<CityGisData> getCityGisModelsFromLinesAsStream(Stream<String> lines) {
        return lines
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(";")))
                    .map(list -> supplier.get().create(list));
    }

    public Stream<String> getLinesFromCsv() {
        BufferedReader breader = null;
        try{
            Path path = Paths.get(this.path, this.file);
            LOGGER.info("Trying to read file: " + path.toAbsolutePath());

            breader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
        }catch(IOException exception){
            LOGGER.error("Error occurred while trying to read the file: " + exception);
        }

        return breader.lines();
    }

    public LocalDateTime getFileStartTime(){
        return getData()
                .findFirst()
                .get()
                .getDateTime();
    }
}