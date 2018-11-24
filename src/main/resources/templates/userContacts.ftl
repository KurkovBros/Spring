<#import "parts/common.ftl" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/contactEdit.ftl" />
    </#if>

    <#include "parts/contactList.ftl" />
</@c.page>
