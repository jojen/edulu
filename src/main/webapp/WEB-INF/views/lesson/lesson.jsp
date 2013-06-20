<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>
<c:if test="${!empty lesson}">
    <ul id="lesson-content" data-id="${lesson.id}" class="sortable">


        <c:forEach var="c" items="${lesson.content}">
            <c:url var="link" value="/content/media/${c.id}/${c.name}"/>
            <li class="ui-state-default">
                    <%-- TODO da sollten eigene templates her --%>
                <c:if test="${c.type eq 'Image'}">
                    <img src="${link}" class="img-polaroid">
                </c:if>
                <c:if test="${c.type eq 'Video'}">
                    <video id="${c.id}" width="770" height="480" class="video-js vjs-default-skin" controls
                           preload="auto"
                           data-setup='{"example_option":true}'>
                        <source src="${link}"
                                type="${c.contentType}">
                        Your browser does not support the video tag.
                    </video>
                </c:if>
                <c:if test="${c.type eq 'Text'}">
                    <div>
                        <c:if test="${!empty c.name}">
                            <h2><c:out value="${c.name}"/></h2>
                        </c:if>

                        <c:out value="${c.text}" escapeXml="false"/>
                    </div>

                </c:if>
                <c:if test="${c.type eq 'Download'}">
                    <a href="${link}">
                        <button class="btn"><i class="icon-download"></i>&nbsp;${c.name}</button>
                    </a>
                </c:if>
                <c:if test="${c.type eq 'Quiz'}">
                    <!-- TODO sollte in class übergehen -->
                    <div id="slick-quiz">
                        <h3 class="quizName"></h3>

                        <div class="quizArea">
                            <div class="quizHeader">
                                <!-- where the quiz main copy goes -->

                                <button class="btn btn-primary startQuiz" href="#"><i
                                        class="icon-play icon-white pull-right"></i>Get Started&nbsp;</button>
                            </div>

                            <!-- where the quiz gets built -->
                        </div>

                        <div class="quizResults">
                            <h4 class="quizScore">You Scored: <span><!-- where the quiz score goes --></span></h4>

                            <h4 class="quizLevel"><strong>Ranking:</strong> <span><!-- where the quiz ranking level goes --></span>
                            </h4>

                            <div class="quizResultsCopy">
                                <!-- where the quiz result copy goes -->
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        $('#slick-quiz').slickQuiz({json:
                            ${c.quizContent}
                        });
                    </script>

                </c:if>
                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <div class="btn-group right">

                        <a data-id="${c.id}" class="btn btn-danger">Revert</a>
                        <c:if test="${c.isEditable}">
                            <a data-id="${c.id}" data-courseid="${course.id}"
                               data-lessonid="${lesson.id}" class="update-${c.type} btn btn-warning">Edit</a>
                        </c:if>

                        <a data-id="${c.id}" class="btn btn-success">Publish</a>

                    </div>
                </sec:authorize>

            </li>
        </c:forEach>
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
                                    <li><a href="#" class="update-Quiz" data-courseid="${course.id}"
                                           data-lessonid="${lesson.id}">Quiz</a></li>
                                    <li class="disabled"><a href="#">Survey</a></li>
                                    <li class="disabled"><a href="#">Hot Potato</a></li>
                                    <li class="divider"></li>
                                    <li class="disabled"><a href="#">Test</a></li>
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

