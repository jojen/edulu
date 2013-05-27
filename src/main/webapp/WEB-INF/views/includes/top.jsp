<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>


<div class="navbar-inner">
    <div class="container-fluid">
        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="brand" href="#">Wiki Study</a>
        <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
                <c:choose>
                    <c:when test="${empty user}">
                        <a href="<c:url value="/auth/login" />">Login</a>
                        <a href="<c:url value="/auth/registerpage" />">Register</a>
                    </c:when>
                    <c:otherwise>
                        Logged in as <a class="navbar-link" href="<c:url value="/user" />">${user.name}</a>
                        <a href="<c:url value="/auth/logout" />">Logout</a>
                    </c:otherwise>
                </c:choose>
            </p>
            <ul class="nav">
                <li class="active"><a href="#">Courses</a></li>
                <li><a href="#about">About</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
