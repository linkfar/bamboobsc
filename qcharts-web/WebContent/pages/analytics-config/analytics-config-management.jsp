<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>bambooCORE</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bambooCORE">
	<meta http-equiv="description" content="bambooCORE">
	
<style type="text/css">

</style>

<script type="text/javascript">

function QCHARTS_PROG001D0003Q_GridFieldStructure() {
	return [
			{ name: "${action.getText('QCHARTS_PROG001D0003Q_grid_01')}", field: "oid", formatter: QCHARTS_PROG001D0003Q_GridButtonClick, width: "15%" },  
			{ name: "${action.getText('QCHARTS_PROG001D0003Q_grid_02')}", field: "id", width: "20%" },
			{ name: "${action.getText('QCHARTS_PROG001D0003Q_grid_03')}", field: "name", width: "25%" },
			{ name: "${action.getText('QCHARTS_PROG001D0003Q_grid_04')}", field: "jdbcUrl", width: "40%" }
		];
}

function QCHARTS_PROG001D0003Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"QCHARTS_PROG001D0003Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"QCHARTS_PROG001D0003Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

function QCHARTS_PROG001D0003Q_clear() {
	dijit.byId('QCHARTS_PROG001D0003Q_id').set('value', '');
	dijit.byId('QCHARTS_PROG001D0003Q_name').set('value', '');
	clearQuery_${programId}_grid();
}

function QCHARTS_PROG001D0003Q_edit(oid) {
	QCHARTS_PROG001D0003E_TabShow(oid);
}

function QCHARTS_PROG001D0003Q_confirmDelete(oid) {
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('QCHARTS_PROG001D0003Q_confirmDelete')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/qcharts.analyticsConfigDeleteAction.action', 
						{ 'fields.oid' : oid }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
							getQueryGrid_${programId}_grid();
						}, 
						function(error) {
							alert(error);
						}
				);	
			}, 
			(window.event ? window.event : null) 
	);			
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="flat" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="Y"
		createNewJsMethod="QCHARTS_PROG001D0003A_TabShow();"		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>	
	
	<table border="0" width="100%" height="50px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="25px" width="10%"  align="right"><s:property value="getText('QCHARTS_PROG001D0003Q_id')"/>:</td>
    		<td height="25px" width="40%"  align="left">
    			<gs:textBox name="QCHARTS_PROG001D0003Q_id" id="QCHARTS_PROG001D0003Q_id" value="" width="200" maxlength="20"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0003Q_id'">
    				Input Id.
				</div>       			
    		</td>
    		<td height="25px" width="10%"  align="right"><s:property value="getText('QCHARTS_PROG001D0003Q_name')"/>:</td>
    		<td height="25px" width="40%"  align="left">
    			<gs:textBox name="QCHARTS_PROG001D0003Q_name" id="QCHARTS_PROG001D0003Q_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'QCHARTS_PROG001D0003Q_name'">
    				Input name.
				</div>      			
    		</td>
    	</tr>	
    	<tr>
    		<td  height="25px" width="100%"  align="center" colspan="4">
    			<gs:button name="QCHARTS_PROG001D0003Q_query" id="QCHARTS_PROG001D0003Q_query" onClick="getQueryGrid_${programId}_grid();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/qcharts.analyticsConfigManagementGridQueryAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'searchValue.parameter.id'			: dijit.byId('QCHARTS_PROG001D0003Q_id').get('value'), 
    						'searchValue.parameter.name'		: dijit.byId('QCHARTS_PROG001D0003Q_name').get('value'),
    						'pageOf.size'						: getGridQueryPageOfSize_${programId}_grid(),
    						'pageOf.select'						: getGridQueryPageOfSelect_${programId}_grid(),
    						'pageOf.showRow'					: getGridQueryPageOfShowRow_${programId}_grid()
    					} 
    				"
    				errorFn="clearQuery_${programId}_grid();"
    				loadFn="dataGrid_${programId}_grid(data);" 
    				programId="${programId}"
    				label="${action.getText('QCHARTS_PROG001D0003Q_query')}" 
    				iconClass="dijitIconSearch"
    				cssClass="alt-primary"></gs:button>
    			<gs:button name="QCHARTS_PROG001D0003Q_clear" id="QCHARTS_PROG001D0003Q_clear" onClick="QCHARTS_PROG001D0003Q_clear();" 
    				label="${action.getText('QCHARTS_PROG001D0003Q_clear')}" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>
    		</td>
    	</tr> 	
	</table>
	
	<gs:grid gridFieldStructure="QCHARTS_PROG001D0003Q_GridFieldStructure()" clearQueryFn="" id="_${programId}_grid" programId="${programId}"></gs:grid>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	