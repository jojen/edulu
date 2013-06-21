<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<div id="text-update" class="modal hide fade">
    <c:url var="action" value="/content/text/edit"/>
    <form:form id="form-framework" method="post" action="${action}" modelAttribute="text"
               cssClass="form-horizontal">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">&times;</a>
            Edit Text
        </div>
        <div class="modal-body">


            <form:input path="name" cssClass="span5"
                        cssErrorClass="error"/>


            <div>Text:</div>
            <c:url value="/resources/ckeditor/" var="ckeditorpath"/>
            <c:set value="${text.text}" var="value"/>
            <ckeditor:editor basePath="${ckeditorpath}" editor="text" value="${value}"/>

        </div>


        <form:hidden path="id"/>
        <!-- TODO wir sterben hier nicht in schönheit -->
        <input name="lessonid" type="hidden" value="${lessonid}">
        <input name="courseid" type="hidden" value="${courseid}">
    </form:form>


    <div class="modal-footer">

        <a href="javascript:$('#text-update').modal('hide')" class="btn">Drop changes</a>
        <button id="save" type="submit" class="btn btn-primary">Save</button>
    </div>


</div>


