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
 * CsvConverter class for reading inputs based on fileAndPath arguments and returns the String representation of a line using a Stream.
 */
public class CsvConverter {
    private String fileAndPath = "";
    private Stream<CityGisData> data;
    private Supplier<iCityGisModel> supplier;

    private final static Logger LOGGER = LoggerFactory.getLogger(CsvConverter.class);

    /***
     * @param fileAndPath provide a fileAndPath Path and filename
     * @param fileMapping provide a FileMapping enum which contains filename and type for parsing purposes.
     */
    public CsvConverter(String fileAndPath, FileMapping fileMapping) {
        this.fileAndPath = fileAndPath;
        supplier = fileMapping.getSupplier();
    }

    /***
     * Main utility function of this class which provides a parsed stream of data-objects as a stream.
     * @return return a stream of data.
     */
    public Stream<CityGisData> getCityGisDataFromFile() {
        Stream<String> lines = null;
        try {
            lines = getLinesFromCsv();
        } catch (IOException e) {
            LOGGER.error("Error occurred while trying to read the fileAndPath: " + e);
        }
        setData(getCityGisModelsFromLinesAsStream(lines));
        return data;
    }

    /***
     * Find the first time occurence of the fileAndPath. Usefull for scheduling purposes.
     * @return LocalDateTime off the first element in the fileAndPath
     */
    public LocalDateTime getFileStartTime() {
        return getCityGisDataFromFile()
                .findFirst()
                .get()
                .getDateTime();
    }

    private Stream<CityGisData> getCityGisModelsFromLinesAsStream(Stream<String> lines) {
        // skip the header of the CSV fileAndPath
        return lines
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(list -> supplier.get().create(list));
    }

    private Stream<String> getLinesFromCsv() throws IOException {
        BufferedReader bReader = null;
        Path path = Paths.get(this.fileAndPath);
        LOGGER.info("Trying to read fileAndPath: " + path.toAbsolutePath());

        bReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        return bReader.lines();
    }

    private void setData(Stream<CityGisData> data) {
        this.data = data;
    }
}