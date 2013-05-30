<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head><title>${course.title}</title></head>
<body>
<c:choose>
    <c:when test="${not empty course}">
        <div class="span-5">
            <div class="profile-header">
                    <%--@elvariable id="course" type="org.jojen.wikistudy.domain.Course"--%>
                <c:set var="image" value="${course.imageUrl}"/>
                <c:if test="${empty image}"><c:set var="image" value="/images/course-placeholder.png"/></c:if>
                <div class="profile-image"><img src="<c:url value="${image}"/>"/></div>
                <div class="profile-header-details">
                    <h2>${course.title} (${course.year}) <img src="/images/rated_${stars}.png" alt="${stars} stars"/>
                    </h2>
                </div>
                <h3><${course.tagline}</h3>

                <div class="break"></div>
            </div>

            <div class="span-half">
                <h3>Movie facts</h3>

                <table>
                    <tr>
                        <th>Language</th>
                        <td>${course.language}</td>
                    </tr>
                    <tr>
                        <th>Runtime</th>
                        <td>${course.runtime} Minutes</td>
                    </tr>
                    <tr>
                        <th>Genre</th>
                        <td>${course.genre}</td>
                    </tr>
                    <tr>
                        <th>Other sites</th>
                        <td><a target="cineasts_link" href="http://www.themoviedb.org/course/${course.id}">TheMovieDb.org</a>
                            |
                            <a target="cineasts_link" href="http://www.imdb.com/title/${course.imdbId}">IMDb</a>
                        </td>
                    </tr>
                    <tr>
                        <th>Buy DVDs & Books</th>
                        <td><a target="cineasts_link"
                               href="http://www.amazon.com/s/?url=search-alias%3Daps&field-keywords=<c:url value="${course.title}"/>">Amazon</a>
                            |
                            <a target="cineasts_link"
                               href="http://www.cinebutler.com/search.html?searchBy=title&searchFor=<c:url value="${course.title}"/>">CineButler</a>
                        </td>
                    </tr>
                    <tr>
                        <th>In Cinemas</th>
                        <td><a target="cineasts_link"
                               href="http://www.google.com/movies?q=<c:url value="${course.title}"/>">Google Movies</a>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="span-half last">
                <c:if test="${not empty course.trailer}">
                    <h3>Trailers</h3>
                    <c:set var="youtube" value="course.youtubeId"/>
                    <c:choose>
                        <c:when test="${not empty youtube}">
                            <iframe title="YouTube video player" width="200" height="143"
                                    src="http://www.youtube.com/embed/${course.}?rel=0&controls=0&egm=1&fs=1"
                                    frameborder="0" allowfullscreen></iframe>
                        </c:when>
                        <c:otherwise>
                            <ul>
                                <li><a href="${course.trailer}">Trailer</a></li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>

            <div class="span-half">
                <h3>Release info</h3>

                <ul>
                    <li>
                        <table>
                            <tr>
                                <th>Released</th>
                                <td><fmt:formatDate value="${course.releaseDate}" pattern="yyyy/dd/MM"/></td>
                            </tr>
                            <c:if test="${not empty course.homepage}">
                                <tr>
                                    <th></th>
                                    <td><a href="${course.homepage}">Homepage</a></td>
                                </tr>
                            </c:if>
                            <tr>
                                <th>Studio</th>
                                <td>${course.studio}</td>
                            </tr>
                        </table>
                    </li>
                </ul>
            </div>

        </div>
        <div class="span-7 last">
            <div class="course-content-outer">
                <div class="course-content">
                    <h2>Overview</h2>

                    <p>${course.description}</p>

                    <h2>Cast</h2>
                    <c:if test="${not empty course.roles}">
                        <ul class="actors-list">
                            <c:forEach items="${course.roles}" var="role">
                                <c:set var="actor" value="${role.actor}"/>
                                <li>
                                    <c:set var="image" value="${actor.profileImageUrl}"/>
                                    <c:if test="${empty image}"><c:set var="image"
                                                                       value="/images/profile-placeholder-small.png"/></c:if>
                                    <a class="actor-image" href="<c:url value="/actors/${actor.id}" />"><img
                                            alt="${actor.name}" src="<c:url value="${image}" />"/></a>
                                    <a href="<c:url value="/actors/${actor.id}" />"><c:out value="${actor.name}"/> as
                                        <c:out value="${role.name}"/></a>
                                </li>
                            </c:forEach>
                        </ul>
                        <div class="break"></div>
                    </c:if>

                    <h3>Reviews</h3>
                    <c:if test="${not empty user}">
                        <script type="text/javascript">
                            function rate(n) {
                                var hidden = document.getElementById('rated');
                                hidden.value = n;
                                for (i = 1; i <= 5; i++) {
                                    document.getElementById("rated_" + i).src = (i <= n ) ? "/images/rating-active.png" : "/images/rating-disabled.png";
                                }
                            }
                        </script>
                        <form method="post" action="<c:url value="/movies/${course.id}" />">
                            <h4>Give
                                <c:forEach begin="1" end="5" var="i">
                                    <a href="#" onClick="rate(${i});"><img src="/images/rating-active.png"
                                                                           id="rated_${i}"/></a>
                                </c:forEach>
                                to &quot;${course.title}&quot; saying:
                                <input type="hidden" value="${userRating.stars}" name="rated" id="rated"/>
                                <input type="text" size="100" name="comment" value="${userRating.comment}"/>
                                <input type="submit" value="Rate!"/>
                            </h4>
                        </form>
                        <script type="text/javascript">
                            rate(${userRating.stars});
                        </script>
                    </c:if>
                    <c:if test="${not empty course.ratings}">
                        <ul>
                            <c:forEach items="${course.ratings}" var="rating">
                                <c:if test="${rating != userRating}">
                                    <li><img src="/images/rated_${rating.stars}.png" alt="${rating.stars} stars"/> by <a
                                            href="<c:url value="/user/${rating.user.login}" />">${rating.user.name}</a>
                                        who says: &quot;${rating.comment}&quot;</li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <h2>No course found</h2>

        <p>The course you were looking for could not be found.</p>
    </c:otherwise>
</c:choose>
</body>
</html>
