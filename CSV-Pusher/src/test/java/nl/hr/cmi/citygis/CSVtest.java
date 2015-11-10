package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;

import java.io.File;
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
        Stream<CityGisData> data = mr.getDataFromCSV(FileMapping.MONITORING);
        Assert.assertTrue(data.count() > 0);
    }

    public void testCsvDataCheckFirstDataElement() throws Exception {
        Stream<CityGisData> data = mr.getDataFromCSV(FileMapping.MONITORING);
        Assert.assertNotNull(data.findFirst().get());
    }
}
