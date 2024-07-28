package de.fhkiel.fotomanager.model.mediatypes;

/**
 * The enum Orientation.
 */
public enum Orientation {
    /**
     * D 0 orientation.
     */
    D0 {
        @Override
        public double toAngle() {
            return 0;
        }
    },
    /**
     * D 90 orientation.
     */
    D90 {
        @Override
        public double toAngle() {
            return 90;
        }
    },
    /**
     * D 180 orientation.
     */
    D180 {
        @Override
        public double toAngle() {
            return 180;
        }
    },
    /**
     * D 270 orientation.
     */
    D270 {
        @Override
        public double toAngle() {
            return 270;
        }
    };

    /**
     * To angle double.
     *
     * @return the double
     */
    public abstract double toAngle();
}
