<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<div id="${course.id}" class="course box-shadow media">

    <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">
    <div class="media-body">

        <input class="update" data-id="${course.id}" data-key="title" value="${course.title}" />

        <h4>Level: Beginner</h4>
        <!-- TODO darf der user das -->

        <div class="course-description" data-id="${course.id}" data-key="description">
            <textarea class="rte" cols="80" name="editor1" rows="5"><c:out value="${course.description}"/></textarea>

        </div>

        <ul>
            <li><span>7</span> Lessons</li>
            <li>|</li>
            <li><span>56</span> Exercises</li>
        </ul>

    </div>
    <a class="right more-margin btn btn-primary" href="#">Start Course</a>

</div>


<c:url value="/resources/ckeditor/" var="ckeditorpath" />
<ckeditor:replaceAll basePath="${ckeditorpath}" className="rte"/>