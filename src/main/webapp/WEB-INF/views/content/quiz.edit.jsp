<div id="quiz-update" class="modal hide fade">
    <div class="modal-header">
        <a href="#" class="close" data-dismiss="modal">&times;</a>
        Edit Quiz
    </div>
    <div class="modal-body">
        <c:url var="action" value="/content/quiz/edit"/>
        <c:set var="quizcontent"/>
        <form:form id="quiz-form" method="post" action="${action}" modelAttribute="quiz"
                   cssClass="form-horizontal">
            <c:set var="quizcontent" value="${quiz.quizContent}"/>

            <form:hidden path="id"/>
            <!-- wir sterben hier nicht in schönheit -->
            <input name="lessonid" type="hidden" value="${lessonid}">
            <input name="courseid" type="hidden" value="${courseid}">

        </form:form>
        <form id="quiz-json-form" method="post" action="${action}">

        </form>
    </div>

    <div class="modal-footer">
        <button id="revert" class="btn">Drop changes</button>
        <button id="save" type="submit" class="btn btn-primary">Save</button>
    </div>

</div>

<script type="text/javascript">
    var quizcontent = null;
    $('#quiz-json-form').jsonForm({
        schema: {
            "questions": {
                "type": "array",
                "required": false,
                "items": {
                    "type": "object",
                    "required": false,
                    "properties": {
                        "q": {
                            "type": "string",
                            "description": "Question",
                            "required": true
                        },
                        "a": {
                            "type": "array",
                            "required": true,
                            "items": [
                                {
                                    "type": "object",
                                    "required": false,
                                    "properties": {
                                        "correct": {
                                            "type": "boolean",
                                            "required": false
                                        },
                                        "option": {
                                            "type": "string",
                                            "description": "Answer possibility",
                                            "required": false
                                        }
                                    }
                                }

                            ]
                        },
                        "correct": {
                            "type": "string",
                            "description": "Message for correct answer",
                            "required": true
                        },
                        "incorrect": {
                            "type": "string",
                            "description": "Message for wrong answer",
                            "required": true
                        }

                    }
                }



            }
        },
        <c:if test="${!empty quiz.quizContent}">
        "value":
        ${quiz.quizContent},
        </c:if>
        onSubmit: function (errors, values) {
            if (errors) {
                alert("error");
            }
            else {
                quizcontent = JSON.stringify(values);

            }
        }
    });
    function getQuizContent() {
        return quizcontent;
    }
</script>