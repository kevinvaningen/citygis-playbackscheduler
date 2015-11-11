package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class CSVtest extends TestCase {
    MessageFileRetriever mr;
    FileMapping fileMapping;
    CsvConverter csvc;

    public void setUp() throws Exception {
        super.setUp();
        mr = new MessageFileRetriever();
        fileMapping = FileMapping.EVENTS;

        csvc = new CsvConverter(fileMapping);
        csvc.setPath("test/resources/");
    }

    public void testCsvFileRetrieval() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV(fileMapping);
        Assert.assertTrue(data.count() > 0);
    }

    public void testCsvDataCheckFirstDataElement() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV(fileMapping);
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

    private void getDistinctValues(){
        csvc.getLinesFromCsv()
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(arr -> arr.get(3))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    public void testStream(){
        Stream<String> test = csvc.getLinesFromCsv()
                .skip(1)
                .map(line -> Arrays.asList(line.split(";")))
                .map(arr -> arr.get(3))
                ;
        System.out.println(test.findFirst().get());
        test.forEach(System.out::println);
    }
}
