<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
Пожалуйста войдите или зарегистрируйтесь
<@l.login "/login" />
<div>
    </br><a href="/registration">Регистрация</a>
</div>
</@c.page>
