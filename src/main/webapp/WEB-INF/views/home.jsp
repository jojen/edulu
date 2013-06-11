<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="HOME"/>
    <c:param name="body">

        <div class="container">
            <div class="row">

                <c:if test="${!empty page}">
                    <div class="span3 bs-docs-sidebar">
                        <ul class="nav nav-list bs-docs-sidenav affix-top">
                            <c:forEach items="${page.content}" var="course">
                                <li><a href="#course-${course.id}"><i
                                        class="icon-chevron-right"></i>${course.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="span7">
                        <div class="row">
                            <c:forEach items="${page.content}" var="course" varStatus="status">
                                <div id="course-${course.id}" class="course light-box mini-layout box-shadow">
                                    <div class="row-fluid">
                                        <div class="span2">
                                            <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">
                                        </div>
                                        <div class="span10">
                                            <a class="right more-margin btn btn-primary"
                                               href="<c:url value="/course/${course.id}" />">Start
                                                Course</a>

                                            <h3 class="update" data-id="${course.id}"
                                                data-key="title">${course.name}</h3>
                                            <h4>Level: Beginner</h4>


                                            <p><c:out value="${course.description}" escapeXml="false"/></p>

                                            <ul>
                                                <li><span>${fn:length(course.lessons)}</span> Lessons</li>

                                            </ul>
                                            <sec:authorize access="hasRole('ROLE_TEACHER')">
                                                <div class="btn-group right">
                                                    <c:if test="${course.hasDraft}">
                                                        <a data-id="${course.id}" class="btn btn-danger">Revert</a>
                                                    </c:if>
                                                    <a data-id="${course.id}"
                                                       class="update-course btn btn-warning">Edit</a>
                                                    <c:if test="${course.hasDraft}">
                                                        <a data-id="${course.id}" class="btn btn-success">Publish</a>
                                                    </c:if>
                                                </div>
                                            </sec:authorize>
                                        </div>
                                    </div>

                                </div>
                            </c:forEach>
                        </div>
                        <sec:authorize access="hasRole('ROLE_TEACHER')">
                            <div class="row">
                            <span class="span7" style="text-align: center">
                                  <button class="btn centred update-course btn-large" id="add-course">
                                      <i class="icon-plus"></i>
                                  </button>
                            </span>

                            </div>
                        </sec:authorize>

                    </div>

                </c:if>

            </div>
        </div>

    </c:param>
</c:import>

