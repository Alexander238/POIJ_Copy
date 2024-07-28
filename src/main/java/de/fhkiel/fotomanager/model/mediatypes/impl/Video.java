package de.fhkiel.fotomanager.model.mediatypes.impl;

import de.fhkiel.fotomanager.model.mediatypes.*;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Video.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class Video extends Media {
    /**
     * The Duration.
     */
    private long duration;
    /**
     * The Playback speed.
     */
    private PlaybackSpeed playbackSpeed;
    /**
     * Attribute to check if the video is playing.
     */
    private boolean isPlaying;
    /**
     * The Current time of the video.
     */
    private long currentTime;

    /**
     * Instantiates a new Video.
     *
     * @param file        the file
     * @param name        the name
     * @param date        the date
     * @param description the description
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param duration    the duration
     * @param rating      the rating
     * @param orientation the orientation
     * @param resolution  the resolution
     */
    public Video(
            String file,
            String name,
            LocalDate date,
            String description,
            boolean isPrivate,
            TagList tags,
            long duration,
            Rating rating,
            Orientation orientation,
            Resolution resolution
    ) {
        super(file, name, date, description, isPrivate, tags, rating, orientation, resolution);
        this.duration = duration;
        this.currentTime = 0;
        this.playbackSpeed = PlaybackSpeed.X1;
        this.isPlaying = false;
    }

    /**
     * Delete a video.
     * @return
     */
    @Override
    public boolean delete() {
        super.delete();
        this.duration = 0;
        return true;
    }

    /**
     * Resume playing the video.
     */
    public void play() {
        this.isPlaying = true;
    }

    /**
     * Pause the video.
     */
    public void pause() {
        this.isPlaying = false;
    }

    /**
     * Stop the video.
     */
    public void stop() {
        this.isPlaying = false;
        currentTime = 0;
    }

    /**
     * Play from.
     *
     * @param time the time
     */
// ToDo: Change actual time of video.
    public void playFrom(long time) {currentTime = time;}

    /**
     * Change the playback speed of the video.
     *
     * @param playbackSpeed The new playback speed.
     */
    public void changePlaybackSpeed(PlaybackSpeed playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    /**
     * Increase the playback speed of the video.
     */
    public void increasePlaybackSpeed() {
        if (playbackSpeed == PlaybackSpeed.X32) throw new IllegalArgumentException("Playback speed is already at maximum");
        changePlaybackSpeed(PlaybackSpeed.values()[playbackSpeed.ordinal() + 1]);
    }

    /**
     * Decrease the playback speed of the video.
     */
    public void decreasePlaybackSpeed() {
        if (playbackSpeed == PlaybackSpeed.X025) throw new IllegalArgumentException("Playback speed is already at minimum");
        changePlaybackSpeed(PlaybackSpeed.values()[playbackSpeed.ordinal() - 1]);
    }

    /**
     * Check if this object is equal to another object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Video) {
            Video other = (Video) obj;
            return super.equals(obj) &&
                    this.duration == other.duration &&
                    this.playbackSpeed == other.playbackSpeed &&
                    this.isPlaying == other.isPlaying &&
                    this.currentTime == other.currentTime;
        }
        return false;
    }

    /**
     * To xml element.
     *
     * @param document the document
     * @return the element
     */
    public Element toXML(Document document) {
        Element element = document.createElement("Video");
        element.setAttribute("file", this.getFile());
        element.setAttribute("name", this.getName());
        element.setAttribute("date", this.getDate().toString());
        element.setAttribute("description", this.getDescription());
        element.setAttribute("isPrivate", String.valueOf(this.isPrivate()));
        element.setAttribute("rating", this.getRating().name());
        element.setAttribute("duration", String.valueOf(this.duration));
        element.setAttribute("orientation", this.getOrientation().name());
        element.setAttribute("resolution", this.getResolution().getWidth() + "x" + this.getResolution().getHeight());
        for (Tag tag : this.getTags().getTags()) {
            Element tagElement = tag.toXML(document);
            element.appendChild(tagElement);
        }
        return element;
    }

    /**
     * From xml video.
     *
     * @param element the element
     * @return the video
     */
    public static Video fromXML(Element element) {
        String name = element.getAttribute("name");
        String fileString = element.getAttribute("file");
        LocalDate date = LocalDate.parse(element.getAttribute("date"));
        String description = element.getAttribute("description");
        boolean isPrivate = Boolean.parseBoolean(element.getAttribute("isPrivate"));
        long duration = Long.parseLong(element.getAttribute("duration"));
        Rating rating = Rating.valueOf(element.getAttribute("rating"));
        Orientation orientation = Orientation.valueOf(element.getAttribute("orientation"));
        String[] resolutionParts = element.getAttribute("resolution").split("x");
        long width = Long.parseLong(resolutionParts[0]);
        long height = Long.parseLong(resolutionParts[1]);
        Resolution resolution = new Resolution(width, height);
        TagList tags = Photo.extractTags(element);
        return new Video(fileString, name, date, description, isPrivate, tags, duration, rating, orientation, resolution);
    }
}
