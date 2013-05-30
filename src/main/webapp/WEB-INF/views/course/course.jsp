<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Course" scope="request"/>


<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <h3><c:out value="${self.title}"/></h3>

            <div class="btn-group">
                <c:forEach var="lesson" items="${self.lessons}" varStatus="status">
                    <button class="btn"><c:out value="${status.count}"/></button>
                </c:forEach>
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
                <c:if test="${!empty self.lessons}">
                    <c:forEach var="l" items="${self.lessons}">

                        <c:forEach var="m" items="${l.content}">
                            <c:out value="${m.title}"/>
                        </c:forEach>

                    </c:forEach>

                </c:if>
            </div>

        </div>
    </div>
</div>