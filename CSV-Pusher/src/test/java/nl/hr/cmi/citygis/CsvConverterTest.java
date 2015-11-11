package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;

import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class CsvConverterTest extends TestCase {

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
        Stream<CityGisData> data = mr.getDataFromCSV("resources/", fileMapping);
        Assert.assertTrue(data.count() > 0);
    }

    public void testCsvDataCheckFirstDataElement() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV("resources/", fileMapping);
        Assert.assertNotNull(data.findFirst().get());

    }
}
