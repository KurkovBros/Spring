<#import "parts/common.ftl" as c> <!-- Код этой страницы будет вставлен в указанный шаблон -->
<#import "parts/login.ftl" as l> <!-- Код этой страницы будет вставлен в указанный шаблон -->

<@c.page>
Добавить нового пользователя
${message?ifExists} <#-- если нет сообщений -->
<@l.login "/registration" />
</@c.page>
