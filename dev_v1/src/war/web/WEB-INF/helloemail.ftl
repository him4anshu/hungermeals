<img src="${url}"/><br/>
Hi ${recipientName},<br/>
Welcome to Infiniti Research: ${message} <br/><br/>
 
=======================<br/>
===  Branches WorldWide   ====<br/>
=======================<br/>
<#list countries as country>
    ${country_index + 1}. ${country}<br/>
</#list><br/>

Visit us on <a href="${website} ">Infiniti Research</a>