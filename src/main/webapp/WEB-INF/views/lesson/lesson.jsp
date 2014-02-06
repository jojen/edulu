<%@ taglib tagdir="/WEB-INF/tags" prefix="edu" %>
<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>
<c:if test="${!empty lesson}">


<ul class="main-content" id="lesson-content">

<!-- Headline -->
<sec:authorize access="!hasRole('ROLE_TEACHER')">
    <c:if test="${!empty lesson.name}"><h1><c:out value="${lesson.name}"/></h1></c:if>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_TEACHER')">
    <div class="span12">
        <c:url var="action" value="/course/lesson/rename/${lesson.id}"/>
        <form:form modelAttribute="lesson"
                   cssClass="form-horizontal span10">
            <form:input id="lesson-name-input" path="name" cssClass="edit-btn-large noEnterSubmit"
                        maxlength="50"
                        cssErrorClass="error"/>
        </form:form>
        <button data-action="${action}" data-id="${lesson.id}" id="update-lesson-name" class="btn btn-large"
                >Update
        </button>
    </div>
    <div class="span12 edit-box row">
        <div class="btn-group pull-right">

            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <button data-action="<c:url value="/course/${course.id}/lesson/delete/${lesson.id}" />"
                        data-delete="#lesson-content" class="btn btn-danger">Delete Lesson
                </button>
            </sec:authorize>


            <a href="<c:url value="/course/${course.id}/lesson/${lesson.id}/copy" />"
               class="btn btn-info">Copy</a>

            <a id="move-lesson" class="btn btn-warning">Move to</a>
        </div>

    </div>
    <div class="span12">
        <div id="lesson-settings" style="display: none; min-height: 0; margin-top: 0;"
             class="row span8 content-settings pull-right">
            <c:forEach items="${courses.content}" var="item">
                <c:if test="${item.id != course.id}">
                    <div class="span12 row" style="margin-bottom: 4px">
                        <div class="span9">
                            <c:out value="${item.name}"/>
                        </div>
                        <div class="span3">
                            <a href="<c:url value="/course/move/lesson/${course.id}/${item.id}/${lesson.id}" />">
                                <button class="btn btn-warning pull-right">Move</button>
                            </a>

                        </div>
                    </div>
                </c:if>
            </c:forEach>

        </div>
    </div>


</sec:authorize>

<!-- Content -->
<sec:authorize access="!hasRole('ROLE_TEACHER')">
<div>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_TEACHER')">
    <div data-id="${lesson.id}" data-type="content" class="sortable connectedSortable">
        </sec:authorize>


        <c:forEach var="c" items="${lesson.content}" varStatus="index">
            <c:url var="link" value="/content/media/${c.id}/${c.name}"/>
            <li id="content-${c.id}" class="ui-state-default content-item" data-id="${c.id}" >
                <div class="row">
                    <edu:content c="${c}" link="${link}" />


                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <div class="edit-box btn-group right">

                            <button data-delete="#content-${c.id}"
                                    data-action="<c:url value="/content/delete/${lesson.id}/${c.id}" />"
                                    class="btn btn-danger">Delete
                            </button>


                            <c:if test="${c.isEditable}">
                                <a data-id="${c.id}" data-courseid="${course.id}"
                                   data-lessonid="${lesson.id}"
                                   class="update-${c.type} btn btn-warning">Edit</a>
                            </c:if>
                        </div>

                    </sec:authorize>
                </div>
            </li>
        </c:forEach>

    </div>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
    <li class="row-fluid">
        <div class="well">
            <div class="row-fluid">
                <div class="span6">
                    <button data-courseid="${course.id}" data-lessonid="${lesson.id}"
                            class="btn btn-large update-Text">
                        <i class="icon-align-left"></i>
                        Add Text
                    </button>
                </div>
                <div class="span6">
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-large dropdown-toggle">
                            <i class="icon-th-large"></i>
                            Add Module&nbsp;<span
                                class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li <c:if test="${lesson.hasQuiz}">class="disabled"</c:if>><a href="#"
                                                                                          class="update-Quiz"
                                                                                          data-courseid="${course.id}"
                                                                                          data-lessonid="${lesson.id}"><i class="icon-edit"></i>&nbsp;Quiz</a>
                            </li>
                            <li ><a href="<c:url value="/course/${course.id}/lesson/${lesson.id}/add/container" />"><i class="icon-pause"></i>&nbsp;Container</a></li>
                            <!--li class="disabled"><a href="#">Hot Potato</a></li>
                            <li class="divider"></li>
                            <li class="disabled"><a href="#">Test</a></li-->
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row-fluid">
                <div class="span12 centred">


                    <div id="dropzone" class="fade well">
                            <span class="btn fileinput-button">
                            <i class="icon-plus"></i>
                            <span>Upload Media</span>
                            <input id="fileupload" data-url="<c:url value="/content/fileupload" />?id=${lesson.id}"
                                   name="fileData" type="file"
                                    />
                       </span>

                    </div>
                    <div id="progress" class="progress progress-striped active">
                        <div class="bar"></div>
                    </div>
                </div>
            </div>


        </div>
    </li>
    </sec:authorize>

</ul>
</c:if>

