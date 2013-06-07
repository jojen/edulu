<div id="quiz-update" class="modal hide fade">


    <div class="modal-header">
        <a href="#" class="close" data-dismiss="modal">&times;</a>
        Edit Quiz
    </div>
    <div class="modal-body">
        <c:url var="action" value="/content/quiz/edit"/>
        <form:form id="quiz-form" method="post" action="${action}" modelAttribute="quiz"
                   cssClass="form-horizontal">
            <form:input path="name" cssClass="span5"
                        cssErrorClass="error"/>


            <form:hidden path="id"/>
            <!-- TODO wir sterben hier nicht in schönheit -->
            <input name="lessonid" type="hidden" value="${lessonid}">
            <input name="courseid" type="hidden" value="${courseid}">

        </form:form>
    </div>

    <div class="modal-footer">
        <button id="revert" class="btn">Drop changes</button>
        <button id="save" type="submit" class="btn btn-primary">Save</button>
    </div>

</div>