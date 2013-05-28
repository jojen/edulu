<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Course" scope="request"/>

<!-- TODO darf der user das -->
<div id="course-update" class="modal hide fade">
    <form action="course/update" class="navbar-form pull-left" method="post" id="form-framework">
        <c:if test="${not empty self}">
            <input type="hidden" name="id" value="${self.id}" />
        </c:if>

        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">&times;</a>
            Edit Course
        </div>
        <div class="modal-body">

            <input type="text" placeholder="Title" name="title" value="<c:if test="${!empty self}"><c:out value="${self.title}" /></c:if>"/>

            <label>Level:</label>
            <select name="level">
                <option>Beginner</option>
                <option>Advanced</option>
            </select>

            <div>Description:</div>
            <c:url value="/resources/ckeditor/" var="ckeditorpath"/>
            <c:set value="${self.description}" var="value"/>

            <ckeditor:editor basePath="${ckeditorpath}" editor="description" value="${value}"/>

        </div>
        <div class="modal-footer">
            <a href="#" class="btn">TODO Revert</a>
            <a href="#" class="btn btn-primary">Save</a>
        </div>
    </form>
</div>
