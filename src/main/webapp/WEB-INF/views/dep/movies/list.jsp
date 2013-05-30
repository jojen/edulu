<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Results for &quot;${query}&quot;</h2>

<c:choose>
    <c:when test="${not empty movies}">
        <ul class="search-results">
            <c:forEach items="${movies}" var="course">
                <li>
                    <div class="search-result-details">
                        <c:set var="image" value="${course.imageUrl}"/>
                        <c:if test="${empty image}"><c:set var="image" value="/images/course-placeholder.png"/></c:if>
                        <a class="thumbnail" href="<c:url value="/movies/${course.id}" />"> <img
                                src="<c:url value="${image}" />"/></a>
                        <a href="/movies/${course.id}">${course.title}</a> <img alt="${course.stars} stars"
                                                                                src="/images/rated_${course.stars}.png"/>

                        <p><c:out value="${course.tagline}" escapeXml="true"/></p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <h2>No movies found for query &quot;${query}&quot;.</h2>
    </c:otherwise>
</c:choose>
