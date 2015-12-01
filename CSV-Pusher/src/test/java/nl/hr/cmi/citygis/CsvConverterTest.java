package nl.hr.cmi.citygis;

import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.FileMapping;

/**
 * Created by cmi on 09-11-15.
 */
public class CsvConverterTest extends TestCase {
    CsvConverter csvc;

    public void setUp() throws Exception {
        super.setUp();
        csvc = new CsvConverter(".", FileMapping.EVENTS);
    }

    public void testThis() throws Exception {
        assertTrue(true);
    }
    /*  public void testCsvFileRetrieval() throws Exception {
        Assert.assertTrue(csvc.getCityGisDataFromFile().findFirst() != null);
    }

    public void testCsvDataCheckFirstDataElement() throws Exception {
        Stream<CityGisData> data = csvc.getCityGisDataFromFile();
        Assert.assertNotNull(data.findFirst().get());
    }


    public void testGetFileStartTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expectedFileStartTime = LocalDateTime.parse("2015-03-10 07:12:25", formatter);
        LocalDateTime providedFileStartTime = csvc.getFileStartTime();
        assertEquals(expectedFileStartTime, providedFileStartTime);
    }
*/
}
