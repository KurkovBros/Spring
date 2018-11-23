<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
Please login or registry
<@l.login "/login" />
<div>
    <a href="/registration">Registration</a>
</div>
</@c.page>
