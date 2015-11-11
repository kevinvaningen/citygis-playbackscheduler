package nl.hr.cmi.citygis;

import junit.framework.Assert;
import junit.framework.TestCase;
import nl.hr.cmi.citygis.models.Position;

import java.time.format.DateTimeParseException;

/**
 * Created by cmi on 11-11-15.
 */
public class PositionTest extends TestCase {

    public void testPositionsStartDateUsingEmptyString() {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime("");
        }catch(IllegalArgumentException e){
            catchedCorrectly = true;
        }
        Assert.assertTrue(catchedCorrectly);
    }
    public void testPositionsStartDateUsingNull()  {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime((String)null);
        }catch(IllegalArgumentException e){
            catchedCorrectly = true;
        }
        Assert.assertTrue(catchedCorrectly);
    }
    public void testPositionsStartDateUsingBadFormat()  {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime("2389348923988923");
        }catch(DateTimeParseException e){
            catchedCorrectly = true;
        }
        Assert.assertTrue(catchedCorrectly);
    }
    public void testPositionsStartDateUsingProperFormat()  {
        Position p = new Position();
        boolean catchedCorrectly = true;
        try{
            p.setDateTime("2015-03-10 07:12:25");
        }catch(IllegalArgumentException e){
            catchedCorrectly = false;
        }catch(DateTimeParseException e){
            catchedCorrectly = false;
        }
        Assert.assertTrue(catchedCorrectly);
    }
}
