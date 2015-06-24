<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}">
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="4"><b>${kpi.name}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Target: ${kpi.target}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Min: ${kpi.min}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}">
		<font color="${fontColor}" size="2"><b>Weight: ${kpi.weight} %</b></font>
		</td>
	</tr>			
	<tr>
		<td width="100%" align="left" bgcolor="${kpi.bgColor}">
			<font color="${kpi.fontColor}" size="3"><b>Score: ${kpi.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${kpi.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${kpi.fontColor}">
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="#ffffff">
		description:<BR/>
		${kpi.description}
		</td>
	</tr>
</table>
