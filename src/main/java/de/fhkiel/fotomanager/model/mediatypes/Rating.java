package de.fhkiel.fotomanager.model.mediatypes;

/**
 * The enum Rating.
 */
public enum Rating {
    /**
     * Zero stars rating.
     */
    ZERO_STARS {
        @Override
        public String toString() {
            return "0*";
        }
    },
    /**
     * One stars rating.
     */
    ONE_STARS {
        @Override
        public String toString() {
            return "1*";
        }
    },
    /**
     * Two stars rating.
     */
    TWO_STARS {
        @Override
        public String toString() {
            return "2*";
        }
    },
    /**
     * Three stars rating.
     */
    THREE_STARS {
        @Override
        public String toString() {
            return "3*";
        }
    },
    /**
     * Four stars rating.
     */
    FOUR_STARS {
        @Override
        public String toString() {
            return "4*";
        }
    },
    /**
     * Five stars rating.
     */
    FIVE_STARS {
        @Override
        public String toString() {
            return "5*";
        }
    }
}
