<div id="quiz-update" class="modal hide fade">

    <div class="modal-footer">
        <c:url var="action" value="/content/quiz/edit"/>
        <form:form id="form-framework" method="post" action="${action}" modelAttribute="quiz"
                   cssClass="form-horizontal">
            <form:hidden path="id"/>
            <!-- TODO wir sterben hier nicht in schönheit -->
            <input name="lessonid" type="hidden" value="${lessonid}">
            <input name="courseid" type="hidden" value="${courseid}">

        </form:form>

        <button id="revert" class="btn">Drop changes</button>
        <button id="save" type="submit" class="btn btn-primary">Save</button>
    </div>

</div>