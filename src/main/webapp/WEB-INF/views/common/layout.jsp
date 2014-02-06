<!DOCTYPE html>
<html>
<head>
    <title>edulu <c:if test="${!empty param.title}">- ${param.title}</c:if></title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-responsive.min.css'/>">
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery.fileupload-ui.css'/>">
    </sec:authorize>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/video-js.css'/>">


    <!-- lib -->
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-2.0.0.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>

    <!-- Quiz -->
    <script type="text/javascript" src="<c:url value='/resources/js/slickQuiz.js' />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/edulu.css'/>">

</head>
<body>
<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container-fluid">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="brand"><a href="<c:url value="/" />">edulu</a></span>

            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    <sec:authorize access="hasRole('ROLE_USER')">
                        Logged in as

                            <sec:authentication scope="session" var="user" property="principal.username"/>
                            <c:out value="${user}"/>&nbsp;

                        <a href="<c:url value="/auth/logout" />">Logout</a>
                    </sec:authorize>
                    <c:choose>
                        <c:when test="${empty user}">

                            <a href="<c:url value="/auth/login" />?nextUrl=<c:out value="${encodedUrl}"/>">Sign Up</a>
                        </c:when>

                    </c:choose>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">

                        <a style="margin-left: 10px;" href="<c:url value="/admin/settings" />" title="Settings" >
                            <i class="icon-cog icon-white"></i>
                        </a>
                    </sec:authorize>
                </p>
                <ul class="nav">
                    <!--li><a href="<c:url value="/" />">Courses</a></li-->

                    <!--li><a href="<c:url  value="/static/about"/>">About</a></li-->
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>


<div id="maincontainer" class="container">${param.body}</div>

<footer id="footer">
    <p>
        Designed and build for educational usage
    </p>

</footer>

<div id="modal-from-dom" class="modal hide fade">
    <div class="modal-header">
        <a href="javascript:$('#modal-from-dom').modal('hide')" class="close">&times;</a>
        <h3>Confirm</h3>
    </div>
    <div class="modal-body">
        <p>This procedure is irreversible.<br>
            Do you want to proceed?</p>
    </div>
    <div class="modal-footer">

        <a href="javascript:$('#modal-from-dom').modal('hide')" class="btn">No</a>
        <button id="modal-yes" class="btn btn-danger">Yes</button>
    </div>
</div>


<!-- video -->
<script type="text/javascript" src="<c:url value='/resources/js/video.js' />"></script>
<script>
    videojs.options.flash.swf = "<c:url value='/resources/etc/video-js.swf' />"
</script>


<sec:authorize access="hasRole('ROLE_TEACHER')">
    <!-- File Upload -->
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-ui-1.10.3.custom.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery.fileupload.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery.iframe-transport.js' />"></script>

    <!-- Quiz -->
    <script type="text/javascript" src="<c:url value="/resources/js/jsonform/underscore.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jsonform/jsv.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jsonform/jsonform.js" />"></script>

    <script type="text/javascript" src="<c:url value='/resources/js/edulu-edit.js' />"></script>
</sec:authorize>
<script type="text/javascript" src="<c:url value='/resources/js/edulu.js' />"></script>

</body>
</html>