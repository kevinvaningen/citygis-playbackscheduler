package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CsvConverter {
    private String path;
    private String file;
    private Stream<CityGisData> data;

    public CsvConverter(String path, String file) {
        this.path = path;
        this.file = file;

        Stream<String> lines = CsvConverter.getLinesFromCsv(path, file);

        Supplier<iCityGisModel> supplier = null;
        switch(file){
            case "Positions.csv":
                supplier = () -> new Position();
                break;
            case "Events.csv":
                supplier = () -> new Event();
                break;
            case "Monitoring.csv":
                supplier = () -> new Monitoring();
                break;
            case "Connections.csv":
                supplier = () -> new Connection();
                break;
        }
        this.setData(CsvConverter.getCityGisModelsFromLinesAsStream(lines, supplier) );
    }

    public Stream<CityGisData> getData() {
        return this.data;
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

    public static Stream<String> getLinesFromCsv(String pathname, String filename) {
        BufferedReader breader=null;
        try{
            Path path = Paths.get(pathname, filename);
            breader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
        }catch(IOException exception){
            System.out.println("Error occurred while trying to read the file");
            System.exit(0);
        }

        return breader.lines();
    }

    ////////////////////////////////////////////////////////
    //--------------Debug methods ------------------------//
    ////////////////////////////////////////////////////////

    public static void main(String[] args) {
        //CsvConverter.getDistinctValues();
//        CsvConverter.testStream();
//
        App a = new App();
    }

    public static void getDistinctValues(){
        CsvConverter.getLinesFromCsv("", "Monitoring.csv")
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(arr -> arr.get(3))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
    public static void testStream(){
        Stream<String> test = CsvConverter.getLinesFromCsv("", "Monitoring.csv")
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(arr -> arr.get(3))
                ;

        System.out.println(test.findFirst().get());

        test.forEach(System.out::println);
    }

}