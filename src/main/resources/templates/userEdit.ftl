<#-- загрузка кода из файла -->
<#import "parts/common.ftl" as c>

<@c.page>
<span><a href="/main">На главную</a></span>
<div>
    </br><span>Редактирование пользователей</span>
</div>
<form action="/user" method="post">
    </br><input type="text" name="username" value="${user.username}">
    </br>
    <#list roles as role>
    <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    </br><button type="submit">Сохранить</button>
</form>
</@c.page>
