package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.Position;
import org.junit.Test;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertTrue;


public class PositionUnitTest {
    @Test
    public void testPositionsStartDateUsingEmptyString() {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime("");
        }catch(IllegalArgumentException e){
            catchedCorrectly = true;
        }
        assertTrue(catchedCorrectly);
    }

    @Test
    public void testPositionsStartDateUsingNull()  {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime((String)null);
        }catch(IllegalArgumentException e){
            catchedCorrectly = true;
        }
        assertTrue(catchedCorrectly);
    }

    @Test
    public void testPositionsStartDateUsingBadFormat()  {
        Position p = new Position();
        boolean catchedCorrectly = false;
        try{
            p.setDateTime("2389348923988923");
        }catch(DateTimeParseException e){
            catchedCorrectly = true;
        }
        assertTrue(catchedCorrectly);
    }

    @Test
    public void testPositionsStartDateUsingProperFormat()  {
        Position p = new Position();
        boolean catchedCorrectly = true;
        try{
            p.setDateTime("2015-03-10 07:12:25");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            catchedCorrectly = false;
        }
        assertTrue(catchedCorrectly);
    }
}
