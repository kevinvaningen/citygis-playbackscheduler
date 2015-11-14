package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.mocks.MockClientBroker;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import nl.hr.cmi.citygis.models.Monitoring;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmi on 09-11-15.
 */
public class PlaybackSchedulerTest extends TestCase {
    PlaybackScheduler scheduler;

    public void setUp() throws Exception {
        super.setUp();
        scheduler = new PlaybackScheduler(LocalDateTime.now(), new MockClientBroker(), FileMapping.MONITORING);
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PlaybackSchedulerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(PlaybackSchedulerTest.class);
    }

    public void testScheduler() throws Exception {
        scheduler.startPlayback(getTestDataWithDateTime(LocalDateTime.now()).stream());
        //ASSERT I DIDNT DIE
        Assert.assertTrue(true);

    }

    public List getTestDataWithDateTime(LocalDateTime d) {
        ArrayList<CityGisData> list = new ArrayList<CityGisData>();

        Monitoring m = new Monitoring();
        m.setDateTime(d.plus(2, ChronoUnit.SECONDS));

        Monitoring m2 = new Monitoring();
        m2.setDateTime(d.plus(1, ChronoUnit.SECONDS));
        list.add(m);
        list.add(m2);
        return list;
    }
}
