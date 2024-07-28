package de.fhkiel.fotomanager.model;

import lombok.Getter;

import java.time.LocalDate;

/**
 * The type Period.
 */
@Getter
public class Period {
    /**
     * The Start of a period.
     */
    private LocalDate start;
    /**
     * The End of a period.
     */
    private LocalDate end;

    /**
     * Instantiates a new Period.
     *
     * @param start the start
     * @param end   the end
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Period(LocalDate start, LocalDate end) throws IllegalArgumentException {
        if (start == null && end == null) {
            throw new IllegalArgumentException("Both start and end date cannot be null");
        }

        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        this.start = start;
        this.end = end;
    }
    @Override
    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        return start + " , " + end;
    }
}
