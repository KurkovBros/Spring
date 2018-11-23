<#macro login path>
<form action="${path}" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div> <#-- добавление поля для логина -->
    <div><label> Password: <input type="password" name="password"/> </label></div> <#-- добавление поля для пароля -->
    <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
    <div><input type="submit" value="Sign In"/></div> <#-- создание кнопки "войти" -->
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
    <input type="submit" value="Sign Out"/> <#-- создание кнопки "выйти" -->
</form>
</#macro>
