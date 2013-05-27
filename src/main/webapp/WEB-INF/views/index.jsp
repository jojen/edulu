<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@page trimDirectiveWhitespaces="true" %>

<div class="row">


    <div class="span3 bs-docs-sidebar">
        <ul class="nav nav-list bs-docs-sidenav affix-top">
            <%--c:forEach items="${courses}" var="course">
                <li class="active"><a href="#${course.id}"><i class="icon-chevron-right"></i>${course.title}</a></li>
            </c:forEach--%>
        </ul>
    </div>
    <div class="span9">
        <c:forEach items="${courses}" var="course" varStatus="status">
            <div id="${course.id}" class="course box-shadow media">

                <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">

                <div class="media-body">
                    <a class="right more-margin btn btn-primary" href="#">Start Course</a>
                    <h3 class="update" data-id="${course.id}" data-key="title">${course.title}</h3>
                    <h4>Level: Beginner</h4>
                    <!-- TODO darf der user das -->

                    <p><c:out value="${course.description}"/></p>

                    <ul>
                        <li><span>7</span> Lessons</li>
                        <li>|</li>
                        <li><span>56</span> Exercises</li>
                    </ul>
                    <a data-id="${course.id}" class="edit right btn btn-warning" href="#">Edit</a>
                </div>

            </div>
        </c:forEach>

    </div>
</div>

