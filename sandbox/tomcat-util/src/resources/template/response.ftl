<@compress single_line=true>
<#assign sep="|">
response={
	stat=${response.statusCode!} - ${response.readableStatus!}${sep}
	err=${response.errorCode!}${sep}
	len=${response.contentLength!}${sep}
	type=${response.contentType!}${sep}
	headers={
<#assign out = "">
<#list response.headerNames as n>
	<#assign out = out + "\t\t" + n + "={" >
	<#list response.getHeaders(n) as v>
		<#assign out = out + v + ",">
	</#list>
	<#assign out = out?substring(0,out?length-1) + "},\n" >
</#list>
<#if (out?length > 2)>${out?substring(0,out?length-2)}</#if>
	}${sep}
	cookies=${cookies}
}
</@compress>
