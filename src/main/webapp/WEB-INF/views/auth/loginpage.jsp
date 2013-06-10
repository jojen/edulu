<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="Login"/>
    <c:param name="body">

        <div class="well span3">
            <h1>Login</h1>
            <c:if test="${!empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form action="<c:url value="/j_spring_security_check" />" method="post">
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
                <input class="btn btn-primary" type="submit" value="Login"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                    class="btn" l href="<c:url value="/auth/registerpage" />">Create Account</a>
            </form>


        </div>
    </c:param>
</c:import>
