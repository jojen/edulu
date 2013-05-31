<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Course" scope="request"/>

<%-- wir schauen mal ob ne lesson im model steckt --%>
<c:if test="${empty lesson && !empty self.lessons}">
    <c:set var="lesson" value="${self.firstLesson}"/>
</c:if>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <h3><c:out value="${self.title}"/></h3>

            <div class="btn-group">
                <c:forEach var="l" items="${self.lessons}" varStatus="status">

                    <a href="/course/${self.id}/${l.id}"
                       class="btn <c:if test="${l.id eq lesson.id}">active</c:if>"><c:out value="${status.count}"/></a>
                </c:forEach>
                <a href="/course/${self.id}/${lesson.id}/new-lesson" class="btn">+</a>
            </div>

            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#">Content</a>
                </li>

                <li><a href="#">Glossary</a></li>
            </ul>
        </div>
        <div class="span9">
            <div id="lesson-content">


                <c:if test="${!empty lesson}">


                    <c:forEach var="m" items="${lesson.content}">
                        <h2><c:out value="${m.title}"/></h2>
                    </c:forEach>

                </c:if>
            </div>

        </div>
    </div>
</div>