<%@ attribute name="self" required="true" type="org.jojen.wikistudy.entity.Content"%>
<%@ attribute name="view" required="false" type="java.lang.String"%>
<%@ tag trimDirectiveWhitespaces="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty view}">
    <c:set var="view" value="render" />
</c:if>

