<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html>
<head>
    <title>Wiki Study - <decorator:title default="Welcome" /></title>
    <decorator:head />
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-responsive.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/wikistudy.css'/>">

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
            <%@ include file="/WEB-INF/views/includes/top.jsp" %>
</div>
<div id="container">
    <decorator:body />
</div>
<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
</body>
<script src="<c:url value='/resources/js/jquery-2.0.0.min.js' />"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script src="<c:url value='/resources/js/wikistudy.js' />"></script>
</html>