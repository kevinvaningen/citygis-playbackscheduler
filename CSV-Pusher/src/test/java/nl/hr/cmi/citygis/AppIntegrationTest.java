package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by cmi on 10-11-15.
 */
public class AppIntegrationTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppIntegrationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppIntegrationTest.class);
    }


    public void testAppWithMonitoring() throws Exception {
        /*
        Thread t = new Thread(new Runnable() {
            Calendar c = Calendar.getInstance();
            Date start = c.getTime();
            App a = new App("Monitoring.csv");
            boolean isRunning = true;

            @Override
            public void run() {
                if (!isRunning) {
                    //run it
                }
                Date end = c.getTime();
                if (end.getTime() - start.getTime() > 3000) {
                    Assert.assertTrue(true);
                    isRunning = false;
                }
            }
        });
        }
        */
    }
}
