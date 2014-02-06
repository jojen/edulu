<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Video"%>
<%@ attribute name="link" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true"%>


<video id="${c.id}" width="770" height="480" class="video-js vjs-default-skin" controls
       preload="auto"
       data-setup='{"example_option":true}'>
    <source src="${link}"
            type="${c.contentType}">
    Your browser does not support the video tag.
</video>