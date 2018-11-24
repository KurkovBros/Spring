<#-- загрузка кода из файла -->
<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout />
</div>
</br><span><a href="/user">Список пользователей</a></span>
</br>
<div>
    <form method="post" enctype="multipart/form-data"> <#-- кодирование данных при работе с файлами -->
        <input type="text" name="lastName" placeholder="Введите фамилию" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="firstName" placeholder="Введите имя" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="email" placeholder="Введите email" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="phone" placeholder="Введите телефон" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="file" name="file"> <#-- поле для загрузки файлов -->
        <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
        <button type="submit">Добавить</button> <#-- создание кнопки с заданной надписью -->
    </form>
</div>
</br><div>Список контактов</div></br>
<form method="get" action="/main">
    <input type="text" name="filter" value="${filter?ifExists}"> <#-- если нет фильтра -->
    <button type="submit">Найти</button> <#-- создание кнопки с заданной надписью -->
</form>
<table>
    <thead>
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>email</th>
        <th>телефонный номер</th>
        <th></th>
    <tbody>
    <#list contacts as contact> <#-- представляем список контактов -->
    <tr>
        <td>${contact.lastName}</td>
        <td>${contact.firstName}</td>
        <td>${contact.email}</td>
        <td>${contact.phone}</td>
        <td><a href="/contact/${contact.id}">Изменить контакт</a></td>
    </tr>
    </#list>
    </tbody>
</table>


</@c.page>
