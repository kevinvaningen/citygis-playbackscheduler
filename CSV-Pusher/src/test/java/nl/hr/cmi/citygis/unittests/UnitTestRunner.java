package nl.hr.cmi.citygis.unittests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/***
 * Run the Unit Test suite.
 */
public class UnitTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UnitTestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
