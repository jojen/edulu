<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="body">


        <div class="row">

            <c:if test="${!empty page}">
                <div class="span3 btn-group btn-group-vertical">

                        <%--c:forEach items="${page.content}" var="course">
                            <a class="btn btn-large" href="#course-${course.id}"></i>${course.name}</a>

                        </c:forEach--%>

                </div>
                <div class="span7">
                    <div class="row">
                        <c:forEach items="${page.content}" var="course" varStatus="status">
                            <div id="course-${course.id}" class="course light-box mini-layout box-shadow">
                                <div class="row-fluid">
                                    <div class="span1">
                                        <!-- TODO Image -->
                                    </div>
                                    <div class="span11">
                                        <a class="right more-margin btn btn-primary"
                                           href="<c:url value="/course/${course.id}" />">Start
                                            Course</a>

                                        <h3 class="update" data-id="${course.id}"
                                            data-key="title">${course.name}</h3>

                                        <p><c:out value="${course.description}" escapeXml="false"/></p>

                                        <ul>
                                            <li><span>${fn:length(course.lessons)}</span> Lessons</li>
                                            <li> -</li>
                                            <li>
                                                <a href="<c:url value="/course/pdf/${course.id}/course-${course.id}.pdf"/>">Download
                                                    as PDF</a></li>

                                        </ul>
                                        <sec:authorize access="hasRole('ROLE_TEACHER')">
                                            <div class="btn-group right">

                                                <button data-action="<c:url value="/course/delete/${course.id}"/>"
                                                        data-delete="#course-${course.id}" class="btn btn-danger">Delete
                                                </button>

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
                    <c:if test="${page.totalPages > 1}">
                        <div class="pagination">
                            <ul>
                                <c:set var="link" value="?page=${page.number-1}" />
                                <li <c:if test="${page.firstPage}">class="disabled"<c:set var="link" value="#" /></c:if>>

                                    <a href="${link}">&laquo;</a>

                                </li>


                                <c:forEach begin="1" end="${page.totalPages}" varStatus="index">
                                    <li     <c:if test="${index.count == page.number+1}">
                                        class="active"
                                    </c:if>
                                            ><a href="?page=${index.count -1}">${index.count}</a></li>

                                </c:forEach>
                                <c:set var="link" value="?page=${page.number+1}" />
                                <li <c:if test="${page.lastPage}">class="disabled"<c:set var="link" value="#" /></c:if>>
                                    <a href="${link}"> &raquo </a>

                                </li>

                            </ul>

                        </div>
                    </c:if>
                </div>

            </c:if>

        </div>


    </c:param>
</c:import>

