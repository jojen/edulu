<jsp:useBean id="course" class="org.jojen.wikistudy.entity.Course" scope="request"/>
<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>

<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="HOME"/>
    <c:param name="body">


        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span3">
                    <h3><c:out value="${course.name}"/></h3>

                    <div class="btn-group">
                        <c:forEach var="l" items="${course.lessons}" varStatus="status">

                            <a href="<c:url value="/course/${course.id}/lesson/${l.id}"/>"
                               class="btn <c:if test="${l.id eq lesson.id}">active</c:if>"><c:out
                                    value="${status.count}"/></a>
                        </c:forEach>
                        <a href="<c:url value="/course/${course.id}/add-lesson" />" class="btn">+</a>
                    </div>

                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#">Content</a>
                        </li>

                        <li><a href="#">Glossary</a></li>
                    </ul>
                </div>
                <div class="span9">
                    <div id="lesson-content">


                        <c:if test="${!empty lesson}">


                            <c:forEach var="m" items="${lesson.learnContents}">
                                <h2><c:out value="${m.name}"/></h2>
                                <c:if test="${!empty m.text}">
                                    <p><c:out escapeXml="false" value="${m.text}"/></p>
                                </c:if>
                            </c:forEach>
                            <button data-courseid="${course.id}" data-lessonid="${lesson.id}"
                                    class="btn update-content">new text module
                            </button>
                            <br/>
                            or upload / drop content
                        </c:if>
                    </div>

                </div>
            </div>
        </div>

    </c:param>
</c:import>

