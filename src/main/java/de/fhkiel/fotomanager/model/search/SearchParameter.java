package de.fhkiel.fotomanager.model.search;

import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;

import io.vavr.control.Either;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The type Search parameter.
 */
@Getter
public class SearchParameter {
    /**
     * The Name.
     */
    private final Optional<String> name;
    /**
     * The Date or period.
     */
    private final Optional<Either<LocalDate, Period>> date;
    /**
     * The Rating.
     */
    private final Optional<Rating> rating;
    /**
     * The Is private.
     */
    private final Optional<Boolean> isPrivate;
    /**
     * The Tags.
     */
    private final Optional<TagList> tags;
    /**
     * The Description.
     */
    private final Optional<String> description;

    /**
     * Instantiates a new Search parameter.
     *
     * @param name        the name
     * @param date        the date
     * @param rating      the rating
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param description the description
     * @throws IllegalArgumentException the illegal argument exception
     */
    public SearchParameter(Optional<String> name,
                            Optional<Either<LocalDate, Period>> date,
                           Optional<Rating> rating,
                           Optional<Boolean> isPrivate,
                           Optional<TagList> tags,
                           Optional<String> description) throws IllegalArgumentException {
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.isPrivate = isPrivate;
        this.tags = tags;
        this.description = description;
    }

    /**
     *
     * checks if the media matches the search parameters
     *
     * @param obj
     * @return true if the media matches the search parameters
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SearchParameter) {
            SearchParameter other = (SearchParameter) obj;
            return this.name.equals(other.name) &&
                    this.date.equals(other.date) &&
                    this.rating.equals(other.rating) &&
                    this.isPrivate.equals(other.isPrivate) &&
                    this.tags.equals(other.tags) &&
                    this.description.equals(other.description);
        }
        return false;
    }
}
