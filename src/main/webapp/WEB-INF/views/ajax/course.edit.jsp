<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Course" scope="request"/>

<div id="${self.id}-edit" class="course box-shadow media">

    <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">

    <div class="media-body">
        <a data-id="${self.id}" class="close close-edit">×</a>

        <input class="update" data-id="${self.id}" data-key="title" value="${self.title}"/>

        <h4>Level: Beginner</h4>
        <!-- TODO darf der user das -->

        <div class="course-description" data-id="${self.id}" data-key="description">
            <textarea class="rte" cols="80" name="editor1" rows="5"><c:out value="${self.description}"/></textarea>

        </div>

    </div>

</div>


<c:url value="/resources/ckeditor/" var="ckeditorpath"/>
<ckeditor:replaceAll basePath="${ckeditorpath}" className="rte"/>