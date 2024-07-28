package de.fhkiel.fotomanager.model.search;

import de.fhkiel.fotomanager.controller.modelController.SearchController;
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
 * The type Search parameter builder.
 */
@Getter
public class SearchParameterBuilder {
    /**
     * The Name.
     */
    private Optional<String> name = Optional.empty();
    /**
     * The Date or period.
     */
    private Optional<Either<LocalDate, Period>> date = Optional.empty();
    /**
     * The Rating.
     */
    private Optional<Rating> rating = Optional.empty();
    /**
     * The Is private.
     */
    private Optional<Boolean> isPrivate = Optional.empty();
    /**
     * The Tags.
     */
    private Optional<TagList> tags = Optional.empty();
    /**
     * The Description.
     */
    private Optional<String> description = Optional.empty();

    /**
     * Instantiates a new Search parameter builder.
     */
    public SearchParameterBuilder() {}

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public SearchParameterBuilder setName(String name) {
        this.name = Optional.ofNullable(name);
        return this;
    }

    /**
     * Sets date.
     *
     * @param date the date
     * @return the date
     */
    public SearchParameterBuilder setDate(Either<LocalDate, Period> date) {
        this.date = Optional.ofNullable(date);
        return this;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     * @return the rating
     */
    public SearchParameterBuilder setRating(Rating rating) {
        this.rating = Optional.ofNullable(rating);
        return this;
    }

    /**
     * Sets is private.
     *
     * @param isPrivate the is private
     * @return the is private
     */
    public SearchParameterBuilder setIsPrivate(Boolean isPrivate) {
        this.isPrivate = Optional.ofNullable(isPrivate);
        return this;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     * @return the tags
     */
    public SearchParameterBuilder setTags(TagList tags) {
        this.tags = Optional.ofNullable(tags);
        return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public SearchParameterBuilder setDescription(String description) {
        this.description = Optional.ofNullable(description);
        return this;
    }

    /**
     * Build search parameter.
     *
     * @return the search parameter
     */
    public SearchParameter build(){
        return SearchController.get().createSearchParameter(name, date, rating, isPrivate, tags, description);
    }
}
