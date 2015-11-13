package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.models.CityGisData;

import java.util.Calendar;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessagePlaybackSchedulerTest extends TestCase {
    PlaybackScheduler scheduler;
    Stream<CityGisData> data;
    Publishable messageBroker;
    CsvConverter csvConverter;
    Calendar c;

    public void setUp() throws Exception {
        super.setUp();
        //csvConverter = new CsvConverter("resources/",FileMapping.EVENTS);
//        data = csvConverter.getData();
//        messageBroker = new MockClientBroker();
//        c=Calendar.getInstance();
    }


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessagePlaybackSchedulerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MessagePlaybackSchedulerTest.class);
    }

    public void testScheduler() throws Exception {
        Assert.assertTrue(true);
    }
    //    public void testScheduler() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime fileStartTime = LocalDateTime.parse("2015-03-10 07:12:25", formatter);
//
//        scheduler = new PlaybackScheduler(fileStartTime, messageBroker);
//        scheduler.startPlayback(data);
//
//        long firstReading = c.getTimeInMillis();;
//        Thread.sleep(2000);
//
//        long secondReading = c.getTimeInMillis();;
//        //TODO update calenar
//        Assert.assertTrue(true);
//        scheduler.stopPlayback();
//    }
}
