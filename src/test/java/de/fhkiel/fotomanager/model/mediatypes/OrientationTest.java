package de.fhkiel.fotomanager.model.mediatypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Orientation test.
 */
public class OrientationTest {

    /**
     * DO to angle test.
     */
    @Test
    public void D0ToAngleTest() {
        Orientation orientation = Orientation.D0;
        double result = orientation.toAngle();
        assertEquals(0, result);
    }

    /**
     * D90 to angle test.
     */
    @Test
    public void D90ToAngleTest() {
        Orientation orientation = Orientation.D90;
        double result = orientation.toAngle();
        assertEquals(90, result);
    }

    /**
     * D180 to angle test.
     */
    @Test
    public void D180ToAngleTest() {
        Orientation orientation = Orientation.D180;
        double result = orientation.toAngle();
        assertEquals(180, result);
    }

    /**
     * D270 to angle test.
     */
    @Test
    public void D270ToAngleTest() {
        Orientation orientation = Orientation.D270;
        double result = orientation.toAngle();
        assertEquals(270, result);
    }
}
