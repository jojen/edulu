<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Image" %>
<%@ attribute name="link" required="false" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<img src="${link}" class="img-polaroid">
<sec:authorize access="hasRole('ROLE_TEACHER')">
    <div class="row content-settings" id="update-${c.id}-section">
        <div class="span6">Show in PDF</div>
        <div class="span6">
            <div class="slideCheck">
                <input class="content-property" data-id="${c.id}" type="checkbox"
                       <c:if test="${c.showPdf}">checked="checked"</c:if> id="show-pdf-${c.id}"
                       data-name="showPdf"/>
                <label for="show-pdf-${c.id}"></label>
            </div>
        </div>
    </div>
</sec:authorize>





