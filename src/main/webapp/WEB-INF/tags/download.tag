<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Download"%>
<%@ attribute name="link" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true"%>


<a href="${link}">
    <button class="btn"><i class="icon-download"></i>&nbsp;${c.name}</button>
</a>