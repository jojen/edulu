<jsp:useBean id="course" class="org.jojen.wikistudy.entity.Course" scope="request"/>
<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>

<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="Course"/>
    <c:param name="body">


        <div class="container-fluid">
            <div class="row-fluid">
                <div id="lessons-menu" class="span3">
                    <h3><c:out value="${course.name}"/></h3>

                        <div class="span12" style="margin-left: 0">
                        <div data-type="lesson" data-id="${course.id}" class="btn-group-vertical<sec:authorize access="hasRole('ROLE_TEACHER')"> sortable</sec:authorize>">

                            <c:forEach var="l" items="${course.lessons}" varStatus="status">

                                <a href="<c:url value="/course/${course.id}/lesson/${l.id}"/>"
                                   style="text-align: left;"
                                   class="btn <c:if test="${l.id eq lesson.id}">active</c:if>"><c:out
                                        value="${status.count}"/><c:if test="${!empty l.name}"> -</c:if>&nbsp;<span
                                        id="lesson-name-${l.id}">${l.name}</span></a>

                            </c:forEach>
                        </div>
                        </div>
                        <div class="btn-group">
                            <sec:authorize access="hasRole('ROLE_TEACHER')">
                                <a style="margin-left: 6px; margin-top: 4px" href="<c:url value="/course/${course.id}/add-lesson" />" class="btn"><i
                                        class="icon-plus"></i></a>
                            </sec:authorize>
                        </div>





                </div>
                <div class="span9">
                    <c:import url="/WEB-INF/views/lesson/lesson.jsp" charEncoding="UTF-8"/>
                </div>
            </div>
        </div>

    </c:param>
</c:import>

