<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<jsp:useBean id="lesson" class="org.jojen.wikistudy.entity.Lesson" scope="request"/>
<div id="lesson-content">
    <c:if test="${!empty lesson}">
        <div class="container">
            <c:forEach var="c" items="${lesson.content}">
                <c:url var="link" value="/content/media/${c.id}/${c.name}"/>
                <div class="row-fluid">
                        <%-- TODO da solllten eigene templates her --%>
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
                    <c:if test="${c.type eq 'LearnContent'}">
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
                        <div id="slickQuiz">


                            <c:if test="${!empty c.name}">
                                <h3 class="quizName"><c:out value="${c.name}"/></h3>
                            </c:if>


                            <div class="quizArea">
                                <div class="quizHeader">
                                    <!-- where the quiz main copy goes -->

                                    <a class="button startQuiz" href="#">Get Started!</a>
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
                    </c:if>
                </div>
            </c:forEach>
            <div class="row-fluid">
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
                                <button data-toggle="dropdown" class="btn btn-large dropdown-toggle">
                                    <i class="icon-th"></i>
                                    Add Module&nbsp;<span
                                        class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" class="update-quiz" data-courseid="${course.id}"
                                           data-lessonid="${lesson.id}">Quiz</a></li>
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
                            <input id="fileupload" data-url="<c:url value="/content/fileupload" />?id=${lesson.id}"
                                   name="fileData" type="file"
                                    />
                       </span>

                            </div>
                            <div id="progress" class="progress progress-striped">
                                <div class="bar"></div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

        </div>


    </c:if>
</div>

