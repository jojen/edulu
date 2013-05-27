<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
	    <title>Cineasts.net - <decorator:title default="Welcome" /></title>
	    <decorator:head />
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-responsive.min.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/wikistudy.css'/>">
	</head>
	<body>
		<div id="header">
			<div id="header-topbar">
			    <div id="header-menu">

					<%@ include file="/WEB-INF/views/includes/top.jsp" %>
				</div>
			</div>

		</div>
	    <div id="content">
            <decorator:body />
	    </div>
	    <%@ include file="/WEB-INF/views/includes/footer.jsp" %>
	</body>
    <script src="<c:url value='/resources/js/jquery-2.0.0.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
</html>