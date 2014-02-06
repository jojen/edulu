<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Content"%>
<%@ attribute name="link" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="edu" %>
<%@ tag trimDirectiveWhitespaces="true"%>

<c:if test="${c.type eq 'Image'}">
    <edu:image c="${c}" link="${link}" />

</c:if>
<c:if test="${c.type eq 'Video'}">
    <edu:video c="${c}" link="${link}" />
</c:if>
<c:if test="${c.type eq 'Text'}">
    <edu:text c="${c}" />

</c:if>
<c:if test="${c.type eq 'Download'}">
    <edu:download c="${c}" link="${link}" />
</c:if>
<c:if test="${c.type eq 'Container'}">

    <edu:container c="${c}" link="${link}" />
</c:if>
<c:if test="${c.type eq 'Quiz'}">
    <edu:quiz c="${c}" link="${link}" />
</c:if>