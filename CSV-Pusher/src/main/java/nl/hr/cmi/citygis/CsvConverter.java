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

/***
 * CsvConverter class for reading inputs based on file arguments and returns the String representation of a line using a Stream.
 */
public class CsvConverter {
    private String path = "";
    private String file;
    private Stream<CityGisData> data;
    private Supplier<iCityGisModel> supplier;

    private final static Logger LOGGER = LoggerFactory.getLogger(CsvConverter.class);

    /***
     * @param path        provide a file Path, and with a trailing '/' if necessary
     * @param fileMapping provide a FileMapping enum which contains filename and type for parsing purposes.
     */
    public CsvConverter(String path, FileMapping fileMapping) {
        this.path = path;

        supplier = fileMapping.getSupplier();
        file = fileMapping.getFileName();
    }

    /***
     * Main utility function of this class which provides a parsed stream of data-objects as a stream.
     * @return
     */
    public Stream<CityGisData> getCityGisDataFromFile() {
        Stream<String> lines = getLinesFromCsv();
        setData(getCityGisModelsFromLinesAsStream(lines));
        return data;
    }

    /***
     * Find the first time occurence of the file. Usefull for scheduling purposes.
     *
     * @return LocalDateTime off the first element in the file
     */
    public LocalDateTime getFileStartTime() {
        return getCityGisDataFromFile()
                .findFirst()
                .get()
                .getDateTime();
    }

    private Stream<CityGisData> getCityGisModelsFromLinesAsStream(Stream<String> lines) {
        // skip the header of the CSV file
        return lines
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(list -> supplier.get().create(list));
    }

    private Stream<String> getLinesFromCsv() {
        BufferedReader breader = null;
        try {
            Path path = Paths.get(this.path, this.file);
            LOGGER.info("Trying to read file: " + path.toAbsolutePath());

            breader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
        } catch (IOException exception) {
            LOGGER.error("Error occurred while trying to read the file: " + exception);
        }

        return breader.lines();
    }

    private void setData(Stream<CityGisData> data) {
        this.data = data;
    }
}