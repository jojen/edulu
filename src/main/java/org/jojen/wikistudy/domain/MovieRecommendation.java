package org.jojen.wikistudy.domain;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

/**
* @author mh
* @since 04.11.11
*/
@MapResult
public interface MovieRecommendation {
    @ResultColumn("otherMovie")
	Course getMovie();

    @ResultColumn("rating")
    int getRating();
}
