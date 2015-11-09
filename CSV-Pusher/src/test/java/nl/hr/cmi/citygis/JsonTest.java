package nl.hr.cmi.citygis;

import com.google.gson.Gson;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.models.Position;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Unit test for simple App.
 */
public class JsonTest extends TestCase {

    Calendar c;
    Gson jsonConverter;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JsonTest(String testName) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( JsonTest.class );
    }

    public void setUp() throws Exception {
        c = Calendar.getInstance();
        jsonConverter = new Gson();
        super.setUp();
    }

    public void testJSONConverterRoundtrip(){
        Position p = getPosition();
        String jsonAsString = jsonConverter.toJson(p);
        Assert.assertTrue(jsonAsString.contains("357566000058106"));
    }


    private Position getPosition(){
        Position p = new Position();
        LocalDateTime dateTime = LocalDateTime.now();
        p.setDateTime(dateTime);
        p.setUnitId("357566000058106");
        p.setrDx("158126.102542985");
        p.setrDy("380446.027478599");
        p.setCourse(31);
        p.setNumSattellites(7);
        p.setHdop(1);
        p.setQuality("GPS");
        return p;
    }
}
