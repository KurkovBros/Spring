<#import "parts/common.ftl" as c> <!-- Код этой страницы будет вставлен в указанный шаблон -->

<@c.page>
<span><a href="/main">На главную</a></span>
<div>
    </br><span>Редактирование пользователей</span>
</div>
<form action="/user" method="post">
    </br><input type="text" name="username" value="${user.username}"> <!-- поле для ввода пользователя, которого хотим изменить -->
    </br>
    <#list roles as role> <!-- представление списка ролей выбранного пользователя -->
    <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label> <!-- флажки для ролей пользователя -->
    </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf"> <#-- передача токена при всех запросах (Spring Security) -->
    </br><button type="submit">Сохранить</button> <#-- создание кнопки с заданной надписью -->
</form>
</@c.page>
