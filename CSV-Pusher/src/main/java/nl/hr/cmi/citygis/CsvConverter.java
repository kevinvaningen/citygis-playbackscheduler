package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisModel;
import nl.hr.cmi.citygis.models.Event;
import nl.hr.cmi.citygis.models.iCityGisModel;

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


public class CsvConverter {

    public static LinkedHashMap<LocalDateTime, List<CityGisModel>> getGroupedModelsFromFile(String path, String filename, Supplier<iCityGisModel> icgm){
        List<String> lines = getLinesFromCsv(path, filename);
        List<CityGisModel> cgmData = getCityGisModelsFromLines(lines, icgm);
        LinkedHashMap<LocalDateTime, List<CityGisModel>> grouped = groupCityGisModelsByLocalDateTime(cgmData);

        return grouped;
    }

    public static LinkedHashMap<LocalDateTime, List<CityGisModel>> groupCityGisModelsByLocalDateTime(List<CityGisModel> cgmData) {
        LinkedHashMap<LocalDateTime, List<CityGisModel>> grouped = new LinkedHashMap<LocalDateTime, List<CityGisModel>>( cgmData.size() * 2 );

        for(CityGisModel cgm : cgmData){
            List<CityGisModel> entryList;
            if( grouped.containsKey(cgm.getDateTime())) {
                entryList = grouped.get(cgm.getDateTime());
                entryList.add(cgm);
            }else {
                entryList = new LinkedList<>();
                entryList.add(cgm);
                grouped.put(cgm.getDateTime(), entryList );
            }
        }
        return grouped;
    }

    public static List<CityGisModel> getCityGisModelsFromLines(List<String> lines, Supplier<iCityGisModel> cgm) {
        return lines.stream()
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(";")))
                    .map(list -> cgm.get().create(list))
                    .sorted((e1,e2) -> e1.getDateTime().compareTo(e2.getDateTime()))
                    .collect(Collectors.toList());
    }


    public static List<String> getLinesFromCsv(String pathname, String filename) {
        BufferedReader breader=null;
        try{
            Path path = Paths.get(pathname, filename);
            breader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
        }catch(IOException exception){
            System.out.println("Error occurred while trying to read the file");
            System.exit(0);
        }

        return breader.lines().collect(Collectors.toList());
    }

    public static void main(String[] args) {
//        LinkedHashMap<LocalDateTime, List<CityGisModel>> grouped = getGroupedModelsFromFile("", "Events.csv", () -> new Event());
//
//
//        //debug print every entry containing more than 1 csv entry
//        for(Map.Entry<LocalDateTime, List<CityGisModel>> entry : grouped.entrySet()){
//            if(entry.getValue().size() > 1) {
//                System.out.println(entry.getKey());
//                for (CityGisModel cgm : entry.getValue()) {
//                    System.out.print(cgm);
//                    System.out.print(", ");
//                }
//                System.out.println("");
//            }
//        }
        CsvConverter.getDistinctValues();
    }

    public static void getDistinctValues(){
        CsvConverter.getLinesFromCsv("", "Monitoring.csv")
                .stream()
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(arr -> arr.get(3))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
}