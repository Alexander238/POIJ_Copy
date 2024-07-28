package de.fhkiel.fotomanager.model.mediatypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Rating test.
 */
public class RatingTest {

    /**
     * Zero stars to string test.
     */
    @Test
    public void zeroStarsToStringTest() {
        Rating rating = Rating.ZERO_STARS;
        String result = rating.toString();
        assertEquals("0*", result);
    }

    /**
     * One stars to string test.
     */
    @Test
    public void oneStarsToStringTest() {
        Rating rating = Rating.ONE_STARS;
        String result = rating.toString();
        assertEquals("1*", result);
    }

    /**
     * Two stars to string test.
     */
    @Test
    public void twoStarsToStringTest() {
        Rating rating = Rating.TWO_STARS;
        String result = rating.toString();
        assertEquals("2*", result);
    }

    /**
     * Three stars to string test.
     */
    @Test
    public void threeStarsToStringTest() {
        Rating rating = Rating.THREE_STARS;
        String result = rating.toString();
        assertEquals("3*", result);
    }

    /**
     * Four stars to string test.
     */
    @Test
    public void fourStarsToStringTest() {
        Rating rating = Rating.FOUR_STARS;
        String result = rating.toString();
        assertEquals("4*", result);
    }

    /**
     * Five stars to string test.
     */
    @Test
    public void fiveStarsToStringTest() {
        Rating rating = Rating.FIVE_STARS;
        String result = rating.toString();
        assertEquals("5*", result);
    }
}
