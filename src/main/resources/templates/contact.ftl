<#import "parts/common.ftl" as c> <!-- Код этой страницы будет вставлен в указанный шаблон -->

<@c.page>
<span><a href="/main">На главную</a></span> <!-- Ссылка на главную страницу -->
<div>
    </br><span>Контакт, выбранный для редактирования</span>
</div>
<form action="/contact" method="post">
    <table> <!-- Создание таблицы -->
        </br><thead> <!-- Перечень заголовков таблицы -->
    <tr>
        <th>Фамилия
        <th>Имя</th>
        <th>E-mail</th>
        <th>Телефонный номер</th>
    </tr>
    </thead>
    <tbody> <!-- Заполенние таблицы -->
    <tr>
        <td><input type="text" name="lastName" value="${contact.lastName}"/></td> <!-- Отображение фамилии -->
        <td><input type="text" name="firstName" value="${contact.firstName}"/></td> <!-- Отображение имени -->
        <td><input type="text" name="email" value="${contact.email}"/></td> <!-- Отображение email -->
        <td><input type="text" name="phone" value="${contact.phone}"/></td> <!-- Отображение телефона -->
        <input type="hidden" value="${contact.id}" name="contactId"> <!-- скрытое поле с id контакта -->
        <input type="hidden" value="${_csrf.token}" name="_csrf"> <#-- передача токена при всех запросах (Spring Security) -->
        </br><button type="submit">Сохранить</button> <#-- создание кнопки с заданной надписью -->
    </tr>
    </tbody>
    </table>
</form>
</@c.page>
