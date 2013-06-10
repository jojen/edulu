<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="HOME"/>
    <c:param name="body">


        <h1>Login</h1>

        <div class="error">${error}</div>

        <form action="/j_spring_security_check" method="post">
            <p>
                <label for="j_username">Email:</label>
                <input id="j_username" name="j_username" type="text"
                       value="${not empty param.login_error ? sessionScope['SPRING_SECURITY_LAST_USERNAME'] : '' }"/>
            </p>

            <p>
                <label for="j_password">Password:</label>
                <input id="j_password" name="j_password" type="password"/>
            </p>

            <p>
                <input type="checkbox" name="_spring_security_remember_me"/> Remember me
            </p>
            <input type="submit" value="Login"/>
        </form>
        <br/>
        <a href="<c:url value="/auth/registerpage" />">Register</a>

    </c:param>
</c:import>
