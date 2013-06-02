<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<div id="content-update" class="modal hide fade">
    <spring:hasBindErrors name="learnContent">
        <!-- ERROR - TODO show error-->
    </spring:hasBindErrors>
    <c:url var="action" value="/content/edit"/>
    <form:form method="post" action="${action}" modelAttribute="learnContent"
               cssClass="form-horizontal">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">&times;</a>
            Edit Content
        </div>
        <div class="modal-body">


            <form:input path="name" cssClass="span5"
                        cssErrorClass="error"/>
            <form:errors path="name"
                         cssClass="error help-inline inline"
                         element="span"/>


            <div>Text:</div>
            <c:url value="/resources/ckeditor/" var="ckeditorpath"/>
            <c:set value="${content.text}" var="value"/>
            <ckeditor:editor basePath="${ckeditorpath}" editor="text" value="${value}"/>

        </div>
        <form:hidden path="id"/>
        <!-- TODO wir sterben hier nicht in schönheit -->
        <input name="lessonid" type="hidden" value="${lessonid}">
        <input name="courseid" type="hidden" value="${courseid}">


        <div class="modal-footer">

            <button id="revert" class="btn">Drop changes</button>
            <button id="save" type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>


