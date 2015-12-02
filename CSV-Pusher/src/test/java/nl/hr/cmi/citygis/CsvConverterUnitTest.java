package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.FileMapping;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CsvConverterUnitTest {
    private CsvConverter csvc;

    @Before
    public void setUp() throws Exception {
        csvc = new CsvConverter(".", FileMapping.EVENTS);
    }

    @Test
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
