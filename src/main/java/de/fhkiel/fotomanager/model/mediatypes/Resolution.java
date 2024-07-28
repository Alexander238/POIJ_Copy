package de.fhkiel.fotomanager.model.mediatypes;

import lombok.Data;

/**
 * The type Resolution.
 */
@Data
public class Resolution {
    /**
     * The Width.
     */
    private long width;
    /**
     * The Height.
     */
    private long height;

    /**
     * Instantiates a new Resolution.
     *
     * @param width  the width
     * @param height the height
     */
    public Resolution(long width, long height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(long width) {
        if (width >= 0) {
            this.width = width;
        } else {
            throw new IllegalArgumentException("width must be greater than or equal to 0");
        }
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(long height) {
        if (height >= 0) {
            this.height = height;
        } else {
            throw new IllegalArgumentException("height must be greater than or equal to 0");
        }
    }

    /**
     * Returns resolution as readable string.
     * @return the resolution as string WxH
     */
    @Override
    public String toString() {
        return width + "x" + height;
    }
}
