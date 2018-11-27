<#import "parts/common.ftl" as c> <!-- Код этой страницы будет вставлен в указанный шаблон -->
<#import "parts/login.ftl" as l> <!-- Код этой страницы будет вставлен в указанный шаблон -->

<@c.page>
Пожалуйста войдите или зарегистрируйтесь
<@l.login "/login" />
<div>
    </br><a href="/registration">Регистрация</a>
</div>
</@c.page>
