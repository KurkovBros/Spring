<#macro login path>
<form action="${path}" method="post">
    </br><div><label> Имя пользователя : <input type="text" name="username"/> </label></div> <#-- добавление поля для логина -->
    </br><div><label> Пароль: <input type="password" name="password"/> </label></div> <#-- добавление поля для пароля -->
    <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
    </br><div><input type="submit" value="Sign In"/></div> <#-- создание кнопки "войти" -->
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
    <input type="submit" value="Sign Out"/> <#-- создание кнопки "выйти" -->
</form>
</#macro>
