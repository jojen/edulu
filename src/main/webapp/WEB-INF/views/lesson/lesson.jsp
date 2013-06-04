<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>
<div id="lesson-content">


    <c:if test="${!empty lesson}">


        <c:forEach var="c" items="${lesson.content}">
            <img src="${c.path}">
            <%--
            <h2><c:out value="${c.name}"/></h2>
            <c:if test="${!empty c.text}">
                <p><c:out escapeXml="false" value="${c.text}"/></p>
            </c:if>
            --%>
        </c:forEach>

        <div class="well span8">
            <div class="row-fluid">
                <div class="span6">
                    <button data-courseid="${course.id}" data-lessonid="${lesson.id}"
                            class="btn btn-large update-content">
                        <i class="icon-align-left"></i>
                        Add Text
                    </button>
                </div>
                <div class="span6">
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-large dropdown-toggle">Add Module<span
                                class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#">Quiz</a></li>
                            <li><a href="#">Survey</a></li>
                            <li><a href="#">Hot Potato</a></li>
                            <li class="divider"></li>
                            <li><a href="#">Test</a></li>
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
                            <input id="fileupload" data-url="<c:url value="/content/fileupload" />?id=${lesson.id}" name="fileData" type="file"
                                    />
                       </span>

                    </div>
                    <div id="progress" class="progress progress-striped">
                        <div class="bar"></div>
                    </div>
                </div>
            </div>


        </div>


    </c:if>
</div>

