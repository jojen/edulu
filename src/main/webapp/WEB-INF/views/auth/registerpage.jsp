<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="Register"/>
    <c:param name="body">
        <div class="well span3">
            <h1>Register</h1>

            <c:if test="${!empty error}">
                <div class="error">${error}</div>
            </c:if>

            <form action="<c:url value="/auth/register" />" method="post">
                <p>
                    <label for="j_username">Email:</label>
                    <input id="j_username" name="j_username" type="text" value="${j_username}"/>
                </p>

                <p>
                    <label for="j_password">Password:</label>
                    <input id="j_password" name="j_password" type="password"/>
                </p>
                <input class="btn btn-primary" type="submit" value="Register"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                    class="btn" href="<c:url value="/auth/login" />">Login</a>
            </form>

        </div>


    </c:param>
</c:import>
