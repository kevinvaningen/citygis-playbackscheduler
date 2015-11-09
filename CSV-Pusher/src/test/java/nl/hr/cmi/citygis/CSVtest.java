package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.CityGisModel;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by cmi on 09-11-15.
 */
public class CsvTest extends TestCase {
    MessageFileRetriever mr;

    public void setUp() throws Exception {
        super.setUp();
         mr = new MessageFileRetriever();
    }

    public void testCSV() throws Exception {
        LinkedHashMap<LocalDateTime,List<CityGisModel>> data = mr.getDataFromCSV();
        Assert.assertTrue(data.size()>0);
    }
}
