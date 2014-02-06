<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Text"%>
<%@ attribute name="link" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true"%>

<div>
    <c:if test="${!empty c.name}">
        <h2><c:out value="${c.name}"/></h2>
    </c:if>

    <c:out value="${c.text}" escapeXml="false"/>
</div>