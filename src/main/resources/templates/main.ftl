<#import "parts/common.ftl" as c> <!-- Код этой страницы будет вставлен в указанный шаблон -->
<#import "parts/login.ftl" as l> <!-- Код этой страницы будет вставлен в указанный шаблон -->

<@c.page>

<div>
    <@l.logout /> <#-- кнопка выхода -->
</div>
</br><span><a href="/user">Список пользователей</a></span>
<div>
    <form method="post" enctype="multipart/form-data"> <#-- кодирование данных при работе с файлами -->
        </br><input type="text" name="lastName" placeholder="Введите фамилию" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="firstName" placeholder="Введите имя" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="email" placeholder="Введите email" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="text" name="phone" placeholder="Введите телефон" /> <#-- поле для ввода сообщения со значением по умолчанию -->
        <input type="file" name="file"> <#-- поле для загрузки файлов -->
        <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- передача токена при всех запросах (Spring Security) -->
        <button type="submit">Добавить</button> <#-- создание кнопки с заданной надписью -->
    </form>
</div>
</br>
<div>
    Список контактов
</div></br>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}"> <#-- поле для ввода фильтра с проверкой -->
        <button type="submit">Найти</button> <#-- создание кнопки с заданной надписью -->
    </form>
<table> <#-- создание таблицы -->
    </br><thead> <#-- заголовки таблицы -->
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>E-mail</th>
        <th>Телефонный номер</th>
        <th>Редактирование</th>
        <th>Фото</th>
    <tbody> <#-- данные таблицы -->
    <#list contacts as contact> <#-- представляем список контактов -->
    <tr>
        <td>${contact.lastName}</td>
        <td>${contact.firstName}</td>
        <td>${contact.email}</td>
        <td>${contact.phone}</td>
        <td><a href="/contact/${contact.id}">Изменить контакт</a></td> <#-- ссылка на страницу редактирования -->
        <td><#if contact.filename??> <#-- если есть фото -->
            <img src="/img/${contact.filename}"> <#-- отображаем его -->
        </#if></td>
    </tr>
    </#list>
    </tbody>
</table>

</@c.page>
