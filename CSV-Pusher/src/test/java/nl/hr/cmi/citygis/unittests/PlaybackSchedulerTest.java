package nl.hr.cmi.citygis.unittests;

import nl.hr.cmi.citygis.PlaybackScheduler;
import nl.hr.cmi.citygis.mocks.MockClientBroker;
import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import nl.hr.cmi.citygis.models.Monitoring;
import org.junit.Before;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by cmi on 09-11-15.
 */
public class PlaybackSchedulerTest {
    PlaybackScheduler scheduler;

    @Before
    public void setUp() throws Exception {
        scheduler = new PlaybackScheduler(LocalDateTime.now(), new MockClientBroker(), FileMapping.MONITORING);
    }

    @org.junit.Test
    public void testScheduler() throws Exception {
        scheduler.startPlayback(getTestDataWithDateTime(LocalDateTime.now()).stream());
        //ASSERT I DIDNT DIE
        assertTrue(true);

    }

    private List getTestDataWithDateTime(LocalDateTime d) {
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
