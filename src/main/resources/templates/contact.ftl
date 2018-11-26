<#import "parts/common.ftl" as c>

<@c.page>
<span><a href="/main">На главную</a></span>
<div>
    </br><span>Контакт, выбранный для редактирования</span>
</div>
<form action="/contact" method="post">
    <table>
        </br><thead>
    <tr>
        <th>Фамилия
        <th>Имя</th>
        <th>E-mail</th>
        <th>Телефонный номер</th>
    </tr>
    </thead>
        <tbody>
        <tr>
            <td><input type="text" name="lastName" value="${contact.lastName}"/></td>
            <td><input type="text" name="firstName" value="${contact.firstName}"/></td>
            <td><input type="text" name="email" value="${contact.email}"/></td>
            <td><input type="text" name="phone" value="${contact.phone}"/></td>
            <input type="hidden" value="${contact.id}" name="contactId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            </br><button type="submit">Сохранить</button>
        </tr>
        </tbody>
    </table>
</form>
</@c.page>
