<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Video" %>
<%@ attribute name="link" required="false" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<div class="video-container">
    <video id="${c.id}" width="100%" height="100%" class="video-js vjs-default-skin" controls
           preload="preload"
           data-setup="{}">
        <source src="${link}"
                type="${c.contentType}">
        Your browser does not support the video tag.
    </video>
</div>