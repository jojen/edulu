<%@page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>Wiki Study</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-responsive.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/wikistudy.css'/>">

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="brand">Wiki Study</span>

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
                    <li class="active"><a href="<c:url value="/" />">Courses</a></li>
                    <li><a href="<c:url  value="/static/about"/>">About</a></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>


<div class="container">${param.body}</div>

<div id="footer">
    <p>
        Designed and build by for educational usage
    </p>

    <p>
        &copy; JoJen 2013. All rights reserved.
    </p>
</div>

</body>
</html>