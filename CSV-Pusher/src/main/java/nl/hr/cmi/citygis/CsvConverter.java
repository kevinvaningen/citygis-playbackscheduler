package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.*;

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


/*    public CsvConverter(FileMapping file) {
        Supplier<iCityGisModel> supplier = file.getSupplier();
        Stream<String> lines = getLinesFromCsv(file.getFileName());

        setData(getCityGisModelsFromLinesAsStream(lines, supplier));
    }*/
    private CsvConverter(String file) {
        this.file = FileMapping.valueOf(file).getFileName();
        Supplier<iCityGisModel> supplier = FileMapping.valueOf(file).getSupplier();
    }

    /**
     * Default path is project root. You can override it here
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
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