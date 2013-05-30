package org.jojen.wikistudy.domain;

import org.jojen.wikistudy.domain.dep.Actor;
import org.jojen.wikistudy.domain.dep.Director;
import org.jojen.wikistudy.domain.dep.Rating;
import org.jojen.wikistudy.domain.dep.Role;
import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.*;

import static org.neo4j.graphdb.Direction.INCOMING;


/**
 * @author mh
 * @since 04.03.11
 */
@NodeEntity
public class Course implements Cloneable {


	@GraphId
	Long nodeId;


	private String description;

	@Indexed(indexType = IndexType.FULLTEXT, indexName = "search")
	private String title;

	@Fetch
	@RelatedTo(type = "OLD_VERSION", direction = Direction.OUTGOING)
	Course oldVersion;

	@Fetch
	@RelatedTo(type = "DRAFT_VERSION", direction = Direction.OUTGOING)
	Course draftVersion;

	@Fetch
	@RelatedTo(type = "LESSON", direction = Direction.OUTGOING)
	Collection<Lesson> lessons;


	@RelatedToVia(type = "RATED", direction = INCOMING)
	@Fetch
	Iterable<Rating> ratings;


	public Course() {
	}


	public Course getDraftVersion() {
		return draftVersion;
	}

	public void setDraftVersion(Course draftVersion) {
		this.draftVersion = draftVersion;
	}

	public Course getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(Course oldVersion) {
		this.oldVersion = oldVersion;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Collection<Role> getRoles() {
		return IteratorUtil.asCollection(roles);
	}

	public int getYear() {
		return 0;
	}

	public Long getId() {
		return nodeId;
	}


	public Boolean getHasDraft() {
		return getDraftVersion() != null;
	}

	public Collection<Lesson> getLessons() {
		return lessons;
	}

	public void addLesson(Lesson l) {
		// TODO das sollte noch sortiert möglich sein
		if (lessons == null) {
			lessons = new HashSet<Lesson>();
		}
		lessons.add(l);
	}


	public Collection<Rating> getRatings() {
		Iterable<Rating> allRatings = ratings;
		return allRatings == null ? Collections.<Rating>emptyList() : IteratorUtil.asCollection(allRatings);
	}


	@Override
	public String toString() {
		return String.format("%s (%s) [%s]", getTitle(), getLastModified(), id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Course course = (Course) o;
		if (nodeId == null) return super.equals(o);
		return nodeId.equals(course.nodeId);

	}

	@Override
	public int hashCode() {
		return nodeId != null ? nodeId.hashCode() : super.hashCode();
	}


	public String getLanguage() {
		return null;
	}

	public String getImdbId() {
		return null;
	}

	public String getTagline() {
		return null;
	}

	public Date getReleaseDate() {
		return null;
	}

	public Integer getRuntime() {
		return 0;
	}

	public String getHomepage() {
		return null;
	}

	public String getTrailer() {
		return null;
	}

	public String getGenre() {
		return null;
	}

	public String getStudio() {
		return null;
	}

	public Integer getVersion() {
		return 0;
	}

	public Date getLastModified() {
		return null;
	}


	@Override
	public Course clone() {
		try {
			return (Course) super.clone();    //To change body of overridden methods use File | Settings | File Templates.
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return null;
	}


	@Indexed
	String id;

	@RelatedTo(type = "DIRECTED", direction = INCOMING)
	Set<Director> directors;

	@RelatedTo(type = "ACTS_IN", direction = INCOMING)
	Set<Actor> actors;

	@RelatedToVia(type = "ACTS_IN", direction = INCOMING)
	Iterable<Role> roles;
}

