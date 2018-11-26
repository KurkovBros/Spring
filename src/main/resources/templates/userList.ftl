<#import "parts/common.ftl" as c>

<@c.page>
<span><a href="/main">На главную</a></span>
<div>
    </br><span>Список пользователей</span>
</div>
<table>
    </br><thead>
    <tr>
        <th>Имя пользователя
        <th>Роли</th>
        <th>Редактирование</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">Изменить роли</a></td>
        </tr>
    </#list>
    </tbody>
</table>
</@c.page>
