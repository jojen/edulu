<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="HOME" />
    <c:param name="body">

        <div class="container-fluid">
            <div class="row-fluid">

                <c:if test="${!empty page}">
                    <div class="span3 bs-docs-sidebar">
                        <ul class="nav nav-list bs-docs-sidenav affix-top">
                            <c:forEach items="${page.content}" var="course">
                                <li class="active"><a href="#${course.id}"><i class="icon-chevron-right"></i>${course.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="span7">
                        <div class="row">
                            <c:forEach items="${page.content}" var="course" varStatus="status">
                                <div class="course light-box mini-layout box-shadow">
                                    <div class="row-fluid">
                                        <div class="span2">
                                            <img src="http://static.learnstreet.com/commons/static/images/icons/icon_javascript_medium.png?20130521">
                                        </div>
                                        <div class="span10">
                                            <a class="right more-margin btn btn-primary" href="/course/${course.id}">Start
                                                Course</a>

                                            <h3 class="update" data-id="${course.id}" data-key="title">${course.name}</h3>
                                            <h4>Level: Beginner</h4>


                                            <p><c:out value="${course.description}" escapeXml="false"/></p>

                                            <ul>
                                                <li><span>7</span> Lessons</li>
                                                <li>|</li>
                                                <li><span>56</span> Exercises</li>
                                            </ul>
                                            <div class="btn-group right">
                                                <c:if test="${course.hasDraft}">
                                                    <a data-id="${course.id}" class="btn btn-danger">Revert</a>
                                                </c:if>
                                                <a data-id="${course.id}" class="update-course btn btn-warning">Edit</a>
                                                <c:if test="${course.hasDraft}">
                                                    <a data-id="${course.id}" class="btn btn-success">Publish</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <div class="light-box span2 box-shadow centred update-course btn" id="add-course">
                                +
                            </div>
                        </div>

                    </div>

                </c:if>

            </div>
        </div>

    </c:param>
</c:import>

