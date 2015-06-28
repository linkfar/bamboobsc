<#assign hrWidth=percentage?number >
<#if ( hrWidth > 100 ) >
	<#assign hrWidth=100 >
</#if>
<#if ( hrWidth < 0 ) >
	<#assign hrWidth=0 >
</#if>
<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="${backgroundColor}">
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<img src="./images/go-next.png" border="0" alt="next" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');"/>
		<font color="${fontColor}" size="4"><b>${objective.name}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Target: ${objective.target}</b></font>
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Min: ${objective.min}</b></font>
		</td>
	</tr>		
	<tr valign="top">
		<td width="100%" align="left" bgcolor="${backgroundColor}" onclick="query_kpiByObjective('${uploadOid}', '${objective.oid}');">
		<font color="${fontColor}" size="2"><b>Weight: ${objective.weight} %</b></font>
		</td>
	</tr>			
	<tr>
		<td width="100%" align="left" bgcolor="${objective.bgColor}">
			<font color="${objective.fontColor}" size="3"><b>Score: ${objective.score?string(',###.##')}</b></font>
			<BR/>
			<font color="${objective.fontColor}" size="3"><b>Percentage: ${percentage} %</b></font>			
			<BR/>
			<hr align="left" width="${hrWidth}%" size="15" color="${objective.fontColor}">
		</td>
	</tr>	
	<tr valign="top">
		<td width="100%" align="left" bgcolor="#ffffff">
		description:<BR/>
		${objective.description}
		</td>
	</tr>
</table>
