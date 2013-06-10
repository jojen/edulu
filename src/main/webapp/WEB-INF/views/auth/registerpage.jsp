<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="Register"/>
    <c:param name="body">
        <h1>Register</h1>

        <div class="error">${error}</div>

        <form action="<c:url var="/auth/register" />" method="post">
            <p>
                <label for="j_username">Email:</label>
                <input id="j_username" name="j_username" type="text" value="${j_username}"/>
            </p>

            <p>
                <label for="j_password">Password:</label>
                <input id="j_password" name="j_password" type="password"/>
            </p>
            <input type="submit" value="Register"/>
        </form>
        <br/>
        <a href="<c:url value="/auth/login" />">Login</a>
    </c:param>
</c:import>
