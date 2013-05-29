<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="self" class="org.jojen.wikistudy.domain.Course" scope="request"/>


<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <h3><c:out value="${self.title}"/></h3>

            <div class="progress">
                <div class="bar" style="width: 10%;"></div>
            </div>
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#">Content</a>
                </li>

                <li><a href="#">Glossary</a></li>
            </ul>
        </div>
        <div class="span9">
            <!--Body content-->
        </div>
    </div>
</div>