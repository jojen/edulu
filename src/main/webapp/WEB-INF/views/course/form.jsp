<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<div id="course-update" class="modal hide fade">
    <spring:hasBindErrors name="course">
        <!-- ERROR - TODO show error-->
    </spring:hasBindErrors>
    <c:url var="action" value="/course/edit"/>
    <form:form method="post" action="${action}" modelAttribute="course"
               cssClass="form-horizontal">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">&times;</a>
            Edit Course
        </div>
        <div class="modal-body">


            <form:input path="name" cssClass="span5"
                        cssErrorClass="error"/>
            <form:errors path="name"
                         cssClass="error help-inline inline"
                         element="span"/>


            <div>Description:</div>
            <c:url value="/resources/ckeditor/" var="ckeditorpath"/>
            <c:set value="${course.description}" var="value"/>
            <ckeditor:editor basePath="${ckeditorpath}" editor="description" value="${value}"/>

        </div>
        <form:hidden path="id"/>
        <div class="modal-footer">

            <button id="revert" class="btn">Drop changes</button>
            <button id="save" type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>


