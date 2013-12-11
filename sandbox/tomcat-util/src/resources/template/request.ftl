<@compress single_line=true>
<#macro fmt_mapOfLists map>
	<#assign out="">
	<#list map?keys as k>
		<#assign out = out + "\t\t" + k + "={" >
		<#list map[k] as v>
			<#assign out = out + v + ",">
		</#list>
		<#assign out = out?substring(0,out?length-1) + "},\n" >
	</#list>
<#if (out?length > 2)>${out?substring(0,out?length-2)}</#if>
</#macro>

<#macro fmt_cookies cookies>
<#assign out="">
<#list cookies as c>
	<#assign out = out + "\n\t\t" + c.name + "= {">
	<#assign out = out + "value=" + (c.value!) + ", ">
	<#if c.domain?has_content><#assign out = out + "domain=" + (c.domain!) + ", "></#if>
	<#if c.path?has_content><#assign out = out + "path=" + (c.path!) + ", "></#if>
	<#if c.maxAge?has_content><#assign out = out + "age=" + (c.maxAge!) + ", "></#if>
	<#if c.secure?has_content><#assign out = out + "sec=" + c.secure?string + ", "></#if>
	<#if c.version?has_content><#assign out = out + "ver=" + (c.version!) + "}, "></#if>
</#list>
<#if (out?length > 3)>${out?substring(1,out?length-2)}</#if>
</#macro>

<#macro sessionId request>
	<#assign out = "NONE">
	<#if (request.getSession(false).getId())?has_content><#assign out=request.getSession(false).getId()></#if>
${out}
</#macro>

<#assign sep="|">
uri=${request.requestURI!} ${sep}
stat=${response.status!} ${sep}
${(duration/1000)?string("0.##")}s ${sep}
request={
	${request.scheme!} ${sep}<#if request.queryString?has_content>
	qry=${request.queryString!} ${sep}</#if><#if request.req_sess?has_content>
	req_sess=${request.requestedSessionId!} ${sep}</#if><#if req_parms?has_content>
	req_parms={
<@fmt_mapOfLists req_parms/>
	} ${sep}</#if><#if req_headers?has_content>
	req_headers={
<@fmt_mapOfLists req_headers/>
	} ${sep}</#if><#if request.cookies?has_content>
	req_cookies={
<@fmt_cookies request.cookies/>
	} ${sep}</#if>
} ${sep}
response={<#if (response.contentLength > 0)>
	len=${response.contentLength!}${sep}</#if><#if resp_headers?has_content>
	resp_headers={
<@fmt_mapOfLists resp_headers/>
	} ${sep}</#if><#if response.cookies?has_content>
	resp_cookies={
<@fmt_cookies response.cookies/>
	}</#if>
} ${sep}
sid=<@sessionId request/>
</@compress>
