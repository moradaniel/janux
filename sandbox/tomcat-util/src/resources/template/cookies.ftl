{
<#assign out="">
<#list cookies as c>
	<#assign out = out + "\n\t\t" + c.name + "= {">
	<#assign out = out + "value=" + (c.value!) + ", ">
	<#assign out = out + "domain=" + (c.domain!) + ", ">
	<#assign out = out + "path=" + (c.path!) + ", ">
	<#assign out = out + "age=" + (c.maxAge!) + ", ">
	<#assign out = out + "sec=" + c.secure?string + ", ">
	<#assign out = out + "ver=" + (c.version!) + "}, ">
</#list>
<#if (out?length > 3)>${out?substring(1,out?length-2)}</#if>
	}<#rt>
