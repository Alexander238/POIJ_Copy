package de.fhkiel.fotomanager.global;

import de.fhkiel.fotomanager.model.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Period test.
 */
public class PeriodTest {

    /**
     * Create period test.
     */
// Test if a period can be created
    @Test
    public void createPeriodTest() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);
        Period period = new Period(start, end);

        // Check if period is not null
        assertNotNull(period);

        // Check if start date is not null
        assertNotNull(period.getStart());

        // Check if end date is not null
        assertNotNull(period.getEnd());

        // Check if start date is correct
        assertTrue(period.getStart().equals(start));

        // Check if end date is correct
        assertTrue(period.getEnd().equals(end));
    }

    /**
     * Create period with null start test.
     */
// Test if a period can be created with null start date
    @Test
    public void createPeriodWithNullStartTest() {
        LocalDate end = LocalDate.of(2020, 1, 1);
        Period period = new Period(null, end);

        // Check if period is not null
        assertNotNull(period);

        // Check if start date is null
        assertTrue(period.getStart() == null);

        // Check if end date is not null
        assertNotNull(period.getEnd());

        // Check if end date is correct
        assertTrue(period.getEnd().equals(end));
    }

    /**
     * Create period with null end test.
     */
// Test if a period can be created with null end date
    @Test
    public void createPeriodWithNullEndTest() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        Period period = new Period(start, null);

        // Check if period is not null
        assertNotNull(period);

        // Check if start date is not null
        assertNotNull(period.getStart());

        // Check if start date is correct
        assertTrue(period.getStart().equals(start));

        // Check if end date is null
        assertTrue(period.getEnd() == null);
    }

    /**
     * Create period with null start and end test.
     */
// Test creating a period with null start and end date
    @Test
    public void createPeriodWithNullStartAndEndTest() {
        // Check if IllegalArgumentException is thrown when creating a period with null start and end date
        assertThrows(IllegalArgumentException.class, () -> new Period(null, null));

        try {
            new Period(null, null);
        } catch (IllegalArgumentException e) {
            // Check if the exception message is correct
            assertEquals("Both start and end date cannot be null", e.getMessage());
        }
    }

    /**
     * Create period with start after end test.
     */
// Test creating a period with start date after end date
    @Test
    public void createPeriodWithStartAfterEndTest() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2000, 1, 1);

        // Check if IllegalArgumentException is thrown when creating a period with start date after end date
        assertThrows(IllegalArgumentException.class, () -> new Period(start, end));

        try {
            new Period(start, end);
        } catch (IllegalArgumentException e) {
            // Check if the exception message is correct
            assertEquals("Start date cannot be after end date", e.getMessage());
        }
    }

    /**
     * To string test.
     */
    @Test
    public void toStringTest() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);
        Period period = new Period(start, end);

        // Check if the toString method returns the correct string
        assertEquals("2000-01-01 , 2024-01-01", period.toString());
    }

}
