<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>
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

    </c:if>
</div>

