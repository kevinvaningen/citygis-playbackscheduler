package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.hr.cmi.citygis.mocks.MockBroker;
import nl.hr.cmi.citygis.models.CityGisData;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessagePlaybackSchedulerTest extends TestCase {
    MessageFileRetriever mr;
    MessagePlaybackScheduler scheduler;
    Stream<CityGisData> data;
    Brokereable messageBroker;

    public void setUp() throws Exception {
        super.setUp();
        mr = new MessageFileRetriever();
        data = mr.getDataFromCSV();
        messageBroker = new MockBroker();
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
        scheduler = new MessagePlaybackScheduler(LocalDateTime.now());
        scheduler.startPlayback(data, messageBroker);
        long firstReading = scheduler.getTimeToNextMessage();
        Thread.sleep(2000);
        long secondReading = scheduler.getTimeToNextMessage();
        Assert.assertTrue(secondReading - firstReading > 0);
        scheduler.stopPlayback();
    }
}
