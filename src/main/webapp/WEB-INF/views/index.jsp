<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Container" scope="request"/>

<!-- TODO darf der user das -->
<div class="container-fluid">
    <div class="row-fluid">

        <c:if test="${!empty self}">
            <div class="span3 bs-docs-sidebar">
                <ul class="nav nav-list bs-docs-sidenav affix-top">
                    <c:forEach items="${self.content}" var="course">
                        <li class="active"><a href="#${course.id}"><i class="icon-chevron-right"></i>${course.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="span7">
                <div class="row">
                    <c:forEach items="${self.content}" var="course" varStatus="status">
                        <div class="course light-box mini-layout box-shadow">
                            <div class="row-fluid">
                                <div class="span2">
                                    <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">
                                </div>
                                <div class="span10">
                                    <a class="right more-margin btn btn-primary" href="/course/${course.id}">Start
                                        Course</a>

                                    <h3 class="update" data-id="${course.id}" data-key="title">${course.title}</h3>
                                    <h4>Level: Beginner</h4>


                                    <p><c:out value="${course.description}" escapeXml="false"/></p>

                                    <ul>
                                        <li><span>7</span> Lessons</li>
                                        <li>|</li>
                                        <li><span>56</span> Exercises</li>
                                    </ul>
                                    <div class="btn-group right">
                                        <c:if test="${course.hasDraft}">
                                            <a data-id="${course.id}" class="btn btn-danger">Revert</a>
                                        </c:if>
                                        <a data-id="${course.id}" class="update-course btn btn-warning">Edit</a>
                                        <c:if test="${course.hasDraft}">
                                            <a data-id="${course.id}" class="btn btn-success">Publish</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </c:forEach>
                </div>
                <div class="row">
                    <div class="light-box span2 box-shadow centred update-course btn" id="add-course">
                        Add Course
                    </div>
                </div>

            </div>

        </c:if>

    </div>
</div>