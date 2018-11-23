<#-- загрузка кода из файла -->
<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout /></br>
    <span><a href="/user">Список пользователей</a></span>
</div>
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
<#list contacts as contact> <#-- представляем список контактов -->
<div>
    <b>${contact.id}</b> <#-- выделение текста жирным -->
    <span>${contact.text}</span> <#-- строка с возможностью настройки стиля -->
    <i>${contact.tag}</i> <#-- выделение текста курсивом -->
    <strong>${contact.authorName}</strong> <#-- выделение текста жирным -->
    <div>
        <#if contact.filename??> <#-- если к контакту приложен файл; ?? - приведение к булеву типу -->
            <img src="/img/${contact.filename}"> <#-- отображение картинки, которая загружена вместе с контактом -->
        </#if>
    </div>
</div>
<#else>
</br>У этого пользователя ещё нет ни одного контакта
</#list>
</@c.page>
