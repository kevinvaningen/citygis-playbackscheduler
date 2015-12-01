package nl.hr.cmi.citygis.unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by cmi on 01-12-15.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BrokerConfigurationTest.class,
        CsvConverterTest.class,
        MockBrokerTest.class,
        PlaybackSchedulerTest.class,
        PositionTest.class
})
public class UnitTestSuite {
}
