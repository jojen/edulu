<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Container" %>
<%@ attribute name="link" required="false" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="edu" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:set var="ceditclass" value=""/>
<sec:authorize access="hasRole('ROLE_TEACHER')">
    <c:set var="ceditclass" value="container-edit"/>
</sec:authorize>
<div class="content-container">
    <c:set var="item" value="${c.firstContent}"/>
    <c:url var="itemlink" value="/content/media/${item.id}/${item.name}"/>
    <div data-cid="${course.id}" data-lid="${lesson.id}" data-id="${c.id}" data-place="1"
         class="${ceditclass} span6 <c:if test="${!empty ceditclass && empty item}">droppable </c:if>">
        <c:if test="${!empty item}">
            <edu:content c="${item}" link="${itemlink}"/>

            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <c:url value="/course/${course.id}/lesson/${lesson.id}/move/out/${c.id}/1" var="detatchLink"/>
                <span class="span12">
                   <a href="${detatchLink}" class="btn btn-info pull-right">Detach</a>
                </span>
            </sec:authorize>
        </c:if>

    </div>

    <c:set var="item2" value="${c.secondContent}" />
    <c:url var="item2link" value="/content/media/${item2.id}/${item2.name}"/>
    <div data-cid="${course.id}" data-lid="${lesson.id}" data-id="${c.id}" data-place="2" class="${ceditclass} span6 <c:if test="${!empty ceditclass && empty item2}">droppable </c:if>">
        <c:if test="${!empty item2}">
            <edu:content c="${item2}" link="${item2link}" />

            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <c:url value="/course/${course.id}/lesson/${lesson.id}/move/out/${c.id}/2" var="detatchLink" />
                <span class="span12">
                   <a href="${detatchLink}" class="btn btn-info pull-right">Detach</a>
                </span>
            </sec:authorize>
        </c:if>

    </div>


</div>