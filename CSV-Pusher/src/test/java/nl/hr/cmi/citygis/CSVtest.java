package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.CityGisData;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class CsvTest extends TestCase {
    MessageFileRetriever mr;

    public void setUp() throws Exception {
        super.setUp();
         mr = new MessageFileRetriever();
    }

    public void testCsvFileRetrieval() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV();
        Assert.assertTrue(data.count() > 0);
    }

    public void testCsvDataCheckFirstDataElement() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV();
        Assert.assertNotNull(data.findFirst().get());
    }

    public void testCsvDistinctValuesWithoutExceptions(){
        getDistinctValues();
        //Check for no Exceptions
        Assert.assertTrue(true);
    }

    public void testCsvTestStream(){
        testStream();
        //Check for no Exceptions
        Assert.assertTrue(true);
    }

    private static void getDistinctValues(){
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
